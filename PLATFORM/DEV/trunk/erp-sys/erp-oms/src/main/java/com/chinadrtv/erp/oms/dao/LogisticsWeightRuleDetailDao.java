package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.LogisticsWeightRuleDetail;

import java.util.List;

/**
 * 运费规则配置明细访问接口(按重量)
 * User: gaodejian
 * Date: 13-3-21
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */
public interface LogisticsWeightRuleDetailDao extends GenericDao<LogisticsWeightRuleDetail, Integer> {
    List<LogisticsWeightRuleDetail> getWeightRuleDetailsByRuleId(Integer ruleId);
    Long getWeightRuleDetailCount(Integer ruleId);
    List<LogisticsWeightRuleDetail> getWeightRuleDetailsByRuleId(Integer ruleId, int index, int size);
}
