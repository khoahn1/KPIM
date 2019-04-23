package com.fsoft.khoahn.model.response.dto;

import lombok.Data;

@Data
public class LanguageDetailResDto {

	private Integer id;

	private String languageCode;

	private String languageName;

	private String description;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;

}