package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class PhaseUpdateReqDto {

	private Integer id;

	private String phaseCode;

	private String phaseName;

	private String description;

}
