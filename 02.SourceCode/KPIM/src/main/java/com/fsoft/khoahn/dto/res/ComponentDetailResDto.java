package com.fsoft.khoahn.dto.res;

import java.util.Date;

import lombok.Data;

@Data
public class ComponentDetailResDto {
    private Integer id;
    private String componentName;
    private StatusDetailResDto status;
    private Date createdDate;
    private Date updatedDate;
    private String createdBy;
    private String updatedBy;
}
