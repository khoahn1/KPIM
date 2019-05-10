package com.fsoft.khoahn.dto.res;

import java.util.List;

import lombok.Data;

@Data
public class ProjectMemberReadResDto {
	private List<UserDetailResDto> users;

}
