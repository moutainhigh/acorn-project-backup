/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.user.service;


import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.model.agent.UserLevel;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;

/**
 * 2013-8-1 上午11:03:49
 * @version 1.0.0
 * @author yangfei
 *
 */
public interface AgentUserService {
	Map<String, AgentUserInfo4TeleDist> queryUserLevelInBatch(List<String> userIds);
	AgentUserInfo4TeleDist queryUserLevel(String userId);
	
	Integer removeAndSaveUserLevels(List<UserLevel> list);
}
