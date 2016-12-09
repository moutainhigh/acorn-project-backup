/*
 * @(#)CallbackServiceTest.java 1.0 2013-7-31下午3:07:19
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import static org.junit.Assert.*;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.model.marketing.Callback;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.uc.dto.AgentQueryDto4Callback;
import com.chinadrtv.erp.uc.dto.AssignAgentDto;
import com.chinadrtv.erp.uc.dto.CallbackDto;
import com.chinadrtv.erp.uc.test.AppTest;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.UserService;

import junit.framework.Assert;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.AssertTrue;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-7-31 下午3:07:19 
 * 
 */
public class CallbackServiceTest extends AppTest {

	@Autowired
	private CallbackService callbackService;
	
	@Autowired
	private UserService userService;

    @Test
    public void testInit(){
        Assert.assertNotNull(callbackService);
    }

	@Test
	//@Rollback(false)
	public void assignToGroup() throws Exception{
		LeadInteractionSearchDto li = new LeadInteractionSearchDto();
		li.setUserId("27430");
		List<Map<String, Object>> agentGroups = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("assignCount", 4);
		groupMap.put("userGroup", "outdialsh55");
		
		Map<String, Object> groupMap1 = new HashMap<String, Object>();
		groupMap1.put("assignCount", 5);
		groupMap1.put("userGroup", "outdialsh62");
		
		Map<String, Object> groupMap2 = new HashMap<String, Object>();
		groupMap2.put("assignCount", 8);
		groupMap2.put("userGroup", "Agentsh23s");
		
		agentGroups.add(groupMap);
		agentGroups.add(groupMap1);
		agentGroups.add(groupMap2);
			
//		callbackService.assignToGroup(li, agentGroups, false);
	}

	@Test
	//@Rollback(false)
	public void assignToAgent() throws Exception{
		LeadInteractionSearchDto li = new LeadInteractionSearchDto();
		li.setUserId("27430");
		List<Map<String, Object>> agentGroups = new ArrayList<Map<String, Object>>();

		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("assignCount", 4);
		groupMap.put("userGroup", "outdialsh55");
		groupMap.put("userId", "sa1");
		
		Map<String, Object> groupMap1 = new HashMap<String, Object>();
		groupMap1.put("assignCount", 5);
		groupMap1.put("userGroup", "outdialsh62");
		groupMap1.put("userId", "sa2");
		
		Map<String, Object> groupMap2 = new HashMap<String, Object>();
		groupMap2.put("assignCount", 8);
		groupMap2.put("userGroup", "Agentsh23s");
		groupMap2.put("userId", "sa3");
		
		agentGroups.add(groupMap);
		agentGroups.add(groupMap1);
		agentGroups.add(groupMap2);
			
		callbackService.assignToAgent(li, agentGroups, true);
	}

    @Test
    public void testFindValidGroup() {
        int size = callbackService.findValidGroup("sa","Monitor", 0L, 0L, null).size();
        logger.info(size);
        Assert.assertTrue(0<size);

    }

    @Test
    public void testAssignCallBackToAgentGrp(){
        CallbackSpecification specification = new CallbackSpecification();
        specification.setEndDate(new Date());
        specification.setAllocatedNumber(0L);
        specification.setDnis("23424234");
        specification.setPriority("100");
        specification.setWorkGroup("sdfsdf");

        List grpIds = new ArrayList();
        grpIds.add("1");
        grpIds.add("2");
        grpIds.add("3");
        callbackService.assignCallBackToAgentGrp(specification, grpIds, 3);
    }

    @Test
    public void testFindCallbacks(){
        CallbackSpecification specification = new CallbackSpecification();
        specification.setEndDate(new Date());
        specification.setAllocatedNumber(0L);
        specification.setDnis("23424234");
        specification.setPriority("100");
        specification.setWorkGroup("sdfsdf");

        List<Callback> list= callbackService.findCallbacks(specification);
        Assert.assertNotNull(list);
    }
    
    @Test
    public void testFindCallbackCount() throws SQLException{
        CallbackSpecification specification = new CallbackSpecification();
        specification.setEndDate(new Date());
        specification.setAllocatedNumber(0L);
        specification.setCallType("2");
        long number = callbackService.findCallbackCount(specification);
        Assert.assertTrue(number > 0);
    }
    
    @Test
    public void testFindExecutedCallbackCount() throws SQLException{
        CallbackSpecification specification = new CallbackSpecification();
//        specification.setEndDate(new Date());
        specification.setAllocatedNumber(0L);
        specification.setCallType("2");
        long number = callbackService.findExecutedCallbackCount(specification);
        Assert.assertTrue(number > 0);
    }

