package com.fsoft.khoahn.service;

import org.springframework.data.domain.Page;

import com.fsoft.khoahn.dto.req.TypeOfWorkCreateReqDto;
import com.fsoft.khoahn.dto.req.TypeOfWorkDeleteReqDto;
import com.fsoft.khoahn.dto.req.TypeOfWorkReadReqDto;
import com.fsoft.khoahn.dto.req.TypeOfWorkUpdateReqDto;
import com.fsoft.khoahn.dto.res.TypeOfWorkDetailResDto;

public interface TypeOfWorkService {

	TypeOfWorkDetailResDto findOne(Integer id);

	Page<TypeOfWorkDetailResDto> findAll(TypeOfWorkReadReqDto typeOfWorkReadReqDto);

	boolean isExitTypeOfWorkName(String typeOfWorkName);

	void save(TypeOfWorkCreateReqDto typeOfWorkCreateReqDto);

	void update(TypeOfWorkUpdateReqDto typeOfWorkUpdateReqDto);

	void delete(TypeOfWorkDeleteReqDto typeOfWorkDeleteReqDto);

}
