package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class ProjectMemberSearchRequest {
	private String projectId;
	private String username;
}
