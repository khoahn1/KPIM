package com.fsoft.khoahn.model.response.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDetailResDto {
	private String id;
	private String fullName;
	private String email;
	private String language;
	private String avatar;
	private String username;
	private String password;
	private String birthday;
	private StatusDetailResDto status;
	private GenderDetailResDto gender;
	private MaritalStatusDetailResDto maritalStatus;
	private RoleDetailResDto role;
	private String address;
	private String phone;
	private String createdDate;
	private String updatedDate;
	private String createdBy;
	private String updatedBy;
	private List<AuthorityDetailResDto> items;
}
