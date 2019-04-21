package com.fsoft.khoahn.service;

import java.util.List;

import com.fsoft.khoahn.model.request.dto.TokenCreateReqDto;
import com.fsoft.khoahn.model.response.dto.TokenDetailResDto;

public interface TokenService {

	List<TokenDetailResDto> findAll();

	void save(TokenCreateReqDto tokenCreateReqDto);

	void delete(String series);

	TokenDetailResDto findOne(String presentedSeries);

}
