package com.chinadrtv.erp.core.dao;

import com.chinadrtv.erp.model.PromotionOption;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-7-26
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
public interface PromotionOptionDao  extends GenericDao<PromotionOption, Long>{
    public void updatePromotionOption(PromotionOption promotionOption);

    public void deletePromotionOption(PromotionOption promotionOption);

    public void deletePromotionOption(Long key);

    void deleteByPromotion(Long key);
}
