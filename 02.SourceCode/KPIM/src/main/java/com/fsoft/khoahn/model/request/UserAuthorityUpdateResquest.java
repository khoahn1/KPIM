package com.fsoft.khoahn.model.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserAuthorityUpdateResquest {
	private String id;
	private MultipartFile avatarFile;
	private String username;
	private String password;
	private String fullName;
	private String email;
	private LanguageDetailRequest language;
	private String avatar;
	private String birthday;
	private StatusDetailRequest status;
	private GenderDetailRequest gender;
	private MaritalStatusDetailRequest maritalStatus;
	private String address;
	private String phone;
	private RoleDetailRequest role;
	private List<AuthorityUpdateResquest> items;
}
