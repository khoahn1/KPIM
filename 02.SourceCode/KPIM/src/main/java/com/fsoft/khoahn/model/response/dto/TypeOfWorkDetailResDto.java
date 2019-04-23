package com.fsoft.khoahn.model.response.dto;

import lombok.Data;

@Data
public class TypeOfWorkDetailResDto {

	private Integer id;

	private String typeName;

	private String description;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;

}