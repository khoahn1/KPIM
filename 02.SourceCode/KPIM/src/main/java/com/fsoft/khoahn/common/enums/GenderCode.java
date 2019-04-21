package com.fsoft.khoahn.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenderCode implements CodeEnum {

	MALE("1", "male"), FEMALE("2", "female");

    private final String value;
    private final String name;

    @Override
	@JsonValue
    public String getValue() {
        return value;
    }
}
