package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class ComponentReadRequest {
    private PaginationRequest paginationRequest;
    private List<SortRequest> sortRequest;
    private ComponentSearchRequest componentSearchRequest;
    private List<ComponentDeleteRequest> componentDeleteRequests;
}
