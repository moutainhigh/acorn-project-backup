package com.chinadrtv.erp.core.service.impl;

import com.chinadrtv.erp.core.dao.PromotionResultDao;
import com.chinadrtv.erp.core.service.PromotionRuleResultService;
import com.chinadrtv.erp.model.PromotionRuleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Jack.Chen
 * Date: 12-9-23
 */
@Service("promotionRuleResultService")
public class PromotionRuleResultServiceImpl implements PromotionRuleResultService {
    @Autowired
    private PromotionResultDao promotionRuleResultDao;

    public List<PromotionRuleResult> findByOrderId(Long orderId) {
        return promotionRuleResultDao.findByOrderId(orderId);
    }
}
