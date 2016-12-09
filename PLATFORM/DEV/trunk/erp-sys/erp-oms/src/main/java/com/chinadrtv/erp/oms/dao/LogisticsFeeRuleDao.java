package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.LogisticsFeeRule;
import com.chinadrtv.erp.model.LogisticsFeeRule;

import java.util.List;
import java.util.Map;

/**
 * 运费规则配置访问接口
 * User: gaodejian
 * Date: 13-3-21
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */
public interface LogisticsFeeRuleDao extends GenericDao<LogisticsFeeRule, Integer> {
    List<LogisticsFeeRule> getActiveLogisticsFeeRules(Integer contractId);
    List<LogisticsFeeRule> getAllLogisticsFeeRules(Map<String, Object> params, int index, int size);
    Long getLogisticsFeeRuleCount(Map<String, Object> params);
}
