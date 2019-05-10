package com.fsoft.khoahn.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.dto.req.ComponentCreateReqDto;
import com.fsoft.khoahn.dto.req.ComponentDeleteReqDto;
import com.fsoft.khoahn.dto.req.ComponentReadReqDto;
import com.fsoft.khoahn.dto.req.ComponentUpdateReqDto;
import com.fsoft.khoahn.dto.res.ComponentDetailResDto;

public interface ComponentService {

    ComponentDetailResDto findOne(Integer id);

    Page<ComponentDetailResDto> findAll(ComponentReadReqDto componentReadReqDto);

    boolean isExistComponentName(String componentName);

    void save(ComponentCreateReqDto componentCreateReqDto);

    void update(ComponentUpdateReqDto componentUpdateReqDto);

    void delete(ComponentDeleteReqDto componentDeleteReqDto);

    List<ComponentDetailResDto> findAll();

}
