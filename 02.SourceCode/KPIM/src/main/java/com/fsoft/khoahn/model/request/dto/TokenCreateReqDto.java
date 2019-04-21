package com.fsoft.khoahn.model.request.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TokenCreateReqDto {
	private String series;

	private String value;

	private Date date;

	private String ipAddress;

	private String userAgent;

	private String userLogin;
}
