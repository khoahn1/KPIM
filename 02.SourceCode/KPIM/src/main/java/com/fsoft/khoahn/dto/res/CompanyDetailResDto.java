package com.fsoft.khoahn.dto.res;

import lombok.Data;

@Data
public class CompanyDetailResDto {

	private String id;

	private String companyCode;

	private String companyName;

	private String taxCode;

	private String address;

	private String province;

	private String district;
	
	private String phone;

	private String status;

	private String createdDate;

	private String updatedDate;

	private String createdBy;

	private String updatedBy;
}
