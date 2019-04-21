package com.fsoft.khoahn.model.response;


import lombok.Data;

@Data
public class UserExportResponse {
	private String fileDownloadUri;
	private String fileName;
}
