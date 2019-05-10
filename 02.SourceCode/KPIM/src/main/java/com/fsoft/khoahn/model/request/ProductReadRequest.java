package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class ProductReadRequest {
    private PaginationRequest paginationRequest;
    private List<SortRequest> sortRequest;
    private ProductSearchRequest productSearchRequest;
    private List<ProductDeleteRequest> productDeleteRequest;
}
