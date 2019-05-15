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

import com.fsoft.khoahn.dto.req.CompanyCreateReqDto;
import com.fsoft.khoahn.dto.req.CompanyDeleteReqDto;
import com.fsoft.khoahn.dto.req.CompanyReadReqDto;
import com.fsoft.khoahn.dto.req.CompanyUpdateReqDto;
import com.fsoft.khoahn.dto.res.CompanyDetailResDto;
import com.fsoft.khoahn.model.request.CompanyCreateRequest;
import com.fsoft.khoahn.model.request.CompanyDeleteRequest;
import com.fsoft.khoahn.model.request.CompanyReadRequest;
import com.fsoft.khoahn.model.request.CompanyUpdateRequest;
import com.fsoft.khoahn.model.response.CompanyDetailResponse;
import com.fsoft.khoahn.model.response.CompanyReadResponse;
import com.fsoft.khoahn.model.response.CompanyUpdateResponse;
import com.fsoft.khoahn.service.CompanyService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/companies")
@Api(description = "Companys management API")
public class CompanyController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	private CompanyService companyService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/company-read", method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		List<CompanyDetailResDto> companyDetailResDtos = companyService.getAll();
		Type typeCompanies = new TypeToken<List<CompanyDetailResponse>>() {
		}.getType();
		List<CompanyDetailResponse> companyDetailResponses = modelMapper.map(companyDetailResDtos, typeCompanies);
		return new ResponseEntity<>(companyDetailResponses, HttpStatus.OK);
	}

	@RequestMapping(value = "/company-read", method = RequestMethod.POST)
	public ResponseEntity<?> findAll(@RequestBody CompanyReadRequest companyReadRequest) {
		logger.debug("get companies list");
		CompanyReadResponse response = new CompanyReadResponse();
		CompanyReadReqDto companyReadReqDto = modelMapper.map(companyReadRequest, CompanyReadReqDto.class);
		Page<CompanyDetailResDto> companyReadResDtos = companyService.findAll(companyReadReqDto);

		Type typeCompanies = new TypeToken<List<CompanyDetailResponse>>() {
		}.getType();
		List<CompanyDetailResponse> companyDetailResponses = modelMapper.map(companyReadResDtos.getContent(), typeCompanies);
		Page<CompanyDetailResponse> page = new PageImpl<>(companyDetailResponses, companyReadResDtos.getPageable(),
				companyReadResDtos.getTotalElements());

		response.setCompanies(page);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/company-create", method = RequestMethod.POST)
	public ResponseEntity<?> saveCompany(@Validated @RequestBody CompanyCreateRequest companyCreateRequest) {
		logger.debug("save Company");
		CompanyCreateReqDto companyCreateReqDto = modelMapper.map(companyCreateRequest, CompanyCreateReqDto.class);
		if (companyService.isExitCompanyCode(companyCreateRequest.getCompanyCode())) {
			String saveErrorMessage = messageSource.getMessage("is.exit.data",
					new String[] { "companyCode", companyCreateRequest.getCompanyCode() }, Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(saveErrorMessage), HttpStatus.CONFLICT);
		}
		companyService.save(companyCreateReqDto);
		String saveSuccessMessage = messageSource.getMessage("create.success", new String[] { "company" },
				Locale.getDefault());
		return new ResponseEntity<>(Collections.singletonList(saveSuccessMessage), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/company-update", method = RequestMethod.GET)
	public ResponseEntity<?> getCompany(@RequestParam(value = "id") String id) {
		logger.debug("get company");
		CompanyDetailResDto companyDetailResDto = companyService.findOne(id);
		if (companyDetailResDto == null) {
			String viewErrorMessage = messageSource.getMessage("data.not.found", new String[] {},
					Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(viewErrorMessage), HttpStatus.NOT_FOUND);
		}
		CompanyUpdateResponse response = modelMapper.map(companyDetailResDto, CompanyUpdateResponse.class);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/company-update", method = RequestMethod.POST)
	public ResponseEntity<?> updateCompany(@Validated @RequestBody CompanyUpdateRequest companyUpdateRequest)
			throws IOException {
		logger.debug("update company");
		String message = null;
		HttpStatus status = null;
		CompanyDetailResDto companyDetailResDto = companyService.findOne(companyUpdateRequest.getId());
		if (companyDetailResDto == null) {
			message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
			status = HttpStatus.NOT_FOUND;
		} else {
			CompanyUpdateReqDto companyUpdateReqDto = modelMapper.map(companyUpdateRequest, CompanyUpdateReqDto.class);
			companyService.update(companyUpdateReqDto);
			message = messageSource.getMessage("update.success", new String[] { "company" }, Locale.getDefault());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}
	
	@RequestMapping(value = "/company-delete", method = RequestMethod.POST)
	public ResponseEntity<?> deleteCompanies(@RequestBody CompanyReadRequest companyReadRequest) throws IOException {
		logger.debug("delete Companies");
		String message = null;
		HttpStatus status = null;
		List<CompanyDeleteRequest> companyDeleteRequests = companyReadRequest.getCompanyDeleteRequests();
		for (Iterator<CompanyDeleteRequest> iterator = companyDeleteRequests.iterator(); iterator.hasNext();) {
			CompanyDeleteRequest companyDeleteRequest = iterator.next();
			CompanyDetailResDto companyDetailResDto = companyService.findOne(companyDeleteRequest.getId());
			if (companyDetailResDto == null) {
				message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
				status = HttpStatus.NOT_FOUND;
			} else {
				CompanyDeleteReqDto companyDeleteReqDto = modelMapper.map(companyDetailResDto, CompanyDeleteReqDto.class);
				companyService.delete(companyDeleteReqDto);
				message = messageSource.getMessage("delete.success", new String[] { "company" }, Locale.getDefault());
				status = HttpStatus.OK;
			}
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}
}
