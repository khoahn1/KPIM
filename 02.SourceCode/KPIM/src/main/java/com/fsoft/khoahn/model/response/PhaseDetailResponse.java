package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class PhaseDetailResponse {

	private String id;

	private String phaseCode;

	private String phaseName;

	private String description;

	private UnitDetailResponse unit;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;

}