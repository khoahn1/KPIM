package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class ProductReadReqDto {
    private PaginationReqDto paginationRequest;
    private List<SortReqDto> sortRequest;
    private ProductSearchReqDto productSearchRequest;
    private List<ProductDeleteReqDto> productDeleteRequest;
}
