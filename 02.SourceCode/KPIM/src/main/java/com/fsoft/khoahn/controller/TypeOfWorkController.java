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
import com.fsoft.khoahn.model.request.TypeOfWorkCreateRequest;
import com.fsoft.khoahn.model.request.TypeOfWorkDeleteRequest;
import com.fsoft.khoahn.model.request.TypeOfWorkReadRequest;
import com.fsoft.khoahn.model.request.TypeOfWorkUpdateRequest;
import com.fsoft.khoahn.model.request.dto.TypeOfWorkCreateReqDto;
import com.fsoft.khoahn.model.request.dto.TypeOfWorkDeleteReqDto;
import com.fsoft.khoahn.model.request.dto.TypeOfWorkReadReqDto;
import com.fsoft.khoahn.model.request.dto.TypeOfWorkUpdateReqDto;
import com.fsoft.khoahn.model.response.TypeOfWorkDetailResponse;
import com.fsoft.khoahn.model.response.TypeOfWorkReadResponse;
import com.fsoft.khoahn.model.response.TypeOfWorkUpdateResponse;
import com.fsoft.khoahn.model.response.dto.TypeOfWorkDetailResDto;
import com.fsoft.khoahn.service.TypeOfWorkService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/type-of-works")
@Api(description = "TypeOfWorks management API")
public class TypeOfWorkController {

	private static final Logger logger = LoggerFactory.getLogger(TypeOfWorkController.class);

	@Autowired
	private TypeOfWorkService typeOfWorkService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/type-of-work-read", method = RequestMethod.POST)
	public ResponseEntity<?> getAll(@RequestBody TypeOfWorkReadRequest typeOfWorkReadRequest) {
		logger.debug("get typeOfWork list");
		TypeOfWorkReadResponse response = new TypeOfWorkReadResponse();
		PaginationRequest paginationRequest = typeOfWorkReadRequest.getPaginationRequest();
		TypeOfWorkReadReqDto typeOfWorkReadReqDto = modelMapper.map(typeOfWorkReadRequest, TypeOfWorkReadReqDto.class);
		Page<TypeOfWorkDetailResDto> typeOfWorkReadResDtos = typeOfWorkService.findAll(typeOfWorkReadReqDto);

		Type typeTypeOfWorks = new TypeToken<List<TypeOfWorkDetailResponse>>() {
		}.getType();
		List<TypeOfWorkDetailResponse> typeOfWorkDetailResponses = modelMapper.map(typeOfWorkReadResDtos.getContent(),
				typeTypeOfWorks);
		Page<TypeOfWorkDetailResponse> page = new PageImpl<>(typeOfWorkDetailResponses,
				new PageRequest(paginationRequest.getPageNumber(), paginationRequest.getPageSize()),
				typeOfWorkReadResDtos.getTotalElements());

		response.setTypeOfWorks(page);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/type-of-work-create", method = RequestMethod.POST)
	public ResponseEntity<?> createTypeOfWork(@Validated @RequestBody TypeOfWorkCreateRequest typeOfWorkCreateRequest)
			throws InvalidFileException, IOException {
		logger.debug("save typeOfWork");
		TypeOfWorkCreateReqDto typeOfWorkCreateReqDto = modelMapper.map(typeOfWorkCreateRequest,
				TypeOfWorkCreateReqDto.class);
		if (typeOfWorkService.isExitTypeOfWorkName(typeOfWorkCreateRequest.getTypeName())) {
			String saveErrorMessage = messageSource.getMessage("is.exit.data",
					new String[] { "typeOfWorkname", typeOfWorkCreateRequest.getTypeName() },
					Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(saveErrorMessage), HttpStatus.CONFLICT);
		}
		typeOfWorkService.save(typeOfWorkCreateReqDto);
		String saveSuccessMessage = messageSource.getMessage("create.success", new String[] { "typeOfWork" },
				Locale.getDefault());
		return new ResponseEntity<>(Collections.singletonList(saveSuccessMessage), HttpStatus.OK);
	}

	@RequestMapping(value = "/type-of-work-update", method = RequestMethod.GET)
	public ResponseEntity<?> getTypeOfWork(@RequestParam(value = "id") Integer id) {
		logger.debug("get typeOfWork");
		TypeOfWorkUpdateResponse response = new TypeOfWorkUpdateResponse();
		TypeOfWorkDetailResDto typeOfWorkDetailResDto = typeOfWorkService.findOne(id);
		if (typeOfWorkDetailResDto == null) {
			String viewErrorMessage = messageSource.getMessage("data.not.found", new String[] {},
					Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(viewErrorMessage), HttpStatus.NOT_FOUND);
		}
		TypeOfWorkDetailResponse typeOfWork = modelMapper.map(typeOfWorkDetailResDto, TypeOfWorkDetailResponse.class);
		response.setTypeOfWork(typeOfWork);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/type-of-work-update", method = RequestMethod.POST)
	public ResponseEntity<?> updateTypeOfWork(@Validated @RequestBody TypeOfWorkUpdateRequest typeOfWorkUpdateRequest)
			throws IOException {
		logger.debug("update typeOfWork");
		String message = null;
		HttpStatus status = null;
		TypeOfWorkDetailResDto typeOfWorkDetailResDto = typeOfWorkService.findOne(typeOfWorkUpdateRequest.getId());
		if (typeOfWorkDetailResDto == null) {
			message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
			status = HttpStatus.NOT_FOUND;
		} else {
			TypeOfWorkUpdateReqDto typeOfWorkUpdateReqDto = modelMapper.map(typeOfWorkUpdateRequest,
					TypeOfWorkUpdateReqDto.class);
			typeOfWorkService.update(typeOfWorkUpdateReqDto);
			message = messageSource.getMessage("update.success", new String[] { "typeOfWork" }, Locale.getDefault());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}

	@RequestMapping(value = "/type-of-work-delete", method = RequestMethod.POST)
	public ResponseEntity<?> deleteTypeOfWorks(@RequestBody TypeOfWorkReadRequest typeOfWorkReadRequest)
			throws IOException {
		logger.debug("delete typeOfWorks");
		String message = null;
		HttpStatus status = null;
		List<TypeOfWorkDeleteRequest> typeOfWorkDeleteRequests = typeOfWorkReadRequest.getTypeOfWorkDeleteRequests();
		for (Iterator<TypeOfWorkDeleteRequest> iterator = typeOfWorkDeleteRequests.iterator(); iterator.hasNext();) {
			TypeOfWorkDeleteRequest typeOfWorkDeleteRequest = iterator.next();
			TypeOfWorkDetailResDto typeOfWorkDetailResDto = typeOfWorkService.findOne(typeOfWorkDeleteRequest.getId());
			if (typeOfWorkDetailResDto == null) {
				message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
				status = HttpStatus.NOT_FOUND;
			} else {
				TypeOfWorkDeleteReqDto typeOfWorkDeleteReqDto = modelMapper.map(typeOfWorkDetailResDto,
						TypeOfWorkDeleteReqDto.class);
				typeOfWorkService.delete(typeOfWorkDeleteReqDto);
				message = messageSource.getMessage("delete.success", new String[] { "typeOfWork" },
						Locale.getDefault());
				status = HttpStatus.OK;
			}
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}
}
