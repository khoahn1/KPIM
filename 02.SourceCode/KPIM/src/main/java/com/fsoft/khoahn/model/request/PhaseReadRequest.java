package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class PhaseReadRequest {
	private PaginationRequest paginationRequest;
	private List<SortRequest> sortRequests;
	private PhaseSearchRequest phaseSearchRequest;
	private List<PhaseDeleteRequest> phaseDeleteRequests;
}
