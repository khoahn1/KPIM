package com.fsoft.khoahn.model.response;

import java.util.List;

import lombok.Data;

@Data
public class RoleUpdateResponse {
	private String id;

	private String description;

	private String roleName;

	private String createdBy;

	private String createdDate;

	private String updatedBy;

	private String updatedDate;

	private List<AuthorityDetailResponse> items;
}