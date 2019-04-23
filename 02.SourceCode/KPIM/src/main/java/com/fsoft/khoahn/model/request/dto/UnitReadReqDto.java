package com.fsoft.khoahn.model.request.dto;

import java.util.List;

import lombok.Data;

@Data
public class UnitReadReqDto {
	private PaginationReqDto paginationRequest;
	private List<SortReqDto> sortRequests;
	private UnitSearchReqDto unitSearchRequest;
	private List<UnitDeleteReqDto> unitDeleteRequest;
}
