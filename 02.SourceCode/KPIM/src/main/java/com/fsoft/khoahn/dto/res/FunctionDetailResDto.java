package com.fsoft.khoahn.dto.res;

import java.util.List;

import lombok.Data;

@Data
public class FunctionDetailResDto {

	private String id;

	private String functionName;

    private String privilegeId;

	private String createdBy;

	private String createdDate;

	private String updatedBy;

	private String updatedDate;

	private List<OperationDetailResDto> operations;

}