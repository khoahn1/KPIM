package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class ProductUpdateReqDto {
    private Integer id;
    private String productName;
    private ComponentUpdateReqDto component;
}
