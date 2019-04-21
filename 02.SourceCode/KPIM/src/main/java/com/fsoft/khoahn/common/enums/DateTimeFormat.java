package com.fsoft.khoahn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DateTimeFormat implements CodeEnum {

	DDMMYYYY("ddMMyyyy", ""), SLASH_DDMMYYYY("dd/MM/yyyy", ""),
	DDMMYYYYHHMMSS("ddMMyyyyHHmmss", ""),
	SLASH_DD_MM_YYYY_HH_MM_SS("dd/MM/yyyy HH:mm:ss", ""),
	SLASH_DD_MM_YYYY_HH_MM_SS_FF("dd/MM/yyyy HH:mm:ss.SSS", ""),
	SLASH_DDMMYYYY_HHMMSSFF("dd-MM-yyyy_hh-mm-ss-SSS", ""),
	DASH_DD_MM_YYYY("dd-MM-yyyy", "");
	
	private final String value;
	private final String name;
}
