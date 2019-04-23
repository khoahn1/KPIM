package com.fsoft.khoahn.service;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.model.request.dto.UnitCreateReqDto;
import com.fsoft.khoahn.model.request.dto.UnitDeleteReqDto;
import com.fsoft.khoahn.model.request.dto.UnitReadReqDto;
import com.fsoft.khoahn.model.request.dto.UnitUpdateReqDto;
import com.fsoft.khoahn.model.response.dto.UnitDetailResDto;

public interface UnitService {

	UnitDetailResDto findOne(Integer id);

	Page<UnitDetailResDto> findAll(UnitReadReqDto unitReadReqDto);

	boolean isExitUnitName(String unitName);

	void save(UnitCreateReqDto unitCreateReqDto);

	void update(UnitUpdateReqDto unitUpdateReqDto);

	void delete(UnitDeleteReqDto unitDeleteReqDto);

}
