package com.chinadrtv.erp.core.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GroupDao;
import com.chinadrtv.erp.core.service.GroupService;
import com.chinadrtv.erp.model.agent.Grp;

@Service("groupService")
public class GroupServiceImpl implements GroupService{

    @Autowired
    private GroupDao groupDao;

	/**
	 * 根据部门获取坐席组
	 */
	public List<Grp> queryList(String department) {
		return groupDao.queryList(department);
	}
}
