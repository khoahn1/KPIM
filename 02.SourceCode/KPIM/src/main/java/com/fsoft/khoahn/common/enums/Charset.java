package com.fsoft.khoahn.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Charset implements CodeEnum {

	UTF_8("UTF-8", "");

	private final String value;
    private final String name;

    @Override
	@JsonValue
	public String getValue() {
        return value;
    }
}
