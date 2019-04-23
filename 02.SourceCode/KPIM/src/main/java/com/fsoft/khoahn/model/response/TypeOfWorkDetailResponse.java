package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class TypeOfWorkDetailResponse {

	private Integer id;

	private String typeName;

	private String description;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;

}