/*
 * @(#)ChannelConverter.java 1.0 2013-2-19下午2:42:06
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.util;

import com.chinadrtv.erp.smsapi.dto.Variables;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-19 下午2:42:06
 * 
 */
public class ChannelConverter implements Converter {

	public boolean canConvert(Class clazz) {
		return clazz.equals(Variables.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext acontext) {
		Variables rawSQL = (Variables) value;
		if (rawSQL != null) {
			if (null != rawSQL.getName()) {
				writer.addAttribute("name", rawSQL.getName());
				writer.addAttribute("value", rawSQL.getValue());
			}
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext arg1) {
		Variables channel = new Variables();
		channel.setName(new String(reader.getAttribute("name")));
		channel.setValue(new String(reader.getAttribute("value")));
		return channel;

	}
}
