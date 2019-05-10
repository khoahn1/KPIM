package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class ParentDepartmentUpdateReqDto {

	private String id;

	private String parentDepartmentCode;
	
	private String parentDepartmentName;

	private String status;
}
