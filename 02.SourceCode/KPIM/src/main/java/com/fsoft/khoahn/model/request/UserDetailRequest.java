package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class UserDetailRequest {
	private String id;
	private String fullName;
	private String email;
	private LanguageDetailRequest language;
	private String avatar;
	private String username;
	private String password;
	private String birthday;
	private StatusDetailRequest status;
	private GenderDetailRequest gender;
	private MaritalStatusDetailRequest maritalStatus;
	private String address;
	private String phone;
	private String createdDate;
	private String updatedDate;
	private String createdBy;
	private String updatedBy;
	private RoleDetailRequest role;
}
