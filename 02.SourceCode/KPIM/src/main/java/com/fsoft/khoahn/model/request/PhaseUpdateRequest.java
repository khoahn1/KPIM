package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class PhaseUpdateRequest {

	private Integer id;

	private String phaseCode;

	private String phaseName;

	private String description;

}
