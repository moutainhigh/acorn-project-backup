package com.chinadrtv.uam.dao;

import com.chinadrtv.uam.dto.ResMenuTreeDto;

import java.util.List;

public interface ResMenuDao {

	List<ResMenuTreeDto> getRoleListByUserId(Long userId, String siteName, Long parentId);
}
