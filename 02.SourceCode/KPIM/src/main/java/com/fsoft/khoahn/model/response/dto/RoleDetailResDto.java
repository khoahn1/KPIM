package com.fsoft.khoahn.model.response.dto;

import java.util.List;

import lombok.Data;

@Data
public class RoleDetailResDto {

	private String id;

	private String description;

	private String roleName;

	private String createdBy;

	private String createdDate;

	private String updatedBy;

	private String updatedDate;

	private List<AuthorityDetailResDto> items;
}
