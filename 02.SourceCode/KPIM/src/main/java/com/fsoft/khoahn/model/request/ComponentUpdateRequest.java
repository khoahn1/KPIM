package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class ComponentUpdateRequest {
    private String id;
    private String componentName;
    private String status;
}
