package com.fsoft.khoahn.model.request;

import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;

import lombok.Data;

@Data
public class UnitCreateRequest {

    @RequireCheck
	private String id;

	private String unitName;

	private String description;

}
