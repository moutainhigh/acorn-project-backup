package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.model.LogisticsFeeRule;

import java.util.List;
import java.util.Map;

/**
 * 承运商运费配置服务
 * User: gaodejian
 * Date: 13-3-22
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
public interface LogisticsFeeRuleService {
    LogisticsFeeRule getLogisticsFeeRuleById(Integer ruleId);
    List<LogisticsFeeRule> getActiveLogisticsFeeRules(Integer contractId);
    List<LogisticsFeeRule> getAllLogisticsFeeRules(Map<String, Object> params, int index, int size);
    Long getLogisticsFeeRuleCount(Map<String, Object> params);
    void saveLogisticsFeeRule(LogisticsFeeRule rule);
    void addLogisticsFeeRule(LogisticsFeeRule rule);
    void removeLogisticsFeeRule(Integer ruleId);
}
