package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class CompanyReadReqDto {
	private PaginationReqDto paginationRequest;
	private List<SortReqDto> sortRequest;
	private CompanySearchReqDto companySearchRequest;
}
