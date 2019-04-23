package com.fsoft.khoahn.model.response.dto;

import lombok.Data;

@Data
public class StatusDetailResDto {

	private Integer id;

	private String statusName;

	private String statusType;

	private String description;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;
}