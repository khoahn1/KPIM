package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class OperationCreateRequest {

	private String id;

	private String opsName;

	private String functionId;

	private String createdDate;

	private String updatedDate;

	private String createdBy;

	private String updatedBy;

}