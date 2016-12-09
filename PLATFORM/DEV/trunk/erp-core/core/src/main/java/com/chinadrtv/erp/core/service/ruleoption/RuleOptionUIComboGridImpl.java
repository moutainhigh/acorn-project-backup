package com.chinadrtv.erp.core.service.ruleoption;


import com.chinadrtv.erp.model.PromotionRuleOption;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-7-31
 * Time: 下午3:36
 * To change this template use File | Settings | File Templates.
 */
public class RuleOptionUIComboGridImpl extends RuleOptionUIAbstract {
    @Override
    public void setPromotionRuleOption(PromotionRuleOption ruleOption) {
        super.setPromotionRuleOption(ruleOption);
        generateHtml(ruleOption);
        generateInitScript(ruleOption);
    }

    private void generateInitScript(PromotionRuleOption ruleOption) {
//    	 String template = "$(\"#@{keyId}\").multiselect({ selectedList: 4});";
    	String template = "$('#@{keyId}').combogrid({"+  
            "panelWidth:350,"+  
            //"value:'006',"+  
            "idField:'ruid',"+  
            "method:'GET',"+
            "textField:'catcode',"+  
            "url:'/admin/getGiftJSON?giftids='+gids,"+ 
            "mode:'remote',"+  
            "singleSelect:'false',"+
    		"multiple: true,"+
            "columns:[["+  
            "{field:'ck',checkbox:true},"+  
            "{field:'ruid',title:'ID',width:60},"+  
                "{field:'pluname',title:'商品名称',width:60},"+  
                "{field:'plucode',title:'商品编码',width:50,editor:'numberbox'}"+  
            "]],"+   
			"fitColumns: true,"+
			"onSelect:function(index,value){"+
			 "initValue(\"@{keyId}\",value);"+
            "  }"+
        "});";  
    	
    	
         this.initScript = runTemplate(ruleOption,template);
         System.out.println("initString:"+ this.initScript);

    }

    private void generateHtml(PromotionRuleOption ruleOption) {

        String template = "<th><label for=\"@{keyId}\">@{ruleOption.i18nKey}:</label>\n" +
                "</th>\n" +
                "<td>\n" +
//                "<select name=\"@{keyId}\" id=\"@{keyId}\" multiple=\"multiple\" style=\"width:200px\">\n"+
//               // "<select id=\"@{keyId}\" name=\"@{keyId}\">\n" +
//                "<option value=\"\">Please Select</option>\n" +
//                "@{ruleOption.options}" +
//                "</select>\n" +
                "<input id=\"@{keyId}\" name=\"@{keyId}\" size=\"@{ruleOption.typeSize}\" class=\"easyui-combogrid\"  /> " +
                "</td>";
        this.html = runTemplate(ruleOption, template);
    }


}
