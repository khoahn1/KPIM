package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class DepartmentReadRequest {
	private PaginationRequest paginationRequest;
	private List<SortRequest> sortRequests;
	private DepartmentSearchRequest departmentSearchRequest;
	private List<DepartmentDeleteRequest> departmentDeleteRequests;
}
