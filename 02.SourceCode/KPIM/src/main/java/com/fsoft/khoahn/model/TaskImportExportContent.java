package com.fsoft.khoahn.model;

import com.fsoft.khoahn.common.annotation.CellBindByName;

import lombok.Data;

@Data
public class TaskImportExportContent {
	@CellBindByName(column = "Id")
	private String id;
	@CellBindByName(column = "Task Code")
	private String taskCode;
	@CellBindByName(column = "Product")
	private String productId;
	@CellBindByName(column = "Phase")
	private String phaseId;
	@CellBindByName(column = "User")
	private String userId;
	@CellBindByName(column = "Status")
	private String statusId;
	@CellBindByName(column = "Created Date")
	private String createdDate;
	@CellBindByName(column = "Updated Date")
	private String updatedDate;
	@CellBindByName(column = "Created By")
	private String createdBy;
	@CellBindByName(column = "Updated By")
	private String updatedBy;
}
