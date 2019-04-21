package com.fsoft.khoahn.model.request.dto;

import com.fsoft.khoahn.model.dto.AuthorityDto;

import lombok.Data;

@Data
public class UserImportReqDto {
	private String id;
	private String username;
	private String fullName;
	private String email;
	private String language;
	private String avatar;
	private String password;
	private String birthday;
	private String status;
	private String gender;
	private String maritalStatus;
	private String address;
	private String phone;
	private String createdDate;
	private String updatedDate;
	private String createdBy;
	private String updatedBy;
	private AuthorityDto authority;
}
