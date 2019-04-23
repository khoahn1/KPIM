package com.fsoft.khoahn.model.request.dto;

import java.util.List;

import lombok.Data;

@Data
public class PhaseReadReqDto {
	private PaginationReqDto paginationRequest;
	private List<SortReqDto> sortRequests;
	private PhaseSearchReqDto phaseSearchRequest;
	private List<PhaseDeleteReqDto> phaseDeleteRequest;
}
