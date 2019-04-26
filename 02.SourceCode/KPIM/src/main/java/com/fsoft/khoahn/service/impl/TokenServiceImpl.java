package com.fsoft.khoahn.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.khoahn.dto.req.TokenCreateReqDto;
import com.fsoft.khoahn.dto.res.TokenDetailResDto;
import com.fsoft.khoahn.repository.TokenRepo;
import com.fsoft.khoahn.repository.entity.TokenEntity;
import com.fsoft.khoahn.service.TokenService;

@Service("tokenService")
@Transactional
public class TokenServiceImpl implements TokenService {
	@Autowired
	private TokenRepo tokenRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<TokenDetailResDto> findAll() {
		List<TokenEntity> tokenEntities = tokenRepo.findAll();
		Type typeTokens = new TypeToken<List<TokenDetailResDto>>() {
		}.getType();
		List<TokenDetailResDto> tokens = modelMapper.map(tokenEntities, typeTokens);

		return tokens;
	}

	@Override
	public void save(TokenCreateReqDto tokenCreateReqDto) {
		TokenEntity tokenEntity = modelMapper.map(tokenCreateReqDto, TokenEntity.class);;
		tokenRepo.save(tokenEntity);
	}

	@Override
	public void delete(String series) {
		tokenRepo.deleteById(series);
	}

	@Override
	public TokenDetailResDto findOne(String presentedSeries) {
		TokenEntity tokenEntity = tokenRepo.findById(presentedSeries).get();
		TokenDetailResDto tokenDetailResDto = modelMapper.map(tokenEntity, TokenDetailResDto.class);
		return tokenDetailResDto;
	}

}
