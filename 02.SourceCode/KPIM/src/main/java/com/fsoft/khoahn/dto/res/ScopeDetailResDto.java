package com.fsoft.khoahn.dto.res;

import java.util.Date;

import lombok.Data;

@Data
public class ScopeDetailResDto {
    private Integer id;
    private String scopeName;
    private String description;
    private Date createdDate;
    private Date updatedDate;
    private String createdBy;
    private String updatedBy;
}
