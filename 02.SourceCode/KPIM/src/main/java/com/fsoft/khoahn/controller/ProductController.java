package com.fsoft.khoahn.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.khoahn.common.exception.InvalidFileException;
import com.fsoft.khoahn.dto.req.ProductCreateReqDto;
import com.fsoft.khoahn.dto.req.ProductDeleteReqDto;
import com.fsoft.khoahn.dto.req.ProductReadReqDto;
import com.fsoft.khoahn.dto.req.ProductUpdateReqDto;
import com.fsoft.khoahn.dto.res.ProductDetailResDto;
import com.fsoft.khoahn.model.request.PaginationRequest;
import com.fsoft.khoahn.model.request.ProductCreateRequest;
import com.fsoft.khoahn.model.request.ProductDeleteRequest;
import com.fsoft.khoahn.model.request.ProductReadRequest;
import com.fsoft.khoahn.model.request.ProductUpdateRequest;
import com.fsoft.khoahn.model.response.ProductDetailResponse;
import com.fsoft.khoahn.model.response.ProductReadResponse;
import com.fsoft.khoahn.model.response.ProductUpdateResponse;
import com.fsoft.khoahn.service.ProductService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/products")
@Api(description = "Products management API")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/product-read", method = RequestMethod.POST)
    public ResponseEntity<?> getAll(@RequestBody ProductReadRequest productReadRequest) {
        logger.debug("get product list");
        ProductReadResponse response = new ProductReadResponse();
        PaginationRequest paginationRequest = productReadRequest.getPaginationRequest();
        ProductReadReqDto productReadReqDto = modelMapper.map(productReadRequest, ProductReadReqDto.class);
        Page<ProductDetailResDto> productReadResDtos = productService.findAll(productReadReqDto);

        Type typeProducts = new TypeToken<List<ProductDetailResponse>>() {}.getType();
        List<ProductDetailResponse> productDetailResponses = modelMapper.map(productReadResDtos.getContent(),
                typeProducts);
        Page<ProductDetailResponse> page = new PageImpl<>(productDetailResponses,
                PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize()),
                productReadResDtos.getTotalElements());

        response.setProducts(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/product-create", method = RequestMethod.POST)
    public ResponseEntity<?> createProduct(@Validated @RequestBody ProductCreateRequest productCreateRequest)
            throws InvalidFileException, IOException {
        logger.debug("save product");
        ProductCreateReqDto productCreateReqDto = modelMapper.map(productCreateRequest,
                ProductCreateReqDto.class);
        if (productService.isExistProductName(productCreateRequest.getProductName())) {
            String saveErrorMessage = messageSource.getMessage("is.exit.data",
                    new String[] { "productname", productCreateRequest.getProductName() },
                    Locale.getDefault());
            return new ResponseEntity<>(Collections.singletonList(saveErrorMessage), HttpStatus.CONFLICT);
        }
        productService.save(productCreateReqDto);
        String saveSuccessMessage = messageSource.getMessage("create.success", new String[] { "product" },
                Locale.getDefault());
        return new ResponseEntity<>(Collections.singletonList(saveSuccessMessage), HttpStatus.OK);
    }

    @RequestMapping(value = "/product-update", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@RequestParam(value = "id") Integer id) {
        logger.debug("get product");
        ProductUpdateResponse response = new ProductUpdateResponse();
        ProductDetailResDto productDetailResDto = productService.findOne(id);
        if (productDetailResDto == null) {
            String viewErrorMessage = messageSource.getMessage("data.not.found", new String[] {},
                    Locale.getDefault());
            return new ResponseEntity<>(Collections.singletonList(viewErrorMessage), HttpStatus.NOT_FOUND);
        }
        ProductDetailResponse product = modelMapper.map(productDetailResDto, ProductDetailResponse.class);
        response.setProduct(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/product-update", method = RequestMethod.POST)
    public ResponseEntity<?> updateProduct(@Validated @RequestBody ProductUpdateRequest productUpdateRequest)
            throws IOException {
        logger.debug("update product");
        String message = null;
        HttpStatus status = null;
        ProductUpdateReqDto productDeleteReqDto = modelMapper.map(productUpdateRequest, ProductUpdateReqDto.class);
        ProductDetailResDto productDetailResDto = productService.findOne(productDeleteReqDto.getId());
        if (productDetailResDto == null) {
            message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
            status = HttpStatus.NOT_FOUND;
        } else {
            ProductUpdateReqDto productUpdateReqDto = modelMapper.map(productUpdateRequest,
                    ProductUpdateReqDto.class);
            productService.update(productUpdateReqDto);
            message = messageSource.getMessage("update.success", new String[] { "product" }, Locale.getDefault());
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(Collections.singletonList(message), status);
    }

    @RequestMapping(value = "/product-delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteProducts(@RequestBody ProductReadRequest productReadRequest)
            throws IOException {
        logger.debug("delete products");
        String message = null;
        HttpStatus status = null;
        List<ProductDeleteRequest> productDeleteRequests = productReadRequest.getProductDeleteRequest();
        List<ProductDeleteReqDto> productDeleteReqDtos =
                modelMapper.map(productDeleteRequests, new TypeToken<List<ProductDeleteReqDto>>() {}.getType());

        for (Iterator<ProductDeleteReqDto> iterator = productDeleteReqDtos.iterator(); iterator.hasNext();) {
            ProductDeleteReqDto productDeleteRequest = iterator.next();
            ProductDetailResDto productDetailResDto = productService.findOne(productDeleteRequest.getId());
            if (productDetailResDto == null) {
                message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
                status = HttpStatus.NOT_FOUND;
            } else {
                ProductDeleteReqDto productDeleteReqDto = modelMapper.map(productDetailResDto,
                        ProductDeleteReqDto.class);
                productService.delete(productDeleteReqDto);
                message = messageSource.getMessage("delete.success", new String[] { "product" },
                        Locale.getDefault());
                status = HttpStatus.OK;
            }
        }
        return new ResponseEntity<>(Collections.singletonList(message), status);
    }
}
