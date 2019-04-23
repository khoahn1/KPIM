package com.fsoft.khoahn.service;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.model.request.dto.TypeOfWorkCreateReqDto;
import com.fsoft.khoahn.model.request.dto.TypeOfWorkDeleteReqDto;
import com.fsoft.khoahn.model.request.dto.TypeOfWorkReadReqDto;
import com.fsoft.khoahn.model.request.dto.TypeOfWorkUpdateReqDto;
import com.fsoft.khoahn.model.response.dto.TypeOfWorkDetailResDto;

public interface TypeOfWorkService {

	TypeOfWorkDetailResDto findOne(Integer id);

	Page<TypeOfWorkDetailResDto> findAll(TypeOfWorkReadReqDto typeOfWorkReadReqDto);

	boolean isExitTypeOfWorkName(String typeOfWorkName);

	void save(TypeOfWorkCreateReqDto typeOfWorkCreateReqDto);

	void update(TypeOfWorkUpdateReqDto typeOfWorkUpdateReqDto);

	void delete(TypeOfWorkDeleteReqDto typeOfWorkDeleteReqDto);

}
