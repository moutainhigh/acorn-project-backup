package com.chinadrtv.erp.core.dao;


import com.chinadrtv.erp.model.Promotion;
import com.chinadrtv.erp.model.PromotionRuleResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-8-2
 * Time: 下午5:46
 * To change this template use File | Settings | File Templates.
 */
public interface PromotionResultDao  extends GenericDao<PromotionRuleResult, Long> {
    List<PromotionRuleResult> findByPromotion(Promotion promotion);

    List<PromotionRuleResult> findByOrderId(Long orderId);

}
