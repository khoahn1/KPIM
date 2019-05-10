package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class DepartmentReadReqDto {
	private PaginationReqDto paginationRequest;
	private List<SortReqDto> sortRequests;
	private DepartmentSearchReqDto departmentSearchRequest;
	private List<DepartmentDeleteReqDto> departmentDeleteRequest;
}
