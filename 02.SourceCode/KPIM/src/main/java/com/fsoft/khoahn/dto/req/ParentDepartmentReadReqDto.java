package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class ParentDepartmentReadReqDto {
	private PaginationReqDto paginationRequest;
	private List<SortReqDto> sortRequests;
	private ParentDepartmentSearchReqDto parentDepartmentSearchRequest;
	private List<ParentDepartmentDeleteReqDto> parentDepartmentDeleteRequest;
}
