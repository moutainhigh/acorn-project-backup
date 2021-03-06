package com.chinadrtv.model.oms;

import java.math.BigDecimal;
import java.util.Date;

public class PreTradeLotRequest {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.ID
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private BigDecimal id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.FILE_NAME
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private String fileName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.PARAMETER
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private String parameter;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.CREATE_DATE
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.ERR_MSG
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private String errMsg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.SOURCE_ID
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private BigDecimal sourceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.URL_ADDRESS
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private String urlAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.IS_SUCCESS
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private Short isSuccess;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.FILE_PATH
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private String filePath;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.ID
     *
     * @return the value of ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.ID
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.ID
     *
     * @param id the value for ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.ID
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.FILE_NAME
     *
     * @return the value of ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.FILE_NAME
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.FILE_NAME
     *
     * @param fileName the value for ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.FILE_NAME
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.PARAMETER
     *
     * @return the value of ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.PARAMETER
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.PARAMETER
     *
     * @param parameter the value for ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.PARAMETER
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.CREATE_DATE
     *
     * @return the value of ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.CREATE_DATE
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.CREATE_DATE
     *
     * @param createDate the value for ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.CREATE_DATE
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.ERR_MSG
     *
     * @return the value of ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.ERR_MSG
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.ERR_MSG
     *
     * @param errMsg the value for ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.ERR_MSG
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg == null ? null : errMsg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.SOURCE_ID
     *
     * @return the value of ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.SOURCE_ID
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public BigDecimal getSourceId() {
        return sourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.SOURCE_ID
     *
     * @param sourceId the value for ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.SOURCE_ID
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setSourceId(BigDecimal sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.URL_ADDRESS
     *
     * @return the value of ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.URL_ADDRESS
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public String getUrlAddress() {
        return urlAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.URL_ADDRESS
     *
     * @param urlAddress the value for ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.URL_ADDRESS
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress == null ? null : urlAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.IS_SUCCESS
     *
     * @return the value of ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.IS_SUCCESS
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public Short getIsSuccess() {
        return isSuccess;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.IS_SUCCESS
     *
     * @param isSuccess the value for ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.IS_SUCCESS
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setIsSuccess(Short isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.FILE_PATH
     *
     * @return the value of ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.FILE_PATH
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.FILE_PATH
     *
     * @param filePath the value for ACOAPP_OMS.PRE_TRADE_LOT_REQUEST.FILE_PATH
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }
}