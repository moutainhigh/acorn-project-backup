package com.chinadrtv.erp.sales.util;

import com.chinadrtv.erp.model.SysMessage;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-9
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class MessageGroupKey {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getbCheck() {
        return bCheck;
    }

    public void setbCheck(Integer bCheck) {
        this.bCheck = bCheck;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    private String userId;
    private Integer bCheck;
    private String sourceType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageGroupKey that = (MessageGroupKey) o;

        if (bCheck != null ? !bCheck.equals(that.bCheck) : that.bCheck != null) return false;
        if (sourceType != null ? !sourceType.equals(that.sourceType) : that.sourceType != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (bCheck != null ? bCheck.hashCode() : 0);
        result = 31 * result + (sourceType != null ? sourceType.hashCode() : 0);
        return result;
    }

    public boolean isSameMessageGroup(SysMessage sysMessage)
    {
        if(isSameValue(this.getbCheck(),sysMessage.getChecked())
                &&isSameValue(this.getSourceType(),sysMessage.getSourceTypeId())
                &&isSameValue(this.getUserId(),sysMessage.getReceiverId()))
            return true;
        return false;
    }

    public static MessageGroupKey createMessageGroupKeyFromMessage(SysMessage sysMessage)
    {
        MessageGroupKey messageGroupKey=new MessageGroupKey();
        messageGroupKey.setbCheck(sysMessage.getChecked());
        messageGroupKey.setSourceType(sysMessage.getSourceTypeId());
        messageGroupKey.setUserId(sysMessage.getReceiverId());
        return messageGroupKey;
    }

    private boolean isSameValue(Object obj1,Object obj2)
    {
        if(obj1==null)
        {
            if(obj2!=null)
                return false;
        }
        else
        {
            if(!obj1.equals(obj2))
                return false;
        }
        return true;
    }
}
