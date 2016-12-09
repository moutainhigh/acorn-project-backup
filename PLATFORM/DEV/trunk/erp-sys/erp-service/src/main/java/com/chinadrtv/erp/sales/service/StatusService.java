package com.chinadrtv.erp.sales.service;

import java.util.List;

import com.chinadrtv.erp.sales.dto.AgentDto;

public interface StatusService {

	/**
	 * 得到坐席
	 * 
	 * @return
	 */
	List<AgentDto> getAgents();
	
	/**
	 * 剔除坐席
	 * 
	 * @return
	 */
	Boolean cullAgent(String uid);
	
	/**
	 * 得到用户成单数量
	 * 
	 * @param uid
	 * @return
	 */
	Integer getOrderCount(String uid);
	
	/**
	 * 得到用户通话数量
	 * 
	 * @param uid
	 * @return
	 */
	Integer getCallCount(String uid);


}
