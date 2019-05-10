package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class TaskUpdateReqDto {

	private String id;

	private String taskCode;

	private ProductUpdateReqDto product;

	private PhaseUpdateReqDto phase;

	private UserUpdateReqDto user;

	private StatusDetailReqDto status;
}
