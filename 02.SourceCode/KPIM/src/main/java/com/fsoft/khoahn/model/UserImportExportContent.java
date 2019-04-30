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
	private String languageId;
	@CellBindByName(column = "Avatar")
	private String avatar;
	@CellBindByName(column = "Password")
	private String password;
	@CellBindByName(column = "Birthday")
	private String birthday;
	@CellBindByName(column = "Status")
	private String statusId;
	@CellBindByName(column = "Gender")
	private String genderId;
	@CellBindByName(column = "Marital Status")
	private String maritalStatusId;
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
