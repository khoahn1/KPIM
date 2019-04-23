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
import com.fsoft.khoahn.model.request.dto.PaginationReqDto;
import com.fsoft.khoahn.model.request.dto.SortReqDto;
import com.fsoft.khoahn.model.request.dto.UnitCreateReqDto;
import com.fsoft.khoahn.model.request.dto.UnitDeleteReqDto;
import com.fsoft.khoahn.model.request.dto.UnitReadReqDto;
import com.fsoft.khoahn.model.request.dto.UnitSearchReqDto;
import com.fsoft.khoahn.model.request.dto.UnitUpdateReqDto;
import com.fsoft.khoahn.model.response.dto.UnitDetailResDto;
import com.fsoft.khoahn.repository.UnitRepo;
import com.fsoft.khoahn.repository.entity.UnitEntity;
import com.fsoft.khoahn.service.UnitService;

@Service("unitService")
@Transactional
public class UnitServiceImpl implements UnitService {
	@Autowired
	private UnitRepo unitRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UnitDetailResDto findOne(Integer id) {
		UnitDetailResDto unitDetailResDto = new UnitDetailResDto();
		UnitEntity unitEntity = unitRepo.findById(id).get();
		if (unitEntity != null) {
			unitDetailResDto = modelMapper.map(unitEntity, UnitDetailResDto.class);
		}
		return unitDetailResDto;
	}

	@Override
	public Page<UnitDetailResDto> findAll(UnitReadReqDto unitReadReqDto) {
		PaginationReqDto paginationRequest = unitReadReqDto.getPaginationRequest();
		UnitSearchReqDto unitSearchRequest = unitReadReqDto.getUnitSearchRequest();

		List<SortReqDto> sortDtoList = unitReadReqDto.getSortRequests();

		Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
		Page<UnitEntity> unitEntitys = unitRepo.findByUnitNameContaining(unitSearchRequest.getUnitName(), pageable);

		Type typeUnits = new TypeToken<List<UnitDetailResDto>>() {
		}.getType();
		List<UnitDetailResDto> unitDetailResDtos = modelMapper.map(unitEntitys.getContent(), typeUnits);

		Page<UnitDetailResDto> page = new PageImpl<>(unitDetailResDtos,
				new PageRequest(paginationRequest.getPageNumber(), paginationRequest.getPageSize()),
				unitEntitys.getTotalElements());
		return page;
	}

	@Override
	public boolean isExitUnitName(String unitName) {
		UnitEntity unitEntity = unitRepo.findByUnitName(unitName);
		return (unitEntity != null);
	}

	@Override
	public void save(UnitCreateReqDto unitCreateReqDto) {
		UnitEntity unitEntity = modelMapper.map(unitCreateReqDto, UnitEntity.class);
		unitRepo.save(unitEntity);
	}

	@Override
	public void update(UnitUpdateReqDto unitUpdateReqDto) {
		UnitEntity unitEntity = modelMapper.map(unitUpdateReqDto, UnitEntity.class);
		unitRepo.save(unitEntity);

	}

	@Override
	public void delete(UnitDeleteReqDto unitDeleteReqDto) {
		unitRepo.deleteById(unitDeleteReqDto.getId());
	}

}
