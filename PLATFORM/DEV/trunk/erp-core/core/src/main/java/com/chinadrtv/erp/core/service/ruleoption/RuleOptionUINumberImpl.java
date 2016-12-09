package com.chinadrtv.erp.core.service.ruleoption;


import com.chinadrtv.erp.model.PromotionRuleOption;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-7-31
 * Time: 下午3:36
 * To change this template use File | Settings | File Templates.
 */
public class RuleOptionUINumberImpl extends RuleOptionUIAbstract {
    @Override
    public void setPromotionRuleOption(PromotionRuleOption ruleOption) {
        super.setPromotionRuleOption(ruleOption);
        generateHtml(ruleOption);
        generateInitScript(ruleOption);
        generateValidateScript(ruleOption);
    }

    private void generateInitScript(PromotionRuleOption ruleOption) {
    }

    private void generateHtml(PromotionRuleOption ruleOption) {
        int size = ruleOption.getTypeSize() == null ? 30 : ruleOption.getTypeSize();
        String template = "<th><label for=\"" + "@{keyId}"+  "\">@{ruleOption.i18nKey}:</label>\n" +
                "</th>\n" +
                "<td>\n" +
                "<input id=\"" + "@{keyId}"+  "\" name=\"" +  "@{keyId}"+ "\" type=\"text\" value=\"\" size=\""+size+"\"/>\n" +
                "</td>\n";
        this.html = runTemplate(ruleOption, template);
    }

    private void generateValidateScript(PromotionRuleOption ruleOption) {
        StringBuilder stringBuilder = new StringBuilder();
        if (ruleOption.getRequired())  {
            stringBuilder.append("frmRuleOptionValidator.addValidation(\"@{keyId}\",\"req\"," +
                    "\"必填字段\"+\"@{ruleOption.i18nKey}\");\n" );
        }
        if (ruleOption.getMaxValue() != null) {
            int maxLength = ruleOption.getMaxValue().intValue();
            stringBuilder.append( "frmRuleOptionValidator.addValidation(\"" +
               "@{keyId}\",\"lt=@{maxLength}\",\"@{ruleOption.i18nKey}\" + \"最大值\" + \" @{maxLength}\");\n");
            vars.put("maxLength",maxLength);
        }
        if (ruleOption.getMinValue() != null) {
            int minLength = ruleOption.getMinValue().intValue();
            stringBuilder.append( "frmRuleOptionValidator.addValidation(\"" +
                    "@{keyId}\",\"gt=@{minLength}\",\"@{ruleOption.i18nKey}\" + \"最小值\" + \" @{minLength}\");\n");
            vars.put("minLength",minLength);
        }
        String template = stringBuilder.toString();
        this.validateScript = runTemplate(ruleOption, template); ;
    }

}
