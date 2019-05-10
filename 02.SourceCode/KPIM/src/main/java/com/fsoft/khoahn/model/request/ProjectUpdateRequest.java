package com.fsoft.khoahn.model.request;

import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;

import lombok.Data;

@Data
public class ProjectUpdateRequest {

	private String id;
	@RequireCheck
	private String projectCode;
	private String projectName;
	private String status;
	@RequireCheck
	private DepartmentUpdateRequest department;
}
