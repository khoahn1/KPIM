package com.fsoft.khoahn.model.request;

import com.fsoft.khoahn.model.dto.AuthorityDto;

import lombok.Data;

@Data
public class UserSearchRequest {
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
	private AuthorityDto authority;
}
