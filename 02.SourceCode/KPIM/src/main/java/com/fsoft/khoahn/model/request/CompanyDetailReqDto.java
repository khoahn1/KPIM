package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class CompanyDetailReqDto {

	private String id;
	
	private String companyCode;

	private String companyName;
	
	private String taxCode;
	
	private String address;
	
	private String province;
	
	private String district;
	
	private String phone;

	private Integer status;

}
