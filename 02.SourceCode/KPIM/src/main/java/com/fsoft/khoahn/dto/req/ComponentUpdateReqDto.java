package com.fsoft.khoahn.dto.req;

import lombok.Data;

@Data
public class ComponentUpdateReqDto {
    private Integer id;
    private String componentName;
    private StatusDetailReqDto status;
}
