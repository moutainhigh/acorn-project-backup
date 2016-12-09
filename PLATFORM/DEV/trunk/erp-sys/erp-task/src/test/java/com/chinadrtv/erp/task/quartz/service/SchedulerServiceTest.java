package com.chinadrtv.erp.task.quartz.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.task.core.exception.BizException;
import com.chinadrtv.erp.task.core.test.TestSupport;

public class SchedulerServiceTest extends TestSupport{

	@Autowired
	private SchedulerService service;
	
	@Test
	public void testGetQrtzTriggers() {
//		fail("Not yet implemented");
	}

	@Test
	public void testPauseTrigger() {
//		fail("Not yet implemented");
	}

	@Test
	public void testResumeTrigger() {
//		fail("Not yet implemented");
	}

	@Test
	public void testRemoveTrigger() {
//		fail("Not yet implemented");
	}

	@Test
	public void testExecuteJob() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetJobDetail() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetTrigger() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetRegisteredJob() {
//		fail("Not yet implemented");
	}

	@Test
	public void testCreateTrigger() throws BizException {
//		for(int i=0; i<100; i++){
//			service.createTrigger(TriggerType.CRON, Demo1QuartzJob.class, "测试抛出异常触发器"+i, "测试抛出异常任务"+i, "0 */10 * * * ?", null, null, null, "测试抛出异常每10分钟运行一次");
//			service.createTrigger(TriggerType.CRON, Demo2QuartzJob.class, "测试读取数据库常触发器"+i, "测试读取数据库任务"+i, "0 */10 * * * ?", null, null, null, "测试读取数据库每10分钟运行一次");
//			service.createTrigger(TriggerType.CRON, Demo3QuartzJob.class, "测试打印日志触发器"+i, "测试打印日志任务"+i, "0 */10 * * * ?", null, null, null, "测试打印日志每10分钟运行一次");
//		}
	}

}
