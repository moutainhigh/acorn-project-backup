package com.chinadrtv.erp.core.service;


import com.chinadrtv.erp.exception.PromotionException;
import com.chinadrtv.erp.model.Promotion;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: liuhaidong
 * Date: 12-9-7
 */
public interface PromotionCacheService {

    void invalidCacheOfAllPromotions(Boolean activeOnly, Boolean requiresCouponOnly, Boolean includeExpired, Integer clacRound);

    List<Promotion> getAllPromotions(String channel,Date orderDate,Boolean activeOnly, Boolean requiresCouponOnly, Boolean includeExpired, Integer clacRound);

    public void invalidCacheOfGlobalPromotions();

    public List<Promotion> getGlobalPromotions() ;

    public void invalidPromotionOptionValues(Long promotionId);

    public Map getPromotionOptionValues(Long promotionId) throws PromotionException;
}
