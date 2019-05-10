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
import com.fsoft.khoahn.dto.req.UnitCreateReqDto;
import com.fsoft.khoahn.dto.req.UnitDeleteReqDto;
import com.fsoft.khoahn.dto.req.UnitReadReqDto;
import com.fsoft.khoahn.dto.req.UnitUpdateReqDto;
import com.fsoft.khoahn.dto.res.UnitDetailResDto;
import com.fsoft.khoahn.model.request.UnitCreateRequest;
import com.fsoft.khoahn.model.request.UnitDeleteRequest;
import com.fsoft.khoahn.model.request.UnitReadRequest;
import com.fsoft.khoahn.model.request.UnitUpdateRequest;
import com.fsoft.khoahn.model.response.UnitDetailResponse;
import com.fsoft.khoahn.model.response.UnitReadResponse;
import com.fsoft.khoahn.model.response.UnitUpdateResponse;
import com.fsoft.khoahn.service.UnitService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/units")
@Api(description = "Units management API")
public class UnitController {

	private static final Logger logger = LoggerFactory.getLogger(UnitController.class);

	@Autowired
	private UnitService unitService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/unit-read", method = RequestMethod.POST)
	public ResponseEntity<?> getAll(@RequestBody UnitReadRequest unitReadRequest) {
		logger.debug("get unit list");
		UnitReadResponse response = new UnitReadResponse();
		UnitReadReqDto unitReadReqDto = modelMapper.map(unitReadRequest, UnitReadReqDto.class);
		Page<UnitDetailResDto> unitReadResDtos = unitService.findAll(unitReadReqDto);

		Type typeUnits = new TypeToken<List<UnitDetailResponse>>() {
		}.getType();
		List<UnitDetailResponse> unitDetailResponses = modelMapper.map(unitReadResDtos.getContent(), typeUnits);
		Page<UnitDetailResponse> page = new PageImpl<>(unitDetailResponses, unitReadResDtos.getPageable(),
				unitReadResDtos.getTotalElements());

		response.setUnits(page);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/unit-create", method = RequestMethod.POST)
	public ResponseEntity<?> createUnit(@Validated @RequestBody UnitCreateRequest unitCreateRequest)
			throws InvalidFileException, IOException {
		logger.debug("save unit");
		UnitCreateReqDto unitCreateReqDto = modelMapper.map(unitCreateRequest, UnitCreateReqDto.class);
		if (unitService.isExitUnitName(unitCreateRequest.getUnitName())) {
			String saveErrorMessage = messageSource.getMessage("is.exit.data",
					new String[] { "unitname", unitCreateRequest.getUnitName() }, Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(saveErrorMessage), HttpStatus.CONFLICT);
		}
		unitService.save(unitCreateReqDto);
		String saveSuccessMessage = messageSource.getMessage("create.success", new String[] { "unit" },
				Locale.getDefault());
		return new ResponseEntity<>(Collections.singletonList(saveSuccessMessage), HttpStatus.OK);
	}

	@RequestMapping(value = "/unit-update", method = RequestMethod.GET)
	public ResponseEntity<?> getUnit(@RequestParam(value = "id") Integer id) {
		logger.debug("get unit");
		UnitUpdateResponse response = new UnitUpdateResponse();
		UnitDetailResDto unitDetailResDto = unitService.findOne(id);
		if (unitDetailResDto == null) {
			String viewErrorMessage = messageSource.getMessage("data.not.found", new String[] {},
					Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(viewErrorMessage), HttpStatus.NOT_FOUND);
		}
		UnitDetailResponse unit = modelMapper.map(unitDetailResDto, UnitDetailResponse.class);
		response.setUnit(unit);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/unit-update", method = RequestMethod.POST)
	public ResponseEntity<?> updateUnit(@Validated @RequestBody UnitUpdateRequest unitUpdateRequest)
			throws IOException {
		logger.debug("update unit");
		String message = null;
		HttpStatus status = null;
		UnitDetailResDto unitDetailResDto = unitService.findOne(unitUpdateRequest.getId());
		if (unitDetailResDto == null) {
			message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
			status = HttpStatus.NOT_FOUND;
		} else {
			UnitUpdateReqDto unitUpdateReqDto = modelMapper.map(unitUpdateRequest, UnitUpdateReqDto.class);
			unitService.update(unitUpdateReqDto);
			message = messageSource.getMessage("update.success", new String[] { "unit" }, Locale.getDefault());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}

	@RequestMapping(value = "/unit-delete", method = RequestMethod.POST)
	public ResponseEntity<?> deleteUnits(@RequestBody UnitReadRequest unitReadRequest) throws IOException {
		logger.debug("delete units");
		String message = null;
		HttpStatus status = null;
		List<UnitDeleteRequest> unitDeleteRequests = unitReadRequest.getUnitDeleteRequests();
		for (Iterator<UnitDeleteRequest> iterator = unitDeleteRequests.iterator(); iterator.hasNext();) {
			UnitDeleteRequest unitDeleteRequest = iterator.next();
			UnitDetailResDto unitDetailResDto = unitService.findOne(unitDeleteRequest.getId());
			if (unitDetailResDto == null) {
				message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
				status = HttpStatus.NOT_FOUND;
			} else {
				UnitDeleteReqDto unitDeleteReqDto = modelMapper.map(unitDetailResDto, UnitDeleteReqDto.class);
				unitService.delete(unitDeleteReqDto);
				message = messageSource.getMessage("delete.success", new String[] { "unit" }, Locale.getDefault());
				status = HttpStatus.OK;
			}
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}
}
