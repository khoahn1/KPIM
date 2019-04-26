package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class LanguageDetailRequest {

	private Integer id;

	private String languageCode;

	private String languageName;

	private String description;
}