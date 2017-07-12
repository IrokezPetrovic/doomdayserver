package org.doomday.server.misc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class JsonConfig {
	@Bean
	@Scope("prototype")
	public ObjectMapper jsonMapper(){		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		mapper.configure(Feature.IGNORE_UNKNOWN, true);
		mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_MISSING_VALUES, true);
		mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
		return mapper;
	}
}
