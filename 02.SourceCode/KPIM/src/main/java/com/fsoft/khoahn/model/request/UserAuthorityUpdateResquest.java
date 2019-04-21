package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class UserAuthorityUpdateResquest {
	private String id;
	private String username;
	private String fullName;
	private String email;
	private String language;
	private String avatar;
	private String password;
	private String birthday;
	private int status;
	private int gender;
	private int maritalStatus;
	private String address;
	private String phone;
	private RoleUpdateRequest role;
	private List<AuthorityUpdateResquest> items;
}
