package com.fsoft.khoahn.dto.req;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class AuthorityDetailReqDto {
	private String id;
	private String userId;
	private String roleId;
	private Integer userAuthorityId;
	private Integer roleAuthorityId;
	private String text;
	private boolean isChecked;
	private boolean isExpanded;
	private boolean isOperation;
	private List<AuthorityDetailReqDto> items;
	private String createdBy;

	private Date createdDate;

	private String updatedBy;

	private Date updatedDate;
}
