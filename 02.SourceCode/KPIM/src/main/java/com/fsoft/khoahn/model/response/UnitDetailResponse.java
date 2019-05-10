package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class UnitDetailResponse {

	private String id;

	private String unitName;

	private String description;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;

}