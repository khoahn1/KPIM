package com.fsoft.khoahn.model.request.dto;

import java.util.List;

import lombok.Data;

@Data
public class TypeOfWorkReadReqDto {
	private PaginationReqDto paginationRequest;
	private List<SortReqDto> sortRequests;
	private TypeOfWorkSearchReqDto typeOfWorkSearchRequest;
	private List<TypeOfWorkDeleteReqDto> typeOfWorkDeleteRequest;
}
