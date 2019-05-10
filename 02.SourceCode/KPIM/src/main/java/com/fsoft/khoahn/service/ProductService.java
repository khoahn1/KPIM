package com.fsoft.khoahn.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.dto.req.ProductCreateReqDto;
import com.fsoft.khoahn.dto.req.ProductDeleteReqDto;
import com.fsoft.khoahn.dto.req.ProductReadReqDto;
import com.fsoft.khoahn.dto.req.ProductUpdateReqDto;
import com.fsoft.khoahn.dto.res.ProductDetailResDto;

public interface ProductService {

    ProductDetailResDto findOne(Integer id);

    Page<ProductDetailResDto> findAll(ProductReadReqDto productReadReqDto);

    boolean isExistProductName(String productName);

    void save(ProductCreateReqDto productCreateReqDto);

    void update(ProductUpdateReqDto productUpdateReqDto);

    void delete(ProductDeleteReqDto productDeleteReqDto);

    List<ProductDetailResDto> findAll();

}
