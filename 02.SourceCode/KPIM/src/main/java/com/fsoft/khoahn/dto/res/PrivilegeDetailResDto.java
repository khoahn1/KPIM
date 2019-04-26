package com.fsoft.khoahn.dto.res;

import java.util.List;

import lombok.Data;

@Data
public class PrivilegeDetailResDto {

	private String id;

	private String privilegeName;

	private String createdBy;

	private String createdDate;

	private String updatedBy;

	private String updatedDate;

	private List<FunctionDetailResDto> functions;
}