    @Test
    public void testAssignCallBackToAgent() throws ServiceException {
        CallbackSpecification specification = new CallbackSpecification();
        specification.setEndDate(new Date());
        specification.setAllocatedNumber(1L);
        specification.setDnis("23424234");
        specification.setPriority("100");
        specification.setWorkGroup("sdfsdf");

        AssignAgentDto assignAgentDto1 = new AssignAgentDto();
        assignAgentDto1.setUserId("11");
        assignAgentDto1.setWorkGrp("1");
        assignAgentDto1.setAssignCount(3);
        AssignAgentDto assignAgentDto2 = new AssignAgentDto();
        assignAgentDto2.setUserId("12");
        assignAgentDto2.setWorkGrp("1");
        assignAgentDto2.setAssignCount(2);
        AssignAgentDto assignAgentDto3 = new AssignAgentDto();
        assignAgentDto3.setUserId("2");
        assignAgentDto3.setWorkGrp("2");
        assignAgentDto3.setAssignCount(1);
        List assignAgentDtos = new ArrayList();
        assignAgentDtos.add(assignAgentDto1);
        assignAgentDtos.add(assignAgentDto2);
        assignAgentDtos.add(assignAgentDto3);
        callbackService.assignCallBackToAgent(specification, assignAgentDtos);
    }
    
    @Test
    public void testCountAssignableVirtualCallback() throws ErpException {
    	CallbackDto callbackDto = new CallbackDto();
    	callbackService.countAssignableVirtualCallback(callbackDto);
    }

    @Test
    public void testAssignCallbackToGrpMember() throws ServiceException {
        LeadInteractionSearchDto li = new LeadInteractionSearchDto();
        li.setUserId("27430");
        List<String> grpMembers = new ArrayList<String>();
        grpMembers.add("27430");
        callbackService.assignCallbackToGrpMember(li, "outdialsh55", grpMembers, 1);
    }

    @Test
    public void testAssignVirtualCallBackToAgentGrp() throws Exception {
        List grpIds = new ArrayList();
        grpIds.add("1");
        grpIds.add("2");
        grpIds.add("3");
        callbackService.assignVirtualCallbackToAgentGrp(new CallbackDto(), grpIds, 3);
    }
    
    @Test
    public void testGetAgentUserByGroups() throws ServiceException {
    	List<String> groups = new ArrayList<String>();
    	generateGroups(groups);
    	
    	AgentQueryDto4Callback agentQueryDto4Callback = new AgentQueryDto4Callback();
    	agentQueryDto4Callback.setGroups(groups);
/*    	agentQueryDto4Callback.setcType("3");*/
//    	agentQueryDto4Callback.setAgentId("12650");
/*    	agentQueryDto4Callback.setHighDate("2013-08-09 10:02:20");
    	agentQueryDto4Callback.setLowDate("2013-08-08 10:02:20");*/
    	long startTime = System.currentTimeMillis();
    	List<AgentUserInfo4TeleDist> agentUsers = callbackService.queryQualifiedAgentUser(agentQueryDto4Callback);
    	long endTime = System.currentTimeMillis();
    	System.out.println("Cost:"+(endTime-startTime));
    	assertTrue(agentUsers != null);
    }
    
    private void generateGroups(List<String> groups) throws ServiceException {
    	List<GroupInfo> g1 = userService.getGroupList("23");
    	for(GroupInfo gi : g1) {
    		groups.add(gi.getId());
    	}
    	List<GroupInfo> g2 = userService.getGroupList("24");
    	for(GroupInfo gi : g2) {
    		groups.add(gi.getId());
    	}
    	List<GroupInfo> g3 = userService.getGroupList("25");
    	for(GroupInfo gi : g3) {
    		groups.add(gi.getId());
    	}
/*    	groups.add("outdialsh38");
    	groups.add("outdialsh39");
    	groups.add("outdialsh40");
    	groups.add("outdialsh41");
    	groups.add("outdialsh42");
    	groups.add("outdialsh43");
    	groups.add("outdialsh44");
    	groups.add("outdialsh45");
    	groups.add("outdialsh53");
    	groups.add("outdialsh54");
    	groups.add("outdialsh55");
    	groups.add("outdialsh63");
    	groups.add("outdialsh67");
    	groups.add("outdialsh79");
    	groups.add("outdialsh84");
    	groups.add("outdialsh85");
    	groups.add("outdialsh86");
    	groups.add("outdialsh87");
    	groups.add("outdialsh88");
    	groups.add("outdialsh89");
    	groups.add("outdialsh90");
    	groups.add("outdialsh91");
    	groups.add("outdialsh92");
    	groups.add("outdialsh93");
    	groups.add("outdialsh77");
    	groups.add("outdialsh27");
    	groups.add("outdialsh28");
    	groups.add("outdialsh29");
    	groups.add("outdialsh30");
    	groups.add("outdialsh69");
    	groups.add("outdialsh49");
    	groups.add("outdialsh51");*/
    }
    
