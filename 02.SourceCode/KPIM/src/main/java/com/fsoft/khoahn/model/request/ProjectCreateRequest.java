package com.fsoft.khoahn.model.request;

import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;
import com.fsoft.khoahn.dto.req.DepartmentDetailReqDto;

import lombok.Data;

@Data
public class ProjectCreateRequest {

	@RequireCheck
	private String projectCode;
	private String projectName;
	private String status;
	@RequireCheck
	private DepartmentDetailReqDto department;
}
