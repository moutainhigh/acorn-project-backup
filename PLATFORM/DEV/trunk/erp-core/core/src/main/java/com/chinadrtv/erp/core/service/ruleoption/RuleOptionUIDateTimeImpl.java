package com.chinadrtv.erp.core.service.ruleoption;


import com.chinadrtv.erp.model.PromotionRuleOption;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-7-31
 * Time: 下午3:36
 * To change this template use File | Settings | File Templates.
 */
public class RuleOptionUIDateTimeImpl extends RuleOptionUIAbstract {
    @Override
    public void setPromotionRuleOption(PromotionRuleOption ruleOption) {
        super.setPromotionRuleOption(ruleOption);
        generateHtml(ruleOption);
        generateInitScript(ruleOption);
    }

    private void generateInitScript(PromotionRuleOption ruleOption) {
        String template = "$(\"#@{keyId}\").datebox();";
        this.initScript = runTemplate(ruleOption,template);
    }

    private void generateHtml(PromotionRuleOption ruleOption) {

        String template = "<th><label for=\"@{keyId}\">@{ruleOption.i18nKey}:</label></th>\n" +
                "<td>\n" +
                "<input id=\"@{keyId}\" name=\"@{keyId}\" type=\"text\" class=\"easyui-datebox\" value=\"\" size=\"30\"/>\n" +
                "</td>\n";
        this.html = runTemplate(ruleOption, template);
    }


}
