package com.fsoft.khoahn.model.response;


import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class UserReadResponse {
	private Page<UserDetailResponse> users;
}
