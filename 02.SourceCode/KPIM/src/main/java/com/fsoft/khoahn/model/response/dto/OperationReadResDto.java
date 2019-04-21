package com.fsoft.khoahn.model.response.dto;

import lombok.Data;

@Data
public class OperationReadResDto {

	private String id;

	private String opsName;

	private String functionId;

	private String createdDate;

	private String updatedDate;

	private String createdBy;

	private String updatedBy;

}