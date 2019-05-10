package com.fsoft.khoahn.dto.res;

import java.util.Date;

import lombok.Data;

@Data
public class TaskDetailResDto {

	private String id;

	private String taskCode;

	private ProductDetailResDto product;

	private PhaseDetailResDto phase;

	private UserDetailResDto user;

	private StatusDetailResDto status;

	private String createdBy;

	private Date createdDate;

	private String updatedBy;

	private Date updatedDate;
}
