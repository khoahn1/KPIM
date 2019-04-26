package com.fsoft.khoahn.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.khoahn.common.utils.PageRequestUtils;
import com.fsoft.khoahn.dto.req.PaginationReqDto;
import com.fsoft.khoahn.dto.req.PhaseCreateReqDto;
import com.fsoft.khoahn.dto.req.PhaseDeleteReqDto;
import com.fsoft.khoahn.dto.req.PhaseReadReqDto;
import com.fsoft.khoahn.dto.req.PhaseSearchReqDto;
import com.fsoft.khoahn.dto.req.PhaseUpdateReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;
import com.fsoft.khoahn.dto.res.PhaseDetailResDto;
import com.fsoft.khoahn.repository.PhaseRepo;
import com.fsoft.khoahn.repository.entity.PhaseEntity;
import com.fsoft.khoahn.service.PhaseService;

@Service("phaseService")
@Transactional
public class PhaseServiceImpl implements PhaseService {
	@Autowired
	private PhaseRepo phaseRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PhaseDetailResDto findOne(Integer id) {
		PhaseDetailResDto phaseDetailResDto = new PhaseDetailResDto();
		PhaseEntity phaseEntity = phaseRepo.findById(id).get();
		if (phaseEntity != null) {
			phaseDetailResDto = modelMapper.map(phaseEntity, PhaseDetailResDto.class);
		}
		return phaseDetailResDto;
	}

	@Override
	public Page<PhaseDetailResDto> findAll(PhaseReadReqDto phaseReadReqDto) {
		PaginationReqDto paginationRequest = phaseReadReqDto.getPaginationRequest();
		PhaseSearchReqDto phaseSearchRequest = phaseReadReqDto.getPhaseSearchRequest();

		List<SortReqDto> sortDtoList = phaseReadReqDto.getSortRequests();

		Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
		Page<PhaseEntity> phaseEntitys = phaseRepo.findByPhaseCodeContainingAndPhaseNameContaining(
				phaseSearchRequest.getPhaseCode(), phaseSearchRequest.getPhaseName(), pageable);

		Type typePhases = new TypeToken<List<PhaseDetailResDto>>() {
		}.getType();
		List<PhaseDetailResDto> phaseDetailResDtos = modelMapper.map(phaseEntitys.getContent(), typePhases);

		Page<PhaseDetailResDto> page = new PageImpl<>(phaseDetailResDtos,
				new PageRequest(paginationRequest.getPageNumber(), paginationRequest.getPageSize()),
				phaseEntitys.getTotalElements());
		return page;
	}

	@Override
	public boolean isExitPhaseName(String phaseName) {
		PhaseEntity phaseEntity = phaseRepo.findByPhaseName(phaseName);
		return (phaseEntity != null);
	}

	@Override
	public void save(PhaseCreateReqDto phaseCreateReqDto) {
		PhaseEntity phaseEntity = modelMapper.map(phaseCreateReqDto, PhaseEntity.class);
		phaseRepo.save(phaseEntity);
	}

	@Override
	public void update(PhaseUpdateReqDto phaseUpdateReqDto) {
		PhaseEntity phaseEntity = modelMapper.map(phaseUpdateReqDto, PhaseEntity.class);
		phaseRepo.save(phaseEntity);

	}

	@Override
	public void delete(PhaseDeleteReqDto phaseDeleteReqDto) {
		phaseRepo.deleteById(phaseDeleteReqDto.getId());
	}

}
