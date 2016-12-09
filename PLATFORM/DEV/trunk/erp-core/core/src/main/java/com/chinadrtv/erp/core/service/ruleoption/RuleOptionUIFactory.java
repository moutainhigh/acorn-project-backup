package com.chinadrtv.erp.core.service.ruleoption;

import com.chinadrtv.erp.constant.PromotionConstants;
import com.chinadrtv.erp.core.service.impl.PromotionEngineServiceImpl;
import com.chinadrtv.erp.exception.PromotionException;
import com.chinadrtv.erp.model.PromotionRule;
import com.chinadrtv.erp.model.PromotionRuleOption;
import org.mvel2.templates.TemplateRuntime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-8-1
 * Time: 上午11:40
 * To change this template use File | Settings | File Templates.
 */
public class RuleOptionUIFactory {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RuleOptionUIFactory.class);
    private static String html="";
    private static String initScript="";
    private static String validateScript="";
    private static Map vars = new HashMap();

    private static String formHead = " <form id=\"frmRuleOption\" action=\"\" method=\"post\" enctype=\"multipart/form-data\">\n" +
            "<input id=\"ruleId\" name=\"ruleId\" type=\"hidden\" value=\"@{promotionRule.ruleId}\"/>\n" +
            "<table cellspacing=\"0\" class=\"yui-skin-sam nobpromotion_table\">\n" ;
    private static String formFoot = "</table>\n" +
            "</form>";
    private static String validHead = //"<script language=\"JavaScript\" type=\"text/javascript\"\n" +
           // "            xml:space=\"preserve\">//<![CDATA[\n" +
            "            var frmRuleOptionValidator  = new Validator(\"frmRuleOption\");";
    private static String validFoot = "";//]]></script>";
    private static void init(){
        html="";
        initScript="";
        validateScript="";
        vars.clear();
    }
    private static RuleOptionUI getRuleOptionUI(PromotionRuleOption ruleOption){
        RuleOptionUI ruleOptionUI = null;
        String type = ruleOption.getType();
        if (type == null||type.isEmpty()){
            throw new IllegalArgumentException("PromotionRuleOption has Invalid empty type");
        }else if (type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_STRING)){
            ruleOptionUI = new RuleOptionUIStringImpl();
        }  else if (type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_DATE)) {
            ruleOptionUI = new RuleOptionUIDateTimeImpl();
        }else if (type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_INT)) {
            ruleOptionUI = new RuleOptionUINumberImpl();
        }else if (type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_FLOAT)){
            ruleOptionUI = new RuleOptionUINumberImpl();
        }else if (type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_BOOLEAN)){
            ruleOptionUI = new RuleOptionUIBooleanImpl();
        }else if (type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_SELECT)){
            ruleOptionUI = new RuleOptionUISelecttImpl();
        }else if (type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_COMBOGRID)){
            ruleOptionUI = new RuleOptionUIComboGridImpl();
        }else if (type.equalsIgnoreCase(PromotionConstants.PROMOTION_RULE_OPTION_TYPE_TEXTAREA)){
            ruleOptionUI = new RuleOptionUITextAreaImpl();
        } else {
            throw new IllegalArgumentException("PromotionRuleOption has unsupported type: "+type);
        }
        ruleOptionUI.setPromotionRuleOption(ruleOption);
        return  ruleOptionUI;
    }
    public static void buildUI(PromotionRule promotionRule ,boolean showth ){
        init();
        if(promotionRule == null || promotionRule.getRuleOptions().size()==0){
            return;
        }
        int columns = 3;
        if(promotionRule.getRuleName().equals("搭销")) columns = 7;
        if(promotionRule.getRuleName().equals("赠品")) columns = 6;
        int i = 0;
        StringBuffer sForm = new StringBuffer();
        StringBuffer sInitScript = new StringBuffer();
        StringBuffer sValidateScript = new StringBuffer();
        int optionSize = promotionRule.getRuleOptions().size();
        for(PromotionRuleOption promotionRuleOption:promotionRule.getRuleOptions()) {
            if((i % columns)==0) {
                sForm.append("<tr>\n");
            }
            RuleOptionUI ruleOptionUI = getRuleOptionUI(promotionRuleOption);
            if(showth){
                //sForm.append(ruleOptionUI.getHtml().substring(ruleOptionUI.getHtml().indexOf("</th>")));
                //sForm.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            	sForm.append(ruleOptionUI.getHtml());
            }else{
                sForm.append(ruleOptionUI.getHtml());
            }
            sInitScript.append(ruleOptionUI.getInitScript());
            sValidateScript.append(ruleOptionUI.getValidateScript());
            if (((i + 1) % columns==0)||((i + 1) == optionSize)) {
                sForm.append("</tr>\n");
            }
            i++;
        }
        initScript = sInitScript.toString();
        validateScript = validHead + sValidateScript.toString() + validFoot;
        sForm.insert(0,formHead) ;
        sForm.append(formFoot);
        
        html = sForm.toString();
   
        vars.put("promotionRule",promotionRule);
        html = (String) TemplateRuntime.eval(html, vars);

    }
    
    public static void buildUIFile(PromotionRule promotionRule){
        init();
        if(promotionRule == null || promotionRule.getRuleOptions().size()==0){
            return;
        }
        int i = 0;
        StringBuffer sForm = new StringBuffer();
        StringBuffer sInitScript = new StringBuffer();
        StringBuffer sValidateScript = new StringBuffer();       
        initScript = sInitScript.toString();
        validateScript = validHead + sValidateScript.toString() + validFoot;
        formHead = " <form id=\"frmRuleOption\" action=\"\" method=\"post\" enctype=\"multipart/form-data\">\n" +
            "<input id=\"ruleId\" name=\"ruleId\" type=\"hidden\" value=\"@{promotionRule.ruleId}\"/>" ; 

        try {
			sForm = new StringBuffer( readTextFromFile("D:\\mvel\\"+promotionRule.getRuleUIFileName(), "utf-8"));
		} catch (PromotionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        sForm.insert(0,formHead) ;
        formFoot = "</form>";
        sForm.append(formFoot);
        html = sForm.toString();
        vars.put("promotionRule",promotionRule);
        html = (String) TemplateRuntime.eval(html, vars);

    }

    public static String getHtml() {
        return html;
    }

    public static String getInitScript() {
        return initScript;
    }

    public static String getValidateScript() {
        return validateScript;
    }
    
    public static String getHtmlFromFile(){
    	
    	return html;
    	
    }
    
	private static String readTextFromFile(String fileName, String encoding) throws PromotionException {

		File file = null;

		try {
			file = new File(fileName);
            FileInputStream stream;
            if (file.exists()){
                stream = new FileInputStream(file);
            } else{
                stream = new FileInputStream(PromotionEngineServiceImpl.class.getResource("/").getPath()+fileName);
            }

			try {
				FileChannel fc = stream.getChannel();
				MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
				/* Instead of using default, pass in a decoder. */
				return Charset.forName(encoding).decode(bb).toString();
			} finally {
				stream.close();
			}
		} catch (FileNotFoundException e) {
            log.error(e.getMessage());
			throw new PromotionException("the file does not exist:" + file);
		} catch (IOException e) {
            log.error(e.getMessage());
			throw new PromotionException("reading file failure:" + file);
		}
	}
    
    
}
