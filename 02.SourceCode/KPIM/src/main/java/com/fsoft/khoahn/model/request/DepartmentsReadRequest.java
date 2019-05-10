package com.fsoft.khoahn.model.request;

import java.util.List;

import com.fsoft.khoahn.dto.req.SortReqDto;

import lombok.Data;

@Data
public class DepartmentsReadRequest {

	private PaginationRequest paginationRequest;
	private List<SortReqDto> sortRequest;
}
