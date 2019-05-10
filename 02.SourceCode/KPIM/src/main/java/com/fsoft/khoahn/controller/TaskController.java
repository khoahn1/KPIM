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
import com.fsoft.khoahn.dto.req.TaskCreateReqDto;
import com.fsoft.khoahn.dto.req.TaskDeleteReqDto;
import com.fsoft.khoahn.dto.req.TaskReadReqDto;
import com.fsoft.khoahn.dto.req.TaskUpdateReqDto;
import com.fsoft.khoahn.dto.res.TaskDetailResDto;
import com.fsoft.khoahn.model.TaskImportExportContent;
import com.fsoft.khoahn.model.request.FileUploadRequest;
import com.fsoft.khoahn.model.request.TaskCreateRequest;
import com.fsoft.khoahn.model.request.TaskDeleteRequest;
import com.fsoft.khoahn.model.request.TaskReadRequest;
import com.fsoft.khoahn.model.request.TaskUpdateRequest;
import com.fsoft.khoahn.model.response.TaskDetailResponse;
import com.fsoft.khoahn.model.response.TaskExportResponse;
import com.fsoft.khoahn.model.response.TaskReadResponse;
import com.fsoft.khoahn.model.response.TaskUpdateResponse;
import com.fsoft.khoahn.service.TaskService;
import com.fsoft.khoahn.validator.TaskImportValidator;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/tasks")
@Api(description = "Tasks management API")
public class TaskController {

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private TaskImportValidator taskImportValidator;

	@InitBinder(value = "fileUploadRequest")
	public void initBinderUpload(final WebDataBinder binder) {
		binder.addValidators(taskImportValidator);
	}

