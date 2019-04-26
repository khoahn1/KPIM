package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class RoleUpdateReqDto {

	private String id;

	private String description;

	private String roleName;

	private List<AuthorityUpdateReqDto> items;
}