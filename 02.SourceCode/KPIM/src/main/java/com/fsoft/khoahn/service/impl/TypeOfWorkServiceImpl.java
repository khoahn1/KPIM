package com.fsoft.khoahn.service.impl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.fsoft.khoahn.dto.req.TypeOfWorkCreateReqDto;
import com.fsoft.khoahn.dto.req.TypeOfWorkDeleteReqDto;
import com.fsoft.khoahn.dto.req.TypeOfWorkReadReqDto;
import com.fsoft.khoahn.dto.req.TypeOfWorkSearchReqDto;
import com.fsoft.khoahn.dto.req.TypeOfWorkUpdateReqDto;
import com.fsoft.khoahn.dto.res.TypeOfWorkDetailResDto;
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

		Page<TypeOfWorkDetailResDto> page = new PageImpl<>(typeOfWorkDetailResDtos, pageable,
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

    @Override
    public List<TypeOfWorkDetailResDto> findAll() {
        return modelMapper.map(typeOfWorkRepo.findAll(), new TypeToken<List<TypeOfWorkDetailResDto>>() {}.getType());
    }

    @Override
    public Map<String, TypeOfWorkEntity> getTypeOfWorkMap() {
        return typeOfWorkRepo.findAll().stream()
                .collect(Collectors.toMap(TypeOfWorkEntity::getTypeName, tow -> tow));
    }

}
