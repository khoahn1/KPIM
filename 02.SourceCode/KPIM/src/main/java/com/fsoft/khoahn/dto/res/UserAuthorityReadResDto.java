package com.fsoft.khoahn.dto.res;

import lombok.Data;

@Data
public class UserAuthorityReadResDto {
	private String id;

	private OperationDetailResDto operation;

	private String authority;

	private String createdBy;

	private String createdDate;

}
