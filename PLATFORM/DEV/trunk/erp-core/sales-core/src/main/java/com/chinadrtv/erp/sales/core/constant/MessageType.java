package com.chinadrtv.erp.sales.core.constant;

import org.codehaus.jackson.annotate.JsonCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public enum MessageType {
    PROBLEM_ORDER("问题单",0),
    URGE_ORDER("催送货",1),
    CANCEL_ORDER("取消订单",2),
    MODIFY_ORDER("修改订单",3),
    MODIFY_CONTACT("修改客户",4),
    CANCEL_ORDER_REJECT("取消订单驳回",5),
    MODIFY_ORDER_REJECT("修改订单驳回",6),
    MODIFY_CONTACT_REJECT("修改客户驳回",7),
    ADD_CONTACT("新增客户",8),
    ADD_CONTACT_REJECT("新增客户驳回",9),
    ADD_ORDER("新增订单",10),
    ADD_ORDER_REJECT("新增订单驳回",11);
    //UPGRADE_CONTACT("潜客转正",12),
    //UPGRADE_CONTACT_REJECT("潜客转正驳回",13);

    private String name;
    private int index;

    MessageType(String name,int index)
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

    public static List<MessageType>  getShowList()
    {
        List<MessageType> messageTypeList=new ArrayList<MessageType>();
        for(MessageType messageType:MessageType.values())
        {
            if(messageType.getIndex()!=5&&messageType.getIndex()!=6&&messageType.getIndex()!=7
                &&messageType.getIndex()!=9&&messageType.getIndex()!=11)
            {
               messageTypeList.add(messageType);
            }
        }
        return messageTypeList;
    }

    @JsonCreator
    public static MessageType getMessageTypeFromIndex(Integer index)
    {
        if(index!=null)
        {
            for(MessageType messageType:MessageType.values())
            {
                if(index.intValue()==messageType.getIndex())
                    return messageType;
            }
        }
        return null;
    }
}
