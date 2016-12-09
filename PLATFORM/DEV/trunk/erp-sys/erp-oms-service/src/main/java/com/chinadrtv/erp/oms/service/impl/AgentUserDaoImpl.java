package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.core.dao.query.Criteria;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.model.agent.UserLevel;
import com.chinadrtv.erp.user.dao.AgentUserDao;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;
import com.chinadrtv.erp.user.model.AgentUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository("userDao")
public class AgentUserDaoImpl implements AgentUserDao, UserDetailsService {
    public String queryAgentUserType(String userId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map<String, Object> queryUserLevel(String userId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AgentUserInfo4TeleDist> queryUserLevelInBatch(List<String> strings) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> queryAgentUserByGroup(String groupId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removeAllUserLevel() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Integer saveUserLevels(List<UserLevel> list) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Integer batchMergeUserLevel(List<UserLevel> list) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> getAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> getAllDistinct() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public AgentUser get(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean exists(String id) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public AgentUser save(AgentUser object) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void remove(String id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public AgentUser merge(AgentUser entity) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void saveOrUpdate(AgentUser object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int findPageCount(String hql, Map<String, Parameter> parameters) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int findPageCount(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> findPageList(String hql, Map<String, Parameter> parameters) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> findPageList(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> findList(String hql, Map<String, Parameter> parameters) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> findList(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public AgentUser find(String hql, Map<String, Parameter> parameters) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public AgentUser find(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int execUpdate(String hql, Map<String, Parameter> parameters) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int execUpdate(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public AgentUser find(String hql, Parameter... parameters) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> findList(String hql, Parameter... parametersArray) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int execUpdate(String hql, Parameter... parametersArray) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
