package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class LanguageDetailReqDto {

	private Integer id;

	private String languageCode;

	private String languageName;

	private String description;
}