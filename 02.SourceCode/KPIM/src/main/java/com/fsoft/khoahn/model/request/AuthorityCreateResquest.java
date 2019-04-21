package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class AuthorityCreateResquest {

	private String id;

	private String userId;

	private Integer userAuthorityId;

	private String text;

	private boolean isChecked;

	private boolean isExpanded;

	private boolean isOperation;

	private String createdBy;

	private String createdDate;

	private String updatedBy;

	private String updatedDate;

}
