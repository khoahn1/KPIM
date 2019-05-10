package com.fsoft.khoahn.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.khoahn.common.download.ExcelToObjectMapper;
import com.fsoft.khoahn.common.exception.InvalidFileException;
import com.fsoft.khoahn.dto.req.DepartmentDetailReqDto;
import com.fsoft.khoahn.dto.req.ProjectCreateReqDto;
import com.fsoft.khoahn.dto.req.ProjectDeleteReqDto;
import com.fsoft.khoahn.dto.req.ProjectMemberReadReqDto;
import com.fsoft.khoahn.dto.req.ProjectReadReqDto;
import com.fsoft.khoahn.dto.req.ProjectUpdateReqDto;
import com.fsoft.khoahn.dto.res.DepartmentDetailResDto;
import com.fsoft.khoahn.dto.res.ProjectDetailResDto;
import com.fsoft.khoahn.dto.res.UserDetailResDto;
import com.fsoft.khoahn.model.ProjectImportExportContent;
import com.fsoft.khoahn.model.request.FileUploadRequest;
import com.fsoft.khoahn.model.request.ProjectCreateRequest;
import com.fsoft.khoahn.model.request.ProjectDeleteRequest;
import com.fsoft.khoahn.model.request.ProjectMemberReadRequest;
import com.fsoft.khoahn.model.request.ProjectReadRequest;
import com.fsoft.khoahn.model.request.ProjectUpdateRequest;
import com.fsoft.khoahn.model.response.DepartmentDetailResponse;
import com.fsoft.khoahn.model.response.ProjectCreateResponse;
import com.fsoft.khoahn.model.response.ProjectDetailResponse;
import com.fsoft.khoahn.model.response.ProjectReadResponse;
import com.fsoft.khoahn.model.response.ProjectUpdateResponse;
import com.fsoft.khoahn.model.response.UserDetailResponse;
import com.fsoft.khoahn.service.DepartmentService;
import com.fsoft.khoahn.service.ProjectService;


