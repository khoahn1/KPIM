package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class UnitReadRequest {
	private PaginationRequest paginationRequest;
	private List<SortRequest> sortRequests;
	private UnitSearchRequest unitSearchRequest;
	private List<UnitDeleteRequest> unitDeleteRequests;
}
