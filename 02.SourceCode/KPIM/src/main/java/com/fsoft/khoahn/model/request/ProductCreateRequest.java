package com.fsoft.khoahn.model.request;

import javax.validation.Valid;

import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;

import lombok.Data;

@Data
public class ProductCreateRequest {

    @RequireCheck
    private String id;

    private String productName;

    @Valid
    private ComponentCreateRequest component;

    @Valid
    private ScopeCreateRequest scope;

    private StatusDetailRequest status;

}
