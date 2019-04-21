package com.fsoft.khoahn.model.request.dto;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserUpdateReqDto {
	private String id;
	private MultipartFile avatarFile;
	private String username;
	private String fullName;
	private String email;
	private String language;
	private String avatar;
	private String password;
	private Date birthday;
	private int status;
	private int gender;
	private int maritalStatus;
	private String address;
	private String phone;
	private RoleUpdateReqDto role;
	private List<AuthorityUpdateResDto> items;
}
