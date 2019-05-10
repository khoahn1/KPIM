package com.fsoft.khoahn.model.request;

import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;

import lombok.Data;

@Data
public class ScopeCreateRequest {

    @RequireCheck
    private String id;

    private String scopeName;

    private String description;

}