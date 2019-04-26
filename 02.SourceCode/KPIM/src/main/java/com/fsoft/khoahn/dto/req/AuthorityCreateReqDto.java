package com.fsoft.khoahn.dto.req;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AuthorityCreateReqDto {

	private String id;

	private String userId;

	private Integer userAuthorityId;

	private String text;

	private boolean isChecked;

	private boolean isExpanded;

	private boolean isOperation;

	private String createdBy;

	private Timestamp createdDate;

	private String updatedBy;

	private Timestamp updatedDate;

}
