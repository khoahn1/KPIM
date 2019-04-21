package com.fsoft.khoahn.model;

import com.fsoft.khoahn.common.annotation.CellBindByName;

import lombok.Data;

@Data
public class CustomerImportExportContent {

	@CellBindByName(column = "Id")
	private String id;
	@CellBindByName(column = "Full Name")
	private String fullName;
	@CellBindByName(column = "Email")
	private String email;
	@CellBindByName(column = "Avatar")
	private String avatar;
	@CellBindByName(column = "Birthday")
	private String birthday;
	@CellBindByName(column = "Status")
	private String status;
	@CellBindByName(column = "Gender")
	private String gender;
	@CellBindByName(column = "Address")
	private String address;
	@CellBindByName(column = "Phone")
	private String phone;
	@CellBindByName(column = "Customer Type")
	private String customerType;
	@CellBindByName(column = "Last Trading Day")
	private String lastTradingDay;
	@CellBindByName(column = "Location")
	private String locationId;
	@CellBindByName(column = "Note")
	private String note;
	@CellBindByName(column = "Organization")
	private String organization;
	@CellBindByName(column = "Tax Code")
	private String taxCode;
	@CellBindByName(column = "Ward")
	private String wardId;
	@CellBindByName(column = "Created Date")
	private String createdDate;
	@CellBindByName(column = "Updated Date")
	private String updatedDate;
	@CellBindByName(column = "Created By")
	private String createdBy;
	@CellBindByName(column = "Updated By")
	private String updatedBy;

}
