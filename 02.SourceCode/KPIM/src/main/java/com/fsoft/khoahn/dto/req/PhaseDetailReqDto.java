package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class PhaseDetailReqDto {

	private Integer id;

	private String phaseCode;

	private String phaseName;

	private String description;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;

}