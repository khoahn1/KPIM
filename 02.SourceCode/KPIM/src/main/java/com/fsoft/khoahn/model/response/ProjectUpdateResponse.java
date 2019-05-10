package com.fsoft.khoahn.model.response;

import java.util.List;

import lombok.Data;

@Data
public class ProjectUpdateResponse {

	private List<DepartmentDetailResponse> departments;
	
	private ProjectDetailResponse projectDetailResponse;
}
