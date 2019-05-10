package com.fsoft.khoahn.dto.req;

import java.util.Date;

import lombok.Data;

@Data
public class WorkLogCreateReqDto {
    private Integer id;
    private Date logDate;
    private String taskId;
    private Integer componentId;
    private Integer phaseId;
    private String anken;
    private String screen;
    private String description;
    private Integer typeOfWorkId;
    private Double planEffort;
    private Double actualEffort;
    private Double quantity;
    private Integer unitId;
    private String issue;
    private String notes;
    private Integer statusId;
    private StatusDetailReqDto status;
    private TaskCreateReqDto task;
    private TypeOfWorkCreateReqDto typeOfWork;
    private UnitCreateReqDto unit;
}
