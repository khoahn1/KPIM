package com.fsoft.khoahn.model.response;

import java.util.List;

import lombok.Data;

@Data
public class AuthorityDetailResponse {
	private String id;
	private String userId;
	private String roleId;
	private Integer userAuthorityId;
	private Integer roleAuthorityId;
	private String text;
	private boolean isChecked;
	private boolean isExpanded;
	private boolean isOperation;
	private List<AuthorityDetailResponse> items;
	private String createdBy;

	private String createdDate;

	private String updatedBy;

	private String updatedDate;

}
