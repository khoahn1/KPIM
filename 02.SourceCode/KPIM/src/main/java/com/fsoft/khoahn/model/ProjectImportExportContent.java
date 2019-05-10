package com.fsoft.khoahn.model;

import com.fsoft.khoahn.common.annotation.CellBindByName;

import lombok.Data;

@Data
public class ProjectImportExportContent {

	@CellBindByName(column = "Id")
	private String id;
	@CellBindByName(column = "projectCode")
	private String projectCode;
	@CellBindByName(column = "projectName")
	private String projectName;
	@CellBindByName(column = "status")
	private String status;
	@CellBindByName(column = "department")
	private String departmentId;
}
