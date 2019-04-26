package com.fsoft.khoahn.service;

import java.io.IOException;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.common.exception.InvalidFileException;
import com.fsoft.khoahn.dto.req.UserCreateReqDto;
import com.fsoft.khoahn.dto.req.UserDeleteReqDto;
import com.fsoft.khoahn.dto.req.UserReadReqDto;
import com.fsoft.khoahn.dto.req.UserUpdateReqDto;
import com.fsoft.khoahn.dto.res.DataExportResDto;
import com.fsoft.khoahn.dto.res.UserDetailResDto;
import com.fsoft.khoahn.dto.res.UserReadResDto;

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
