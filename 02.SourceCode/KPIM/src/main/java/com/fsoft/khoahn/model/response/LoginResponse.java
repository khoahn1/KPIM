package com.fsoft.khoahn.model.response;


import java.util.List;

import com.fsoft.khoahn.dto.res.MasterDataResDto;
import com.fsoft.khoahn.dto.res.RoleDetailResDto;
import com.fsoft.khoahn.dto.res.UserReadResDto;

import lombok.Data;

@Data
public class LoginResponse {
	List<RoleDetailResDto> roles;
	private UserReadResDto user;
	private MasterDataResDto masterData;
}
