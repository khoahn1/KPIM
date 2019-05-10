package com.fsoft.khoahn.model.request;

import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;

import lombok.Data;

@Data
public class PhaseCreateRequest {

    @RequireCheck
	private String id;

	private String phaseCode;

	private String phaseName;

	private String description;

}
