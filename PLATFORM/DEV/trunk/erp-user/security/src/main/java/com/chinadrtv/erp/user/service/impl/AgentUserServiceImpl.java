/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.model.agent.UserLevel;
import com.chinadrtv.erp.user.dao.AgentUserDao;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;
import com.chinadrtv.erp.user.service.AgentUserService;

/**
 * 2013-8-1 下午1:02:49
 * @version 1.0.0
 * @author yangfei
 *
 */
@Service("agentUserService")
public class AgentUserServiceImpl implements AgentUserService{
	@Autowired
	private AgentUserDao userDao;
	
	@Override
	public Map<String, AgentUserInfo4TeleDist> queryUserLevelInBatch(List<String> userIds) {
		List<AgentUserInfo4TeleDist> agentUsers= userDao.queryUserLevelInBatch(userIds);
		Map<String, AgentUserInfo4TeleDist> agentUsersMap = new HashMap<String, AgentUserInfo4TeleDist>();
		for(AgentUserInfo4TeleDist agentUser : agentUsers) {
			agentUsersMap.put(agentUser.getUserId(), agentUser);
		}
		return agentUsersMap;
	}
	
	@Override
	public AgentUserInfo4TeleDist queryUserLevel(String userId) {
		Map<String, Object> objs = userDao.queryUserLevel(userId);
		AgentUserInfo4TeleDist agentUserInfo4TeleDist = null;
		if(objs != null) {
			agentUserInfo4TeleDist = convertDBResult2Vo(objs);
		}
		return agentUserInfo4TeleDist;
	}
	
	private AgentUserInfo4TeleDist convertDBResult2Vo(Map<String, Object> records) {
//		List<AgentUserInfo4TeleDist> vos = new ArrayList<AgentUserInfo4TeleDist>();
		AgentUserInfo4TeleDist aui = new AgentUserInfo4TeleDist();

		String userId = (String) records.get("USERID");
		if (StringUtils.isNotBlank(userId)) {
			aui.setUserId(userId);
		}
		String userName = (String) records.get("USERNAME");
		if (StringUtils.isNotBlank(userName)) {
			aui.setUserName(userName);
		}
		String levelId = (String) records.get("LEVELID");
		if (StringUtils.isNotBlank(levelId)) {
			aui.setLevelId(levelId);
		}
		String levelId2 = (String) records.get("LEVELID2");
		if (StringUtils.isNotBlank(levelId2)) {
			aui.setLevelId2(levelId2);
		}
		String levelId3 = (String) records.get("LEVELID3");
		if (StringUtils.isNotBlank(levelId3)) {
			aui.setLevelId3(levelId3);
		}
		return aui;
	}

	@Override
	public Integer removeAndSaveUserLevels(List<UserLevel> list) {
		userDao.removeAllUserLevel();
		return userDao.saveUserLevels(removeDuplicate(list));
	}
	
	private List<UserLevel> removeDuplicate(List<UserLevel> list) {
		if (list.size() == 0) 
			return list;
		
		Map<String, UserLevel> map = new HashMap<String, UserLevel>();
		for (UserLevel ul : list) {
			map.put(ul.getUserId(), ul);
		}
		return new ArrayList<UserLevel>(map.values());
	}
}
