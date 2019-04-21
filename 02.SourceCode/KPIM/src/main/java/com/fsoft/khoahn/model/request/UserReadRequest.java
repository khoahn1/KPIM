package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class UserReadRequest {
	private PaginationRequest paginationRequest;
	private List<SortRequest> sortRequest;
	private UserSearchRequest userSearchRequest;
	private List<UserDeleteRequest> userDeleteRequests;
}
