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
import com.fsoft.khoahn.model.request.dto.TypeOfWorkCreateReqDto;
import com.fsoft.khoahn.model.request.dto.TypeOfWorkDeleteReqDto;
import com.fsoft.khoahn.model.request.dto.TypeOfWorkReadReqDto;
import com.fsoft.khoahn.model.request.dto.TypeOfWorkSearchReqDto;
import com.fsoft.khoahn.model.request.dto.TypeOfWorkUpdateReqDto;
import com.fsoft.khoahn.model.response.dto.TypeOfWorkDetailResDto;
import com.fsoft.khoahn.repository.TypeOfWorkRepo;
import com.fsoft.khoahn.repository.entity.TypeOfWorkEntity;
import com.fsoft.khoahn.service.TypeOfWorkService;

@Service("typeOfWorkService")
@Transactional
public class TypeOfWorkServiceImpl implements TypeOfWorkService {
	@Autowired
	private TypeOfWorkRepo typeOfWorkRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TypeOfWorkDetailResDto findOne(Integer id) {
		TypeOfWorkDetailResDto typeOfWorkDetailResDto = new TypeOfWorkDetailResDto();
		TypeOfWorkEntity typeOfWorkEntity = typeOfWorkRepo.findById(id).get();
		if (typeOfWorkEntity != null) {
			typeOfWorkDetailResDto = modelMapper.map(typeOfWorkEntity, TypeOfWorkDetailResDto.class);
		}
		return typeOfWorkDetailResDto;
	}

	@Override
	public Page<TypeOfWorkDetailResDto> findAll(TypeOfWorkReadReqDto typeOfWorkReadReqDto) {
		PaginationReqDto paginationRequest = typeOfWorkReadReqDto.getPaginationRequest();
		TypeOfWorkSearchReqDto typeOfWorkSearchRequest = typeOfWorkReadReqDto.getTypeOfWorkSearchRequest();

		List<SortReqDto> sortDtoList = typeOfWorkReadReqDto.getSortRequests();

		Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
		Page<TypeOfWorkEntity> typeOfWorkEntitys = typeOfWorkRepo
				.findByTypeNameContaining(typeOfWorkSearchRequest.getTypeName(),
						pageable);

		Type typeTypeOfWorks = new TypeToken<List<TypeOfWorkDetailResDto>>() {
		}.getType();
		List<TypeOfWorkDetailResDto> typeOfWorkDetailResDtos = modelMapper.map(typeOfWorkEntitys.getContent(),
				typeTypeOfWorks);

		Page<TypeOfWorkDetailResDto> page = new PageImpl<>(typeOfWorkDetailResDtos,
				new PageRequest(paginationRequest.getPageNumber(), paginationRequest.getPageSize()),
				typeOfWorkEntitys.getTotalElements());
		return page;
	}

	@Override
	public boolean isExitTypeOfWorkName(String typeOfWorkName) {
		TypeOfWorkEntity typeOfWorkEntity = typeOfWorkRepo.findByTypeName(typeOfWorkName);
		return (typeOfWorkEntity != null);
	}

	@Override
	public void save(TypeOfWorkCreateReqDto typeOfWorkCreateReqDto) {
		TypeOfWorkEntity typeOfWorkEntity = modelMapper.map(typeOfWorkCreateReqDto, TypeOfWorkEntity.class);
		typeOfWorkRepo.save(typeOfWorkEntity);
	}

	@Override
	public void update(TypeOfWorkUpdateReqDto typeOfWorkUpdateReqDto) {
		TypeOfWorkEntity typeOfWorkEntity = modelMapper.map(typeOfWorkUpdateReqDto, TypeOfWorkEntity.class);
		typeOfWorkRepo.save(typeOfWorkEntity);

	}

	@Override
	public void delete(TypeOfWorkDeleteReqDto typeOfWorkDeleteReqDto) {
		typeOfWorkRepo.deleteById(typeOfWorkDeleteReqDto.getId());
	}

}
