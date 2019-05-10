package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class DepartmentDetailResponse {

    private String id;

	private ParentDepartmentDetailResponse parentDepartment;
	
	private String departmentCode;
	
	private String departmentName;
	
	private Integer status;
	
	private String createDate;
	
	private String updateDate;
	
	private String createBy;
	
	private String updateBy;
}
