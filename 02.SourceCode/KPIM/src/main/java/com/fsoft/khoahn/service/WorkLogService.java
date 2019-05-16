package com.fsoft.khoahn.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.dto.req.WorkLogChangeReqDto;
import com.fsoft.khoahn.dto.req.WorkLogDeleteReqDto;
import com.fsoft.khoahn.dto.req.WorkLogReadReqDto;
import com.fsoft.khoahn.dto.res.WorkLogDetailResDto;
import com.fsoft.khoahn.model.WorkLogImportExportContent;

public interface WorkLogService {
    Page<WorkLogDetailResDto> findAll(WorkLogReadReqDto workLogReadReqDto);
    void changes(WorkLogChangeReqDto workLogChangeReqDto);
    void deletes(WorkLogDeleteReqDto workLogDeleteReqDto);
    void imports(List<WorkLogImportExportContent> importDatas);
}
