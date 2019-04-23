package com.fsoft.khoahn.model.response.dto;

import lombok.Data;

@Data
public class MaritalStatusDetailResDto {

	private Integer id;

	private String maritalStatusName;

	private String description;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;

}