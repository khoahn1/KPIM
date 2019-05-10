package com.fsoft.khoahn.service.impl;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fsoft.khoahn.common.Constants;
import com.fsoft.khoahn.common.download.ExcelGenerator;
import com.fsoft.khoahn.common.utils.PageRequestUtils;
import com.fsoft.khoahn.dto.req.PaginationReqDto;
import com.fsoft.khoahn.dto.req.ProjectCreateReqDto;
import com.fsoft.khoahn.dto.req.ProjectDeleteReqDto;
import com.fsoft.khoahn.dto.req.ProjectMemberReadReqDto;
import com.fsoft.khoahn.dto.req.ProjectMemberSearchReqDto;
import com.fsoft.khoahn.dto.req.ProjectReadReqDto;
import com.fsoft.khoahn.dto.req.ProjectSearchReqDto;
import com.fsoft.khoahn.dto.req.ProjectUpdateReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;
import com.fsoft.khoahn.dto.res.DataExportResDto;
import com.fsoft.khoahn.dto.res.ProjectDetailResDto;
import com.fsoft.khoahn.dto.res.UserDetailResDto;
import com.fsoft.khoahn.model.ProjectImportExportContent;
import com.fsoft.khoahn.repository.DepartmentRepo;
import com.fsoft.khoahn.repository.ProjectRepo;
import com.fsoft.khoahn.repository.ProjectUserRepo;
import com.fsoft.khoahn.repository.entity.DepartmentEntity;
import com.fsoft.khoahn.repository.entity.ProjectEntity;
import com.fsoft.khoahn.repository.entity.ProjectUserEntity;
import com.fsoft.khoahn.security.SecurityUtils;
import com.fsoft.khoahn.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepo projectRepo;

	@Autowired
	private ProjectUserRepo projectUserRepo;

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<ProjectDetailResDto> getAll(ProjectReadReqDto projectReadReqDto) {

		PaginationReqDto paginationRequest = projectReadReqDto.getPaginationRequest();

		ProjectSearchReqDto projectSearchRequest = projectReadReqDto.getProjectSearchRequest();

		List<SortReqDto> sortDtoList = projectReadReqDto.getSortRequest();

		Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);

		Page<ProjectEntity> projectEntitys = projectRepo.findByProjectCodeContainingAndProjectNameContaining(
				projectSearchRequest.getProjectCode(), projectSearchRequest.getProjectName(), pageable);

		Type typeProjects = new TypeToken<List<ProjectDetailResDto>>() {
		}.getType();
		List<ProjectDetailResDto> phaseDetailResDtos = modelMapper.map(projectEntitys.getContent(), typeProjects);

		Page<ProjectDetailResDto> page = new PageImpl<>(phaseDetailResDtos, pageable,
				projectEntitys.getTotalElements());

		return page;

	}

	@Override
	@Transactional
	public void save(ProjectCreateReqDto projectCreateReqDto) {

		String currentUser = SecurityUtils.getCurrentLogin();

		ProjectEntity projectEntity = modelMapper.map(projectCreateReqDto, ProjectEntity.class);

		projectEntity.setCreatedBy(currentUser);

		projectEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));

		DepartmentEntity departmentEntity = departmentRepo.findById(projectEntity.getDepartment().getId()).get();

		projectEntity.setDepartment(departmentEntity);

		projectRepo.save(projectEntity);

	}

	@Override
	public ProjectDetailResDto findOne(String id) {

		ProjectEntity projectEntity = projectRepo.findById(id).get();

		ProjectDetailResDto projectDetailResDto = modelMapper.map(projectEntity, ProjectDetailResDto.class);

		return projectDetailResDto;
	}

	@Override
	public void update(ProjectUpdateReqDto projectUpdateReqDto) {

		DepartmentEntity departmentEntity = departmentRepo.findById(projectUpdateReqDto.getDepartment().getId()).get();

		ProjectEntity projectEntity = projectRepo.findById(projectUpdateReqDto.getId()).get();

		projectEntity.setDepartment(departmentEntity);

		projectEntity = modelMapper.map(projectUpdateReqDto, ProjectEntity.class);

		projectRepo.save(projectEntity);

	}

	@Override
	public void delete(ProjectDeleteReqDto projectDeleteReqDto) {

		projectRepo.deleteById(projectDeleteReqDto.getId());

	}

	@Override
	public boolean isExist(String id) {
		ProjectEntity projectEntity = projectRepo.findById(id).get();
		
		if (projectEntity == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public DataExportResDto exportExcel() throws Exception {
		
		List<ProjectEntity> projectEntities = projectRepo.findAll();
		
		Type typeProjects = new TypeToken<List<ProjectImportExportContent>>() {}.getType();
		
		List<ProjectImportExportContent> projectImportExportContents = modelMapper.map(projectEntities, typeProjects);
		
		DataExportResDto resDto = ExcelGenerator.map(new ProjectImportExportContent(), projectImportExportContents, 
				"Project_Export", Constants.PATH_EXPORT_DATA_PROJECT);
		
		return resDto;
	}

	@Override
	public Page<UserDetailResDto> getMember(ProjectMemberReadReqDto projectMemberReadReqDto) {
		PaginationReqDto paginationRequest = projectMemberReadReqDto.getPaginationRequest();

		ProjectMemberSearchReqDto projectMemberSearchReqDto = projectMemberReadReqDto.getProjectMemberSearchRequest();

		List<SortReqDto> sortDtoList = projectMemberReadReqDto.getSortRequest();

		Pageable pageable = PageRequestUtils.createPageRequest(paginationRequest, sortDtoList);

		Page<ProjectUserEntity> projectUserEntitys = projectUserRepo.findByProjectId(
				projectMemberSearchReqDto.getProjectId(), pageable);

		List<UserDetailResDto> userDetailResDtos = new ArrayList<UserDetailResDto>();
		for (Iterator<ProjectUserEntity> iterator = projectUserEntitys.iterator(); iterator.hasNext();) {
			ProjectUserEntity projectUserEntity = iterator.next();
			UserDetailResDto userDetailResDto = modelMapper.map(projectUserEntity.getUser(), UserDetailResDto.class);
			userDetailResDtos.add(userDetailResDto);
		}

		Page<UserDetailResDto> page = new PageImpl<>(userDetailResDtos, pageable,
				projectUserEntitys.getTotalElements());

		return page;
	}
}
