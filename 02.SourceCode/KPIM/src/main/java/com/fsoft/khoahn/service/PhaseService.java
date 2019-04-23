package com.fsoft.khoahn.service;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.model.request.dto.PhaseCreateReqDto;
import com.fsoft.khoahn.model.request.dto.PhaseDeleteReqDto;
import com.fsoft.khoahn.model.request.dto.PhaseReadReqDto;
import com.fsoft.khoahn.model.request.dto.PhaseUpdateReqDto;
import com.fsoft.khoahn.model.response.dto.PhaseDetailResDto;

public interface PhaseService {

	PhaseDetailResDto findOne(Integer id);

	Page<PhaseDetailResDto> findAll(PhaseReadReqDto phaseReadReqDto);

	boolean isExitPhaseName(String phaseName);

	void save(PhaseCreateReqDto phaseCreateReqDto);

	void update(PhaseUpdateReqDto phaseUpdateReqDto);

	void delete(PhaseDeleteReqDto phaseDeleteReqDto);

}
