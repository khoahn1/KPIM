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
import com.fsoft.khoahn.dto.req.DepartmentCreateReqDto;
import com.fsoft.khoahn.dto.req.DepartmentDeleteReqDto;
import com.fsoft.khoahn.dto.req.DepartmentReadReqDto;
import com.fsoft.khoahn.dto.req.DepartmentSearchReqDto;
import com.fsoft.khoahn.dto.req.DepartmentUpdateReqDto;
import com.fsoft.khoahn.dto.req.PaginationReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;
import com.fsoft.khoahn.dto.res.DepartmentDetailResDto;
import com.fsoft.khoahn.repository.DepartmentRepo;
import com.fsoft.khoahn.repository.entity.DepartmentEntity;
import com.fsoft.khoahn.service.DepartmentService;

@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentRepo departmentRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public DepartmentDetailResDto findOne(String id) {
		DepartmentDetailResDto departmentDetailResDto = new DepartmentDetailResDto();
		DepartmentEntity departmentEntity = departmentRepo.findById(id).get();
		if (departmentEntity != null) {
			departmentDetailResDto = modelMapper.map(departmentEntity, DepartmentDetailResDto.class);
		}
		return departmentDetailResDto;
	}

	@Override
	public Page<DepartmentDetailResDto> findAll(DepartmentReadReqDto departmentReadReqDto) {
		PaginationReqDto paginationRequest = departmentReadReqDto.getPaginationRequest();
		DepartmentSearchReqDto departmentSearchRequest = departmentReadReqDto.getDepartmentSearchRequest();

		List<SortReqDto> sortDtoList = departmentReadReqDto.getSortRequests();

		Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
		Page<DepartmentEntity> departmentEntitys = departmentRepo
				.findByDepartmentCodeContainingAndDepartmentNameContaining(departmentSearchRequest.getDepartmentCode(),
						departmentSearchRequest.getDepartmentName(), pageable);

		Type typeDepartments = new TypeToken<List<DepartmentDetailResDto>>() {
		}.getType();
		List<DepartmentDetailResDto> departmentDetailResDtos = modelMapper.map(departmentEntitys.getContent(),
				typeDepartments);

		Page<DepartmentDetailResDto> page = new PageImpl<>(departmentDetailResDtos, pageable,
				departmentEntitys.getTotalElements());
		return page;
	}

	@Override
	public boolean isExitDepartmentCode(String departmentCode) {
		DepartmentEntity departmentEntity = departmentRepo.findByDepartmentCode(departmentCode);
		return (departmentEntity != null);
	}

	@Override
	public void save(DepartmentCreateReqDto departmentCreateReqDto) {
		DepartmentEntity departmentEntity = modelMapper.map(departmentCreateReqDto, DepartmentEntity.class);
		departmentRepo.save(departmentEntity);
	}

	@Override
	public void update(DepartmentUpdateReqDto departmentUpdateReqDto) {
		DepartmentEntity departmentEntity = modelMapper.map(departmentUpdateReqDto, DepartmentEntity.class);
		departmentRepo.save(departmentEntity);

	}

	@Override
	public void delete(DepartmentDeleteReqDto departmentDeleteReqDto) {
		departmentRepo.deleteById(departmentDeleteReqDto.getId());
	}

	@Override
	public List<DepartmentDetailResDto> getAll() {
		return modelMapper.map(departmentRepo.findAll(), new TypeToken<List<DepartmentDetailResDto>>() {
		}.getType());
	}

}
