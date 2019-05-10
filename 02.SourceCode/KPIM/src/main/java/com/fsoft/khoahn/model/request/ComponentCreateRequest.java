package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class ComponentCreateRequest {

    private String id;

    private String componentName;

    private StatusDetailRequest status;

}
