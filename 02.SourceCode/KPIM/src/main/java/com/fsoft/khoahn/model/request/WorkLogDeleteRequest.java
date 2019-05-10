package com.fsoft.khoahn.model.request;

import java.util.List;

import lombok.Data;

@Data
public class WorkLogDeleteRequest {
    private List<String> ids;
}
