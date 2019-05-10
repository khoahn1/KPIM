package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class WorkLogReadRequest {
    private PaginationRequest paginationRequest;
    private List<SortRequest> sortRequest;
    private WorkLogSearchRequest workLogSearchRequest;
}
