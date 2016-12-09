package com.chinadrtv.dal.iagent.dao;

import java.util.List;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.iagent.MailOperCustomize;

public interface MailOperCustomizeDao extends BaseDao<MailOperCustomize>{

    public List<MailOperCustomize> queryStateByType(String operatype);

}
