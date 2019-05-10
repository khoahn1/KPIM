package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class WorkLogDeleteReqDto {
    private List<Integer> ids;
}
