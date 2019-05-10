package com.fsoft.khoahn.dto.req;

import com.fsoft.khoahn.model.request.StatusDetailRequest;

import lombok.Data;

@Data
public class ComponentCreateReqDto {
    private Integer id;
    private String componentName;
    private StatusDetailRequest status;
}
