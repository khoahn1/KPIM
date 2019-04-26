package com.fsoft.khoahn.service;

import java.util.List;

import com.fsoft.khoahn.dto.req.TokenCreateReqDto;
import com.fsoft.khoahn.dto.res.TokenDetailResDto;

public interface TokenService {

	List<TokenDetailResDto> findAll();

	void save(TokenCreateReqDto tokenCreateReqDto);

	void delete(String series);

	TokenDetailResDto findOne(String presentedSeries);

}
