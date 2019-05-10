package com.fsoft.khoahn.service;

import java.io.IOException;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.common.exception.InvalidFileException;
import com.fsoft.khoahn.dto.req.TaskCreateReqDto;
import com.fsoft.khoahn.dto.req.TaskDeleteReqDto;
import com.fsoft.khoahn.dto.req.TaskReadReqDto;
import com.fsoft.khoahn.dto.req.TaskUpdateReqDto;
import com.fsoft.khoahn.dto.res.DataExportResDto;
import com.fsoft.khoahn.dto.res.TaskDetailResDto;

public interface TaskService {

	TaskDetailResDto findByTaskCode(String taskCode);

	TaskDetailResDto findOne(String id);

	Page<TaskDetailResDto> findAll(TaskReadReqDto taskReadReqDto);

	void delete(TaskDeleteReqDto taskDeleteReqDto) throws IOException;

	DataExportResDto exportExcelData() throws Exception;

	void save(TaskCreateReqDto taskCreateReqDto) throws InvalidFileException, IOException;

	boolean isExitTaskCode(String taskCode);

	boolean isExitTaskId(String id);

	void update(TaskUpdateReqDto taskUpdateReqDto) throws IOException;

}
