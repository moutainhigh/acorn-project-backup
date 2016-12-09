package com.chinadrtv.erp.core.dao;

import com.chinadrtv.erp.model.Promotion;

import java.util.Date;
import java.util.List;

public interface PromotionDao extends GenericDao<Promotion, Long> {
	public Promotion findById(Long promotionId);
	public void deletePromotion(Long promotionId);
	public Promotion savePromotion(Promotion promotion);
	/**
	 * get promotions. The promotions should be sorted by sortOrder attribute
	 * @param activeOnly if return active promotions
	 * @param requiresCouponOnly if the result promotions require coupon(s)
	 * @param includeExpired
	 * 
	 * @return list of promotions
	 */
	public List<Promotion> getAllPromotions(String channel,Date orderDate,Boolean activeOnly, Boolean requiresCouponOnly, Boolean includeExpired, Integer clacRound);
	
	public void setActive(Long promotionId, Boolean active);
	
	/**
	 * set active for the promotions with given rule Id
	 * @param ruleId
	 * @param active
	 */
	public void setActiveForRule(Long ruleId, Boolean active);
    public int getPromotionsCount();
    public List<Promotion> getPaginatedPromotionsList(int start_index, Integer num_per_page);

    void deletePromotionByIds(String promotionIDs);
    public Integer getMaxExecOrder();
    public void updateExecOrder(Integer execOrder);
    public Promotion getLatePromotion(Integer execOrder);
    public Promotion getEarlyPromotion(Integer execOrder);
    int getPromotionsCountByNameActive(int start_index, Integer num_per_page, String promotionName, int active);
    List<Promotion> getPaginatedPromotionsListByNameActive(int start_index, Integer num_per_page, String promotionName, int active);
    void clearExecOrder();
    List<Promotion> getPaginatedPromotionsListByNameActive(String promotionName, int active);

    int usePromotion(Promotion promotion);

    public List<Promotion> getGlobalPromotions();
	public int updatePromotinActive(Long promotionId, Boolean acvite);
}
