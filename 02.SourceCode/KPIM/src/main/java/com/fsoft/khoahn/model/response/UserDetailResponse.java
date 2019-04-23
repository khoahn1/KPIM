package com.fsoft.khoahn.model.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserDetailResponse {
	private String id;
	private String fullName;
	private String email;
	private String language;
	private String avatar;
	private String username;
	private String password;
	private String birthday;
	private String address;
	private String phone;
	private StatusDetailResponse status;
	private PhaseDetailResponse gender;
	private MaritalStatusDetailResponse maritalStatus;
	private RoleDetailResponse role;
	private String createdDate;
	private String updatedDate;
	private String createdBy;
	private String updatedBy;
	private List<AuthorityDetailResponse> items = new ArrayList<AuthorityDetailResponse>();
}
