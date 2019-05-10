package com.fsoft.khoahn.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.fsoft.khoahn.common.download.ExcelToObjectMapper;
import com.fsoft.khoahn.common.support.ValidationSupport;
import com.fsoft.khoahn.dto.res.TaskDetailResDto;
import com.fsoft.khoahn.model.Error;
import com.fsoft.khoahn.model.TaskImportExportContent;
import com.fsoft.khoahn.model.request.FileUploadRequest;
import com.fsoft.khoahn.service.TaskService;

@Component
public class TaskImportValidator implements Validator {

	@Autowired
	private TaskService taskService;

	@Override
	public boolean supports(Class<?> clazz) {
		return FileUploadRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		FileUploadRequest fileUploadRequest = (FileUploadRequest) obj;
		MultipartFile file = fileUploadRequest.getFile();
		if (file == null || file.isEmpty()) {
			return;
		}

		ExcelToObjectMapper mapper = new ExcelToObjectMapper(fileUploadRequest.getFile());

		try {
			List<TaskImportExportContent> taskImportDatas = mapper.map(TaskImportExportContent.class);
			int rowCount = taskImportDatas.size();
			for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
				List<Error> rowErrorCodes = new ArrayList<Error>();
				TaskImportExportContent uploadTaskContentI = taskImportDatas.get(rowIndex);
				String idI = uploadTaskContentI.getId();
				String taskCodeI = uploadTaskContentI.getTaskCode();

				// Data import validation
				validImportData(rowIndex, uploadTaskContentI, rowErrorCodes);
				// Check duplicate data import
				checkDuplicateData(taskImportDatas, rowCount, rowIndex, idI, taskCodeI, rowErrorCodes);
				// Check exit data
				checkExitTaskCode(idI, taskCodeI, rowErrorCodes);

				if (rowErrorCodes == null || rowErrorCodes.isEmpty()) {
					continue;
				}
				errors.rejectValue("file", "import.row.index", new Object[] { String.valueOf(rowIndex + 1) },
						"import.row.index");
				for (Error error : rowErrorCodes) {
					errors.rejectValue("file", error.getMessage(), new Object[] { error.getReason() },
							error.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param taskValidator
	 * @param rowIndex
	 * @param rowMessages
	 * @param taskCreateReqDto
	 */
	private void validImportData(int rowIndex, TaskImportExportContent taskImportExportContent,
			List<Error> rowErrorCodes) {
		if (ValidationSupport.isBlank(taskImportExportContent.getTaskCode())) {
			rowErrorCodes.add(new Error("taskCode", "import.row.field.required"));
		}
	}

	/**
	 * @param table
	 * @param rowCount
	 * @param rowIndex
	 * @param rowMessages
	 * @param uploadTaskContentI
	 */
	private void checkDuplicateData(List<TaskImportExportContent> csvTasks, int rowCount, int rowIndex, String idI,
			String taskCodeI, List<Error> rowErrorCodes) {
		boolean isDuplicateId = false;
		boolean isDuplicateTaskCode = false;
		for (int rowIndexCheck = 0; rowIndexCheck < rowCount; rowIndexCheck++) {
			TaskImportExportContent uploadTaskContentJ = csvTasks.get(rowIndexCheck);
			if (rowIndex != rowIndexCheck) {
				String idJ = uploadTaskContentJ.getId();
				String taskCodeJ = uploadTaskContentJ.getTaskCode();
				if (!ValidationSupport.isBlank(idI) && idI.equals(idJ) && !isDuplicateId) {
					rowErrorCodes.add(new Error(idI, "task.import.row.is.duplicate.id"));
					isDuplicateId = true;
				}
				if (idI == null && !ValidationSupport.isBlank(taskCodeI) && taskCodeI.equals(taskCodeJ)
						&& !isDuplicateTaskCode) {
					rowErrorCodes.add(new Error(taskCodeI, "task.import.row.is.duplicate.taskCode"));
					isDuplicateTaskCode = true;
				}
			}

		}
	}

	/**
	 * @param status
	 * @param rowIndex
	 * @param taskImportReqDto
	 * @param taskCreateReqDto
	 * @param messages
	 */
	private void checkExitTaskCode(String idI, String taskCodeI, List<Error> rowErrorCodes) {
		if (idI != null && !idI.isEmpty()) {
			if (!taskService.isExitTaskId(idI)) {
				rowErrorCodes.add(new Error(idI, "task.import.row.is.not.exit.id"));
				if (taskService.isExitTaskCode(taskCodeI)) {
					rowErrorCodes.add(new Error(taskCodeI, "task.import.row.exit.taskCode"));
				}
			} else {
				TaskDetailResDto taskDetailResDto = taskService.findOne(idI);
				if (!taskDetailResDto.getTaskCode().equals(taskCodeI)) {
					rowErrorCodes.add(new Error(taskCodeI, "task.import.row.exit.taskCode"));
				}
			}
		} else {
			if (taskService.isExitTaskCode(taskCodeI)) {
				rowErrorCodes.add(new Error(taskCodeI, "task.import.row.exit.taskCode"));
			}
		}
	}

}
