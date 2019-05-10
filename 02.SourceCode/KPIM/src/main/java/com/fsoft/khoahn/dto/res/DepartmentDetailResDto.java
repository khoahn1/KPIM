package com.fsoft.khoahn.dto.res;

import lombok.Data;

@Data
public class DepartmentDetailResDto {

	private String id;
	
	private ParentDepartmentDetailResDto parentDepartment;

	private String departmentCode;
	
	private String departmentName;
	
	private Integer status;
	
	private String createDate;
	
	private String updateDate;
	
	private String createBy;
	
	private String updateBy;
}
