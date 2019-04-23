package com.fsoft.khoahn.model.request;

import org.springframework.web.multipart.MultipartFile;

import com.fsoft.khoahn.common.annotation.constraints.DateCheck;
import com.fsoft.khoahn.common.annotation.constraints.EmailCheck;
import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;
import com.fsoft.khoahn.model.request.dto.RoleDetailReqDto;

import lombok.Data;

@Data
public class UserCreateRequest {
	private MultipartFile avatarFile;
	@RequireCheck
	private String username;
	@RequireCheck
	private String password;
	private String fullName;
	@EmailCheck
	private String email;
	private String language;
	private String avatar;
	@DateCheck(strick = true)
	private String birthday;
	private String status;
	private String gender;
	private String maritalStatus;
	private String address;
	private String phone;
	@RequireCheck
	private RoleDetailReqDto role;
}
