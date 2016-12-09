/*
 * @(#)ClConverter.java 1.0 2013-3-11下午2:16:38
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.util;

import com.chinadrtv.erp.smsapi.dto.Channel;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-3-11 下午2:16:38
 * 
 */
public class ClConverter implements Converter {
	public boolean canConvert(Class clazz) {
		return clazz.equals(Channel.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext acontext) {
		Channel rawSQL = (Channel) value;
		if (rawSQL != null) {
			if (null != rawSQL.getType()) {
				writer.addAttribute("type", rawSQL.getType());
			}
			writer.setValue(rawSQL.getChannel() == null ? "" : rawSQL
					.getChannel());
		}

	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext arg1) {
		Channel channel = new Channel();
		String type = reader.getAttribute("type");
		channel.setType(type);

		String value = reader.getValue();

		channel.setChannel(value);

		return channel;

	}

}
