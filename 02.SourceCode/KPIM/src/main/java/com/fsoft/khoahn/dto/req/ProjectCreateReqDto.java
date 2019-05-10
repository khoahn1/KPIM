package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class ProjectCreateReqDto {

	private String projectCode;
	private String projectName;
	private String status;
	private DepartmentDetailReqDto department;
}
