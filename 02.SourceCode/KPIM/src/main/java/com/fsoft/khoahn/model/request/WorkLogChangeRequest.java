package com.fsoft.khoahn.model.request;

import java.util.List;

import javax.validation.Valid;

import lombok.Data;

@Data
public class WorkLogChangeRequest {
    @Valid
    private List<WorkLogCreateRequest> worklogs;
}
