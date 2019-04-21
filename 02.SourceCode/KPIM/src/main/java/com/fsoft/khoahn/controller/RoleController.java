package com.fsoft.khoahn.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.khoahn.common.exception.InvalidFileException;
import com.fsoft.khoahn.model.request.PaginationRequest;
import com.fsoft.khoahn.model.request.RoleCreateRequest;
import com.fsoft.khoahn.model.request.RoleDeleteRequest;
import com.fsoft.khoahn.model.request.RoleReadRequest;
import com.fsoft.khoahn.model.request.RoleUpdateRequest;
import com.fsoft.khoahn.model.request.dto.RoleCreateReqDto;
import com.fsoft.khoahn.model.request.dto.RoleDeleteReqDto;
import com.fsoft.khoahn.model.request.dto.RoleReadReqDto;
import com.fsoft.khoahn.model.request.dto.RoleUpdateReqDto;
import com.fsoft.khoahn.model.response.AuthorityDetailResponse;
import com.fsoft.khoahn.model.response.RoleCreateResponse;
import com.fsoft.khoahn.model.response.RoleDetailResponse;
import com.fsoft.khoahn.model.response.RoleReadResponse;
import com.fsoft.khoahn.model.response.RoleUpdateResponse;
import com.fsoft.khoahn.model.response.dto.AuthorityDetailResDto;
import com.fsoft.khoahn.model.response.dto.RoleDetailResDto;
import com.fsoft.khoahn.service.RoleService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/roles")
@Api(description = "Roles management API")
public class RoleController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/role-read", method = RequestMethod.POST)
	public ResponseEntity<?> getAll(@RequestBody RoleReadRequest roleReadRequest) {
		logger.debug("get roles list");
		RoleReadResponse response = new RoleReadResponse();
		PaginationRequest paginationRequest = roleReadRequest.getPaginationRequest();
		RoleReadReqDto roleReadReqDto = modelMapper.map(roleReadRequest, RoleReadReqDto.class);
		Page<RoleDetailResDto> roleReadResDtos = roleService.findAll(roleReadReqDto);

		Type typeRoles = new TypeToken<List<RoleDetailResponse>>() {
		}.getType();
		List<RoleDetailResponse> roleDetailResponses = modelMapper.map(roleReadResDtos.getContent(), typeRoles);
		Page<RoleDetailResponse> page = new PageImpl<>(roleDetailResponses,
				new PageRequest(paginationRequest.getPageNumber(), paginationRequest.getPageSize()),
				roleReadResDtos.getTotalElements());

		response.setRoles(page);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/role-create", method = RequestMethod.GET)
	public ResponseEntity<?> getRole() {
		logger.debug("get role");
		RoleCreateResponse response = new RoleCreateResponse();
		List<AuthorityDetailResDto> authorityDetailResDtos = roleService.getRoleAuthority();
		Type typeRoles = new TypeToken<List<AuthorityDetailResponse>>() {
		}.getType();

		List<AuthorityDetailResponse> roleReadResponses = modelMapper.map(authorityDetailResDtos, typeRoles);
		response.setItems(roleReadResponses);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/role-create", method = RequestMethod.POST)
	public ResponseEntity<?> createRole(@Validated @RequestBody RoleCreateRequest roleCreateRequest)
			throws InvalidFileException, IOException {
		logger.debug("save role");
		RoleCreateReqDto roleCreateReqDto = modelMapper.map(roleCreateRequest, RoleCreateReqDto.class);
		if (roleService.isExitRoleName(roleCreateRequest.getRoleName())) {
			String saveErrorMessage = messageSource.getMessage("is.exit.data",
					new String[] { "roleName", roleCreateRequest.getRoleName() }, Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(saveErrorMessage), HttpStatus.CONFLICT);
		}
		roleService.save(roleCreateReqDto);
		String saveSuccessMessage = messageSource.getMessage("create.success", new String[] { "role" },
				Locale.getDefault());
		return new ResponseEntity<>(Collections.singletonList(saveSuccessMessage), HttpStatus.OK);
	}

	@RequestMapping(value = "/role-update", method = RequestMethod.GET)
	public ResponseEntity<?> getRole(@RequestParam(value = "id") String id) {
		logger.debug("get role");
		RoleDetailResDto roleDetailResDto = roleService.findOne(id);
		if (roleDetailResDto == null) {
			String viewErrorMessage = messageSource.getMessage("data.not.found", new String[] {},
					Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(viewErrorMessage), HttpStatus.NOT_FOUND);
		}
		RoleUpdateResponse response = modelMapper.map(roleDetailResDto, RoleUpdateResponse.class);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/role-update", method = RequestMethod.POST)
	public ResponseEntity<?> updateRole(@Validated @RequestBody RoleUpdateRequest roleUpdateRequest)
			throws IOException {
		logger.debug("update role");
		String message = null;
		HttpStatus status = null;
		RoleDetailResDto roleDetailResDto = roleService.findOne(roleUpdateRequest.getId());
		if (roleDetailResDto == null) {
			message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
			status = HttpStatus.NOT_FOUND;
		} else {
			RoleUpdateReqDto roleUpdateReqDto = modelMapper.map(roleUpdateRequest, RoleUpdateReqDto.class);
			roleService.update(roleUpdateReqDto);
			message = messageSource.getMessage("update.success", new String[] { "role" }, Locale.getDefault());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}

	@RequestMapping(value = "/role-delete", method = RequestMethod.POST)
	public ResponseEntity<?> deleteRoles(@RequestBody RoleReadRequest roleReadRequest) throws IOException {
		logger.debug("delete roles");
		String message = null;
		HttpStatus status = null;
		List<RoleDeleteRequest> roleDeleteRequests = roleReadRequest.getRoleDeleteRequests();
		for (Iterator<RoleDeleteRequest> iterator = roleDeleteRequests.iterator(); iterator.hasNext();) {
			RoleDeleteRequest roleDeleteRequest = iterator.next();
			RoleDetailResDto roleDetailResDto = roleService.findOne(roleDeleteRequest.getId());
			if (roleDetailResDto == null) {
				message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
				status = HttpStatus.NOT_FOUND;
			} else {
				RoleDeleteReqDto roleDeleteReqDto = modelMapper.map(roleDetailResDto, RoleDeleteReqDto.class);
				roleService.delete(roleDeleteReqDto);
				message = messageSource.getMessage("delete.success", new String[] { "role" }, Locale.getDefault());
				status = HttpStatus.OK;
			}
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}
}
