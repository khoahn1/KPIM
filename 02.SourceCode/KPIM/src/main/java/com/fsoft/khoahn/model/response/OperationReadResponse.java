package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class OperationReadResponse {

	private String id;

	private String opsName;

	private String functionId;

	private String createdDate;

	private String updatedDate;

	private String createdBy;

	private String updatedBy;

}