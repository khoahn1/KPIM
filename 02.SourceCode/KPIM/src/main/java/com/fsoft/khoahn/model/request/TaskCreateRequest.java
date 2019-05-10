package com.fsoft.khoahn.model.request;

import javax.validation.Valid;

import lombok.Data;

@Data
public class TaskCreateRequest {

    private String id;

	private String taskCode;

	@Valid
	private ProductCreateRequest product;

	@Valid
	private PhaseCreateRequest phase;

	private UserCreateRequest user;

	private StatusDetailRequest status;
}
