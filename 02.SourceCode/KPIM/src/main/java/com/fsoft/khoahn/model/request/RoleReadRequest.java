package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class RoleReadRequest {
	private PaginationRequest paginationRequest;
	private List<SortRequest> sortRequest;
	private RoleSearchRequest roleSearchRequest;
	private List<RoleDeleteRequest> roleDeleteRequests;
}