@RestController
@RequestMapping("/projects")
public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value="/project-read", method = RequestMethod.POST)
	public ResponseEntity<?> getAll(@RequestBody ProjectReadRequest projectReadRequest) {
		
		logger.debug("get project list");
		ProjectReadResponse response = new ProjectReadResponse();
		ProjectReadReqDto projectReadReqDto = modelMapper.map(projectReadRequest, ProjectReadReqDto.class);
		Page<ProjectDetailResDto> userReadResDtos = projectService.getAll(projectReadReqDto);

		Type typeProjects = new TypeToken<List<ProjectDetailResponse>>() {
		}.getType();
		List<ProjectDetailResponse> projectDetailResponses = modelMapper.map(userReadResDtos.getContent(), typeProjects);
		Page<ProjectDetailResponse> page = new PageImpl<>(projectDetailResponses, userReadResDtos.getPageable(),
				userReadResDtos.getTotalElements());

		response.setProjects(page);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/project-detail/info", method = RequestMethod.GET)
	public ResponseEntity<?> getProjectDetail(@RequestParam("id") String id) {

		ProjectDetailResDto projectDetailResDto = projectService.findOne(id);

		ProjectDetailResponse projectDetailResponse = modelMapper.map(projectDetailResDto, ProjectDetailResponse.class);

		return new ResponseEntity<>(projectDetailResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/project-detail/member-read", method = RequestMethod.POST)
	public ResponseEntity<?> getMember(@RequestBody ProjectMemberReadRequest projectMemberReadRequest) {
		ProjectMemberReadReqDto projectMemberReadReqDto = modelMapper.map(projectMemberReadRequest,
				ProjectMemberReadReqDto.class);
		Page<UserDetailResDto> userDetailResDtos = projectService
				.getMember(projectMemberReadReqDto);

		Type typeUsers = new TypeToken<List<UserDetailResponse>>() {
		}.getType();
		List<UserDetailResponse> userDetailResponses = modelMapper.map(userDetailResDtos.getContent(),
				typeUsers);
		Page<UserDetailResponse> page = new PageImpl<>(userDetailResponses, userDetailResDtos.getPageable(),
				userDetailResDtos.getTotalElements());

		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	@RequestMapping(value="/project-create", method = RequestMethod.GET)
	public ResponseEntity<?> initFormSave() {
		
		ProjectCreateResponse response = new ProjectCreateResponse();
		
		List<DepartmentDetailResDto> departmentDetailResDtos = departmentService.getAll();
		
		Type typeDepartments = new TypeToken<List<DepartmentDetailResponse>>() {
		}.getType();
		
		List<DepartmentDetailResponse> departmentDetailResponses = modelMapper.map(departmentDetailResDtos, typeDepartments);
		
		response.setDepartments(departmentDetailResponses);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@RequestMapping(value="/project-create",method = RequestMethod.POST)
	public ResponseEntity<?> saveProject(@Validated @ModelAttribute ProjectCreateRequest projectCreateRequest) {
		
		ProjectCreateReqDto projectCreateReqDto = modelMapper.map(projectCreateRequest, ProjectCreateReqDto.class);
		
		projectService.save(projectCreateReqDto);
		String saveSuccessMessage = messageSource.getMessage("create.success", new String[] { "project" },
				Locale.getDefault());
		return new ResponseEntity<>(Collections.singletonList(saveSuccessMessage), HttpStatus.OK);
	}
	
	@RequestMapping(value="/project-update", method= RequestMethod.GET)
	public ResponseEntity<?> initFormUpdate(@RequestParam("id") String id) {
		
		ProjectUpdateResponse projectUpdateResponse = new ProjectUpdateResponse();
		
		ProjectDetailResDto projectDetailResDto = projectService.findOne(id);
		
		List<DepartmentDetailResDto> departmentDetailResDtos = departmentService.getAll();
		
		Type typeDepartments = new TypeToken<List<DepartmentDetailResponse>>() {
		}.getType();
		
		List<DepartmentDetailResponse> departmentDetailResponses = modelMapper.map(departmentDetailResDtos, typeDepartments);
		
		ProjectDetailResponse projectDetailResponse = modelMapper.map(projectDetailResDto,ProjectDetailResponse.class);
		
		projectUpdateResponse.setDepartments(departmentDetailResponses);
		
		projectUpdateResponse.setProjectDetailResponse(projectDetailResponse);
		
		return new ResponseEntity<>(projectUpdateResponse,HttpStatus.OK);
	}
	
	@RequestMapping(value="/project-update", method = RequestMethod.POST)
	public ResponseEntity<?> saveUpdate(@Validated @ModelAttribute ProjectUpdateRequest projectUpdateRequest) {
		
		String message = null;
		HttpStatus status = null;
		
		ProjectDetailResDto projectDetailResDto = projectService.findOne(projectUpdateRequest.getId());
		
		if (projectDetailResDto == null) {
			message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
			status = HttpStatus.NOT_FOUND;
		} else {
			ProjectUpdateReqDto projectUpdateReqDto = modelMapper.map(projectUpdateRequest, ProjectUpdateReqDto.class);
			
			projectService.update(projectUpdateReqDto);
			
			message = messageSource.getMessage("update.success", new String[] { "project" }, Locale.getDefault());
			status = HttpStatus.OK;
		}	
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}
	
	@RequestMapping(value="/project-delete", method = RequestMethod.POST)
	public ResponseEntity<?> deleteProject(@RequestBody ProjectReadRequest projectReadRequest) {
		
		String message = null;
		
		HttpStatus status = null;
		
		List<ProjectDeleteRequest> projectReadRequests = projectReadRequest.getProjectDeleteRequests();
		
		for(Iterator<ProjectDeleteRequest> iterator = projectReadRequests.iterator();iterator.hasNext();){
			
			ProjectDeleteRequest projectDeleteRequest = iterator.next();
			ProjectDetailResDto projectDetailResDto = projectService.findOne(projectDeleteRequest.getId());
			
			if (projectDetailResDto == null) {
				message = messageSource.getMessage("data.not.found", new String[] {},Locale.getDefault());
				status = HttpStatus.NOT_FOUND;
			} else {
				
				ProjectDeleteReqDto projectDeleteReqDto = modelMapper.map(projectDetailResDto,
						ProjectDeleteReqDto.class);
				projectService.delete(projectDeleteReqDto);
				message = messageSource.getMessage("delete.success", new String[] { "project" }, Locale.getDefault());
				status = HttpStatus.OK;
			}
			
		}
		
		return new ResponseEntity<>(Collections.singletonList(message), status);
	}
	
	@RequestMapping(value="/project-import", method = RequestMethod.POST)
	public ResponseEntity<?> importProject(@Valid @ModelAttribute FileUploadRequest fileUploadRequest) {
		
		List<String> messages = new ArrayList<String>();
		
		String message = null;
		
		HttpStatus status = null;
		
		try {
			ExcelToObjectMapper mapper = new ExcelToObjectMapper(fileUploadRequest.getFile());
			List<ProjectImportExportContent> projectImportExportContents = mapper.map(ProjectImportExportContent.class);
			status = importData(messages,message,projectImportExportContents);
		} catch (Exception e) {
			message = messageSource.getMessage("error.file.upload", new String[]{},Locale.getDefault());
			messages.add(message);
			status = HttpStatus.FORBIDDEN;
		}
		if (status == HttpStatus.OK) {
			message = messageSource.getMessage("import.success", new String[] { "project" }, Locale.getDefault());
			messages.add(message);
		}
		return new ResponseEntity<>(messages, status);
	}
	
	private HttpStatus importData(List<String> messages, String message, List<ProjectImportExportContent> dataImportProjects)
			throws InvalidFileException, IOException {
		HttpStatus status = null;
		int rowCount = dataImportProjects.size();
		for(int rowIdx = 0 ; rowIdx < rowCount; rowIdx ++) {
			ProjectImportExportContent projectImportExportContent = dataImportProjects.get(rowIdx);
			String id = projectImportExportContent.getId();
			String departmentId = projectImportExportContent.getDepartmentId();
			
			if (id != null && !id.isEmpty()) {
				ProjectUpdateReqDto projectUpdateReqDto = modelMapper.map(projectImportExportContent, ProjectUpdateReqDto.class);
				DepartmentDetailReqDto departmentUpdateReqDto = new DepartmentDetailReqDto();
				departmentUpdateReqDto.setId(departmentId);
				projectUpdateReqDto.setDepartment(departmentUpdateReqDto);
				projectService.update(projectUpdateReqDto);
				status = HttpStatus.OK;
			} else {
				ProjectCreateReqDto projectCreateReqDto = modelMapper.map(projectImportExportContent, ProjectCreateReqDto.class);
				DepartmentDetailReqDto departmentCreateReqDto = new DepartmentDetailReqDto();
				departmentCreateReqDto.setId(departmentId);
				projectCreateReqDto.setDepartment(departmentCreateReqDto);
				projectService.save(projectCreateReqDto);
				status = HttpStatus.OK;
			}
		}
		return status;
	}
	
}
