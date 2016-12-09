package com.chinadrtv.model.oms;

import java.math.BigDecimal;
import java.util.Date;

public class PromotionRule {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PROMOTIONRULE.RULE_ID
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    private BigDecimal ruleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PROMOTIONRULE.CREATED_BY
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    private String createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PROMOTIONRULE.DATE_ADDED
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    private Date dateAdded;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PROMOTIONRULE.RULE_INSTALLED
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    private Short ruleInstalled;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PROMOTIONRULE.LAST_MODIFIED
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    private Date lastModified;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PROMOTIONRULE.RULE_CONTEXT
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    private String ruleContext;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PROMOTIONRULE.RULE_FILE_NAME
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    private String ruleFileName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PROMOTIONRULE.RULE_NAME
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    private String ruleName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PROMOTIONRULE.RULE_UI_FILE_NAME
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    private String ruleUiFileName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PROMOTIONRULE.UPDATE_BY
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    private String updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PROMOTIONRULE.RULE_TEXT
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    private String ruleText;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PROMOTIONRULE.RULE_BTEXT
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    private String ruleBtext;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_ID
     *
     * @return the value of ACOAPP_OMS.PROMOTIONRULE.RULE_ID
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public BigDecimal getRuleId() {
        return ruleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_ID
     *
     * @param ruleId the value for ACOAPP_OMS.PROMOTIONRULE.RULE_ID
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public void setRuleId(BigDecimal ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PROMOTIONRULE.CREATED_BY
     *
     * @return the value of ACOAPP_OMS.PROMOTIONRULE.CREATED_BY
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PROMOTIONRULE.CREATED_BY
     *
     * @param createdBy the value for ACOAPP_OMS.PROMOTIONRULE.CREATED_BY
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PROMOTIONRULE.DATE_ADDED
     *
     * @return the value of ACOAPP_OMS.PROMOTIONRULE.DATE_ADDED
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PROMOTIONRULE.DATE_ADDED
     *
     * @param dateAdded the value for ACOAPP_OMS.PROMOTIONRULE.DATE_ADDED
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_INSTALLED
     *
     * @return the value of ACOAPP_OMS.PROMOTIONRULE.RULE_INSTALLED
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public Short getRuleInstalled() {
        return ruleInstalled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_INSTALLED
     *
     * @param ruleInstalled the value for ACOAPP_OMS.PROMOTIONRULE.RULE_INSTALLED
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public void setRuleInstalled(Short ruleInstalled) {
        this.ruleInstalled = ruleInstalled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PROMOTIONRULE.LAST_MODIFIED
     *
     * @return the value of ACOAPP_OMS.PROMOTIONRULE.LAST_MODIFIED
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PROMOTIONRULE.LAST_MODIFIED
     *
     * @param lastModified the value for ACOAPP_OMS.PROMOTIONRULE.LAST_MODIFIED
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_CONTEXT
     *
     * @return the value of ACOAPP_OMS.PROMOTIONRULE.RULE_CONTEXT
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public String getRuleContext() {
        return ruleContext;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_CONTEXT
     *
     * @param ruleContext the value for ACOAPP_OMS.PROMOTIONRULE.RULE_CONTEXT
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public void setRuleContext(String ruleContext) {
        this.ruleContext = ruleContext == null ? null : ruleContext.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_FILE_NAME
     *
     * @return the value of ACOAPP_OMS.PROMOTIONRULE.RULE_FILE_NAME
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public String getRuleFileName() {
        return ruleFileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_FILE_NAME
     *
     * @param ruleFileName the value for ACOAPP_OMS.PROMOTIONRULE.RULE_FILE_NAME
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public void setRuleFileName(String ruleFileName) {
        this.ruleFileName = ruleFileName == null ? null : ruleFileName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_NAME
     *
     * @return the value of ACOAPP_OMS.PROMOTIONRULE.RULE_NAME
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_NAME
     *
     * @param ruleName the value for ACOAPP_OMS.PROMOTIONRULE.RULE_NAME
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_UI_FILE_NAME
     *
     * @return the value of ACOAPP_OMS.PROMOTIONRULE.RULE_UI_FILE_NAME
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public String getRuleUiFileName() {
        return ruleUiFileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_UI_FILE_NAME
     *
     * @param ruleUiFileName the value for ACOAPP_OMS.PROMOTIONRULE.RULE_UI_FILE_NAME
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public void setRuleUiFileName(String ruleUiFileName) {
        this.ruleUiFileName = ruleUiFileName == null ? null : ruleUiFileName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PROMOTIONRULE.UPDATE_BY
     *
     * @return the value of ACOAPP_OMS.PROMOTIONRULE.UPDATE_BY
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PROMOTIONRULE.UPDATE_BY
     *
     * @param updateBy the value for ACOAPP_OMS.PROMOTIONRULE.UPDATE_BY
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_TEXT
     *
     * @return the value of ACOAPP_OMS.PROMOTIONRULE.RULE_TEXT
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public String getRuleText() {
        return ruleText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_TEXT
     *
     * @param ruleText the value for ACOAPP_OMS.PROMOTIONRULE.RULE_TEXT
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public void setRuleText(String ruleText) {
        this.ruleText = ruleText == null ? null : ruleText.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_BTEXT
     *
     * @return the value of ACOAPP_OMS.PROMOTIONRULE.RULE_BTEXT
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public String getRuleBtext() {
        return ruleBtext;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PROMOTIONRULE.RULE_BTEXT
     *
     * @param ruleBtext the value for ACOAPP_OMS.PROMOTIONRULE.RULE_BTEXT
     *
     * @mbggenerated Fri Nov 01 13:43:44 CST 2013
     */
    public void setRuleBtext(String ruleBtext) {
        this.ruleBtext = ruleBtext == null ? null : ruleBtext.trim();
    }
}