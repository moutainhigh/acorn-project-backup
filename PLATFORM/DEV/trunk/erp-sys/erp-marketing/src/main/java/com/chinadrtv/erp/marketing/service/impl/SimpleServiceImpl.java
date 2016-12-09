package com.chinadrtv.erp.marketing.service.impl;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.service.SimpleService;

@Service("simpleService")
public class SimpleServiceImpl implements Serializable, SimpleService {

	private static final long serialVersionUID = 122323233244334343L;
	private static final Logger logger = LoggerFactory
			.getLogger(SimpleServiceImpl.class);

	/***
	 * 测试方法一
	 * 
	 * @Description: TODO
	 * @param triggerName
	 * @param group
	 * @return void
	 * @throws
	 */
	public void testMethod(String triggerName, String group) {
		// 这里执行定时调度业务
		logger.info("AAAA:" + triggerName + "==" + group);

	}

	/***
	 * 测试方法二
	 * 
	 * @Description: TODO
	 * @param triggerName
	 * @param group
	 * @return void
	 * @throws
	 */
	public void testMethod2(String triggerName, String group) {
		// 这里执行定时调度业务
		logger.info("BBBB:" + triggerName + "==" + group);
	}

}
