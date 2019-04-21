package com.fsoft.khoahn.model.response;

import java.util.List;

import lombok.Data;

@Data
public class PrivilegeDetailResponse {

	private String id;

	private String privilegeName;

	private String createdBy;

	private String createdDate;

	private String updatedBy;

	private String updatedDate;

	private List<FunctionReadResponse> functions;
}