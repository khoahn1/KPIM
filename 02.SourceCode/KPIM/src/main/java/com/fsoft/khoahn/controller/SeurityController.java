package com.fsoft.khoahn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.khoahn.dto.res.MasterDataResDto;
import com.fsoft.khoahn.dto.res.RoleDetailResDto;
import com.fsoft.khoahn.dto.res.TokenDetailResDto;
import com.fsoft.khoahn.dto.res.UserReadResDto;
import com.fsoft.khoahn.model.response.LoginResponse;
import com.fsoft.khoahn.security.SecurityUtils;
import com.fsoft.khoahn.service.MasterService;
import com.fsoft.khoahn.service.RoleService;
import com.fsoft.khoahn.service.TokenService;
import com.fsoft.khoahn.service.UserService;

import io.swagger.annotations.Api;

@RestController
@Api(description = "Users management API")
public class SeurityController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MasterService masterService;

	@Autowired
	private TokenService tokenService;

	@RequestMapping(value = "/security/account", method = RequestMethod.GET)
	public @ResponseBody LoginResponse getUserAccount() {
		LoginResponse loginResponse = new LoginResponse();
		List<RoleDetailResDto> roles = roleService.findAll();
		UserReadResDto user = userService.findByUsername(SecurityUtils.getCurrentLogin());
		MasterDataResDto masterDataResDto = masterService.getMasterData();
		user.setPassword(null);
		loginResponse.setRoles(roles);
		loginResponse.setUser(user);
		loginResponse.setMasterData(masterDataResDto);
		return loginResponse;
	}

	@PreAuthorize("hasAuthority('admin')")
	@RequestMapping(value = "/security/tokens", method = RequestMethod.GET)
	public @ResponseBody List<TokenDetailResDto> getTokens() {
		List<TokenDetailResDto> tokens = tokenService.findAll();
		for (TokenDetailResDto token : tokens) {
			token.setSeries(null);
			token.setValue(null);
		}
		return tokens;
	}

}
