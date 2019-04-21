package com.fsoft.khoahn.model.response.dto;

import java.util.List;

import lombok.Data;

@Data
public class FunctionReadResDto {

	private String id;

	private String functionName;

    private String privilegeId;

	private String createdBy;

	private String createdDate;

	private String updatedBy;

	private String updatedDate;

	private List<OperationReadResDto> operations;

}