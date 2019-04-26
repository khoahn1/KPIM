package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class RoleReadReqDto {
	private PaginationReqDto paginationRequest;
	private List<SortReqDto> sortRequest;
	private RoleSearchReqDto roleSearchRequest;
	private List<RoleDeleteReqDto> roleDeleteRequests;
}
