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
import com.fsoft.khoahn.dto.req.CompanyCreateReqDto;
import com.fsoft.khoahn.dto.req.CompanyDeleteReqDto;
import com.fsoft.khoahn.dto.req.CompanyReadReqDto;
import com.fsoft.khoahn.dto.req.CompanySearchReqDto;
import com.fsoft.khoahn.dto.req.CompanyUpdateReqDto;
import com.fsoft.khoahn.dto.req.PaginationReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;
import com.fsoft.khoahn.dto.res.CompanyDetailResDto;
import com.fsoft.khoahn.repository.CompanyRepo;
import com.fsoft.khoahn.repository.entity.CompanyEntity;
import com.fsoft.khoahn.service.CompanyService;

@Service("companyService")
@Transactional
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<CompanyDetailResDto> findAll(CompanyReadReqDto companyReadReqDto) {
		PaginationReqDto paginationRequest = companyReadReqDto.getPaginationRequest();
		CompanySearchReqDto companySearchRequest = companyReadReqDto.getCompanySearchRequest();

		List<SortReqDto> sortDtoList = companyReadReqDto.getSortRequest();

		Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
		Page<CompanyEntity> companyEntitys = companyRepo.findByIdContainingAndCompanyNameContaining(
				companySearchRequest.getId(), companySearchRequest.getCompanyName(), pageable);

		Type typeComapnies = new TypeToken<List<CompanyDetailResDto>>() {
		}.getType();
		List<CompanyDetailResDto> companyDetailResDtos = modelMapper.map(companyEntitys.getContent(), typeComapnies);

		Page<CompanyDetailResDto> page = new PageImpl<>(companyDetailResDtos, pageable,
				companyEntitys.getTotalElements());
		return page;
	}

	@Override
	public void save(CompanyCreateReqDto companyCreateReqDto) {
		CompanyEntity companyEntity = modelMapper.map(companyCreateReqDto, CompanyEntity.class);
		companyRepo.save(companyEntity);
	}

	@Override
	public boolean isExitCompanyCode(String companyCode) {
		CompanyEntity companyEntity = companyRepo.findByCompanyCode(companyCode);
		return (companyEntity != null);
	}

	@Override
	public CompanyDetailResDto findOne(String id) {
		CompanyDetailResDto companyDetailResDto = new CompanyDetailResDto();
		CompanyEntity companyEntity = companyRepo.findById(id).get();
		if (companyEntity != null) {
			companyDetailResDto = modelMapper.map(companyEntity, CompanyDetailResDto.class);
		}
		return companyDetailResDto;
	}

	@Override
	public void update(CompanyUpdateReqDto companyUpdateReqDto) {
		CompanyEntity companyEntity = modelMapper.map(companyUpdateReqDto, CompanyEntity.class);
		companyRepo.save(companyEntity);
	}

	@Override
	public void delete(CompanyDeleteReqDto companyDeleteReqDto) {
		companyRepo.deleteById(companyDeleteReqDto.getId());
	}

}
