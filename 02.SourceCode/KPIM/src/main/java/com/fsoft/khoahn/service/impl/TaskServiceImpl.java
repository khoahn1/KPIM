package com.fsoft.khoahn.service.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.khoahn.common.Constants;
import com.fsoft.khoahn.common.download.ExcelGenerator;
import com.fsoft.khoahn.common.exception.InvalidFileException;
import com.fsoft.khoahn.common.utils.PageRequestUtils;
import com.fsoft.khoahn.dto.req.PaginationReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;
import com.fsoft.khoahn.dto.req.TaskCreateReqDto;
import com.fsoft.khoahn.dto.req.TaskDeleteReqDto;
import com.fsoft.khoahn.dto.req.TaskReadReqDto;
import com.fsoft.khoahn.dto.req.TaskSearchReqDto;
import com.fsoft.khoahn.dto.req.TaskUpdateReqDto;
import com.fsoft.khoahn.dto.res.DataExportResDto;
import com.fsoft.khoahn.dto.res.TaskDetailResDto;
import com.fsoft.khoahn.model.TaskImportExportContent;
import com.fsoft.khoahn.repository.TaskRepo;
import com.fsoft.khoahn.repository.entity.TaskEntity;
import com.fsoft.khoahn.service.TaskService;

@Service("taskService")
@Transactional
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskRepo taskRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TaskDetailResDto findByTaskCode(String taskCode) {
		TaskDetailResDto taskResDto = new TaskDetailResDto();
		TaskEntity taskEntity = taskRepo.findByTaskCode(taskCode);
		if (taskEntity != null) {
			taskResDto = modelMapper.map(taskEntity, TaskDetailResDto.class);
		}
		return taskResDto;
	}

	@Override
	public TaskDetailResDto findOne(String id) {
		TaskDetailResDto taskReadResDto = new TaskDetailResDto();
		TaskEntity taskEntity = taskRepo.findById(id).get();
		if (taskEntity != null) {
			taskReadResDto = modelMapper.map(taskEntity, TaskDetailResDto.class);
		}
		return taskReadResDto;
	}

	@Override
	public Page<TaskDetailResDto> findAll(TaskReadReqDto taskReadReqDto) {
		PaginationReqDto paginationRequest = taskReadReqDto.getPaginationRequest();
		TaskSearchReqDto taskSearchRequest = taskReadReqDto.getTaskSearchRequest();

		List<SortReqDto> sortDtoList = taskReadReqDto.getSortRequest();

		Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
		Page<TaskEntity> taskEntitys = taskRepo.findByTaskCodeContaining(taskSearchRequest.getTaskCode(), pageable);

		List<TaskDetailResDto> taskDtoList = new ArrayList<>();

		Page<TaskDetailResDto> page = new PageImpl<>(taskDtoList,
		        PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize()),
				taskEntitys.getTotalElements());
		return page;
	}

	@Override
	public void delete(TaskDeleteReqDto taskDeleteReqDto) throws IOException {
		taskRepo.deleteById(taskDeleteReqDto.getId());
	}

	@Override
	public DataExportResDto exportExcelData() throws Exception {
		List<TaskEntity> taskEntitys = taskRepo.findAll();
		Type typeTasks = new TypeToken<List<TaskImportExportContent>>() {
		}.getType();
		List<TaskImportExportContent> taskImportExportContents = modelMapper.map(taskEntitys, typeTasks);
		DataExportResDto resDto = ExcelGenerator.map(new TaskImportExportContent(), taskImportExportContents,
				"Tasks_DataExport",
				Constants.PATH_EXPORT_DATA_USERS);


		return resDto;
	}

	@Override
	public void save(TaskCreateReqDto taskCreateReqDto) throws InvalidFileException, IOException {
		TaskEntity taskEntity = modelMapper.map(taskCreateReqDto, TaskEntity.class);
		taskEntity = taskRepo.save(taskEntity);
	}

	@Override
	public boolean isExitTaskCode(String taskCode) {
		TaskEntity taskEntity = taskRepo.findByTaskCode(taskCode);
		return (taskEntity != null);
	}

	@Override
	public boolean isExitTaskId(String id) {
		TaskEntity taskEntity = taskRepo.findById(id).get();
		return (taskEntity != null);
	}

	@Override
	public void update(TaskUpdateReqDto taskUpdateReqDto) throws IOException {
		TaskEntity taskEntity = modelMapper.map(taskUpdateReqDto, TaskEntity.class);
		taskRepo.save(taskEntity);
	}
}
