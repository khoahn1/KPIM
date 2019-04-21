package com.fsoft.khoahn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.khoahn.model.response.dto.TokenDetailResDto;
import com.fsoft.khoahn.model.response.dto.UserReadResDto;
import com.fsoft.khoahn.security.SecurityUtils;
import com.fsoft.khoahn.service.TokenService;
import com.fsoft.khoahn.service.UserService;

import io.swagger.annotations.Api;

@RestController
@Api(description = "Users management API")
public class SeurityController {

	@Autowired
	private UserService userRepo;

	@Autowired
	private TokenService tokenService;

	@RequestMapping(value = "/security/account", method = RequestMethod.GET)
	public @ResponseBody UserReadResDto getUserAccount() {
		UserReadResDto userReadResDto = userRepo.findByUsername(SecurityUtils.getCurrentLogin());
		userReadResDto.setPassword(null);
		return userReadResDto;
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
