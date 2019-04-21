package com.fsoft.khoahn.model.request.dto;

import lombok.Data;

@Data
public class PaginationReqDto {
	private int pageNumber;
	private int pageSize;
}
