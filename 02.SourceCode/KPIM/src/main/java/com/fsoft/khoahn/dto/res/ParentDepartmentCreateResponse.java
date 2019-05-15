package com.fsoft.khoahn.dto.res;

import java.util.List;

import com.fsoft.khoahn.model.response.CompanyDetailResponse;

import lombok.Data;

@Data
public class ParentDepartmentCreateResponse {

	List<CompanyDetailResponse> companies;
}
