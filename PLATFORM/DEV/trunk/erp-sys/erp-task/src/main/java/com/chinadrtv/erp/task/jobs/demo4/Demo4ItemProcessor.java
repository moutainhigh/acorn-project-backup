package com.chinadrtv.erp.task.jobs.demo4;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.chinadrtv.erp.task.entity.User;

@Component
public class Demo4ItemProcessor implements ItemProcessor<User,User> {

	private static final Log logger = LogFactory.getLog(Demo4ItemProcessor.class);
	
	@Override
	public User process(User item) throws Exception {
		logger.info("模拟处理用户,ID: " + item.getId());
		return item;
	}

}
