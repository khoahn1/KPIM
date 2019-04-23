package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class TypeOfWorkReadRequest {
	private PaginationRequest paginationRequest;
	private List<SortRequest> sortRequests;
	private TypeOfWorkSearchRequest typeOfWorkSearchRequest;
	private List<TypeOfWorkDeleteRequest> typeOfWorkDeleteRequests;
}
