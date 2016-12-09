package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.smsapi.model.SmsSendDetail;

import javax.persistence.Column;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-7-17
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class SmsSendDetailDto {
    private String smsType;
    private String mobile;
    private String content;
    private String createTime;
    private String receiveStatusDes;

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReceiveStatusDes() {
        return receiveStatusDes;
    }

    public void setReceiveStatusDes(String receiveStatusDes) {
        this.receiveStatusDes = receiveStatusDes;
    }
}
