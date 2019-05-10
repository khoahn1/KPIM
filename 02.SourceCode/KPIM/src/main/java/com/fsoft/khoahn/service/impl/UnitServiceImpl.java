package com.fsoft.khoahn.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.khoahn.common.utils.PageRequestUtils;
import com.fsoft.khoahn.dto.req.PaginationReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;
import com.fsoft.khoahn.dto.req.UnitCreateReqDto;
import com.fsoft.khoahn.dto.req.UnitDeleteReqDto;
import com.fsoft.khoahn.dto.req.UnitReadReqDto;
import com.fsoft.khoahn.dto.req.UnitSearchReqDto;
import com.fsoft.khoahn.dto.req.UnitUpdateReqDto;
import com.fsoft.khoahn.dto.res.UnitDetailResDto;
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

		Page<UnitDetailResDto> page = new PageImpl<>(unitDetailResDtos, pageable,
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

    @Override
    public List<UnitDetailResDto> findAll() {
        return modelMapper.map(unitRepo.findAll(), new TypeToken<List<UnitDetailResDto>>() {}.getType()) ;
    }

}
