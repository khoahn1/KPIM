package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class DepartmentDetailReqDto {

	private String id;

	private ParentDepartmentDetailReqDto parentDepartment;

	private String departmentCode;
	
	private String departmentName;

	private Integer status;

	private String createDate;

	private String updateDate;

	private String createBy;

	private String updateBy;
}
