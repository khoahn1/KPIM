package com.fsoft.khoahn.security;


import static com.fsoft.khoahn.config.SecurityConfig.REMEMBER_ME_KEY;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.khoahn.model.request.dto.TokenCreateReqDto;
import com.fsoft.khoahn.model.response.dto.TokenDetailResDto;
import com.fsoft.khoahn.model.response.dto.UserReadResDto;
import com.fsoft.khoahn.service.TokenService;
import com.fsoft.khoahn.service.UserService;

/**
 * Custom implementation of Spring Security's RememberMeServices.
 * <p/>
 * Persistent tokens are used by Spring Security to automatically log in users.
 * <p/>
 * This is a specific implementation of Spring Security's remember-me authentication, but it is much
 * more powerful than the standard implementations:
 * <ul>
 * <li>It allows a user to see the list of his currently opened sessions, and invalidate them</li>
 * <li>It stores more information, such as the IP address and the user agent, for audit purposes<li>
 * <li>When a user logs out, only his current session is invalidated, and not all of his sessions</li>
 * </ul>
 * <p/>
 */
@Service
public class RememberMeServices extends
        AbstractRememberMeServices {

    private final Logger log = LoggerFactory.getLogger(RememberMeServices.class);

    // TokenEntity is valid for one month
    private static final int TOKEN_VALIDITY_DAYS = 31;

    private static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * TOKEN_VALIDITY_DAYS;

    private static final int DEFAULT_SERIES_LENGTH = 16;

    private static final int DEFAULT_TOKEN_LENGTH = 16;

    private SecureRandom random;

    @Autowired
	private TokenService tokenService;

    @Autowired
	private UserService userService;

    @Autowired
    public RememberMeServices(Environment env, org.springframework.security.core.userdetails.UserDetailsService userDetailsService) {
        super(REMEMBER_ME_KEY, userDetailsService);
        super.setParameter("rememberme");
        random = new SecureRandom();
    }

    @Override
    @Transactional
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {

		TokenDetailResDto tokenDetailResDto = getPersistentToken(cookieTokens);
		String login = tokenDetailResDto.getUserLogin();
		TokenCreateReqDto tokenCreateReqDto = new TokenCreateReqDto();
        // TokenEntity also matches, so login is valid. Update the token value, keeping the *same* series number.
		log.debug("Refreshing persistent login token for user '{}', series '{}'", login, tokenDetailResDto.getSeries());
		tokenCreateReqDto.setDate(new Date());
		tokenCreateReqDto.setValue(generateTokenData());
		tokenCreateReqDto.setIpAddress(request.getRemoteAddr());
		tokenCreateReqDto.setUserAgent(request.getHeader("User-Agent"));
        try {
			tokenService.save(tokenCreateReqDto);
			addCookie(tokenCreateReqDto, request, response);
        } catch (DataAccessException e) {
            log.error("Failed to update token: ", e);
            throw new RememberMeAuthenticationException("Autologin failed due to data access problem", e);
        }
        return getUserDetailsService().loadUserByUsername(login);
    }

    @Override
	protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication successfulAuthentication) {
        String login = successfulAuthentication.getName();

        log.debug("Creating new persistent login for user {}", login);
		UserReadResDto userReadResDto = userService.findByUsername(login);
		TokenCreateReqDto tokenEntity = new TokenCreateReqDto();
        tokenEntity.setSeries(generateSeriesData());
		tokenEntity.setUserLogin(userReadResDto.getUsername());
        tokenEntity.setValue(generateTokenData());
        tokenEntity.setDate(new Date());
        tokenEntity.setIpAddress(request.getRemoteAddr());
		tokenEntity.setUserAgent(request.getHeader("User-Agent"));
        try {
            tokenService.save(tokenEntity);
            addCookie(tokenEntity, request, response);
        } catch (DataAccessException e) {
            log.error("Failed to save persistent token ", e);
        }
    }

    /**
     * When logout occurs, only invalidate the current token, and not all user sessions.
     * <p/>
     * The standard Spring Security implementations are too basic: they invalidate all tokens for the
     * current user, so when he logs out from one browser, all his other sessions are destroyed.
     */
    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String rememberMeCookie = extractRememberMeCookie(request);
        if (rememberMeCookie != null && rememberMeCookie.length() != 0) {
            try {
                String[] cookieTokens = decodeCookie(rememberMeCookie);
				TokenDetailResDto tokenDetailResDto = getPersistentToken(cookieTokens);
                tokenService.delete(tokenDetailResDto.getSeries());
            } catch (InvalidCookieException ice) {
                log.info("Invalid cookie, no persistent token could be deleted");
            } catch (RememberMeAuthenticationException rmae) {
                log.debug("No persistent token found, so no token could be deleted");
            }
        }
        super.logout(request, response, authentication);
    }

    /**
     * Validate the token and return it.
     */
	private TokenDetailResDto getPersistentToken(String[] cookieTokens) {
        if (cookieTokens.length != 2) {
            throw new InvalidCookieException("Cookie token did not contain " + 2 +
                    " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
        }

        final String presentedSeries = cookieTokens[0];
        final String presentedToken = cookieTokens[1];

		TokenDetailResDto tokenDetailResDto = null;
        try {
            tokenDetailResDto = tokenService.findOne(presentedSeries);
        } catch (DataAccessException e) {
            log.error("Error to access database", e );
        }

        if (tokenDetailResDto == null) {
            // No series match, so we can't authenticate using this cookie
            throw new RememberMeAuthenticationException("No persistent token found for series id: " + presentedSeries);
        }

        // We have a match for this user/series combination
        log.info("presentedToken={} / tokenValue={}", presentedToken, tokenDetailResDto.getValue());
        if (!presentedToken.equals(tokenDetailResDto.getValue())) {
            // TokenEntity doesn't match series value. Delete this session and throw an exception.
            tokenService.delete(tokenDetailResDto.getSeries());
            throw new CookieTheftException("Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack.");
        }

        if (DateUtils.addDays(tokenDetailResDto.getDate(), TOKEN_VALIDITY_DAYS).before(new Date())) {
            tokenService.delete(tokenDetailResDto.getSeries());
            throw new RememberMeAuthenticationException("Remember-me login has expired");
        }
        return tokenDetailResDto;
    }

    private String generateSeriesData() {
        byte[] newSeries = new byte[DEFAULT_SERIES_LENGTH];
        random.nextBytes(newSeries);
        return new String(Base64.encode(newSeries));
    }

    private String generateTokenData() {
        byte[] newToken = new byte[DEFAULT_TOKEN_LENGTH];
        random.nextBytes(newToken);
        return new String(Base64.encode(newToken));
    }

	private void addCookie(TokenCreateReqDto tokenCreateReqDto, HttpServletRequest request,
			HttpServletResponse response) {
        setCookie(
				new String[] { tokenCreateReqDto.getSeries(), tokenCreateReqDto.getValue() },
                TOKEN_VALIDITY_SECONDS, request, response);
    }
}
