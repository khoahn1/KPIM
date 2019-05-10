package com.fsoft.khoahn.model.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class WorkLogReadResponse {
    private Page<WorkLogDetailResponse> worklogs;
    private List<UnitDetailResponse> units;
    private List<ComponentDetailResponse> components;
    private List<PhaseDetailResponse> phases;
    private List<TypeOfWorkDetailResponse> typeOfWorks;
    private List<ProductDetailResponse> products;
}
