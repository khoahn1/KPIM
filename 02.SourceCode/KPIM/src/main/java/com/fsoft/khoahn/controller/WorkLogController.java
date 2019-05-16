package com.fsoft.khoahn.controller;

import java.util.ArrayList;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.khoahn.common.download.ExcelToObjectMapper;
import com.fsoft.khoahn.dto.req.WorkLogChangeReqDto;
import com.fsoft.khoahn.dto.req.WorkLogDeleteReqDto;
import com.fsoft.khoahn.dto.req.WorkLogReadReqDto;
import com.fsoft.khoahn.dto.res.WorkLogDetailResDto;
import com.fsoft.khoahn.model.WorkLogImportExportContent;
import com.fsoft.khoahn.model.request.FileUploadRequest;
import com.fsoft.khoahn.model.request.WorkLogChangeRequest;
import com.fsoft.khoahn.model.request.WorkLogDeleteRequest;
import com.fsoft.khoahn.model.request.WorkLogReadRequest;
import com.fsoft.khoahn.model.response.PhaseDetailResponse;
import com.fsoft.khoahn.model.response.ProductDetailResponse;
import com.fsoft.khoahn.model.response.TypeOfWorkDetailResponse;
import com.fsoft.khoahn.model.response.UnitDetailResponse;
import com.fsoft.khoahn.model.response.WorkLogChangeResponse;
import com.fsoft.khoahn.model.response.WorkLogDeleteResponse;
import com.fsoft.khoahn.model.response.WorkLogDetailResponse;
import com.fsoft.khoahn.model.response.WorkLogReadResponse;
import com.fsoft.khoahn.service.ComponentService;
import com.fsoft.khoahn.service.PhaseService;
import com.fsoft.khoahn.service.ProductService;
import com.fsoft.khoahn.service.TypeOfWorkService;
import com.fsoft.khoahn.service.UnitService;
import com.fsoft.khoahn.service.WorkLogService;

@RestController
@RequestMapping("/worklogs")
public class WorkLogController {

    private static final Logger logger = LoggerFactory.getLogger(WorkLogController.class);

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private ComponentService componentService;

    @Autowired
    private PhaseService phaseService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private TypeOfWorkService typeOfWorkService;

    @Autowired
    private ProductService productService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/worklog-read", method = RequestMethod.POST)
    public ResponseEntity<?> getAll(@RequestBody WorkLogReadRequest workLogReadRequest) {
        logger.debug("get worklog list");
        WorkLogReadResponse response = new WorkLogReadResponse();
        WorkLogReadReqDto workLogReadReqDto = modelMapper.map(workLogReadRequest, WorkLogReadReqDto.class);
        Page<WorkLogDetailResDto> workLogReadResDtos = workLogService.findAll(workLogReadReqDto);

        List<WorkLogDetailResponse> content =
                modelMapper.map(workLogReadResDtos.getContent(),
                        new TypeToken<List<WorkLogDetailResponse>>() {}.getType());

        Page<WorkLogDetailResponse> page =
                new PageImpl<>(content, workLogReadResDtos.getPageable(), workLogReadResDtos.getTotalElements());
        response.setWorklogs(page);

        List<PhaseDetailResponse> phases = modelMapper.map(
                phaseService.findAll(),
                new TypeToken<List<PhaseDetailResponse>>() {}.getType());
        response.setPhases(phases);

        List<UnitDetailResponse> units = modelMapper.map(
                unitService.findAll(),
                new TypeToken<List<UnitDetailResponse>>() {}.getType());
        response.setUnits(units);

        List<TypeOfWorkDetailResponse> typeOfWorks = modelMapper.map(
                typeOfWorkService.findAll(),
                new TypeToken<List<TypeOfWorkDetailResponse>>() {}.getType());
        response.setTypeOfWorks(typeOfWorks);

        List<ProductDetailResponse> products = modelMapper.map(
                productService.findAll(),
                new TypeToken<List<ProductDetailResponse>>() {}.getType());
        response.setProducts(products);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/worklog-changes", method = RequestMethod.POST)
    public ResponseEntity<?> changeRequest(@Validated @RequestBody WorkLogChangeRequest workLogChangeRequest) {
        WorkLogChangeResponse response = new WorkLogChangeResponse();
        String message = null;
        boolean success = false;
        try {
            workLogService.changes(modelMapper.map(workLogChangeRequest, WorkLogChangeReqDto.class));
            message = messageSource.getMessage("update.success", new String[] { "worklogs" }, Locale.getDefault());
            success = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message = messageSource.getMessage("update.failure", new String[] { "worklogs" }, Locale.getDefault());
        }
        response.setSuccess(success);
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/worklog-deletes", method = RequestMethod.POST)
    public ResponseEntity<?> deleteRequest(@RequestBody WorkLogDeleteRequest workLogDeleteRequest) {
        WorkLogDeleteResponse response = new WorkLogDeleteResponse();
        String message = null;
        boolean success = false;
        try {
            workLogService.deletes(modelMapper.map(workLogDeleteRequest, WorkLogDeleteReqDto.class));
            message = messageSource.getMessage("delete.success", new String[] { "worklogs" }, Locale.getDefault());
            success = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message = messageSource.getMessage("delete.failure", new String[] { "worklog" }, Locale.getDefault());
        }
        response.setSuccess(success);
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/worklogs-imports", method = RequestMethod.POST)
    public ResponseEntity<?> importWorkLogs(@Validated @ModelAttribute FileUploadRequest fileUploadRequest) {
        logger.debug("Import worklogs");
        List<String> messages = new ArrayList<String>();
        String message = null;
        HttpStatus status = null;
        try {
            ExcelToObjectMapper mapper = new ExcelToObjectMapper(fileUploadRequest.getFile());
            List<WorkLogImportExportContent> dataImportWorkLogs = mapper.map(WorkLogImportExportContent.class);
            workLogService.imports(dataImportWorkLogs);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message = messageSource.getMessage("error.file.upload", new String[] {}, Locale.getDefault());
            messages.add(message);
            status = HttpStatus.FORBIDDEN;
        }
        if (status == HttpStatus.OK) {
            message = messageSource.getMessage("import.success", new String[] { "worklogs" }, Locale.getDefault());
            messages.add(message);
        }
        return new ResponseEntity<>(messages, status);
    }

}
