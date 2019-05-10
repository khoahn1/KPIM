package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class ParentDepartmentReadRequest {
	private PaginationRequest paginationRequest;
	private List<SortRequest> sortRequests;
	private ParentDepartmentSearchRequest parentDepartmentSearchRequest;
	private List<ParentDepartmentDeleteRequest> parentDepartmentDeleteRequests;
}
