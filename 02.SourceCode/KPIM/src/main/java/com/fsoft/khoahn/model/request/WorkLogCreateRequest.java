package com.fsoft.khoahn.model.request;

import javax.validation.Valid;

import com.fsoft.khoahn.common.annotation.constraints.DateCheck;
import com.fsoft.khoahn.common.annotation.constraints.DecimalNumbericCheck;
import com.fsoft.khoahn.common.annotation.constraints.MaxLengthCheck;
import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;
import com.fsoft.khoahn.common.enums.DateTimeFormat;

import lombok.Data;

@Data
public class WorkLogCreateRequest {

    private String id;

    @RequireCheck
    @DateCheck(format = DateTimeFormat.SLASH_DDMMYYYY)
    private String logDate;

    @MaxLengthCheck(max = 50)
    private String anken;

    @MaxLengthCheck(max = 50)
    private String screen;

    @MaxLengthCheck(max = 500)
    private String description;

    @RequireCheck
    @DecimalNumbericCheck
    private String planEffort;

    @RequireCheck
    @DecimalNumbericCheck
    private String actualEffort;

    @DecimalNumbericCheck
    private String quantity;

    @MaxLengthCheck(max = 500)
    private String issue;

    @MaxLengthCheck(max = 500)
    private String notes;

    private StatusDetailRequest status;

    @Valid
    private TaskCreateRequest task;

    @Valid
    private TypeOfWorkCreateRequest typeOfWork;

    @Valid
    private UnitCreateRequest unit;

}
