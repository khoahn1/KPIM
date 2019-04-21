package com.fsoft.khoahn.service;

import java.io.IOException;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.common.exception.InvalidFileException;
import com.fsoft.khoahn.model.request.dto.UserCreateReqDto;
import com.fsoft.khoahn.model.request.dto.UserDeleteReqDto;
import com.fsoft.khoahn.model.request.dto.UserReadReqDto;
import com.fsoft.khoahn.model.request.dto.UserUpdateReqDto;
import com.fsoft.khoahn.model.response.dto.DataExportResDto;
import com.fsoft.khoahn.model.response.dto.UserDetailResDto;
import com.fsoft.khoahn.model.response.dto.UserReadResDto;

public interface UserService {

	UserReadResDto findOne(String id);

	Page<UserDetailResDto> findAll(UserReadReqDto userReadReqDto);

	void delete(UserDeleteReqDto userDeleteReqDto) throws IOException;

	void save(UserCreateReqDto userCreateReqDto) throws InvalidFileException, IOException;

	boolean isExitUsername(String username);

	boolean isExitUserId(String id);

	void update(UserUpdateReqDto userUpdateReqDto) throws IOException;

	void updateUserAuthority(UserUpdateReqDto userUpdateReqDto);

	UserReadResDto findByUsername(String username);

	DataExportResDto exportExcelData() throws Exception;

	DataExportResDto exportPdfData() throws Exception;

}
