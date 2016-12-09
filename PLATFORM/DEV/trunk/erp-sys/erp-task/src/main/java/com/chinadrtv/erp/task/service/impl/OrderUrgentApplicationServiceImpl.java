package com.chinadrtv.erp.task.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.task.core.service.BaseServiceImpl;
import com.chinadrtv.erp.task.dao.cntrpbank.OrderUrgentApplicationDao;
import com.chinadrtv.erp.task.entity.OrderUrgentApplication;
import com.chinadrtv.erp.task.service.OrderUrgentApplicationService;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderUrgentApplicationServiceImpl extends BaseServiceImpl<OrderUrgentApplication,Long> implements OrderUrgentApplicationService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderUrgentApplicationServiceImpl.class);

    public OrderUrgentApplicationServiceImpl()
    {

    }
    @Autowired
    private OrderUrgentApplicationDao orderUrgentApplicationDao;

    //@Resource
    //DataSource cntrpbankDS;

    @Override
    public BaseRepository<OrderUrgentApplication, Long> getDao() {
        return orderUrgentApplicationDao;
    }

    @Override
    @Transactional
    public List<OrderUrgentApplication> getAllNotHandleUrgentOrders() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private static SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Override
    public List<OrderUrgentApplication> getHandledByOrderIds(List<String> orderIdList) {
        if(orderIdList==null)
            return new ArrayList<OrderUrgentApplication>();

        logger.info("begin query urgent order size:"+orderIdList.size());
        List<OrderUrgentApplication> orderUrgentApplicationList=new ArrayList<OrderUrgentApplication>();
        int index=0;
        do{
            List<String> queryOrderIdList=new ArrayList<String>();
            for(int i=0;i<50;i++)
            {
                if(index>=orderIdList.size())
                    break;
                queryOrderIdList.add(orderIdList.get(index));
                index++;
            }
            if(queryOrderIdList.size()>0)
            {
                List<OrderUrgentApplication> itemOrderUrgentApplicationList=this.getHandledByOrderIdsInner(queryOrderIdList);
                if(itemOrderUrgentApplicationList!=null&&itemOrderUrgentApplicationList.size()>0)
                {
                    orderUrgentApplicationList.addAll(itemOrderUrgentApplicationList);
                }
            }
        }while (index<orderIdList.size());

        logger.info("return query urgent order size:"+orderUrgentApplicationList.size());
        return orderUrgentApplicationList;
    }

    private List<OrderUrgentApplication> getHandledByOrderIdsInner(List<String> orderIdList) {
        logger.info("begin batch query urgent order size:"+orderIdList.size());


        StringBuilder strBld=new StringBuilder("select ORDERID,APPPSN,to_char(APPDATE,'yyyy-MM-dd hh24:mm:ss') from ACOAPP_CNTRPBANK.ORDERURGENTAPPLICATION where status='3' and (");

        Map<String,Object> parms=new Hashtable<String,Object>();
        for(int i=0;i<orderIdList.size();i++)
        {
            String name="oid"+i;
            parms.put(name,orderIdList.get(i));
            if(i==0)
            {
                strBld.append("ORDERID=:"+name);
            }
            else
            {
                strBld.append(" or ORDERID=:"+name);
            }
        }
        strBld.append(")");
//      PageRequest pageable = new PageRequest(0, orderIdList.size()) ;
        List<Object[]> list=orderUrgentApplicationDao.nativeQuery(strBld.toString(),parms,1,orderIdList.size()+10);
        //List<OrderUrgentApplication> orderUrgentApplicationList=orderUrgentApplicationDao.nativeQuery(OrderUrgentApplication.class,strBld.toString(),parms,0,orderIdList.size());
        //List<OrderUrgentApplication> orderUrgentApplicationList=new ArrayList<OrderUrgentApplication>();
        logger.info("after query urgent order");

        List<OrderUrgentApplication> orderUrgentApplicationList=new ArrayList<OrderUrgentApplication>();
        if(list!=null&&list.size()>0)
        {
            for(Object[] datas:list)
            {
                OrderUrgentApplication orderUrgentApplication=new OrderUrgentApplication();
                orderUrgentApplication.setOrderid(datas[0].toString());
                orderUrgentApplication.setApppsn(datas[1].toString());
                if(datas[2]!=null)
                {
                    String str=datas[2].toString();
                    if(StringUtils.isNotEmpty(str))
                    {
                        try{
                            orderUrgentApplication.setAppdate(simpleDateFormat.parse(str));
                        }catch (Exception exp)
                        {
                            exp.printStackTrace();
                        }
                    }
                }


                orderUrgentApplicationList.add(orderUrgentApplication);
            }
        }
        //Page<OrderUrgentApplication> page = orderUrgentApplicationDao.nativeQuery(OrderUrgentApplication.class, strBld.toString(),parms, pageable);
        //return orderUrgentApplicationList;

        logger.info("return query urgent order size:"+orderUrgentApplicationList.size());

        return orderUrgentApplicationList;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
