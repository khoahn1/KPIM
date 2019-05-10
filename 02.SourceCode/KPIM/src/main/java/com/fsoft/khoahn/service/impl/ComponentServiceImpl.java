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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fsoft.khoahn.common.utils.PageRequestUtils;
import com.fsoft.khoahn.dto.req.ComponentCreateReqDto;
import com.fsoft.khoahn.dto.req.ComponentDeleteReqDto;
import com.fsoft.khoahn.dto.req.ComponentReadReqDto;
import com.fsoft.khoahn.dto.req.ComponentSearchReqDto;
import com.fsoft.khoahn.dto.req.ComponentUpdateReqDto;
import com.fsoft.khoahn.dto.req.PaginationReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;
import com.fsoft.khoahn.dto.res.ComponentDetailResDto;
import com.fsoft.khoahn.repository.ComponentRepo;
import com.fsoft.khoahn.repository.entity.ComponentEntity;
import com.fsoft.khoahn.service.ComponentService;

@Service
public class ComponentServiceImpl implements ComponentService {

    @Autowired
    private ComponentRepo componentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ComponentDetailResDto findOne(Integer id) {
        ComponentDetailResDto componentDetailResDto = new ComponentDetailResDto();
        ComponentEntity componentEntity = componentRepo.findById(id).get();
        if (componentEntity != null) {
            componentDetailResDto = modelMapper.map(componentEntity, ComponentDetailResDto.class);
        }
        return componentDetailResDto;
    }

    @Override
    public Page<ComponentDetailResDto> findAll(ComponentReadReqDto componentReadReqDto) {
        PaginationReqDto paginationRequest = componentReadReqDto.getPaginationRequest();
        ComponentSearchReqDto componentSearchRequest = componentReadReqDto.getComponentSearchRequest();

        List<SortReqDto> sortDtoList = componentReadReqDto.getSortRequest();

        Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
        Page<ComponentEntity> componentEntitys = componentRepo
                .findByComponentNameContaining(componentSearchRequest.getComponentName(),
                        pageable);

        Type typeComponents = new TypeToken<List<ComponentDetailResDto>>() {
        }.getType();
        List<ComponentDetailResDto> componentDetailResDtos = modelMapper.map(componentEntitys.getContent(),
                typeComponents);

        Page<ComponentDetailResDto> page = new PageImpl<>(componentDetailResDtos,
                PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize()),
                componentEntitys.getTotalElements());
        return page;
    }

    @Override
    public boolean isExistComponentName(String componentName) {
        ComponentEntity componentEntity = componentRepo.findByComponentName(componentName);
        return (componentEntity != null);
    }

    @Override
    public void save(ComponentCreateReqDto componentCreateReqDto) {
        ComponentEntity componentEntity = modelMapper.map(componentCreateReqDto, ComponentEntity.class);
        componentRepo.save(componentEntity);
    }

    @Override
    public void update(ComponentUpdateReqDto componentUpdateReqDto) {
        ComponentEntity componentEntity = modelMapper.map(componentUpdateReqDto, ComponentEntity.class);
        componentRepo.save(componentEntity);
    }

    @Override
    public void delete(ComponentDeleteReqDto componentDeleteReqDto) {
        componentRepo.deleteById(componentDeleteReqDto.getId());
    }

    @Override
    public List<ComponentDetailResDto> findAll() {
        return modelMapper.map(componentRepo.findAll(Sort.by(Direction.DESC, "componentName")), new TypeToken<List<ComponentDetailResDto>>() {}.getType());
    }

}
