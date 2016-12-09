package com.chinadrtv.erp.core.service.ruleoption;

import com.chinadrtv.erp.model.PromotionRuleOption;
import org.mvel2.templates.TemplateRuntime;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-7-31
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
public abstract class RuleOptionUIAbstract implements RuleOptionUI {
    protected String html="";
    protected String initScript="";
    protected String validateScript="";
    protected   Map vars = new HashMap();

    public void setPromotionRuleOption(PromotionRuleOption ruleOption){
        vars.put("ruleOption", ruleOption);
        vars.put("keyId",ruleOption.getKey() + "-" + ruleOption.getPromotionRule().getRuleId());
    }

    protected String runTemplate(PromotionRuleOption ruleOption, String template) {
        String output = (String) TemplateRuntime.eval(template, vars);
        return output;
    }

    public String getHtml() {
        return html;
    }

    public String getInitScript() {
        return initScript;
    }

    public String getValidateScript() {
        return validateScript;
    }

}
