package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class LanguageDetailResponse {

	private Integer id;

	private String languageCode;

	private String languageName;

	private String description;
}