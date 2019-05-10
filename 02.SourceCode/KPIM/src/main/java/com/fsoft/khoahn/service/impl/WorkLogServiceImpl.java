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
import org.springframework.util.CollectionUtils;

import com.fsoft.khoahn.common.utils.PageRequestUtils;
import com.fsoft.khoahn.common.utils.StringUtils;
import com.fsoft.khoahn.dto.req.PaginationReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;
import com.fsoft.khoahn.dto.req.WorkLogChangeReqDto;
import com.fsoft.khoahn.dto.req.WorkLogCreateReqDto;
import com.fsoft.khoahn.dto.req.WorkLogDeleteReqDto;
import com.fsoft.khoahn.dto.req.WorkLogReadReqDto;
import com.fsoft.khoahn.dto.res.WorkLogDetailResDto;
import com.fsoft.khoahn.repository.TaskRepo;
import com.fsoft.khoahn.repository.UserRepo;
import com.fsoft.khoahn.repository.WorkLogRepo;
import com.fsoft.khoahn.repository.entity.TaskEntity;
import com.fsoft.khoahn.repository.entity.UserEntity;
import com.fsoft.khoahn.repository.entity.WorkLogEntity;
import com.fsoft.khoahn.security.SecurityUtils;
import com.fsoft.khoahn.service.WorkLogService;

@Service
public class WorkLogServiceImpl implements WorkLogService {

    @Autowired
    private WorkLogRepo workLogRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TaskRepo taskRepo;

    @Override
    public Page<WorkLogDetailResDto> findAll(WorkLogReadReqDto workLogReadReqDto) {
        PaginationReqDto paginationRequest = workLogReadReqDto.getPaginationRequest();
        List<SortReqDto> sortDtoList = workLogReadReqDto.getSortRequest();

        Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);
        Page<WorkLogEntity> workLogEntities = workLogRepo
                .findByTaskUserUsername(SecurityUtils.getCurrentLogin(), pageable);

        Type type = new TypeToken<List<WorkLogDetailResDto>>() {}.getType();
        List<WorkLogDetailResDto> workLogDetailResDtos = modelMapper.map(workLogEntities.getContent(), type);

        Page<WorkLogDetailResDto> page = new PageImpl<>(workLogDetailResDtos,
                pageable,
                workLogEntities.getTotalElements());

        return page;
    }

    @Override
    public void changes(WorkLogChangeReqDto workLogChangeReqDto) {
        // Declare variables
        WorkLogEntity workLogEntity = null;
        UserEntity userEntity = null;
        TaskEntity taskEntity = null;
        TaskEntity taskEntityCheckExist = null;

        // Get current user
        userEntity = userRepo.findByUsername(SecurityUtils.getCurrentLogin());
        for (WorkLogCreateReqDto worklogChanges : workLogChangeReqDto.getWorklogs()) {
            workLogEntity = modelMapper.map(worklogChanges, WorkLogEntity.class);
            taskEntity = workLogEntity.getTask();

            if (taskEntity.getProduct() != null && taskEntity.getPhase() != null) {
                // Get task by ProductId and PhaseId and UserId
                taskEntityCheckExist =
                        taskRepo.findByProductIdAndPhaseIdAndUserId(
                                taskEntity.getProduct().getId(), taskEntity.getPhase().getId(), userEntity.getId());
            }

            if (taskEntityCheckExist != null) {
                workLogEntity.setTask(taskEntityCheckExist);
            } else {
                taskEntity.setId(null);
                taskEntity.setUserId(userEntity.getId());
                taskEntity.setUser(userEntity);
                taskEntity.setTaskCode(this.generateTaskCode(taskEntity));
            }
            // If workLogEntity has id then update
            // Else create
            workLogRepo.save(workLogEntity);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deletes(WorkLogDeleteReqDto workLogDeleteReqDto) {
        if (workLogDeleteReqDto != null && !CollectionUtils.isEmpty(workLogDeleteReqDto.getIds())) {
            for (Integer id : workLogDeleteReqDto.getIds()) {
                workLogRepo.deleteById(id);
            }
        }
    }

    private String generateTaskCode(TaskEntity taskEntity) {
        if (taskEntity == null || taskEntity.getProduct() == null || taskEntity.getPhase() == null) {
            return null;
        }

        String productName = taskEntity.getProduct().getProductName();
        String phaseCode = taskEntity.getPhase().getPhaseCode();

        if(StringUtils.isBlank(productName) && StringUtils.isBlank(phaseCode)) {
            return null;
        }

        return String.format("[%s][%s]", productName, phaseCode);
    }

}
