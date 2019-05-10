package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class ParentDepartmentDetailResponse {

    private String id;
	
	private String parentDepartmentCode;
	
	private String parentDepartmentName;
	
	private Integer status;
	
	private String createDate;
	
	private String updateDate;
	
	private String createBy;
	
	private String updateBy;
}
