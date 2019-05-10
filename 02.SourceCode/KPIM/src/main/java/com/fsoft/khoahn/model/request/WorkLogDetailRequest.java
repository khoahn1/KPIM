package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class WorkLogDetailRequest {
    private String id;
    private String logDate;
    private Double planEffort;
    private Double actualEffort;
    private String anken;
    private String screen;
    private String description;
    private String issue;
    private String notes;
    private Integer quantity;
    private UnitCreateRequest unit;
}
