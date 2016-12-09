package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.OrderAssignRule;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 12-12-25
 * To change this template use File | Settings | File Templates.
 */
public interface OrderAssignRuleDao extends GenericDao<OrderAssignRule, Long> {
    List<OrderAssignRule> findFromPartial(OrderAssignRule partialOrderAssignRule);

    List<OrderAssignRule> getAllValidRules();
}
