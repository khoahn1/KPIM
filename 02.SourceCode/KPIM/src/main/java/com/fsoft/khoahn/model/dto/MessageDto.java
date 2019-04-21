package com.fsoft.khoahn.model.dto;

import com.fsoft.khoahn.model.MessageType;

import lombok.Data;

@Data
public class MessageDto {
	private String field;
	private String code;
	private String message;
	private MessageType type;

	public MessageDto() {
		super();
	}

	public MessageDto(MessageType type, String message, String field, String code) {
		super();
		this.field = field;
		this.code = code;
		this.message = message;
		this.type = type;
	}
}
