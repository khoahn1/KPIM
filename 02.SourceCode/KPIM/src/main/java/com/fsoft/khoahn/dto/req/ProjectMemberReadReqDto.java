package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class ProjectMemberReadReqDto {

	private PaginationReqDto paginationRequest;
	private List<SortReqDto> sortRequest;
	private ProjectMemberSearchReqDto projectMemberSearchRequest;
	private List<ProjectMemberDeleteReqDto> projectMemberDeleteRequests;
}
