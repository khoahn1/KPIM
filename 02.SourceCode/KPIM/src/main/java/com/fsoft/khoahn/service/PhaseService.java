package com.fsoft.khoahn.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.dto.req.PhaseCreateReqDto;
import com.fsoft.khoahn.dto.req.PhaseDeleteReqDto;
import com.fsoft.khoahn.dto.req.PhaseReadReqDto;
import com.fsoft.khoahn.dto.req.PhaseUpdateReqDto;
import com.fsoft.khoahn.dto.res.PhaseDetailResDto;
import com.fsoft.khoahn.repository.entity.PhaseEntity;

public interface PhaseService {

	PhaseDetailResDto findOne(Integer id);

	Page<PhaseDetailResDto> findAll(PhaseReadReqDto phaseReadReqDto);

	boolean isExitPhaseName(String phaseName);

	void save(PhaseCreateReqDto phaseCreateReqDto);

	void update(PhaseUpdateReqDto phaseUpdateReqDto);

	void delete(PhaseDeleteReqDto phaseDeleteReqDto);

    List<PhaseDetailResDto> findAll();

    Map<String, PhaseEntity> getPhaseMap();

}
