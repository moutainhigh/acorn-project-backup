package com.chinadrtv.erp.task.jobs.demo4;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.chinadrtv.erp.task.entity.User;

@Component
public class Demo4ItemWriter implements ItemWriter<User> {

	private static final Log logger = LogFactory.getLog(Demo4ItemWriter.class);
	
	public void write(final List<? extends User> items) throws Exception {
		for (User item : items) {
			logger.info("模拟写入用户, ID: " + item.getId());
		}
	}

}
