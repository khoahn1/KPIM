package com.fsoft.khoahn.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.dto.req.UnitCreateReqDto;
import com.fsoft.khoahn.dto.req.UnitDeleteReqDto;
import com.fsoft.khoahn.dto.req.UnitReadReqDto;
import com.fsoft.khoahn.dto.req.UnitUpdateReqDto;
import com.fsoft.khoahn.dto.res.UnitDetailResDto;
import com.fsoft.khoahn.repository.entity.UnitEntity;

public interface UnitService {

	UnitDetailResDto findOne(Integer id);

	Page<UnitDetailResDto> findAll(UnitReadReqDto unitReadReqDto);

	boolean isExitUnitName(String unitName);

	void save(UnitCreateReqDto unitCreateReqDto);

	void update(UnitUpdateReqDto unitUpdateReqDto);

	void delete(UnitDeleteReqDto unitDeleteReqDto);

	List<UnitDetailResDto> findAll();

	Map<String, UnitEntity> getUnitMap();

}
