/*
 * @(#)CampaignApiServiceTest.java 1.0 2013-5-13上午10:20:10
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.service;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;

import com.chinadrtv.erp.JunitTestBase;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.service.JobRelationexService;
import com.chinadrtv.erp.message.AssignMessage;
import com.chinadrtv.erp.model.agent.ObAssignHist;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-13 上午10:20:10
 * 
 */
public class JobRelationexServiceTest extends JunitTestBase {
	@Autowired
	private JobRelationexService jobRelationexService;

//	@Test
//	public void queryJobRelationexTest() {

//		List<JobsRelationex> result = jobRelationexService.queryJobRelationex(
//				"sa", "1", "SCPVIP");
//
//		Assert.assertTrue(result.size() >= 0);
//	}

//	@Test
//	public void queryInboundCampaignTest() {
//
//		ObAssignHist assignHist = new ObAssignHist();
//		Long id = jobRelationexService.getSeqNextValue();
		// assignHist.setId(String.valueOf(id));
//		assignHist.setAgent("test");
//		assignHist.setAssignTime(new Date());
//		assignHist.setBatchId("2000000001");
//		assignHist.setContactId("11520575");
//		assignHist.setDefGrp("80060");
//		assignHist.setDept("11");
//		assignHist.setJobId("SCPVIP");
//		assignHist.setOperator("test"); 
	
//		assignHist.setPdCustomerId("20081210101227000000");
//		assignHist.setQueueId("111");
//
//		try {
//			jobRelationexService.submitAssignHist(assignHist);
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		//Assert.assertNotNull(assignHist.getId());
//	}
	
	
//	@Test
//	public void assignHistTest() {
//
//		try {
//			ObAssignHist assignHist = new ObAssignHist();
//			assignHist.setJobId("VIP");
//			assignHist.setAgent("000");
//			AssignMessage message = jobRelationexService.assignHist(assignHist);
//			System.out.println("====="+message.getContactId());
//			message =  jobRelationexService.assignHist(assignHist);
//			System.out.println("====="+message.getContactId());
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
//
//		
//	}
	
	 	@Test  
	    @Repeat(2)  
	    public void MultiRequestsTest() {  
//	        // 构造一个Runner  
//	        TestRunnable runner = new TestRunnable() {  
//	            @Override  
//	            public void runTest() throws Throwable {  
//	              
//	            }  
//	        };
	        
	        
	        
	        int runnerCount = 1;  
	        // Rnner数组，相当于并发多少个。  
	        TestRunnable[] trs = new TestRunnable[runnerCount];  
	        for (int i = 0; i < runnerCount; i++) {  
	            trs[i] = new ThreadA();  
	        }  
	        // 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入  
	        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(trs);  
	        try {  
	            // 开发并发执行数组里定义的内容  
	            mttr.runTestRunnables();  
	        } catch (Throwable e) {  
	            e.printStackTrace();  
	        }  
	    }  
	 
	
	 	private class ThreadA extends TestRunnable {  
            @Override  
            public void runTest() throws Throwable {  
      
            	// 测试内容  
	            	
	        		try {
	    			ObAssignHist assignHist = new ObAssignHist();
	    			assignHist.setJobId("VIP");
	    			assignHist.setAgent("000");
	    			AssignMessage message = jobRelationexService.assignHist(assignHist);
	    			System.out.println("====="+message.getContactId());
//	    			message =  jobRelationexService.assignHist(assignHist);
//	    			System.out.println("====="+message.getContactId());
	    		} catch (ServiceException e) {
	    			e.printStackTrace();
	    		}
	                // System.out.println("a");  
            }  
        }  
	 	
//	@Test
//	public void queryUnprocessTest() {
//
//			List<AssignMessage> list = jobRelationexService.queryUnprocessed("sa", "VIP");
//			System.out.println("====="+list.size());
//
//		
//	}

}
