package com.fsoft.khoahn.model.response;


import java.util.List;

import lombok.Data;

@Data
public class UserCreateResponse {
	private List<RoleDetailResponse> roles;
}