	@RequestMapping(value = "/task-read", method = RequestMethod.POST)
	public ResponseEntity<?> getAll(@RequestBody TaskReadRequest taskReadRequest) {
		logger.debug("get tasks list");
		TaskReadResponse response = new TaskReadResponse();
		TaskReadReqDto taskReadReqDto = modelMapper.map(taskReadRequest, TaskReadReqDto.class);
		Page<TaskDetailResDto> taskDetailResDtos = taskService.findAll(taskReadReqDto);

		Type typeTasks = new TypeToken<List<TaskDetailResponse>>() {
		}.getType();
		List<TaskDetailResponse> taskDetailResponses = modelMapper.map(taskDetailResDtos.getContent(), typeTasks);
		Page<TaskDetailResponse> page = new PageImpl<>(taskDetailResponses, taskDetailResDtos.getPageable(),
				taskDetailResDtos.getTotalElements());

		response.setTasks(page);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/task-delete", method = RequestMethod.POST)
	public ResponseEntity<?> deleteTasks(@RequestBody TaskReadRequest taskReadRequest) throws IOException {
		logger.debug("delete tasks");
		String message = null;
		HttpStatus status = null;
		List<TaskDeleteRequest> taskDeleteRequests = taskReadRequest.getTaskDeleteRequests();
		for (Iterator<TaskDeleteRequest> iterator = taskDeleteRequests.iterator(); iterator.hasNext();) {
			TaskDeleteRequest taskDeleteRequest = iterator.next();
			TaskDetailResDto taskDetailResDto = taskService.findOne(taskDeleteRequest.getId());
			if (taskDetailResDto == null) {
				message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
				status = HttpStatus.NOT_FOUND;
			} else {
				TaskDeleteReqDto taskDeleteReqDto = modelMapper.map(taskDetailResDto, TaskDeleteReqDto.class);
				taskService.delete(taskDeleteReqDto);
				message = messageSource.getMessage("delete.success", new String[] { "task" }, Locale.getDefault());
				status = HttpStatus.OK;
			}
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}

	@RequestMapping(value = "/task-create", method = RequestMethod.POST)
	public ResponseEntity<?> createTask(@Validated @ModelAttribute TaskCreateRequest taskCreateRequest)
			throws InvalidFileException, IOException {
		logger.debug("save task");
		TaskCreateReqDto taskCreateReqDto = modelMapper.map(taskCreateRequest, TaskCreateReqDto.class);
		if (taskService.isExitTaskCode(taskCreateRequest.getTaskCode())) {
			String saveErrorMessage = messageSource.getMessage("is.exit.data",
					new String[] { "taskCode", taskCreateRequest.getTaskCode() }, Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(saveErrorMessage), HttpStatus.CONFLICT);
		}
		taskService.save(taskCreateReqDto);
		String saveSuccessMessage = messageSource.getMessage("create.success", new String[] { "task" },
				Locale.getDefault());
		return new ResponseEntity<>(Collections.singletonList(saveSuccessMessage), HttpStatus.OK);
	}

	@RequestMapping(value = "/task-update", method = RequestMethod.GET)
	public ResponseEntity<?> getTask(@RequestParam(value = "id") String id) {
		logger.debug("get task");
		TaskUpdateResponse response = new TaskUpdateResponse();
		TaskDetailResDto taskDetailResDto = taskService.findOne(id);
		if (taskDetailResDto == null) {
			String viewErrorMessage = messageSource.getMessage("data.not.found", new String[] {},
					Locale.getDefault());
			return new ResponseEntity<>(Collections.singletonList(viewErrorMessage), HttpStatus.NOT_FOUND);
		}
		TaskDetailResponse taskDetailResponse = modelMapper.map(taskDetailResDto, TaskDetailResponse.class);

		response.setTask(taskDetailResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/task-update", method = RequestMethod.POST)
	public ResponseEntity<?> updateTask(@Validated @ModelAttribute TaskUpdateRequest taskUpdateRequest)
			throws IOException {
		logger.debug("update task");
		String message = null;
		HttpStatus status = null;
		TaskDetailResDto taskDetailResDto = taskService.findOne(taskUpdateRequest.getId());
		if (taskDetailResDto == null) {
			message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
			status = HttpStatus.NOT_FOUND;
		} else {
			TaskUpdateReqDto taskUpdateReqDto = modelMapper.map(taskUpdateRequest, TaskUpdateReqDto.class);
			taskService.update(taskUpdateReqDto);
			message = messageSource.getMessage("update.success", new String[] { "task" }, Locale.getDefault());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}

	@RequestMapping(value = "/task-export/excel", method = RequestMethod.GET)
	public ResponseEntity<?> exportExcelData() throws Exception {
		TaskExportResponse response = modelMapper.map(taskService.exportExcelData(), TaskExportResponse.class);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * @param file
	 * @param request
	 * @return responseEntity
	 * @throws Exception
	 */
	@RequestMapping(value = "/task-import", method = RequestMethod.POST)
	public ResponseEntity<?> importTask(@Validated @ModelAttribute FileUploadRequest fileUploadRequest) {
		logger.debug("Import tasks");
		List<String> messages = new ArrayList<String>();
		String message = null;
		HttpStatus status = null;
		try {
			ExcelToObjectMapper mapper = new ExcelToObjectMapper(fileUploadRequest.getFile());
			List<TaskImportExportContent> taskImportDatas = mapper.map(TaskImportExportContent.class);
			status = importData(messages, message, taskImportDatas);
		} catch (Exception e) {
			message = messageSource.getMessage("error.file.upload", new String[] {}, Locale.getDefault());
			messages.add(message);
			status = HttpStatus.FORBIDDEN;
		}
		if (status == HttpStatus.OK) {
			message = messageSource.getMessage("import.success", new String[] { "task" }, Locale.getDefault());
			messages.add(message);
		}
		return new ResponseEntity<>(messages, status);
	}

	/**
	 * @param status
	 * @param messages
	 * @param message
	 * @param taskValidator
	 * @param bytes
	 * @return
	 * @throws IOException
	 * @throws InvalidFileException
	 */
	private HttpStatus importData(List<String> messages, String message, List<TaskImportExportContent> dataImportTasks)
			throws InvalidFileException, IOException {
		HttpStatus status = null;
		int rowCount = dataImportTasks.size();
		for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
			TaskImportExportContent taskImportExportContent = dataImportTasks.get(rowIndex);
			String id = taskImportExportContent.getId();
			String taskName = taskImportExportContent.getTaskCode();
			if (id != null && !id.isEmpty()) {
				TaskUpdateReqDto taskUpdateReqDto = modelMapper.map(taskImportExportContent, TaskUpdateReqDto.class);
				taskService.update(taskUpdateReqDto);
				status = HttpStatus.OK;
			} else if (!taskService.isExitTaskCode(taskName)) {
				TaskCreateReqDto taskCreateReqDto = modelMapper.map(taskImportExportContent, TaskCreateReqDto.class);
				taskService.save(taskCreateReqDto);
				status = HttpStatus.OK;
			}
		}
		return status;
	}
}
