package com.fsoft.khoahn.dto.res;

import java.util.Date;

import lombok.Data;

@Data
public class ProductDetailResDto {
    private Integer id;
    private String productName;
    private StatusDetailResDto status;
    private ComponentDetailResDto component;
    private ScopeDetailResDto scope;
    private Date createdDate;
    private Date updatedDate;
    private String createdBy;
    private String updatedBy;

}
