package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class RoleDetailResponse {

	private String id;

	private String description;

	private String roleName;

	private String createdBy;

	private String createdDate;

	private String updatedBy;

	private String updatedDate;
}
