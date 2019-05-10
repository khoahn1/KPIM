package com.fsoft.khoahn.service;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.dto.req.ProjectCreateReqDto;
import com.fsoft.khoahn.dto.req.ProjectDeleteReqDto;
import com.fsoft.khoahn.dto.req.ProjectMemberReadReqDto;
import com.fsoft.khoahn.dto.req.ProjectReadReqDto;
import com.fsoft.khoahn.dto.req.ProjectUpdateReqDto;
import com.fsoft.khoahn.dto.res.DataExportResDto;
import com.fsoft.khoahn.dto.res.ProjectDetailResDto;
import com.fsoft.khoahn.dto.res.UserDetailResDto;

public interface ProjectService {

	Page<ProjectDetailResDto> getAll(ProjectReadReqDto projectReadReqDto);
	
	void save(ProjectCreateReqDto projectCreateReqDto);
	
	ProjectDetailResDto findOne(String id);
	
	void update(ProjectUpdateReqDto projectUpdateReqDto);
	
	void delete(ProjectDeleteReqDto projectDeleteReqDto);
	
	boolean isExist(String id);
	
	DataExportResDto exportExcel() throws Exception;

	Page<UserDetailResDto> getMember(ProjectMemberReadReqDto projectMemberReadReqDto);
}
