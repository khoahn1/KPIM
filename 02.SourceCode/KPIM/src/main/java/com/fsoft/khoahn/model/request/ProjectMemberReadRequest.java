package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class ProjectMemberReadRequest {

	private PaginationRequest paginationRequest;
	private List<SortRequest> sortRequest;
	private ProjectMemberSearchRequest projectMemberSearchRequest;
	private List<ProjectMemberDeleteRequest> projectMemberDeleteRequests;
	private List<ProjectDeleteRequest> projectDeleteRequests;
}
