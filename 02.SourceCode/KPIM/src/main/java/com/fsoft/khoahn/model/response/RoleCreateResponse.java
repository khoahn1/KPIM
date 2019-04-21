package com.fsoft.khoahn.model.response;

import java.util.List;

import lombok.Data;

@Data
public class RoleCreateResponse {
	private List<AuthorityDetailResponse> items;
}
