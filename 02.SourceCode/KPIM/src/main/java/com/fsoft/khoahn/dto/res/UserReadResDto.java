package com.fsoft.khoahn.dto.res;

import java.util.List;

import lombok.Data;

@Data
public class UserReadResDto {
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
	private List<UserAuthorityReadResDto> authorities;
}
