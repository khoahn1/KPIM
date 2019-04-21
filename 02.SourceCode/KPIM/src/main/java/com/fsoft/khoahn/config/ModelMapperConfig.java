package com.fsoft.khoahn.config;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fsoft.khoahn.common.enums.CodeEnum;
import com.fsoft.khoahn.common.enums.DateTimeFormat;
import com.fsoft.khoahn.common.utils.DateTimeUtils;

@Configuration
@Import({ DateTimeUtils.class })
public class ModelMapperConfig {

	@Bean
	protected ModelMapper modelMapper() {
		final ModelMapper modelMapper = new ModelMapper();
		final List<ConditionalConverter<?, ?>> converters = modelMapper.getConfiguration().getConverters();

		modelMapper.addConverter(trimString());
		modelMapper.addConverter(convertStringToUtilDate());
		modelMapper.addConverter(convertUtilDateToString());
		modelMapper.addConverter(convertStringToTimestamp());
		modelMapper.addConverter(convertTimestampToString());
		// modelMapper.addConverter(convertCommonDateTimeToUtilDate());
		// modelMapper.addConverter(convertUtilDateToCommonDateTime());
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		converters.add(0, objectToEnumConverter());
		return modelMapper;
	}

	private AbstractConverter<String, String> trimString() {
		return new AbstractConverter<String, String>() {

			@Override
			protected String convert(String source) {
				if (source == null) {
					source = "";
				}
				return StringUtils.trim(source);
			}
		};
	}

	private AbstractConverter<String, Date> convertStringToUtilDate() {
		return new AbstractConverter<String, Date>() {

			@Override
			protected Date convert(String source) {

				return DateTimeUtils.convertStringToUtilDate(source, DateTimeFormat.SLASH_DDMMYYYY);
			}
		};
	}

	private AbstractConverter<String, Timestamp> convertStringToTimestamp() {
		return new AbstractConverter<String, Timestamp>() {

			@Override
			protected Timestamp convert(String source) {

				return DateTimeUtils.convertStringToTimestamp(source, DateTimeFormat.SLASH_DD_MM_YYYY_HH_MM_SS_FF);
			}
		};
	}

	private AbstractConverter<Timestamp, String> convertTimestampToString() {
		return new AbstractConverter<Timestamp, String>() {

			@Override
			protected String convert(Timestamp source) {

				return DateTimeUtils.convertTimestampToString(source, DateTimeFormat.SLASH_DD_MM_YYYY_HH_MM_SS_FF);
			}
		};
	}

	private AbstractConverter<Date, String> convertUtilDateToString() {
		return new AbstractConverter<Date, String>() {

			@Override
			protected String convert(Date source) {

				return DateTimeUtils.convertUtilDateToString(source, DateTimeFormat.SLASH_DDMMYYYY);
			}
		};
	}
	//
	// private AbstractConverter<DateTime, Date>
	// convertCommonDateTimeToUtilDate() {
	// return new AbstractConverter<DateTime, Date>() {
	//
	// @Override
	// protected Date convert(DateTime source) {
	//
	// return dateTimeUtils.convertCommonDateTimeToUtilDate(source,
	// DateTimeFormat.SLASH_DDMMYYYY);
	// }
	// };
	// }
	//
	// private AbstractConverter<Date, DateTime>
	// convertUtilDateToCommonDateTime() {
	// return new AbstractConverter<Date, DateTime>() {
	//
	// @Override
	// protected DateTime convert(Date source) {
	//
	// return dateTimeUtils.convertUtilDateToCommonDateTime(source,
	// DateTimeFormat.SLASH_DDMMYYYY);
	// }
	// };
	// }

	private ConditionalConverter<String, Enum<? extends CodeEnum>> objectToEnumConverter() {
		return new ConditionalConverter<String, Enum<? extends CodeEnum>>() {
			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Enum<? extends CodeEnum> convert(final MappingContext<String, Enum<? extends CodeEnum>> context) {
				final Object source = context.getSource();
				if (source == null) {
					return null;
				}
				String value = null;
				if (source.getClass() == String.class) {
					value = String.valueOf(source);
				} else {
					value = ((CodeEnum) source).getValue();
				}
				if (value != null) {
					final Class clazz = context.getDestinationType();
					return EnumUtils.getEnum(clazz, value);
				}
				return null;
			}

			@Override
			public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
				MatchResult matchResult = MatchResult.NONE;
				if (destinationType.isEnum() && sourceType == String.class) {
					matchResult = MatchResult.FULL;
				}
				return matchResult;
			}
		};

	}
}