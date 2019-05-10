package com.fsoft.khoahn.model.response;

import lombok.Data;

@Data
public class ProductDetailResponse {
    private String id;
    private String productName;
    private StatusDetailResponse status;
    private ComponentDetailResponse component;
    private ScopeDetailResponse scope;
}
