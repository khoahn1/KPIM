package com.fsoft.khoahn.service;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.model.request.dto.PhaseCreateReqDto;
import com.fsoft.khoahn.model.request.dto.PhaseDeleteReqDto;
import com.fsoft.khoahn.model.request.dto.PhaseReadReqDto;
import com.fsoft.khoahn.model.request.dto.PhaseUpdateReqDto;
import com.fsoft.khoahn.model.response.dto.GenderDetailResDto;

public interface GenderService {

	GenderDetailResDto findOne(Integer id);

	Page<GenderDetailResDto> findAll(PhaseReadReqDto phaseReadReqDto);

	boolean isExitGenderName(String genderName);

	void save(PhaseCreateReqDto phaseCreateReqDto);

	void update(PhaseUpdateReqDto phaseUpdateReqDto);

	void delete(PhaseDeleteReqDto phaseDeleteReqDto);

}
