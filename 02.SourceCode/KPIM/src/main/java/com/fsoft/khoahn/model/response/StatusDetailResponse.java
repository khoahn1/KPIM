package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class StatusDetailResponse {

	private Integer id;

	private String statusName;

	private String statusType;

	private String description;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;
}