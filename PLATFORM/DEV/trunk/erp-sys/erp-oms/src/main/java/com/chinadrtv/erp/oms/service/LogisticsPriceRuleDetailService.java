package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.model.LogisticsFeeRule;
import com.chinadrtv.erp.model.LogisticsPriceRuleDetail;

import java.util.List;

/**
 * 承运商运费明细配置服务
 * User: gaodejian
 * Date: 13-3-22
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
public interface LogisticsPriceRuleDetailService {
    LogisticsPriceRuleDetail getPriceRuleDetailById(Integer detailId);
    List<LogisticsPriceRuleDetail> getPriceRuleDetailsByRuleId(Integer ruleId);
    Long getPriceRuleDetailCount(Integer ruleId);
    List<LogisticsPriceRuleDetail> getPriceRuleDetailsByRuleId(Integer ruleId, int index, int size);

    void save(LogisticsPriceRuleDetail detail);
    void add(LogisticsPriceRuleDetail detail);
    void remove(Integer detailId);
}
