package com.fsoft.khoahn.dto.res;

import java.util.List;

import lombok.Data;

@Data
public class MasterDataResDto {
	private List<GenderDetailResDto> genders;
	private List<LanguageDetailResDto> languages;
	List<MaritalStatusDetailResDto> maritalStatuses;
}
