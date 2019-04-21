package com.fsoft.khoahn.model.request;

import org.springframework.web.multipart.MultipartFile;

import com.fsoft.khoahn.common.annotation.constraints.RequireFileCheck;

import lombok.Data;

@Data
public class FileUploadRequest {

	@RequireFileCheck
	private MultipartFile file;

}
