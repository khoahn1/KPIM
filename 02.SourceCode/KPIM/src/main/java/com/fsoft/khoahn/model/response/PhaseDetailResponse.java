package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class PhaseDetailResponse {

	private Integer id;

	private String phaseCode;

	private String phaseName;

	private String description;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;

}