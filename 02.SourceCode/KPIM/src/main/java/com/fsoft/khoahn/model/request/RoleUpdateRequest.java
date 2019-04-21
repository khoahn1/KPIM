package com.fsoft.khoahn.model.request;

import java.util.List;

import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;

import lombok.Data;

@Data
public class RoleUpdateRequest {

	private String id;

	private String description;

	@RequireCheck
	private String roleName;

	private List<AuthorityUpdateResquest> items;
}