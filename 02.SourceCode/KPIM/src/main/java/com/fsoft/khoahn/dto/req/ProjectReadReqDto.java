package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class ProjectReadReqDto {

	private PaginationReqDto paginationRequest;
	private List<SortReqDto> sortRequest;
	private ProjectSearchReqDto projectSearchRequest;
	private List<ProjectDeleteReqDto> projectDeleteRequests;
}
