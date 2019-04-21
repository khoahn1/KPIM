package com.fsoft.khoahn.model.response.dto;

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
	private Integer status;
	private Integer gender;
	private Integer maritalStatus;
	private String address;
	private String phone;
	private String createdDate;
	private String updatedDate;
	private String createdBy;
	private String updatedBy;
	private RoleDetailResDto role;
	private List<UserAuthorityReadResDto> authorities;
}
