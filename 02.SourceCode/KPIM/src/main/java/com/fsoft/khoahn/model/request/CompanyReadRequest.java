package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class CompanyReadRequest {
	private PaginationRequest paginationRequest;
	private List<SortRequest> sortRequest;
	private CompanySearchRequest companySearchRequest;
	private List<CompanyDeleteRequest> companyDeleteRequests;
}
