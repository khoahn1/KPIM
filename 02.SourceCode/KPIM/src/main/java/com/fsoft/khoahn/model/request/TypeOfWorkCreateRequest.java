package com.fsoft.khoahn.model.request;

import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;

import lombok.Data;

@Data
public class TypeOfWorkCreateRequest {

    @RequireCheck
	private String id;

	private String typeName;

	private String description;

}
