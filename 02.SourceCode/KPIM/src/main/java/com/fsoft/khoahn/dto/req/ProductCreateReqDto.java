package com.fsoft.khoahn.dto.req;

import com.fsoft.khoahn.model.request.StatusDetailRequest;

import lombok.Data;

@Data
public class ProductCreateReqDto {
    private Integer id;
    private String productName;
    private ComponentCreateReqDto component;
    private ScopeCreateReqDto scope;
    private StatusDetailRequest status;
}
