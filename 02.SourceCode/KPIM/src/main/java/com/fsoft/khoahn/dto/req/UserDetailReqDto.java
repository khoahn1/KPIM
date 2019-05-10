package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class UserDetailReqDto {
	private String id;
	private String fullName;
	private String email;
	private String language;
	private String avatar;
	private String username;
	private String password;
	private String birthday;
	private StatusDetailReqDto status;
	private GenderDetailReqDto gender;
	private MaritalStatusDetailReqDto maritalStatus;
	private RoleDetailReqDto role;
	private String address;
	private String phone;
	private String createdDate;
	private String updatedDate;
	private String createdBy;
	private String updatedBy;
	private List<AuthorityDetailReqDto> items;
}
