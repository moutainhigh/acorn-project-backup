package com.chinadrtv.erp.user.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.UserLevel;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;
import com.chinadrtv.erp.user.model.AgentUser;

public interface AgentUserDao extends GenericDao<AgentUser,String> {
	String queryAgentUserType(String userId);
	Map<String, Object> queryUserLevel(String userId);
	List<AgentUserInfo4TeleDist> queryUserLevelInBatch(List<String> userIds);
	List<AgentUser> queryAgentUserByGroup(String groupId);
	void removeAllUserLevel();
	Integer saveUserLevels(List<UserLevel> list);
}
