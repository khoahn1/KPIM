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
import com.fsoft.khoahn.dto.req.ParentDepartmentCreateReqDto;
import com.fsoft.khoahn.dto.req.ParentDepartmentDeleteReqDto;
import com.fsoft.khoahn.dto.req.ParentDepartmentReadReqDto;
import com.fsoft.khoahn.dto.req.ParentDepartmentSearchReqDto;
import com.fsoft.khoahn.dto.req.ParentDepartmentUpdateReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;
import com.fsoft.khoahn.dto.res.ParentDepartmentDetailResDto;
import com.fsoft.khoahn.repository.ParentDepartmentRepo;
import com.fsoft.khoahn.repository.entity.ParentDepartmentEntity;
import com.fsoft.khoahn.service.ParentDepartmentService;

@Service("parentDepartmentService")
@Transactional
public class ParentDepartmentServiceImpl implements ParentDepartmentService {
	@Autowired
	private ParentDepartmentRepo parentDepartmentRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ParentDepartmentDetailResDto findOne(String id) {
		ParentDepartmentDetailResDto parentDepartmentDetailResDto = new ParentDepartmentDetailResDto();
		ParentDepartmentEntity parentDepartmentEntity = parentDepartmentRepo.findById(id).get();
		if (parentDepartmentEntity != null) {
			parentDepartmentDetailResDto = modelMapper.map(parentDepartmentEntity, ParentDepartmentDetailResDto.class);
		}
		return parentDepartmentDetailResDto;
	}

	@Override
	public Page<ParentDepartmentDetailResDto> findAll(ParentDepartmentReadReqDto parentDepartmentReadReqDto) {
		PaginationReqDto paginationRequest = parentDepartmentReadReqDto.getPaginationRequest();
		ParentDepartmentSearchReqDto parentDepartmentSearchRequest = parentDepartmentReadReqDto
				.getParentDepartmentSearchRequest();

		List<SortReqDto> sortDtoList = parentDepartmentReadReqDto.getSortRequests();

		Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
		Page<ParentDepartmentEntity> parentDepartmentEntitys = parentDepartmentRepo
				.findByParentDepartmentCodeContainingAndParentDepartmentNameContaining(
						parentDepartmentSearchRequest.getParentDepartmentCode(),
						parentDepartmentSearchRequest.getParentDepartmentName(), pageable);

		Type typeParentDepartments = new TypeToken<List<ParentDepartmentDetailResDto>>() {
		}.getType();
		List<ParentDepartmentDetailResDto> parentDepartmentDetailResDtos = modelMapper
				.map(parentDepartmentEntitys.getContent(), typeParentDepartments);

		Page<ParentDepartmentDetailResDto> page = new PageImpl<>(parentDepartmentDetailResDtos, pageable,
				parentDepartmentEntitys.getTotalElements());
		return page;
	}

	@Override
	public boolean isExitParentDepartmentCode(String parentDepartmentCode) {
		ParentDepartmentEntity parentDepartmentEntity = parentDepartmentRepo
				.findByParentDepartmentCode(parentDepartmentCode);
		return (parentDepartmentEntity != null);
	}

	@Override
	public void save(ParentDepartmentCreateReqDto parentDepartmentCreateReqDto) {
		ParentDepartmentEntity parentDepartmentEntity = modelMapper.map(parentDepartmentCreateReqDto,
				ParentDepartmentEntity.class);
		parentDepartmentRepo.save(parentDepartmentEntity);
	}

	@Override
	public void update(ParentDepartmentUpdateReqDto parentDepartmentUpdateReqDto) {
		ParentDepartmentEntity parentDepartmentEntity = modelMapper.map(parentDepartmentUpdateReqDto,
				ParentDepartmentEntity.class);
		parentDepartmentRepo.save(parentDepartmentEntity);

	}

	@Override
	public void delete(ParentDepartmentDeleteReqDto parentDepartmentDeleteReqDto) {
		parentDepartmentRepo.deleteById(parentDepartmentDeleteReqDto.getId());
	}

	@Override
	public List<ParentDepartmentDetailResDto> getAll() {
		return modelMapper.map(parentDepartmentRepo.findAll(), new TypeToken<List<ParentDepartmentDetailResDto>>() {
		}.getType());
	}

}
