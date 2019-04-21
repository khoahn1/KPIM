package com.fsoft.khoahn.security;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.khoahn.model.response.dto.UserAuthorityReadResDto;
import com.fsoft.khoahn.model.response.dto.UserReadResDto;
import com.fsoft.khoahn.service.UserService;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) {
		log.debug("Authenticating {}", username);

		UserReadResDto userEntity = userService.findByUsername(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("UserEntity " + username + " was not found in the database");
		} else if (userEntity.getStatus() == null || userEntity.getStatus() != 1) {
			throw new UserNotEnabledException("UserEntity " + username + " was not enabled");
		}

		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority grantedAuthority;
		if (userEntity.getAuthorities() != null) {
			for (UserAuthorityReadResDto userAuthorityEntity : userEntity.getAuthorities()) {
				grantedAuthority = new SimpleGrantedAuthority(userAuthorityEntity.getOperation().getOpsName());
		        grantedAuthorities.add(grantedAuthority);
		    }
		}

		return new org.springframework.security.core.userdetails.User(username, userEntity.getPassword(), grantedAuthorities);
	}
}
