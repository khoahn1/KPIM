package com.fsoft.khoahn.controller;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.khoahn.dto.res.AuthorityDetailResDto;
import com.fsoft.khoahn.model.response.AuthorityDetailResponse;
import com.fsoft.khoahn.model.response.PrivilegeReadResponse;
import com.fsoft.khoahn.service.PrivilegeService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/privileges")
@Api(description = "PrivilegeEntity management API")
public class PrivilegeReadController {

	private static final Logger logger = LoggerFactory.getLogger(PrivilegeReadController.class);

	@Autowired
	private PrivilegeService privilegeService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/privilege-read", method = RequestMethod.POST)
	public ResponseEntity<?> getAll() {
		logger.debug("get privilege list");
		PrivilegeReadResponse response = new PrivilegeReadResponse(); 
		List<AuthorityDetailResDto> authorityDetailResDto = privilegeService.findAll();
		
		Type typeItems = new TypeToken<List<AuthorityDetailResponse>>() {
		}.getType();

		List<AuthorityDetailResponse> authorityDetailResponses = modelMapper.map((authorityDetailResDto), typeItems);
		response.setPrivileges(authorityDetailResponses);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
