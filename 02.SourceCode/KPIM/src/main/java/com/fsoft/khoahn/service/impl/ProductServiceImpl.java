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
import com.fsoft.khoahn.dto.req.PaginationReqDto;
import com.fsoft.khoahn.dto.req.ProductCreateReqDto;
import com.fsoft.khoahn.dto.req.ProductDeleteReqDto;
import com.fsoft.khoahn.dto.req.ProductReadReqDto;
import com.fsoft.khoahn.dto.req.ProductSearchReqDto;
import com.fsoft.khoahn.dto.req.ProductUpdateReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;
import com.fsoft.khoahn.dto.res.ProductDetailResDto;
import com.fsoft.khoahn.repository.ProductRepo;
import com.fsoft.khoahn.repository.entity.ProductEntity;
import com.fsoft.khoahn.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDetailResDto findOne(Integer id) {
        ProductDetailResDto productDetailResDto = new ProductDetailResDto();
        ProductEntity productEntity = productRepo.findById(id).get();
        if (productEntity != null) {
            productDetailResDto = modelMapper.map(productEntity, ProductDetailResDto.class);
        }
        return productDetailResDto;
    }

    @Override
    public Page<ProductDetailResDto> findAll(ProductReadReqDto productReadReqDto) {
        PaginationReqDto paginationRequest = productReadReqDto.getPaginationRequest();
        ProductSearchReqDto productSearchRequest = productReadReqDto.getProductSearchRequest();

        List<SortReqDto> sortDtoList = productReadReqDto.getSortRequest();

        Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
        Page<ProductEntity> productEntitys = productRepo
                .findByProductNameContaining(productSearchRequest.getProductName(),
                        pageable);

        Type typeProducts = new TypeToken<List<ProductDetailResDto>>() {
        }.getType();
        List<ProductDetailResDto> productDetailResDtos = modelMapper.map(productEntitys.getContent(),
                typeProducts);

        Page<ProductDetailResDto> page = new PageImpl<>(productDetailResDtos,
                PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize()),
                productEntitys.getTotalElements());
        return page;
    }

    @Override
    public boolean isExistProductName(String productName) {
        ProductEntity productEntity = productRepo.findByProductName(productName);
        return (productEntity != null);
    }

    @Override
    public void save(ProductCreateReqDto productCreateReqDto) {
        ProductEntity productEntity = modelMapper.map(productCreateReqDto, ProductEntity.class);
        productRepo.save(productEntity);
    }

    @Override
    public void update(ProductUpdateReqDto productUpdateReqDto) {
        ProductEntity productEntity = modelMapper.map(productUpdateReqDto, ProductEntity.class);
        productRepo.save(productEntity);
    }

    @Override
    public void delete(ProductDeleteReqDto productDeleteReqDto) {
        productRepo.deleteById(productDeleteReqDto.getId());
    }

    @Override
    public List<ProductDetailResDto> findAll() {
        return modelMapper.map(productRepo.findAll(Sort.by(Direction.DESC, "productName")), new TypeToken<List<ProductDetailResDto>>() {}.getType());
    }

}
