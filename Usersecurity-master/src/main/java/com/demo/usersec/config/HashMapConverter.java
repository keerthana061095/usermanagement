package com.demo.usersec.config;

import java.io.IOException;
import java.util.Map;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.usersec.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public String convertToDatabaseColumn(Map<String, Object> someMap) {
		String someString = null;
		try {
			someString = new ObjectMapper().writeValueAsString(someMap);
		} catch (final JsonProcessingException e) {
			logger.error("Json write error ", e);
		}
		
		return someString;
	}
	
	@Override
	public Map<String, Object> convertToEntityAttribute(String someString) {
		Map<String, Object> someMap = null;
		try {
			someMap = new ObjectMapper().readValue(someString, Map.class);
		} catch (final IOException e) {
			logger.error("JSON read error " , e);
		}
		
		return someMap;				
	}
	
}
