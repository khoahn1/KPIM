package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class PaginationRequest {
	private int pageNumber;
	private int pageSize;
}
