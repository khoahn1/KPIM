package com.fsoft.khoahn.model.response;

import java.util.Date;

import lombok.Data;

@Data
public class TaskDetailResponse {

    private String id;

    private String taskCode;

    private ProductDetailResponse product;

    private PhaseDetailResponse phase;

    private UserDetailResponse user;

    private StatusDetailResponse status;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

}
