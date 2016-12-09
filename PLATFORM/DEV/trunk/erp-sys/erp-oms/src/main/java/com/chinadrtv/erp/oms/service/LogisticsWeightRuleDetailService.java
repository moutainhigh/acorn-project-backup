package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.model.LogisticsWeightRuleDetail;

import java.util.List;

/**
 * 承运商运费明细配置服务
 * User: gaodejian
 * Date: 13-3-22
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
public interface LogisticsWeightRuleDetailService {
    LogisticsWeightRuleDetail getWeightRuleDetailById(Integer detailId);
    List<LogisticsWeightRuleDetail> getWeightRuleDetailsByRuleId(Integer ruleId);
    Long getWeightRuleDetailCount(Integer ruleId);
    List<LogisticsWeightRuleDetail> getWeightRuleDetailsByRuleId(Integer ruleId, int index, int size);

    void save(LogisticsWeightRuleDetail detail);
    void add(LogisticsWeightRuleDetail detail);
    void remove(Integer detailId);
}
