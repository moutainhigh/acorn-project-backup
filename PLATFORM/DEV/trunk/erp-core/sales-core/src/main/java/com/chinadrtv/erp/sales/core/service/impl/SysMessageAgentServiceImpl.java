package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.sales.core.dao.SysMessageDao;
import com.chinadrtv.erp.sales.core.model.MessageCheckStatus;
import com.chinadrtv.erp.sales.core.service.SysMessageAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-27
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class SysMessageAgentServiceImpl implements SysMessageAgentService {

    @Autowired
    private SysMessageDao sysMessageDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout =30)
    @Override
    public void updateMessageCheck(List<SysMessage> sysMessageList, MessageCheckStatus messageCheckStatus) {
        //更新消息记录，此时注意并发问题
        if(sysMessageList==null||sysMessageList.size()==0)
            return;
        int index=0;
        do{
            List<SysMessage> updateMessageList=new ArrayList<SysMessage>();
            for(int i=0;i<50;i++)
            {
                if(index>=sysMessageList.size())
                    break;
                updateMessageList.add(sysMessageList.get(index));
                index++;
            }
            if(updateMessageList.size()>0)
            {
                this.updateMessageCheckInner(updateMessageList,messageCheckStatus);
            }
        }while (index<sysMessageList.size());
    }

    private void updateMessageCheckInner(List<SysMessage> sysMessageList, MessageCheckStatus messageCheckStatus)
    {
        StringBuilder strBld=new StringBuilder();
        Map<String,Parameter> parameterMap=new Hashtable<String, Parameter>();

        for(int i=0;i<sysMessageList.size();i++)
        {
            String name="msgid"+i;
            if(i==0)
            {
                strBld.append("update SysMessage set checked=:check where id=:"+name);
                parameterMap.put("check",new ParameterInteger("check",messageCheckStatus.getIndex()));
            }
            else
            {
                strBld.append(" or id=:"+name);
            }
            parameterMap.put(name,new ParameterLong(name,sysMessageList.get(i).getId()));
        }
        sysMessageDao.execUpdate(strBld.toString(),parameterMap);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void handleMessage(SysMessage sysMessage)
    {
        sysMessageDao.saveOrUpdate(sysMessage);
    }
}
