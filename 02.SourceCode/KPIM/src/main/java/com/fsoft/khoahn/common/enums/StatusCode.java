package com.fsoft.khoahn.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode implements CodeEnum {

    SUCCESS("200", "Success"),
    FAIL("400", "Fail"),
    UNAUTHORIZED("401", "Unauthorized"),
    NOT_FOUND("404", "Not found"),
    INTERNAL_SERVER_ERROR("500", "Intenal Server Error"),
    VALIDATION_ERROR("2000", "Validation error"),
    SYSTEM_FAILURE("1001", "System Failure"),
    TOKEN_EXPIRED("1004", "Token expired"),
    TOKEN_FAILURED("1005", "Token failured"),
    BAD_CREDENTIALS("1006", "Bad credentials");

    private final String value;
    private final String name;

    @Override
	@JsonValue
    public String getValue() {
        return value;
    }
}
