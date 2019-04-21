package com.fsoft.khoahn.model;

import com.fsoft.khoahn.common.annotation.CellBindByName;

import lombok.Data;

@Data
public class SupplierImportExportContent {

	@CellBindByName(column = "Id")
	private String id;
	@CellBindByName(column = "Supplier Name")
	private String supplierName;
	@CellBindByName(column = "Email")
	private String email;
	@CellBindByName(column = "Status")
	private String status;
	@CellBindByName(column = "Address")
	private String address;
	@CellBindByName(column = "Phone")
	private String phone;
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
