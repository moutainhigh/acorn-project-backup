package com.chinadrtv.erp.core.service.ruleoption;


import com.chinadrtv.erp.model.PromotionRuleOption;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-7-31
 * Time: 下午3:36
 * To change this template use File | Settings | File Templates.
 */
public class RuleOptionUISelecttImpl extends RuleOptionUIAbstract {
    @Override
    public void setPromotionRuleOption(PromotionRuleOption ruleOption) {
        super.setPromotionRuleOption(ruleOption);
        generateHtml(ruleOption);
        generateInitScript(ruleOption);
    }

    private void generateInitScript(PromotionRuleOption ruleOption) {

    }

    private void generateHtml(PromotionRuleOption ruleOption) {

        String template = "<th><label for=\"@{keyId}\">@{ruleOption.i18nKey}:</label>\n" +
                "</th>\n" +
                "<td>\n" +
                "<select id=\"@{keyId}\" name=\"@{keyId}\">\n" +
                "<option value=\"\">Please Select</option>\n" +
                "@{ruleOption.options}" +
                "</select>\n" +
                "</td>";
        this.html = runTemplate(ruleOption, template);
    }


}
