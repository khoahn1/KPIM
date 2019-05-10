package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class TaskReadRequest {
	private PaginationRequest paginationRequest;
	private List<SortRequest> sortRequest;
	private TaskSearchRequest taskSearchRequest;
	private List<TaskDeleteRequest> taskDeleteRequests;
}
