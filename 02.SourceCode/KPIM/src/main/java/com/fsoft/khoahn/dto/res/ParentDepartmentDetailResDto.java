package com.fsoft.khoahn.dto.res;

import lombok.Data;

@Data
public class ParentDepartmentDetailResDto {

	private String id;
	
	private String parentDepartmentCode;
	
	private String parentDepartmentName;
	
	private Integer status;
	
	private String createDate;
	
	private String updateDate;
	
	private String createBy;
	
	private String updateBy;
}
