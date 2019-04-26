package com.fsoft.khoahn.dto.res;

import lombok.Data;

@Data
public class UnitDetailResDto {

	private Integer id;

	private String unitName;

	private String description;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;

}