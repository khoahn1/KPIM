package com.fsoft.khoahn.model.request.dto;

import lombok.Data;

@Data
public class OperationCreateReqDto {

	private String id;

	private String opsName;

	private String functionId;

	private String createdDate;

	private String updatedDate;

	private String createdBy;

	private String updatedBy;

}