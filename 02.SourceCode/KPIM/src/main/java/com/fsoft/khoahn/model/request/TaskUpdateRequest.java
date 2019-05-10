package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class TaskUpdateRequest {

	private String id;

	private String taskCode;

	private ProductCreateRequest product;

	private PhaseCreateRequest phase;

	private UserCreateRequest user;

	private StatusDetailRequest status;
}
