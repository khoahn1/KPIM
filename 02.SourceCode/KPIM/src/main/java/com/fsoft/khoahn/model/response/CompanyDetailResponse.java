package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class CompanyDetailResponse {

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
