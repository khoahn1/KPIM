package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class ComponentDetailResponse {
    private String id;
    private String componentName;
    private StatusDetailResponse status;
    private String createdDate;
    private String updatedDate;
    private String createdBy;
    private String updatedBy;
}
