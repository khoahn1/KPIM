package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class TaskReadReqDto {
	private PaginationReqDto paginationRequest;
	private List<SortReqDto> sortRequest;
	private TaskSearchReqDto taskSearchRequest;
	private List<TaskDeleteReqDto> taskDeleteRequests;
}
