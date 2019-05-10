package com.fsoft.khoahn.model.request;

import org.springframework.web.multipart.MultipartFile;

import com.fsoft.khoahn.common.annotation.constraints.DateCheck;
import com.fsoft.khoahn.common.annotation.constraints.EmailCheck;
import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String id;
	private MultipartFile avatarFile;
	@RequireCheck
	private String username;
	@RequireCheck
	private String password;
	private String fullName;
	@EmailCheck
	private String email;
	private LanguageDetailRequest language;
	private String avatar;
	@DateCheck(strick = true)
	private String birthday;
	private StatusDetailRequest status;
	private GenderDetailRequest gender;
	private MaritalStatusDetailRequest maritalStatus;
	private String address;
	private String phone;
	@RequireCheck
	private RoleDetailRequest role;
}