    @Test
    public void testGetMostFrequentAgentLevel() throws Exception {
    	List<AgentUserInfo4TeleDist> userInfo4TeleDists = new ArrayList<AgentUserInfo4TeleDist>();
    	AgentUserInfo4TeleDist user = new AgentUserInfo4TeleDist();
    	user.setUserId("12233");
    	user.setLevelId("A");
    	
    	AgentUserInfo4TeleDist user2 = new AgentUserInfo4TeleDist();
    	user2.setUserId("12236");
    	user2.setLevelId("a");
    	user2.setLevelId2("B");
    	
    	AgentUserInfo4TeleDist user3 = new AgentUserInfo4TeleDist();
    	user3.setUserId("12239");
    	user3.setLevelId2("A");
    	user3.setLevelId3("A");
    	
    	AgentUserInfo4TeleDist user4 = new AgentUserInfo4TeleDist();
    	user4.setUserId("12259");
    	user4.setLevelId2("C");
    	
    	userInfo4TeleDists.add(user);
    	userInfo4TeleDists.add(user2);
    	userInfo4TeleDists.add(user3);
    	userInfo4TeleDists.add(user4);
    	
    	Field f = callbackService.getMostFrequentAgentLevel(userInfo4TeleDists);
    	assertTrue("levelId2".equals(f.getName()));
    }
    
    @Test
    public void testSortAgents() throws Exception {
    	List<AgentUserInfo4TeleDist> userInfo4TeleDists = new ArrayList<AgentUserInfo4TeleDist>();
    	AgentUserInfo4TeleDist user = new AgentUserInfo4TeleDist();
    	user.setUserId("12233");
    	user.setLevelId("A");
    	
    	AgentUserInfo4TeleDist user2 = new AgentUserInfo4TeleDist();
    	user2.setUserId("12236");
    	user2.setLevelId("a");
    	user2.setLevelId2("B");
    	
    	AgentUserInfo4TeleDist user3 = new AgentUserInfo4TeleDist();
    	user3.setUserId("12239");
    	user3.setLevelId2("A");
    	user3.setLevelId3("A");
    	
    	AgentUserInfo4TeleDist user4 = new AgentUserInfo4TeleDist();
    	user4.setUserId("12259");
    	user4.setLevelId2("C");
    	
    	AgentUserInfo4TeleDist user5 = new AgentUserInfo4TeleDist();
    	user5.setUserId("11567");
    	user5.setLevelId2("A");
    	
    	AgentUserInfo4TeleDist user6 = new AgentUserInfo4TeleDist();
    	user6.setUserId("33567");
    	user6.setLevelId2("B");
    	
    	AgentUserInfo4TeleDist user7 = new AgentUserInfo4TeleDist();
    	user7.setUserId("00951");
    	
    	userInfo4TeleDists.add(user);
    	userInfo4TeleDists.add(user2);
    	userInfo4TeleDists.add(user3);
    	userInfo4TeleDists.add(user4);
    	userInfo4TeleDists.add(user5);
    	userInfo4TeleDists.add(user6);
    	userInfo4TeleDists.add(user7);
    	
    	callbackService.sortAgents(userInfo4TeleDists);
    	assertTrue("11567".equals(((AgentUserInfo4TeleDist)userInfo4TeleDists.get(0)).getUserId()));
    	assertTrue("12239".equals(((AgentUserInfo4TeleDist)userInfo4TeleDists.get(1)).getUserId()));
    	assertTrue("12236".equals(((AgentUserInfo4TeleDist)userInfo4TeleDists.get(2)).getUserId()));
    	assertTrue("33567".equals(((AgentUserInfo4TeleDist)userInfo4TeleDists.get(3)).getUserId()));
    	assertTrue("12259".equals(((AgentUserInfo4TeleDist)userInfo4TeleDists.get(4)).getUserId()));
    	assertTrue("00951".equals(((AgentUserInfo4TeleDist)userInfo4TeleDists.get(5)).getUserId()));
    	assertTrue("12233".equals(((AgentUserInfo4TeleDist)userInfo4TeleDists.get(6)).getUserId()));
    }
}
