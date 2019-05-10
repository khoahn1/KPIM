package com.fsoft.khoahn.model.request;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    private String id;
    private String productName;
    private ComponentUpdateRequest component;
}
