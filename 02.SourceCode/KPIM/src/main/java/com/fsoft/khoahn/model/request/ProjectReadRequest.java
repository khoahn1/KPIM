package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class ProjectReadRequest {

	private PaginationRequest paginationRequest;
	private List<SortRequest> sortRequest;
	private ProjectSearchRequest projectSearchRequest;
	private List<ProjectDeleteRequest> projectDeleteRequests;
}
