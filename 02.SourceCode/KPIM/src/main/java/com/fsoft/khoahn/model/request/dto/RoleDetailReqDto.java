package com.fsoft.khoahn.model.request.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RoleDetailReqDto {

	private String id;

	private String description;

	private String roleName;

	private List<AuthorityCreateResDto> items = new ArrayList<>();

}