package com.chinadrtv.erp.task.jobs.demo4;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.chinadrtv.erp.task.entity.User;
import com.chinadrtv.erp.task.service.UserService;

@Component
public class Demo4ItemReader implements ItemReader<User>, ItemStream{

	private static final Log logger = LogFactory.getLog(Demo4ItemReader.class);
	
    @Autowired
    private UserService userService;
    
	private final List<User> items = new ArrayList<User>();
	public volatile int count = 0;
	
	public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		if (items.size() > count) {
			final User item = items.get(count++);
			logger.info("模拟读取第" + count + "个用户，用户ID:" + item.getId());
			return item;
		}else {
			logger.info("模拟读取第" + count + "个用户，返回了null");
			return null;
		}
	}
	
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		PageRequest pageable = new PageRequest(0, 13) ;
		items.addAll(userService.queryUser(pageable).getContent());
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {}

	@Override
	public void close() throws ItemStreamException {
		items.clear();
		count = 0;
	}

}
