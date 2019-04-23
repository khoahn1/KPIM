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
import com.fsoft.khoahn.model.request.dto.PhaseCreateReqDto;
import com.fsoft.khoahn.model.request.dto.PhaseDeleteReqDto;
import com.fsoft.khoahn.model.request.dto.PhaseReadReqDto;
import com.fsoft.khoahn.model.request.dto.PhaseUpdateReqDto;
import com.fsoft.khoahn.model.request.dto.PaginationReqDto;
import com.fsoft.khoahn.model.request.dto.SortReqDto;
import com.fsoft.khoahn.model.response.dto.GenderDetailResDto;
import com.fsoft.khoahn.repository.GenderRepo;
import com.fsoft.khoahn.repository.entity.GenderEntity;
import com.fsoft.khoahn.service.GenderService;

@Service("genderService")
@Transactional
public class GenderServiceImpl implements GenderService {
	@Autowired
	private GenderRepo genderRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public GenderDetailResDto findOne(Integer id) {
		GenderDetailResDto genderDetailResDto = new GenderDetailResDto();
		GenderEntity genderEntity = genderRepo.findById(id).get();
		if (genderEntity != null) {
			genderDetailResDto = modelMapper.map(genderEntity, GenderDetailResDto.class);
		}
		return genderDetailResDto;
	}

	@Override
	public Page<GenderDetailResDto> findAll(PhaseReadReqDto phaseReadReqDto) {
		PaginationReqDto paginationRequest = phaseReadReqDto.getPaginationRequest();

		List<SortReqDto> sortDtoList = phaseReadReqDto.getSortRequests();

		Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
		Page<GenderEntity> genderEntitys = genderRepo.findAll(pageable);

		Type typeGenders = new TypeToken<List<GenderDetailResDto>>() {
		}.getType();
		List<GenderDetailResDto> genderDetailResDtos = modelMapper.map(genderEntitys.getContent(), typeGenders);

		Page<GenderDetailResDto> page = new PageImpl<>(genderDetailResDtos,
				new PageRequest(paginationRequest.getPageNumber(), paginationRequest.getPageSize()),
				genderEntitys.getTotalElements());
		return page;
	}

	@Override
	public boolean isExitGenderName(String genderName) {
		GenderEntity genderEntity = genderRepo.findByGenderName(genderName);
		return (genderEntity != null);
	}

	@Override
	public void save(PhaseCreateReqDto phaseCreateReqDto) {
		GenderEntity genderEntity = modelMapper.map(phaseCreateReqDto, GenderEntity.class);
		genderRepo.save(genderEntity);
	}

	@Override
	public void update(PhaseUpdateReqDto phaseUpdateReqDto) {
		GenderEntity genderEntity = modelMapper.map(phaseUpdateReqDto, GenderEntity.class);
		genderRepo.save(genderEntity);

	}

	@Override
	public void delete(PhaseDeleteReqDto phaseDeleteReqDto) {
		genderRepo.deleteById(phaseDeleteReqDto.getId());
	}

}
