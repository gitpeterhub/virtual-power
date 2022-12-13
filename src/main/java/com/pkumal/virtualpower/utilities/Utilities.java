package com.pkumal.virtualpower.utilities;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utilities {

	public static <T> T parseObject(Object objToParse, Class<T> parseToType) {

		ObjectMapper objectMapper = new ObjectMapper();
		/**
		 * 
		 * Make json key case insensitive for mapping to target class eg: json key is
		 * not in camel case but target class has camel case field
		 * 
		 */
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return objectMapper.convertValue(objToParse, parseToType);

	}

}
