package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class ParentDepartmentDetailReqDto {

	private String id;

	private String parentDepartmentCode;
	
	private String parentDepartmentName;

	private Integer status;

	private String createDate;

	private String updateDate;

	private String createBy;

	private String updateBy;
}
