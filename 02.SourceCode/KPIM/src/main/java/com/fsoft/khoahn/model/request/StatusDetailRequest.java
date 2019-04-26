package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class StatusDetailRequest {

	private Integer id;

	private String statusName;

	private String statusType;

	private String description;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;
}