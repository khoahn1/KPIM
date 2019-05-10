package com.fsoft.khoahn.service;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.dto.req.WorkLogChangeReqDto;
import com.fsoft.khoahn.dto.req.WorkLogDeleteReqDto;
import com.fsoft.khoahn.dto.req.WorkLogReadReqDto;
import com.fsoft.khoahn.dto.res.WorkLogDetailResDto;

public interface WorkLogService {
    Page<WorkLogDetailResDto> findAll(WorkLogReadReqDto workLogReadReqDto);
    void changes(WorkLogChangeReqDto workLogChangeReqDto);
    void deletes(WorkLogDeleteReqDto workLogDeleteReqDto);
}
