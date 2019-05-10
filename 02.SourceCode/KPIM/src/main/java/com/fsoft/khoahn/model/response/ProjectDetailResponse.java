package com.fsoft.khoahn.model.response;

import java.util.List;

import lombok.Data;

@Data
public class ProjectDetailResponse {

	private String id;
	
	private String projectCode;
	
	private String projectName;
	
	private Integer status;
	
	private String createDate;
	
	private String updateDate;
	
	private String createBy;
	
	private String updateBy;
	
	private DepartmentDetailResponse department;

	private List<UserDetailResponse> users;
}
