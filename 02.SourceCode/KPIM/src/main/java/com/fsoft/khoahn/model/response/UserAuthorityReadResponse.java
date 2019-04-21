package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class UserAuthorityReadResponse {
	private String id;

	private OperationReadResponse operation;

	private String authority;

	private String createdBy;

	private String createdDate;

}
