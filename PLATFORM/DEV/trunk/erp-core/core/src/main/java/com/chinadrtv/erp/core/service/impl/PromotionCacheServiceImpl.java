package com.chinadrtv.erp.core.service.impl;

import com.chinadrtv.erp.core.controller.Util.Constants;
import com.chinadrtv.erp.core.dao.PromotionDao;
import com.chinadrtv.erp.core.service.PromotionCacheService;
import com.chinadrtv.erp.core.service.PromotionEngineService;
import com.chinadrtv.erp.exception.PromotionException;
import com.chinadrtv.erp.model.Promotion;
import com.chinadrtv.erp.model.PromotionOption;
import com.chinadrtv.erp.model.PromotionRule;
import com.chinadrtv.erp.model.PromotionRuleOption;
import com.google.code.ssm.api.InvalidateAssignCache;
import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * User: liuhaidong
 * Date: 12-9-7
 */
@Service("promotionCacheService")
public class PromotionCacheServiceImpl implements PromotionCacheService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PromotionCacheServiceImpl.class);
    @Autowired
    private PromotionDao promotionDao;

    @Autowired
    private PromotionEngineService promotionEngineService;

    @InvalidateSingleCache(namespace = Constants.CACHE_PROMOTION)
    public void invalidCacheOfAllPromotions(@ParameterValueKeyProvider(order = 0)Boolean activeOnly,
                                             @ParameterValueKeyProvider(order = 1)Boolean requiresCouponOnly,
                                             @ParameterValueKeyProvider(order = 2)Boolean includeExpired,
                                             @ParameterValueKeyProvider(order = 3)Integer clacRound)  {

    }

    @ReadThroughSingleCache(namespace = Constants.CACHE_PROMOTION, expiration =  60 * 60 * 24)
    public List<Promotion> getAllPromotions(@ParameterValueKeyProvider(order = 0)String channel,
                                             @ParameterValueKeyProvider(order = 1)Date orderDate,
                                             @ParameterValueKeyProvider(order = 2)Boolean activeOnly,
                                             @ParameterValueKeyProvider(order = 3)Boolean requiresCouponOnly,
                                             @ParameterValueKeyProvider(order = 4)Boolean includeExpired,
                                             @ParameterValueKeyProvider(order = 5)Integer clacRound) {
        List<Promotion> promotions = promotionDao.getAllPromotions(channel,orderDate,true, false, false,clacRound);
        return promotions;
    }

    @InvalidateAssignCache(namespace = Constants.CACHE_PROMOTION_GLOBAL, assignedKey = "123" )
    public void invalidCacheOfGlobalPromotions() {

    }

    @InvalidateAssignCache(namespace = Constants.CACHE_PROMOTION_GLOBAL, assignedKey = "123" )
    public List<Promotion> getGlobalPromotions() {
        List<Promotion> promotions = promotionEngineService.getGlobalPromotions();
        return  promotions;
    }


    private PromotionRuleOption getRuleOption(PromotionRule rule, String key){
        for (PromotionRuleOption ruleOption : rule.getRuleOptions()){
            if (ruleOption.getKey().equalsIgnoreCase(key)){
                return  ruleOption;
            }
        }
        return  null;
    }

    private Object convert(PromotionRuleOption ruleOption, String ruleOptionStringValue) throws PromotionException {

        if (ruleOption == null || ruleOptionStringValue == null || ruleOptionStringValue.isEmpty())
            return null;

        String type = ruleOption.getType();

        if (type.equalsIgnoreCase("String")||type.equalsIgnoreCase("SELECT")||type.equalsIgnoreCase("TEXTAREA"))
            return ruleOptionStringValue;
        else if (type.equalsIgnoreCase("date")) {
            try {
                return DateUtils.parseDate(ruleOptionStringValue, new String[]{"yyyy-MM-dd"});
            } catch (ParseException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e.getMessage());
            }
        } else if (type.equalsIgnoreCase("int")){
            return Integer.valueOf(ruleOptionStringValue);
        } else if (type.equalsIgnoreCase("float")){
            return Float.valueOf(ruleOptionStringValue);
        } else if (type.equalsIgnoreCase("boolean")){
            return ruleOptionStringValue.equalsIgnoreCase("true");
        } else
            throw new PromotionException("the rule option value type is not supported:" + type);
    }

    @InvalidateSingleCache(namespace = Constants.CACHE_PROMOTION_VALUES)
    public void invalidPromotionOptionValues(@ParameterValueKeyProvider(order = 0)Long promotionId)  {

    }

    @ReadThroughSingleCache(namespace = Constants.CACHE_PROMOTION_VALUES, expiration =  60 * 60 * 24)
    public Map getPromotionOptionValues(@ParameterValueKeyProvider(order = 0)Long promotionId) throws PromotionException {

        Map<String, Object> optionValues = new HashMap<String, Object>();
        Promotion promotion = promotionDao.get(promotionId);
        Set<PromotionOption> options = promotion.getPromotionOptions();
        PromotionRule rule = promotion.getPromotionRule();

        if (options == null)
            return null;

        PromotionRuleOption ruleOption = null;

        for (PromotionOption option : options) {
            ruleOption = getRuleOption(rule, option.getOptionKey());
            if (option.getOptionKey() != null){
                optionValues.put(option.getOptionKey(),convert(ruleOption, option.getOptionValue()));

            }
        }

        return optionValues;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
