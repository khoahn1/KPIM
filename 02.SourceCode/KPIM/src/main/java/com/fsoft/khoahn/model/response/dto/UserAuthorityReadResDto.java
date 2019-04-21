package com.fsoft.khoahn.model.response.dto;

import lombok.Data;

@Data
public class UserAuthorityReadResDto {
	private String id;

	private OperationReadResDto operation;

	private String authority;

	private String createdBy;

	private String createdDate;

}
