package com.fsoft.khoahn.model.request.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserReadReqDto {
	private PaginationReqDto paginationRequest;
	private List<SortReqDto> sortRequest;
	private UserSearchReqDto userSearchRequest;
	private List<UserDeleteReqDto> userDeleteRequests;
}
