package com.chinadrtv.erp.marketing.core.dto;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Title: SearchContactAssignHistDto
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public class SearchContactAssignHistDto implements Serializable {
    //private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger logger = Logger.getLogger(SearchContactAssignHistDto.class.getName());
    private String campaignId;
    private String batchCode;
    private String status;
    private Date assignStartTime;
    private Date assignEndTime;

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAssignStartTime() {
        return assignStartTime;
    }

    public void setAssignStartTime(Date assignStartTime) {
        this.assignStartTime = assignStartTime;
    }

    public void setStrAssignStartTime(String assignStartTime) {
        if (StringUtils.isNotBlank(assignStartTime)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                this.assignStartTime = simpleDateFormat.parse(assignStartTime);
            } catch (ParseException e) {
                logger.error("时间转换异常，数据为:" + assignStartTime, e);
            }
        }
    }

    public Date getAssignEndTime() {
        return assignEndTime;
    }

    public void setAssignEndTime(Date assignEndTime) {
        this.assignEndTime = assignEndTime;
    }

    public void setStrAssignEndTime(String assignEndTime) {
        if (StringUtils.isNotBlank(assignEndTime)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                this.assignEndTime = simpleDateFormat.parse(assignEndTime);
            } catch (ParseException e) {
                logger.error("时间转换异常，数据为:" + assignEndTime, e);
            }
        }
    }
}
