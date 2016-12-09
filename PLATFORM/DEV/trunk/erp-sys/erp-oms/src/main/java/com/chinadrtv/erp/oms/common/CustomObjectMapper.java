package com.chinadrtv.erp.oms.common;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
/**
 * Mapper
 * @author haoleitao
 *
 */
public class CustomObjectMapper extends ObjectMapper {

	public CustomObjectMapper() {
		super();
		configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		this.getSerializationConfig().setDateFormat(new SimpleDateFormat(
				"yyyy-Mm-dd HH:mm:ss"));
	}
}