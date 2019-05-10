package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class ParentDepartmentCreateReqDto {

	private String parentDepartmentCode;
	
	private String parentDepartmentName;

	private String status;
}
