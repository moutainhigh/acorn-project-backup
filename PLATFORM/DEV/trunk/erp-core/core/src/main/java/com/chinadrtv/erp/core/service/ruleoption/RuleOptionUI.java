package com.chinadrtv.erp.core.service.ruleoption;


import com.chinadrtv.erp.model.PromotionRuleOption;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-7-31
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
public interface RuleOptionUI {
    void setPromotionRuleOption(PromotionRuleOption ruleOption);
    String getHtml();
    String getInitScript();
    String getValidateScript();
}
