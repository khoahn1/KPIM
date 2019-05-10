package com.fsoft.khoahn.dto.res;

import java.util.Date;

import lombok.Data;

@Data
public class WorkLogDetailResDto {
    private Integer id;
    private Date logDate;
    private Double planEffort;
    private Double actualEffort;
    private String anken;
    private String screen;
    private String description;
    private String issue;
    private String notes;
    private Double quantity;
    private UnitDetailResDto unit;
    private TaskDetailResDto task;
    private TypeOfWorkDetailResDto typeOfWork;
    private StatusDetailResDto status;
    private Integer statusId;
}