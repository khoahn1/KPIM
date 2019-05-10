package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class ParentDepartmentDetailRequest {

	private String id;

	private String parentDepartmentCode;
	
	private String parentDepartmentName;

	private Integer status;

	private String createDate;

	private String updateDate;

	private String createBy;

	private String updateBy;
}
