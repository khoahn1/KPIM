package com.fsoft.khoahn.dto.res;

import lombok.Data;

@Data
public class PhaseDetailResDto {

	private Integer id;

	private String phaseCode;

	private String phaseName;

	private String description;

	private UnitDetailResDto unit;

	protected String createdBy;

	protected String createdDate;

	protected String updatedBy;

	protected String updatedDate;

}