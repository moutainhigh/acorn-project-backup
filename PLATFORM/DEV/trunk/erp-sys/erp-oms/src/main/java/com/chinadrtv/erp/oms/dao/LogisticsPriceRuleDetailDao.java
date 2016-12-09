package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.LogisticsFeeRule;
import com.chinadrtv.erp.model.LogisticsFeeRuleDetail;
import com.chinadrtv.erp.model.LogisticsPriceRuleDetail;

import java.util.List;

/**
 * 运费规则配置明细访问接口
 * User: gaodejian
 * Date: 13-3-21
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */
public interface LogisticsPriceRuleDetailDao extends GenericDao<LogisticsPriceRuleDetail, Integer> {
    List<LogisticsPriceRuleDetail> getPriceRuleDetailsByRuleId(Integer ruleId);
    Long getPriceRuleDetailCount(Integer ruleId);
    List<LogisticsPriceRuleDetail> getPriceRuleDetailsByRuleId(Integer ruleId, int index, int size);
}
