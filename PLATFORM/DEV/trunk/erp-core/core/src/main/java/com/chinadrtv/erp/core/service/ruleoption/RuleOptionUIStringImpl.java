package com.chinadrtv.erp.core.service.ruleoption;


import com.chinadrtv.erp.model.PromotionRuleOption;

/**
 *  
 * @author haoleitao
 * @date 2013-4-7 下午5:31:57
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class RuleOptionUIStringImpl extends RuleOptionUIAbstract {
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
        String template = "<th><label for=\"@{keyId}\">@{ruleOption.i18nKey}:</label>\n" +
                "</th>\n" +
                "<td>\n" +
                "<input id=\"@{keyId}\" name=\"@{keyId}\" type=\"text\" value=\"\" size=\""+size+"\"/>\n" +
                "</td>\n";
        this.html = runTemplate(ruleOption, template);
    }

    private void generateValidateScript(PromotionRuleOption ruleOption) {
        StringBuilder stringBuilder = new StringBuilder();
        if (ruleOption.getRequired())  {
            stringBuilder.append("frmRuleOptionValidator.addValidation(\"" +
               "@{keyId}\",\"req\"," +
               "\"必填字段\"+\"@{ruleOption.name}\"" +
               ");\n" );
        }
        if (ruleOption.getMaxValue() != null) {
            int maxLength = ruleOption.getMaxValue().intValue();
            stringBuilder.append( "frmRuleOptionValidator.addValidation(\"" +
               "@{keyId}\",\"maxlen=@{maxLength}\",\"@{ruleOption.name}\" +\"最大长度\" + \" @{maxLength}\");\n");
            vars.put("maxLength",maxLength);
        }
        if (ruleOption.getMinValue() != null) {
            int minLength = ruleOption.getMinValue().intValue();
            stringBuilder.append( "frmRuleOptionValidator.addValidation(\"" +
                    "@{keyId}\",\"minlen=@{minLength}\",\"@{ruleOption.name}\" + \"最小长度\" + \" @{minLength}\");\n");
            vars.put("minLength",minLength);
        }
        String template = stringBuilder.toString();
        this.validateScript = runTemplate(ruleOption, template); ;
    }

}
