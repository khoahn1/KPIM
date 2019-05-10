package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class ComponentReadReqDto {
    private PaginationReqDto paginationRequest;
    private List<SortReqDto> sortRequest;
    private ComponentSearchReqDto componentSearchRequest;
    private List<ComponentDeleteReqDto> componentDeleteRequest;
}
