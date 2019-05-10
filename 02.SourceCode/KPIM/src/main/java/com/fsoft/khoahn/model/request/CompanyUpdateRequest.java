package com.fsoft.khoahn.model.request;

import java.sql.Timestamp;

import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;

import lombok.Data;

@Data
public class CompanyUpdateRequest {

	private String id;
	
	@RequireCheck
	private String companyCode;

	@RequireCheck
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