package com.fsoft.khoahn.dto.req;

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
