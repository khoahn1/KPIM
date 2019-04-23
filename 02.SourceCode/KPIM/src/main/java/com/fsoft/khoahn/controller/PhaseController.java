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
import com.fsoft.khoahn.model.request.PhaseCreateRequest;
import com.fsoft.khoahn.model.request.PhaseDeleteRequest;
import com.fsoft.khoahn.model.request.PhaseReadRequest;
import com.fsoft.khoahn.model.request.PhaseUpdateRequest;
import com.fsoft.khoahn.model.request.dto.PhaseCreateReqDto;
import com.fsoft.khoahn.model.request.dto.PhaseDeleteReqDto;
import com.fsoft.khoahn.model.request.dto.PhaseReadReqDto;
import com.fsoft.khoahn.model.request.dto.PhaseUpdateReqDto;
import com.fsoft.khoahn.model.response.PhaseDetailResponse;
import com.fsoft.khoahn.model.response.PhaseReadResponse;
import com.fsoft.khoahn.model.response.PhaseUpdateResponse;
import com.fsoft.khoahn.model.response.dto.PhaseDetailResDto;
import com.fsoft.khoahn.service.PhaseService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/phases")
@Api(description = "Phases management API")
public class PhaseController {

	private static final Logger logger = LoggerFactory.getLogger(PhaseController.class);

	@Autowired
	private PhaseService phaseService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/phase-read", method = RequestMethod.POST)
	public ResponseEntity<?> getAll(@RequestBody PhaseReadRequest phaseReadRequest) {
		logger.debug("get phase list");
		PhaseReadResponse response = new PhaseReadResponse();
		PaginationRequest paginationRequest = phaseReadRequest.getPaginationRequest();
		PhaseReadReqDto phaseReadReqDto = modelMapper.map(phaseReadRequest, PhaseReadReqDto.class);
		Page<PhaseDetailResDto> phaseReadResDtos = phaseService.findAll(phaseReadReqDto);

		Type typePhases = new TypeToken<List<PhaseDetailResponse>>() {
		}.getType();
		List<PhaseDetailResponse> phaseDetailResponses = modelMapper.map(phaseReadResDtos.getContent(), typePhases);
		Page<PhaseDetailResponse> page = new PageImpl<>(phaseDetailResponses,
				new PageRequest(paginationRequest.getPageNumber(), paginationRequest.getPageSize()),
				phaseReadResDtos.getTotalElements());

		response.setPhases(page);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/phase-create", method = RequestMethod.POST)
	public ResponseEntity<?> createPhase(@Validated @RequestBody PhaseCreateRequest phaseCreateRequest)
			throws InvalidFileException, IOException {
		logger.debug("save phase");
		PhaseCreateReqDto phaseCreateReqDto = modelMapper.map(phaseCreateRequest, PhaseCreateReqDto.class);
		if (phaseService.isExitPhaseName(phaseCreateRequest.getPhaseName())) {
			String saveErrorMessage = messageSource.getMessage("is.exit.data",
					new String[] { "phasename", phaseCreateRequest.getPhaseName() }, Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(saveErrorMessage), HttpStatus.CONFLICT);
		}
		phaseService.save(phaseCreateReqDto);
		String saveSuccessMessage = messageSource.getMessage("create.success", new String[] { "phase" },
				Locale.getDefault());
		return new ResponseEntity<>(Collections.singletonList(saveSuccessMessage), HttpStatus.OK);
	}

	@RequestMapping(value = "/phase-update", method = RequestMethod.GET)
	public ResponseEntity<?> getPhase(@RequestParam(value = "id") Integer id) {
		logger.debug("get phase");
		PhaseUpdateResponse response = new PhaseUpdateResponse();
		PhaseDetailResDto phaseDetailResDto = phaseService.findOne(id);
		if (phaseDetailResDto == null) {
			String viewErrorMessage = messageSource.getMessage("data.not.found", new String[] {},
					Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(viewErrorMessage), HttpStatus.NOT_FOUND);
		}
		PhaseDetailResponse phase = modelMapper.map(phaseDetailResDto, PhaseDetailResponse.class);
		response.setPhase(phase);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/phase-update", method = RequestMethod.POST)
	public ResponseEntity<?> updatePhase(@Validated @RequestBody PhaseUpdateRequest phaseUpdateRequest)
			throws IOException {
		logger.debug("update phase");
		String message = null;
		HttpStatus status = null;
		PhaseDetailResDto phaseDetailResDto = phaseService.findOne(phaseUpdateRequest.getId());
		if (phaseDetailResDto == null) {
			message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
			status = HttpStatus.NOT_FOUND;
		} else {
			PhaseUpdateReqDto phaseUpdateReqDto = modelMapper.map(phaseUpdateRequest, PhaseUpdateReqDto.class);
			phaseService.update(phaseUpdateReqDto);
			message = messageSource.getMessage("update.success", new String[] { "phase" }, Locale.getDefault());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}

	@RequestMapping(value = "/phase-delete", method = RequestMethod.POST)
	public ResponseEntity<?> deletePhases(@RequestBody PhaseReadRequest phaseReadRequest) throws IOException {
		logger.debug("delete phases");
		String message = null;
		HttpStatus status = null;
		List<PhaseDeleteRequest> phaseDeleteRequests = phaseReadRequest.getPhaseDeleteRequests();
		for (Iterator<PhaseDeleteRequest> iterator = phaseDeleteRequests.iterator(); iterator.hasNext();) {
			PhaseDeleteRequest phaseDeleteRequest = iterator.next();
			PhaseDetailResDto phaseDetailResDto = phaseService.findOne(phaseDeleteRequest.getId());
			if (phaseDetailResDto == null) {
				message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
				status = HttpStatus.NOT_FOUND;
			} else {
				PhaseDeleteReqDto phaseDeleteReqDto = modelMapper.map(phaseDetailResDto, PhaseDeleteReqDto.class);
				phaseService.delete(phaseDeleteReqDto);
				message = messageSource.getMessage("delete.success", new String[] { "phase" }, Locale.getDefault());
				status = HttpStatus.OK;
			}
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}
}
