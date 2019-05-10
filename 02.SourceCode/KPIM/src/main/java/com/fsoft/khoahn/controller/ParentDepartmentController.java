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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.khoahn.common.exception.InvalidFileException;
import com.fsoft.khoahn.dto.req.ParentDepartmentCreateReqDto;
import com.fsoft.khoahn.dto.req.ParentDepartmentDeleteReqDto;
import com.fsoft.khoahn.dto.req.ParentDepartmentReadReqDto;
import com.fsoft.khoahn.dto.req.ParentDepartmentUpdateReqDto;
import com.fsoft.khoahn.dto.res.ParentDepartmentDetailResDto;
import com.fsoft.khoahn.model.request.ParentDepartmentCreateRequest;
import com.fsoft.khoahn.model.request.ParentDepartmentDeleteRequest;
import com.fsoft.khoahn.model.request.ParentDepartmentReadRequest;
import com.fsoft.khoahn.model.request.ParentDepartmentUpdateRequest;
import com.fsoft.khoahn.model.response.ParentDepartmentDetailResponse;
import com.fsoft.khoahn.model.response.ParentDepartmentReadResponse;
import com.fsoft.khoahn.model.response.ParentDepartmentUpdateResponse;
import com.fsoft.khoahn.service.ParentDepartmentService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/parent-departments")
@Api(description = "ParentDepartments management API")
public class ParentDepartmentController {

	private static final Logger logger = LoggerFactory.getLogger(ParentDepartmentController.class);

	@Autowired
	private ParentDepartmentService parentDepartmentService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/parent-department-read", method = RequestMethod.POST)
	public ResponseEntity<?> getAll(@RequestBody ParentDepartmentReadRequest parentDepartmentReadRequest) {
		logger.debug("get parentDepartment list");
		ParentDepartmentReadResponse response = new ParentDepartmentReadResponse();
		ParentDepartmentReadReqDto parentDepartmentReadReqDto = modelMapper.map(parentDepartmentReadRequest,
				ParentDepartmentReadReqDto.class);
		Page<ParentDepartmentDetailResDto> parentDepartmentReadResDtos = parentDepartmentService
				.findAll(parentDepartmentReadReqDto);

		Type typeParentDepartments = new TypeToken<List<ParentDepartmentDetailResponse>>() {
		}.getType();
		List<ParentDepartmentDetailResponse> parentDepartmentDetailResponses = modelMapper
				.map(parentDepartmentReadResDtos.getContent(), typeParentDepartments);
		Page<ParentDepartmentDetailResponse> page = new PageImpl<>(parentDepartmentDetailResponses,
				parentDepartmentReadResDtos.getPageable(),
				parentDepartmentReadResDtos.getTotalElements());

		response.setParentDepartments(page);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/parent-department-create", method = RequestMethod.POST)
	public ResponseEntity<?> createParentDepartment(
			@Validated @RequestBody ParentDepartmentCreateRequest parentDepartmentCreateRequest)
			throws InvalidFileException, IOException {
		logger.debug("save parentDepartment");
		ParentDepartmentCreateReqDto parentDepartmentCreateReqDto = modelMapper.map(parentDepartmentCreateRequest,
				ParentDepartmentCreateReqDto.class);
		String parentDepartmentCode = parentDepartmentCreateRequest.getParentDepartmentCode();
		if (parentDepartmentService.isExitParentDepartmentCode(parentDepartmentCode)) {
			String saveErrorMessage = messageSource.getMessage("is.exit.data",
					new String[] { "parentDepartmentCode", parentDepartmentCode },
					Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(saveErrorMessage), HttpStatus.CONFLICT);
		}
		parentDepartmentService.save(parentDepartmentCreateReqDto);
		String saveSuccessMessage = messageSource.getMessage("create.success", new String[] { "parentDepartment" },
				Locale.getDefault());
		return new ResponseEntity<>(Collections.singletonList(saveSuccessMessage), HttpStatus.OK);
	}

	@RequestMapping(value = "/parent-department-update", method = RequestMethod.GET)
	public ResponseEntity<?> getParentDepartment(@RequestParam(value = "id") String id) {
		logger.debug("get parentDepartment");
		ParentDepartmentUpdateResponse response = new ParentDepartmentUpdateResponse();
		ParentDepartmentDetailResDto parentDepartmentDetailResDto = parentDepartmentService.findOne(id);
		if (parentDepartmentDetailResDto == null) {
			String viewErrorMessage = messageSource.getMessage("data.not.found", new String[] {},
					Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(viewErrorMessage), HttpStatus.NOT_FOUND);
		}
		ParentDepartmentDetailResponse parentDepartment = modelMapper.map(parentDepartmentDetailResDto,
				ParentDepartmentDetailResponse.class);
		response.setParentDepartment(parentDepartment);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/parent-department-update", method = RequestMethod.POST)
	public ResponseEntity<?> updateParentDepartment(
			@Validated @RequestBody ParentDepartmentUpdateRequest parentDepartmentUpdateRequest)
			throws IOException {
		logger.debug("update parentDepartment");
		String message = null;
		HttpStatus status = null;
		ParentDepartmentDetailResDto parentDepartmentDetailResDto = parentDepartmentService
				.findOne(parentDepartmentUpdateRequest.getId());
		if (parentDepartmentDetailResDto == null) {
			message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
			status = HttpStatus.NOT_FOUND;
		} else {
			ParentDepartmentUpdateReqDto parentDepartmentUpdateReqDto = modelMapper.map(parentDepartmentUpdateRequest,
					ParentDepartmentUpdateReqDto.class);
			parentDepartmentService.update(parentDepartmentUpdateReqDto);
			message = messageSource.getMessage("update.success", new String[] { "parentDepartment" },
					Locale.getDefault());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}

	@RequestMapping(value = "/parent-department-delete", method = RequestMethod.POST)
	public ResponseEntity<?> deleteParentDepartments(
			@RequestBody ParentDepartmentReadRequest parentDepartmentReadRequest)
			throws IOException {
		logger.debug("delete parentDepartments");
		String message = null;
		HttpStatus status = null;
		List<ParentDepartmentDeleteRequest> parentDepartmentDeleteRequests = parentDepartmentReadRequest
				.getParentDepartmentDeleteRequests();
		for (Iterator<ParentDepartmentDeleteRequest> iterator = parentDepartmentDeleteRequests.iterator(); iterator
				.hasNext();) {
			ParentDepartmentDeleteRequest parentDepartmentDeleteRequest = iterator.next();
			ParentDepartmentDetailResDto parentDepartmentDetailResDto = parentDepartmentService
					.findOne(parentDepartmentDeleteRequest.getId());
			if (parentDepartmentDetailResDto == null) {
				message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
				status = HttpStatus.NOT_FOUND;
			} else {
				ParentDepartmentDeleteReqDto parentDepartmentDeleteReqDto = modelMapper
						.map(parentDepartmentDetailResDto, ParentDepartmentDeleteReqDto.class);
				parentDepartmentService.delete(parentDepartmentDeleteReqDto);
				message = messageSource.getMessage("delete.success", new String[] { "parentDepartment" },
						Locale.getDefault());
				status = HttpStatus.OK;
			}
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}
}
