package com.fsoft.khoahn.model.response.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TokenDetailResDto {
	private String series;

	private String value;

	private Date date;

	private String ipAddress;

	private String userAgent;

	private String userLogin;
}
