package com.chinadrtv.erp.task.jobs.demo2;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.springframework.data.domain.PageRequest;

import com.chinadrtv.erp.task.core.job.Task;
import com.chinadrtv.erp.task.core.scheduler.SimpleJob;
import com.chinadrtv.erp.task.entity.User;
import com.chinadrtv.erp.task.service.UserService;

/**
 * 本实例为最简单的、对数据库的操作。
 * 模拟batch，将数据处理分为读、操作、写三步处理.
 * @author zhangguosheng
 */
@Task(name="Demo2QuartzJob", description="测试注入service,读取数据库")
public class Demo2QuartzJob extends SimpleJob{
	
    private UserService userService;
    
	private List<User> items = new ArrayList<User>();

	@Override
	public void init(JobExecutionContext context) {
		super.init(context);
    	if(applicationContext!=null){
			userService = (UserService) applicationContext.getBean(UserService.class);
		}
	}

	@Override
	public void doExecute(JobExecutionContext context){
    	//读取
    	read();
    	//处理
    	process();
    	//回写
    	write();
	}
    
    /**
     * 读取
     */
	public void read(){
		PageRequest pageable = new PageRequest(0, 13) ;
		items.addAll(userService.queryUser(pageable).getContent());
	} 
	
	/**
	 * 处理
	 */
	public void process(){
		for (User item : items) {
			logger.info("**********, process address: " + item.getName());
		}
	}
	
	/**
	 * 回写
	 */
	public void write(){
		for (User item : items) {
			logger.info("**********, write address: " + item.getName());
		}
	}

}
