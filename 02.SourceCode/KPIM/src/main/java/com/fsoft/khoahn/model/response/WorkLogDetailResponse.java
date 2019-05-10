package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class WorkLogDetailResponse {
    private String id;
    private String logDate;
    private Double planEffort;
    private Double actualEffort;
    private String anken;
    private String screen;
    private String description;
    private String issue;
    private String notes;
    private Double quantity;
    private UnitDetailResponse unit;
    private TaskDetailResponse task;
    private TypeOfWorkDetailResponse typeOfWork;
    private StatusDetailResponse status;
    private String statusId;
}