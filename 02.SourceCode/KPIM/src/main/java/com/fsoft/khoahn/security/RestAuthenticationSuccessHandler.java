package com.fsoft.khoahn.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fsoft.khoahn.dto.res.MasterDataResDto;
import com.fsoft.khoahn.dto.res.RoleDetailResDto;
import com.fsoft.khoahn.dto.res.UserReadResDto;
import com.fsoft.khoahn.model.response.LoginResponse;
import com.fsoft.khoahn.service.MasterService;
import com.fsoft.khoahn.service.RoleService;
import com.fsoft.khoahn.service.UserService;

/**
 * Spring Security success handler, specialized for Ajax requests.
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MasterService masterService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
		LoginResponse loginResponse = new LoginResponse();
		List<RoleDetailResDto> roles = roleService.findAll();
		UserReadResDto user = userService.findByUsername(authentication.getName());
		MasterDataResDto masterDataResDto = masterService.getMasterData();

		loginResponse.setRoles(roles);
		loginResponse.setUser(user);
		loginResponse.setMasterData(masterDataResDto);
		SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, loginResponse);
    }
}
