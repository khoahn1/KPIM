package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class ScopeDetailResponse {
    private String id;
    private String scopeName;
    private String description;
    private String createdDate;
    private String updatedDate;
    private String createdBy;
    private String updatedBy;
}
