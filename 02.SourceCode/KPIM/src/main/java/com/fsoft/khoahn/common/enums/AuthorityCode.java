package com.fsoft.khoahn.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthorityCode implements CodeEnum {

	ADMIN("1", "admin"), USER("2", "user");

	private final String value;
    private final String name;

    @Override
	@JsonValue
	public String getValue() {
        return value;
    }
}
