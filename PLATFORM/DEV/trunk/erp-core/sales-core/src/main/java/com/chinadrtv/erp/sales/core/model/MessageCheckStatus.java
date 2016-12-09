package com.chinadrtv.erp.sales.core.model;

import org.codehaus.jackson.annotate.JsonCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-27
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public enum MessageCheckStatus {
    NOT_READ("未读",0),
    READED("已读",1),
    HANDLED("已处理",2);

    private String name;
    private int index;

    MessageCheckStatus(String name,int index)
    {
        this.name=name;
        this.index=index;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    public static List<MessageCheckStatus> getShowList()
    {
        List<MessageCheckStatus> messageTypeList=new ArrayList<MessageCheckStatus>();
        for(MessageCheckStatus messageType:MessageCheckStatus.values())
        {
            if(messageType.getIndex()<5)
            {
                messageTypeList.add(messageType);
            }
        }
        return messageTypeList;
    }

    @JsonCreator
    public static MessageCheckStatus getMessageTypeFromIndex(Integer index)
    {
        if(index!=null)
        {
            for(MessageCheckStatus messageType:MessageCheckStatus.values())
            {
                if(index.intValue()==messageType.getIndex())
                    return messageType;
            }
        }
        return null;
    }
}
