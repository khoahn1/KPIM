package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class WorkLogChangeReqDto {
    private List<WorkLogCreateReqDto> worklogs;
}
