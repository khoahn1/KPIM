package com.fsoft.khoahn.model;

import com.fsoft.khoahn.common.annotation.CellBindByName;

import lombok.Data;

@Data
public class UserImportExportContent {
	@CellBindByName(column = "Id")
	private String id;
	@CellBindByName(column = "Username")
	private String username;
	@CellBindByName(column = "Full Name")
	private String fullName;
	@CellBindByName(column = "Email")
	private String email;
	@CellBindByName(column = "Language")
	private String language;
	@CellBindByName(column = "Avatar")
	private String avatar;
	@CellBindByName(column = "Password")
	private String password;
	@CellBindByName(column = "Birthday")
	private String birthday;
	@CellBindByName(column = "Status")
	private String status;
	@CellBindByName(column = "Gender")
	private String gender;
	@CellBindByName(column = "Marital Status")
	private String maritalStatus;
	@CellBindByName(column = "Address")
	private String address;
	@CellBindByName(column = "Phone")
	private String phone;
	@CellBindByName(column = "Role")
	private String roleId;
	@CellBindByName(column = "Created Date")
	private String createdDate;
	@CellBindByName(column = "Updated Date")
	private String updatedDate;
	@CellBindByName(column = "Created By")
	private String createdBy;
	@CellBindByName(column = "Updated By")
	private String updatedBy;
}
