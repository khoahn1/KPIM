package com.fsoft.khoahn.model.response;


import java.util.List;

import lombok.Data;

@Data
public class UserUpdateResponse {
	private UserDetailResponse userDetailResponse;
	private List<RoleDetailResponse> roles;
}
