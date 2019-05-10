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
import com.fsoft.khoahn.dto.req.ComponentCreateReqDto;
import com.fsoft.khoahn.dto.req.ComponentDeleteReqDto;
import com.fsoft.khoahn.dto.req.ComponentReadReqDto;
import com.fsoft.khoahn.dto.req.ComponentUpdateReqDto;
import com.fsoft.khoahn.dto.res.ComponentDetailResDto;
import com.fsoft.khoahn.model.request.ComponentCreateRequest;
import com.fsoft.khoahn.model.request.ComponentDeleteRequest;
import com.fsoft.khoahn.model.request.ComponentReadRequest;
import com.fsoft.khoahn.model.request.ComponentUpdateRequest;
import com.fsoft.khoahn.model.request.PaginationRequest;
import com.fsoft.khoahn.model.response.ComponentDetailResponse;
import com.fsoft.khoahn.model.response.ComponentReadResponse;
import com.fsoft.khoahn.model.response.ComponentUpdateResponse;
import com.fsoft.khoahn.service.ComponentService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/components")
@Api(description = "Components management API")
public class ComponentController {

    private static final Logger logger = LoggerFactory.getLogger(ComponentController.class);

    @Autowired
    private ComponentService componentService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/component-read", method = RequestMethod.POST)
    public ResponseEntity<?> getAll(@RequestBody ComponentReadRequest componentReadRequest) {
        logger.debug("get component list");
        ComponentReadResponse response = new ComponentReadResponse();
        PaginationRequest paginationRequest = componentReadRequest.getPaginationRequest();
        ComponentReadReqDto componentReadReqDto = modelMapper.map(componentReadRequest, ComponentReadReqDto.class);
        Page<ComponentDetailResDto> componentReadResDtos = componentService.findAll(componentReadReqDto);

        Type typeComponents = new TypeToken<List<ComponentDetailResponse>>() {}.getType();
        List<ComponentDetailResponse> componentDetailResponses = modelMapper.map(componentReadResDtos.getContent(),
                typeComponents);
        Page<ComponentDetailResponse> page = new PageImpl<>(componentDetailResponses,
                PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize()),
                componentReadResDtos.getTotalElements());

        response.setComponents(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/component-create", method = RequestMethod.POST)
    public ResponseEntity<?> createComponent(@Validated @RequestBody ComponentCreateRequest componentCreateRequest)
            throws InvalidFileException, IOException {
        logger.debug("save component");
        ComponentCreateReqDto componentCreateReqDto = modelMapper.map(componentCreateRequest,
                ComponentCreateReqDto.class);
        if (componentService.isExistComponentName(componentCreateRequest.getComponentName())) {
            String saveErrorMessage = messageSource.getMessage("is.exit.data",
                    new String[] { "componentname", componentCreateRequest.getComponentName() },
                    Locale.getDefault());
            return new ResponseEntity<>(Collections.singletonList(saveErrorMessage), HttpStatus.CONFLICT);
        }
        componentService.save(componentCreateReqDto);
        String saveSuccessMessage = messageSource.getMessage("create.success", new String[] { "component" },
                Locale.getDefault());
        return new ResponseEntity<>(Collections.singletonList(saveSuccessMessage), HttpStatus.OK);
    }

    @RequestMapping(value = "/component-update", method = RequestMethod.GET)
    public ResponseEntity<?> getComponent(@RequestParam(value = "id") Integer id) {
        logger.debug("get component");
        ComponentUpdateResponse response = new ComponentUpdateResponse();
        ComponentDetailResDto componentDetailResDto = componentService.findOne(id);
        if (componentDetailResDto == null) {
            String viewErrorMessage = messageSource.getMessage("data.not.found", new String[] {},
                    Locale.getDefault());
            return new ResponseEntity<>(Collections.singletonList(viewErrorMessage), HttpStatus.NOT_FOUND);
        }
        ComponentDetailResponse component = modelMapper.map(componentDetailResDto, ComponentDetailResponse.class);
        response.setComponent(component);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/component-update", method = RequestMethod.POST)
    public ResponseEntity<?> updateComponent(@Validated @RequestBody ComponentUpdateRequest componentUpdateRequest)
            throws IOException {
        logger.debug("update component");
        String message = null;
        HttpStatus status = null;
        ComponentUpdateReqDto componentDeleteReqDto = modelMapper.map(componentUpdateRequest, ComponentUpdateReqDto.class);
        ComponentDetailResDto componentDetailResDto = componentService.findOne(componentDeleteReqDto.getId());
        if (componentDetailResDto == null) {
            message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
            status = HttpStatus.NOT_FOUND;
        } else {
            ComponentUpdateReqDto componentUpdateReqDto = modelMapper.map(componentUpdateRequest,
                    ComponentUpdateReqDto.class);
            componentService.update(componentUpdateReqDto);
            message = messageSource.getMessage("update.success", new String[] { "component" }, Locale.getDefault());
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(Collections.singletonList(message), status);
    }

    @RequestMapping(value = "/component-delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteComponents(@RequestBody ComponentReadRequest componentReadRequest)
            throws IOException {
        logger.debug("delete components");
        String message = null;
        HttpStatus status = null;
        List<ComponentDeleteRequest> componentDeleteRequests = componentReadRequest.getComponentDeleteRequests();
        List<ComponentDeleteReqDto> componentDeleteReqDtos =
                modelMapper.map(componentDeleteRequests, new TypeToken<List<ComponentDeleteReqDto>>() {}.getType());

        for (Iterator<ComponentDeleteReqDto> iterator = componentDeleteReqDtos.iterator(); iterator.hasNext();) {
            ComponentDeleteReqDto componentDeleteRequest = iterator.next();
            ComponentDetailResDto componentDetailResDto = componentService.findOne(componentDeleteRequest.getId());
            if (componentDetailResDto == null) {
                message = messageSource.getMessage("data.not.found", new String[] {}, Locale.getDefault());
                status = HttpStatus.NOT_FOUND;
            } else {
                ComponentDeleteReqDto componentDeleteReqDto = modelMapper.map(componentDetailResDto,
                        ComponentDeleteReqDto.class);
                componentService.delete(componentDeleteReqDto);
                message = messageSource.getMessage("delete.success", new String[] { "component" },
                        Locale.getDefault());
                status = HttpStatus.OK;
            }
        }
        return new ResponseEntity<>(Collections.singletonList(message), status);
    }
}
