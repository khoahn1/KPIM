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
import com.fsoft.khoahn.dto.req.DepartmentCreateReqDto;
import com.fsoft.khoahn.dto.req.DepartmentDeleteReqDto;
import com.fsoft.khoahn.dto.req.DepartmentReadReqDto;
import com.fsoft.khoahn.dto.req.DepartmentUpdateReqDto;
import com.fsoft.khoahn.dto.res.DepartmentDetailResDto;
import com.fsoft.khoahn.model.request.DepartmentCreateRequest;
import com.fsoft.khoahn.model.request.DepartmentDeleteRequest;
import com.fsoft.khoahn.model.request.DepartmentReadRequest;
import com.fsoft.khoahn.model.request.DepartmentUpdateRequest;
import com.fsoft.khoahn.model.response.DepartmentDetailResponse;
import com.fsoft.khoahn.model.response.DepartmentReadResponse;
import com.fsoft.khoahn.model.response.DepartmentUpdateResponse;
import com.fsoft.khoahn.service.DepartmentService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/departments")
@Api(description = "Departments management API")
public class DepartmentController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService departmentService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/department-read", method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		List<DepartmentDetailResDto> departmentDetailResDtos = departmentService.getAll();
		Type typeDepartments = new TypeToken<List<DepartmentDetailResponse>>() {
		}.getType();
		List<DepartmentDetailResponse> departmentDetailResponses = modelMapper.map(departmentDetailResDtos,
				typeDepartments);
		return new ResponseEntity<>(departmentDetailResponses, HttpStatus.OK);
	}

	@RequestMapping(value = "/department-read", method = RequestMethod.POST)
	public ResponseEntity<?> search(@RequestBody DepartmentReadRequest departmentReadRequest) {
		logger.debug("get department list");
		DepartmentReadResponse response = new DepartmentReadResponse();
		DepartmentReadReqDto departmentReadReqDto = modelMapper.map(departmentReadRequest, DepartmentReadReqDto.class);
		Page<DepartmentDetailResDto> departmentReadResDtos = departmentService.findAll(departmentReadReqDto);

		Type typeDepartments = new TypeToken<List<DepartmentDetailResponse>>() {
		}.getType();
		List<DepartmentDetailResponse> departmentDetailResponses = modelMapper.map(departmentReadResDtos.getContent(),
				typeDepartments);
		Page<DepartmentDetailResponse> page = new PageImpl<>(departmentDetailResponses,
				departmentReadResDtos.getPageable(),
				departmentReadResDtos.getTotalElements());

		response.setDepartments(page);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/department-create", method = RequestMethod.POST)
	public ResponseEntity<?> createDepartment(@Validated @RequestBody DepartmentCreateRequest departmentCreateRequest)
			throws InvalidFileException, IOException {
		logger.debug("save department");
		DepartmentCreateReqDto departmentCreateReqDto = modelMapper.map(departmentCreateRequest,
				DepartmentCreateReqDto.class);
		String departmentCode = departmentCreateRequest.getDepartmentCode();
		if (departmentService.isExitDepartmentCode(departmentCode)) {
			String saveErrorMessage = messageSource.getMessage("is.exit.data",
					new String[] { "departmentCode", departmentCode },
					Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(saveErrorMessage), HttpStatus.CONFLICT);
		}
		departmentService.save(departmentCreateReqDto);
		String saveSuccessMessage = messageSource.getMessage("create.success", new String[] { "department" },
				Locale.getDefault());
		return new ResponseEntity<>(Collections.singletonList(saveSuccessMessage), HttpStatus.OK);
	}

	@RequestMapping(value = "/department-update", method = RequestMethod.GET)
	public ResponseEntity<?> getDepartment(@RequestParam(value = "id") String id) {
		logger.debug("get department");
		DepartmentUpdateResponse response = new DepartmentUpdateResponse();
		DepartmentDetailResDto departmentDetailResDto = departmentService.findOne(id);
		if (departmentDetailResDto == null) {
			String viewErrorMessage = messageSource.getMessage("data.not.found", new String[] {},
					Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(viewErrorMessage), HttpStatus.NOT_FOUND);
		}
		DepartmentDetailResponse department = modelMapper.map(departmentDetailResDto, DepartmentDetailResponse.class);
		response.setDepartment(department);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/department-update", method = RequestMethod.POST)
	public ResponseEntity<?> updateDepartment(@Validated @RequestBody DepartmentUpdateRequest departmentUpdateRequest)
			throws IOException {
		logger.debug("update department");
		String message = null;
		HttpStatus status = null;
		DepartmentDetailResDto departmentDetailResDto = departmentService.findOne(departmentUpdateRequest.getId());
		if (departmentDetailResDto == null) {
			message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
			status = HttpStatus.NOT_FOUND;
		} else {
			DepartmentUpdateReqDto departmentUpdateReqDto = modelMapper.map(departmentUpdateRequest,
					DepartmentUpdateReqDto.class);
			departmentService.update(departmentUpdateReqDto);
			message = messageSource.getMessage("update.success", new String[] { "department" }, Locale.getDefault());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}

	@RequestMapping(value = "/department-delete", method = RequestMethod.POST)
	public ResponseEntity<?> deleteDepartments(@RequestBody DepartmentReadRequest departmentReadRequest)
			throws IOException {
		logger.debug("delete departments");
		String message = null;
		HttpStatus status = null;
		List<DepartmentDeleteRequest> departmentDeleteRequests = departmentReadRequest.getDepartmentDeleteRequests();
		for (Iterator<DepartmentDeleteRequest> iterator = departmentDeleteRequests.iterator(); iterator.hasNext();) {
			DepartmentDeleteRequest departmentDeleteRequest = iterator.next();
			DepartmentDetailResDto departmentDetailResDto = departmentService.findOne(departmentDeleteRequest.getId());
			if (departmentDetailResDto == null) {
				message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
				status = HttpStatus.NOT_FOUND;
			} else {
				DepartmentDeleteReqDto departmentDeleteReqDto = modelMapper.map(departmentDetailResDto,
						DepartmentDeleteReqDto.class);
				departmentService.delete(departmentDeleteReqDto);
				message = messageSource.getMessage("delete.success", new String[] { "department" },
						Locale.getDefault());
				status = HttpStatus.OK;
			}
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}
}
