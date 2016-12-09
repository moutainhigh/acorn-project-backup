package com.chinadrtv.erp.core.service;

import com.chinadrtv.erp.model.PromotionRuleResult;

import java.util.List;

/**
 * User: Jack.Chen
 * Date: 12-9-23
 */
public interface PromotionRuleResultService {
    public List<PromotionRuleResult> findByOrderId(Long orderId);
}
