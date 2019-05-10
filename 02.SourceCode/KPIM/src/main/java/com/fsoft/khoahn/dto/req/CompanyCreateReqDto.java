package com.fsoft.khoahn.dto.req;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CompanyCreateReqDto {

	private String companyCode;

	private String companyName;

	private String taxCode;

	private String address;

	private String province;
	
	private String district;

	private String phone;

	private Integer status;

	private Timestamp createdDate;
	
	private Timestamp updatedDate;
	
	private String createdBy;
	
	private String updatedBy;
}