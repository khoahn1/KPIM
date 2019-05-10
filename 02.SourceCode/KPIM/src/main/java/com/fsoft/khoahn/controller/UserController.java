package com.fsoft.khoahn.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.khoahn.common.download.ExcelToObjectMapper;
import com.fsoft.khoahn.common.exception.InvalidFileException;
import com.fsoft.khoahn.dto.req.RoleDetailReqDto;
import com.fsoft.khoahn.dto.req.UserCreateReqDto;
import com.fsoft.khoahn.dto.req.UserDeleteReqDto;
import com.fsoft.khoahn.dto.req.UserReadReqDto;
import com.fsoft.khoahn.dto.req.UserUpdateReqDto;
import com.fsoft.khoahn.dto.res.UserDetailResDto;
import com.fsoft.khoahn.dto.res.UserReadResDto;
import com.fsoft.khoahn.model.UserImportExportContent;
import com.fsoft.khoahn.model.request.FileUploadRequest;
import com.fsoft.khoahn.model.request.UserAuthorityUpdateResquest;
import com.fsoft.khoahn.model.request.UserCreateRequest;
import com.fsoft.khoahn.model.request.UserDeleteRequest;
import com.fsoft.khoahn.model.request.UserReadRequest;
import com.fsoft.khoahn.model.request.UserUpdateRequest;
import com.fsoft.khoahn.model.response.UserDetailResponse;
import com.fsoft.khoahn.model.response.UserExportResponse;
import com.fsoft.khoahn.model.response.UserReadResponse;
import com.fsoft.khoahn.model.response.UserUpdateResponse;
import com.fsoft.khoahn.service.UserService;
import com.fsoft.khoahn.validator.UserImportValidator;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/users")
@Api(description = "Users management API")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	MessageSource messageSource;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserImportValidator userImportValidator;

	@InitBinder(value = "fileUploadRequest")
	public void initBinderUpload(final WebDataBinder binder) {
		binder.addValidators(userImportValidator);
	}

	@RequestMapping(value = "/user-read", method = RequestMethod.POST)
	public ResponseEntity<?> getAll(@RequestBody UserReadRequest userReadRequest) {
		logger.debug("get users list");
		UserReadResponse response = new UserReadResponse();
		UserReadReqDto userReadReqDto = modelMapper.map(userReadRequest, UserReadReqDto.class);
		Page<UserDetailResDto> userReadResDtos = userService.findAll(userReadReqDto);

		Type typeUsers = new TypeToken<List<UserDetailResponse>>() {
		}.getType();
		List<UserDetailResponse> userDetailResponses = modelMapper.map(userReadResDtos.getContent(), typeUsers);
		Page<UserDetailResponse> page = new PageImpl<>(userDetailResponses, userReadResDtos.getPageable(),
				userReadResDtos.getTotalElements());

		response.setUsers(page);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/user-delete", method = RequestMethod.POST)
	public ResponseEntity<?> deleteUsers(@RequestBody UserReadRequest userReadRequest) throws IOException {
		logger.debug("delete users");
		String message = null;
		HttpStatus status = null;
		List<UserDeleteRequest> userDeleteRequests = userReadRequest.getUserDeleteRequests();
		for (Iterator<UserDeleteRequest> iterator = userDeleteRequests.iterator(); iterator.hasNext();) {
			UserDeleteRequest userDeleteRequest = iterator.next();
			UserReadResDto userReadResDto = userService.findOne(userDeleteRequest.getId());
			if (userReadResDto == null) {
				message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
				status = HttpStatus.NOT_FOUND;
			} else {
				UserDeleteReqDto userDeleteReqDto = modelMapper.map(userReadResDto, UserDeleteReqDto.class);
				userService.delete(userDeleteReqDto);
				message = messageSource.getMessage("delete.success", new String[] { "user" }, Locale.getDefault());
				status = HttpStatus.OK;
			}
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}

	@RequestMapping(value = "/user-create", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@Validated @ModelAttribute UserCreateRequest userCreateRequest)
			throws InvalidFileException, IOException {
		logger.debug("save user");
		UserCreateReqDto userCreateReqDto = modelMapper.map(userCreateRequest, UserCreateReqDto.class);
		if (userService.isExitUsername(userCreateRequest.getUsername())) {
			String saveErrorMessage = messageSource.getMessage("is.exit.data",
					new String[] { "username", userCreateRequest.getUsername() }, Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(saveErrorMessage), HttpStatus.CONFLICT);
		}
		userService.save(userCreateReqDto);
		String saveSuccessMessage = messageSource.getMessage("create.success", new String[] { "user" },
				Locale.getDefault());
		return new ResponseEntity<>(Collections.singletonList(saveSuccessMessage), HttpStatus.OK);
	}

	@RequestMapping(value = "/user-update", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@RequestParam(value = "id") String id) {
		logger.debug("get user");
		UserUpdateResponse response = new UserUpdateResponse();
		UserReadResDto userReadResDto = userService.findOne(id);
		if (userReadResDto == null) {
			String viewErrorMessage = messageSource.getMessage("data.not.found", new String[] {},
					Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(viewErrorMessage), HttpStatus.NOT_FOUND);
		}
		UserDetailResponse userDetailResponse = modelMapper.map(userReadResDto, UserDetailResponse.class);

		response.setUserDetailResponse(userDetailResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/user-update", method = RequestMethod.POST)
	public ResponseEntity<?> updateUser(@Validated @ModelAttribute UserUpdateRequest userUpdateRequest)
			throws IOException {
		logger.debug("update user");
		String message = null;
		HttpStatus status = null;
		UserReadResDto userReadResDto = userService.findOne(userUpdateRequest.getId());
		if (userReadResDto == null) {
			message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
			status = HttpStatus.NOT_FOUND;
		} else {
			UserUpdateReqDto userUpdateReqDto = modelMapper.map(userUpdateRequest, UserUpdateReqDto.class);
			userService.update(userUpdateReqDto);
			message = messageSource.getMessage("update.success", new String[] { "user" }, Locale.getDefault());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}

	@RequestMapping(value = "/user-update/authority", method = RequestMethod.POST)
	public ResponseEntity<?> updateUserAuthority(@RequestBody UserAuthorityUpdateResquest userAuthorityUpdateResquest) {
		logger.debug("update updateUserAuthority");
		String message = null;
		HttpStatus status = null;
		UserUpdateReqDto userUpdateReqDto = modelMapper.map(userAuthorityUpdateResquest, UserUpdateReqDto.class);
		userService.updateUserAuthority(userUpdateReqDto);
		message = messageSource.getMessage("update.success", new String[] { "user" }, Locale.getDefault());
		status = HttpStatus.OK;
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}

	@RequestMapping(value = "/user-export/excel", method = RequestMethod.GET)
	public ResponseEntity<?> exportExcelData() throws Exception {
		UserExportResponse response = modelMapper.map(userService.exportExcelData(), UserExportResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * @param file
	 * @param request
	 * @return responseEntity
	 * @throws Exception
	 */
	@RequestMapping(value = "/user-import", method = RequestMethod.POST)
	public ResponseEntity<?> importUser(@Validated @ModelAttribute FileUploadRequest fileUploadRequest) {
		logger.debug("Import users");
		List<String> messages = new ArrayList<String>();
		String message = null;
		HttpStatus status = null;
		try {
			ExcelToObjectMapper mapper = new ExcelToObjectMapper(fileUploadRequest.getFile());
			List<UserImportExportContent> userImportDatas = mapper.map(UserImportExportContent.class);
			status = importData(messages, message, userImportDatas);
		} catch (Exception e) {
			message = messageSource.getMessage("error.file.upload", new String[] {}, Locale.getDefault());
			messages.add(message);
			status = HttpStatus.FORBIDDEN;
		}
		if (status == HttpStatus.OK) {
			message = messageSource.getMessage("import.success", new String[] { "user" }, Locale.getDefault());
			messages.add(message);
		}
		return new ResponseEntity<>(messages, status);
	}

	/**
	 * @param status
	 * @param messages
	 * @param message
	 * @param userValidator
	 * @param bytes
	 * @return
	 * @throws IOException
	 * @throws InvalidFileException
	 */
	private HttpStatus importData(List<String> messages, String message, List<UserImportExportContent> dataImportUsers)
			throws InvalidFileException, IOException {
		HttpStatus status = null;
		int rowCount = dataImportUsers.size();
		for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
			UserImportExportContent userImportExportContent = dataImportUsers.get(rowIndex);
			String id = userImportExportContent.getId();
			String userName = userImportExportContent.getUsername();
			String roleId = userImportExportContent.getRoleId();
			String password = userImportExportContent.getPassword();
			if (id != null && !id.isEmpty()) {
				UserUpdateReqDto userUpdateReqDto = modelMapper.map(userImportExportContent, UserUpdateReqDto.class);
				RoleDetailReqDto roleReqDto = new RoleDetailReqDto();
				roleReqDto.setId(roleId);
				userUpdateReqDto.setRole(roleReqDto);
				userUpdateReqDto.setPassword(passwordEncoder.encode(password));
				userService.update(userUpdateReqDto);
				status = HttpStatus.OK;
			} else if (!userService.isExitUsername(userName)) {
				UserCreateReqDto userCreateReqDto = modelMapper.map(userImportExportContent, UserCreateReqDto.class);
				RoleDetailReqDto roleReqDto = new RoleDetailReqDto();
				roleReqDto.setId(roleId);
				userCreateReqDto.setRole(roleReqDto);
				userCreateReqDto.setPassword(passwordEncoder.encode(password));
				userService.save(userCreateReqDto);
				status = HttpStatus.OK;
			}
		}
		return status;
	}
}
