package com.chinadrtv.erp.task.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.task.core.service.BaseServiceImpl;
import com.chinadrtv.erp.task.dao.sales.SysMessageDao;
import com.chinadrtv.erp.task.entity.OrderUrgentApplication;
import com.chinadrtv.erp.task.entity.SysMessage;
import com.chinadrtv.erp.task.service.SysMessageService;
import com.google.code.ssm.api.InvalidateMultiCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessage,Long> implements SysMessageService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SysMessageServiceImpl.class);

    @Autowired
    private SysMessageDao sysMessageDao;

    @Override
    public BaseRepository<SysMessage, Long> getDao() {
        return sysMessageDao;
    }

    private static SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public List<SysMessage> queryUrgentMessage() {
        //查询3个月内的，状态为1的催送货订单消息
        logger.info("begin query urgent message");
        String sql="select ID,CONTENT,RECEIVER_ID,to_char(CREATE_DATE,'yyyy-MM-dd hh24:MI:ss') from IAGENT.SYS_MESSAGE where SOURCE_TYPE_ID='1' and (IS_CHECKED=1 or IS_CHECKED=0) and CREATE_DATE>=to_date(:createdate,'yyyy-MM-dd hh24:MI:ss')";
        Map<String,Object> parms=new Hashtable<String, Object>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        String strBegin=String.format("%04d-%02d-01 00:00:00",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);
        parms.put("createdate",strBegin);

        List<Object[]> list=sysMessageDao.nativeQuery(sql,parms,1,10000);
        logger.info("after query urgent message");
        List<SysMessage> sysMessageList=new ArrayList<SysMessage>();
        if(list!=null&&list.size()>0)
        {
            logger.info("query urgent message size:"+list.size());
            for(Object[] datas:list)
            {
                SysMessage sysMessage=new SysMessage();
                sysMessage.setId(Long.parseLong(datas[0].toString()));
                sysMessage.setContent(datas[1]!=null?datas[1].toString():"");
                sysMessage.setReceiverId(datas[2]!=null?datas[2].toString():"");
                if(datas[3]!=null)
                {
                    try{
                        sysMessage.setCreateDate(simpleDateFormat.parse(datas[3].toString()));
                    }catch (Exception exp)
                    {

                    }
                }
                sysMessageList.add(sysMessage);
            }
        }

        logger.info("return query urgent message size:"+sysMessageList.size());
        return sysMessageList;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateUrgentMessage(List<SysMessage> sysMessageList) {
        logger.info("begin update urgent message size:"+sysMessageList.size());
        int index=0;
        do{
            List<SysMessage> updateMsgList=new ArrayList<SysMessage>();
            for(int i=0;i<50;i++)
            {
                if(index>=sysMessageList.size())
                    break;
                updateMsgList.add(sysMessageList.get(index));
                index++;
            }
            if(updateMsgList.size()>0)
            {
                this.updateUrgentMessageInner(updateMsgList);
            }
        }while (index<sysMessageList.size());
        logger.info("after update urgent message size:"+sysMessageList.size());
    }

    private void updateUrgentMessageInner(List<SysMessage> sysMessageList) {
        //TODO:注意并发更新情况
        logger.info("begin batch update urgent message size:"+sysMessageList.size());
        StringBuilder strBld=new StringBuilder("update IAGENT.SYS_MESSAGE set IS_CHECKED=2 where ");
        List<Object> parms=new ArrayList<Object>();
        for(int i=0;i<sysMessageList.size();i++)
        {
            //String name="id"+i;
            String name="?";
            if(i==0)
                strBld.append("( ID="+name);
            else
                strBld.append(" or ID="+name);
            parms.add(sysMessageList.get(i).getId());
        }
        strBld.append(")");
        sysMessageDao.nativeUpdate(strBld.toString(),parms.toArray());

        logger.info("after batch update urgent message size:"+sysMessageList.size());
    }

    public List<SysMessage> checkUrgentMessage(List<SysMessage> sysMessageList, List<OrderUrgentApplication> orderUrgentApplicationList)
    {
        //匹配时间最近的相同订单
        List<SysMessage> matchSysMessageList = new ArrayList<SysMessage>();
        Map<String,List<SysMessage>> map=new Hashtable<String,List<SysMessage>>();
        for(SysMessage sysMessage:sysMessageList)
        {
            if(StringUtils.isNotEmpty(sysMessage.getContent()))
            {
                if(map.containsKey(sysMessage.getContent()))
                {

                    map.get(sysMessage.getContent()).add(sysMessage);
                }
                else
                {
                    List<SysMessage> itemList=new ArrayList<SysMessage>();
                    itemList.add(sysMessage);
                    map.put(sysMessage.getContent(),itemList);
                }
            }
        }

        for(OrderUrgentApplication orderUrgentApplication:orderUrgentApplicationList)
        {
            if(map.containsKey(orderUrgentApplication.getOrderid()))
            {
                matchSysMessageList.add(getMatchMessage(orderUrgentApplication,map.get(orderUrgentApplication.getOrderid())));
            }
        }
        return matchSysMessageList;
    }


    private SysMessage getMatchMessage(OrderUrgentApplication orderUrgentApplication,List<SysMessage> sysMessageList)
    {
        //粗略找到时间最匹配的
        Long delta=null;
        SysMessage matchMessage=null;
        for(SysMessage sysMessage:sysMessageList)
        {
            Long diff=Math.abs(sysMessage.getCreateDate().getTime()-orderUrgentApplication.getAppdate().getTime());
            if(delta==null)
            {
                delta=diff;
                matchMessage=sysMessage;
            }
            else
            {
                if(delta>diff)
                {
                    matchMessage=sysMessage;
                    delta=diff;
                }
            }
        }
        return matchMessage;
    }

    @InvalidateMultiCache(namespace = "com.chinadrtv.erp.model.SysMessage")
    public void refreshUrgentMessage(@ParameterValueKeyProvider List<String> usrIdList) {
        //只是为了刷新缓存
    }

    /*@InvalidateSingleCache(namespace = "com.chinadrtv.erp.model.SysMessage")
    public void refreshUsrUrgentMessage(@ParameterValueKeyProvider String usrId) {
        //只是为了刷新缓存
    }

    @ReadThroughSingleCache(namespace = "com.chinadrtv.erp.model.SysMessage")
    public List<String> getUsrUrgentMessage(@ParameterValueKeyProvider String usrId) {
        //只是为了刷新缓存
        System.out.println("here");
        List<String> list= new ArrayList<String>();
        list.add("aaa");
        return list;
    }*/
}
