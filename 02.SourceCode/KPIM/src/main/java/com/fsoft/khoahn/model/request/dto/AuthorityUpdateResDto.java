package com.fsoft.khoahn.model.request.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AuthorityUpdateResDto {

	private String id;

	private String userId;

	private Integer userAuthorityId;

	private Integer roleAuthorityId;

	private String text;

	private boolean isChecked;

	private boolean isExpanded;

	private boolean isOperation;

	private String createdBy;

	private Timestamp createdDate;

	private String updatedBy;

	private Timestamp updatedDate;

}
