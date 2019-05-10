package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class TaskCreateReqDto {

    private String id;

	private String taskCode;

	private ProductCreateReqDto product;

	private PhaseCreateReqDto phase;

	private UserCreateReqDto user;

	private StatusDetailReqDto status;

}
