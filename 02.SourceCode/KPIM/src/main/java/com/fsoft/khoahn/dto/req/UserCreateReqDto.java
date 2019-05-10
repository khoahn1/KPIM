package com.fsoft.khoahn.dto.req;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserCreateReqDto {
    private String id;
	private MultipartFile avatarFile;
	private String username;
	private String fullName;
	private String email;
	private LanguageDetailReqDto language;
	private String avatar;
	private String password;
	private String birthday;
	private StatusDetailReqDto status;
	private GenderDetailReqDto gender;
	private MaritalStatusDetailReqDto maritalStatus;
	private String address;
	private String phone;
	private RoleDetailReqDto role;
}
