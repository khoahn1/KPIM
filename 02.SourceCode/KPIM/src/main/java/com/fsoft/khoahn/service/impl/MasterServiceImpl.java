package com.fsoft.khoahn.service.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.khoahn.dto.res.GenderDetailResDto;
import com.fsoft.khoahn.dto.res.LanguageDetailResDto;
import com.fsoft.khoahn.dto.res.MaritalStatusDetailResDto;
import com.fsoft.khoahn.dto.res.MasterDataResDto;
import com.fsoft.khoahn.repository.GenderRepo;
import com.fsoft.khoahn.repository.LanguageRepo;
import com.fsoft.khoahn.repository.MaritalStatusRepo;
import com.fsoft.khoahn.repository.entity.GenderEntity;
import com.fsoft.khoahn.repository.entity.LanguageEntity;
import com.fsoft.khoahn.repository.entity.MaritalStatusEntity;
import com.fsoft.khoahn.service.MasterService;

@Service("genderService")
@Transactional
public class MasterServiceImpl implements MasterService {
	@Autowired
	private GenderRepo genderRepo;
	@Autowired
	private LanguageRepo languageRepo;
	@Autowired
	private MaritalStatusRepo maritalStatusRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public MasterDataResDto getMasterData() {
		MasterDataResDto masterDataResDto = new MasterDataResDto();

		// Get gender
		List<GenderEntity> genderEntitys = genderRepo.findAll();

		Type typeGenders = new TypeToken<List<GenderDetailResDto>>() {
		}.getType();
		List<GenderDetailResDto> genderDetailResDtos = modelMapper.map(genderEntitys, typeGenders);

		masterDataResDto.setGenders(genderDetailResDtos);

		// Get Language
		List<LanguageEntity> languageEntitys = languageRepo.findAll();

		Type typeLanguages = new TypeToken<List<LanguageDetailResDto>>() {
		}.getType();
		List<LanguageDetailResDto> languageDetailResDtos = modelMapper.map(languageEntitys, typeLanguages);
		masterDataResDto.setLanguages(languageDetailResDtos);

		// Get maritalStatus
		List<MaritalStatusEntity> maritalStatusEntitys = maritalStatusRepo.findAll();

		Type typeMaritalStatuss = new TypeToken<List<MaritalStatusDetailResDto>>() {
		}.getType();
		List<MaritalStatusDetailResDto> maritalStatusDetailResDtos = modelMapper.map(maritalStatusEntitys,
				typeMaritalStatuss);

		masterDataResDto.setMaritalStatuses(maritalStatusDetailResDtos);

		return masterDataResDto;
	}

}
