package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class DepartmentSearchReqDto {

	private ParentDepartmentDetailReqDto parentDepartment;

	private String departmentCode;

	private String departmentName;
}
