package com.fsoft.khoahn.model.response;

import java.util.List;

import lombok.Data;

@Data
public class PrivilegeReadResponse {

	private List<AuthorityDetailResponse> privileges;
}