package com.fsoft.khoahn.model.response;

import java.util.List;

import lombok.Data;

@Data
public class FunctionReadResponse {

	private String id;

	private String functionName;

    private String privilegeId;

	private String createdBy;

	private String createdDate;

	private String updatedBy;

	private String updatedDate;

	private List<OperationReadResponse> operations;

}