package com.fsoft.khoahn.dto.req;

import java.util.List;

import lombok.Data;

@Data
public class WorkLogReadReqDto {
    private PaginationReqDto paginationRequest;
    private List<SortReqDto> sortRequest;
    private WorkLogSearchReqDto workLogSearchRequest;
    private List<WorkLogDeleteReqDto> workLogDeleteRequest;
}
