package com.fsoft.khoahn.dto.req;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RoleCreateReqDto {

	private String id;

	private String description;

	private String roleName;

	private List<AuthorityCreateReqDto> items = new ArrayList<>();

}