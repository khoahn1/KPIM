package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class RoleDetailRequest {
	private String id;

	private String description;

	private String roleName;

	private List<AuthorityCreateResquest> items;
}