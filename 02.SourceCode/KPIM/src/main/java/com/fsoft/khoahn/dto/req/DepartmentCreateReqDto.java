package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class DepartmentCreateReqDto {

	private ParentDepartmentDetailReqDto parentDepartment;

	private String departmentCode;
	
	private String departmentName;

	private String status;
}
