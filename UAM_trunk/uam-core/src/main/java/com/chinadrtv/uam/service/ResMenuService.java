package com.chinadrtv.uam.service;

import com.chinadrtv.uam.dto.ResMenuTreeDto;

import java.util.List;


public interface ResMenuService extends ServiceSupport {
	
	/**
	 * 加载UAM资源与角色的对应关系
	 * 
	 * @return
	 */
	List<ResMenuTreeDto> getMenuTreeListByUserId(Long userId, String siteName);
}
