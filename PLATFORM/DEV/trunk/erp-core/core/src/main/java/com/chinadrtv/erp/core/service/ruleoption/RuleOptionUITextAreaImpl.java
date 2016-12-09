package com.chinadrtv.erp.core.service.ruleoption;


import com.chinadrtv.erp.model.PromotionRuleOption;

/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 12-7-31
 * Time: 下午3:36
 * To change this template use File | Settings | File Templates.
 */
public class RuleOptionUITextAreaImpl extends RuleOptionUIAbstract {
    @Override
    public void setPromotionRuleOption(PromotionRuleOption ruleOption) {
        super.setPromotionRuleOption(ruleOption);
        generateHtml(ruleOption);
        generateInitScript(ruleOption);
        generateValidateScript(ruleOption);
    }

    private void generateInitScript(PromotionRuleOption ruleOption) {
//    	this.initScript ="function DoCustomValidation(qtyId){"+
//    		  "var qty = $(\"#\"+qtyId).val();"+
//    		  "var suk = $(\"#sku\"+qtyId.substring(3, qtyId.length)).val();"+
//    		  "if( qty.split(\",\").length == suk.split(\",\").length)"+
//    		  "{"+
//    		  "  sfm_show_error_msg('字段值不正确!',qty);"+
//    		  " return false;"+
//    		  "}"+
//    		  "else"+
//    		  "{"+
//    		  " return true;"+
//    		  "}"+
//    		"}";
    	
    }

    private void generateHtml(PromotionRuleOption ruleOption) {
        int size = ruleOption.getTypeSize() == null ? 30 : ruleOption.getTypeSize();
        String template = "<th><label for=\"@{keyId}\">@{ruleOption.i18nKey}:</label>\n" +
                "</th>\n" +
                "<td>\n" +
                "<textarea style=\"float:top\" id=\"@{keyId}\" name=\"@{keyId}\"  cols=\""+size+"\" rows=\"3\" value=\"\" />\n" +
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
        if(ruleOption.getKey().substring(0, 3).equals("qty")){
        	//System.out.println("qty1:"+ruleOption.getKey().substring(0,3));
        	if(ruleOption.getKey().substring(3,ruleOption.getKey().length()).equals("1")){
//        		stringBuilder.append(" function DoCustomValidation(){"+
//                "alert(12);"+
////				 " var qty = $(\"#\"+qtyId).val();"+
////				 " var suk = $(\"#sku\"+qtyId.substring(3, qtyId.length)).val();"+
////				 " if( qty.split(\",\").length == suk.split(\",\").length)"+
////				 " {"+
////				 "  sfm_show_error_msg('字段值不正确!',qty);"+
////				 "   return false;"+
////				 " }"+
////				 " else"+
////				 " {"+
////				 "   return true;"+
////				 " }"+
//                 "}\n");
        		stringBuilder.append("frmRuleOptionValidator.setAddnlValidationFunction(DoCustomValidation);\n");
        	}
        	//System.out.println("qty3:"+ruleOption.getKey());
        	//stringBuilder.append("frmRuleOptionValidator.setAddnlValidationFunction(DoCustomValidation('@{keyId}'));\n");
        }
      
        
        
        String template = stringBuilder.toString();
       // System.out.println(template);
        this.validateScript = runTemplate(ruleOption, template);
        
        //System.out.println(validateScript);
    }

}
