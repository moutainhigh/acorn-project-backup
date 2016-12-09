package com.chinadrtv.order.choose.service.impl;

import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.tc.core.constant.OrderAssignConstants;
import com.chinadrtv.order.choose.dal.dao.OrderChooseDao;
import com.chinadrtv.order.choose.dal.model.OrderAssign;
import com.chinadrtv.order.choose.service.OrderRetryAssignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 保存需要重置分拣的订单
 * 目前采用文件系统保存
 * User: 徐志凯
 * Date: 13-4-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderRetryAssignServiceImpl implements OrderRetryAssignService {

    private static final Logger logger = LoggerFactory.getLogger(OrderRetryAssignServiceImpl.class);

    @Value("${ERROR_ORDER_FILENAME}")
    private String fileName;

    @Value("${ASSIGN_DAYS}")
    private Integer nDays;

    private Date lastTime=new Date();

    @Value("${RETRY_TIME}")
    private Integer retryTime;//minute unit =30*18000L;

    public Object file2Object(String fileName) {

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            return object;
        } catch (Exception e) {
            logger.error("read error:", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    logger.error("read error2:", e1);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e2) {
                    logger.error("read error3:", e2);
                }
            }
        }
        return null;
    }


    public void object2File(Object obj, String outputFile) {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(outputFile));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (Exception e) {
            logger.error("save error1:",e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e1) {
                    logger.error("save error2:", e1);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e2) {
                    logger.error("save error3:", e2);
                }
            }
        }
    }

    private static class OrderIdStamp implements Serializable
    {
        public OrderIdStamp(Date date, List<OrderAssign> orderList)
        {
            this.date=date;
            this.orderList=orderList;
        }

        public OrderIdStamp()
        {

        }
        private List<OrderAssign> orderList;
        private Date date;

        public List<OrderAssign> getOrderList() {
            return orderList;
        }

        public void setOrderIdList(List<OrderAssign> orderList) {
            this.orderList = orderList;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    private static class RetryOrderInfo implements Serializable
    {
        public RetryOrderInfo()
        {
            bAll=true;
            orderIdStampList=null;
        }
        private List<OrderIdStamp> orderIdStampList;
        private Boolean bAll;

        public List<OrderIdStamp> getOrderIdStampList() {
            return orderIdStampList;
        }

        public void setOrderIdStampList(List<OrderIdStamp> orderIdStampList) {
            this.orderIdStampList = orderIdStampList;
        }

        public Boolean getbAll() {
            return bAll;
        }

        public void setbAll(Boolean bAll) {
            this.bAll = bAll;
        }
    }
    private RetryOrderInfo retryOrderInfo=null;

    public List<Integer> addRetryOrders(List<OrderAssign> orderAssignList)
    {
        boolean bFirst=false;
        //首次的话，从文件中获取
        if(retryOrderInfo==null)
        {
            bFirst=true;
            //如果文件不存在，那么先更新数据库
            File file = new File(fileName);
            if(file.exists())
            {
                retryOrderInfo=(RetryOrderInfo)this.file2Object(fileName);
            }
            else
            {
                markingOrderAssignByDate(nDays, OrderAssignConstants.HAND_ASSIGN, OrderAssignConstants.RETRY_HAND_ASSIGN);
                markingOrderAssignByDate(nDays, OrderAssignConstants.SIMPLE_ASSIGN, OrderAssignConstants.RETRY_SIMPLE_ASSIGN);
                retryOrderInfo=new RetryOrderInfo();
                retryOrderInfo.setbAll(false);
                retryOrderInfo.setOrderIdStampList(new ArrayList<OrderIdStamp>());
            }
        }

        if(orderAssignList.size()>0)
        {
            if(retryOrderInfo.getbAll()==false)
            {
                OrderIdStamp orderIdStamp = new OrderIdStamp(new Date(), orderAssignList);
                retryOrderInfo.getOrderIdStampList().add(orderIdStamp);
                this.object2File(retryOrderInfo,fileName);
            }
        }

        if(bFirst==true)
            return null;
        return checkAndRetry();
    }

    private List<Integer> checkAndRetry()
    {
        if(retryOrderInfo.getOrderIdStampList()==null)
            return null;
        Date dtNow=new Date();
        Long detalTime=retryTime*60*1000L;
        Date dtNew =new Date(dtNow.getTime()-detalTime);
        if(dtNew.compareTo(lastTime)<0)
            return null;
        //检查每一项，如果要时间，就重置标记
        List<Integer> indexList=new ArrayList<Integer>();
        List<OrderAssign> orderAssignList=new ArrayList<OrderAssign>();
        for(int i=0;i<retryOrderInfo.getOrderIdStampList().size();i++)
        {
            OrderIdStamp orderIdStamp=retryOrderInfo.getOrderIdStampList().get(i);
            Date dt=new Date(dtNow.getTime()-detalTime);
            if(dt.compareTo(orderIdStamp.getDate())>0)
            {
                indexList.add(i);
                orderAssignList.addAll(orderIdStamp.getOrderList());
            }
        }
        if(indexList.size()>0)
        {
            //重置标记
            resetOrderAssign(orderAssignList);
        }

        lastTime=new Date();
        return indexList;
    }

    private final static int batchSize = 50;

    @Autowired
    private OrderChooseDao orderDao;


    private void resetOrderAssign(List<OrderAssign> orderAssignList)
    {
        logger.debug("begin reset assign");
        List<Long> orderHandList=new ArrayList<Long>();
        List<Long> orderSimpleList=new ArrayList<Long>();
        for(OrderAssign orderAssign:orderAssignList)
        {
            if(OrderAssignConstants.HAND_ASSIGN.equals(orderAssign.getAssignFlag()))
            {
                orderHandList.add(orderAssign.getOrderId());
            }
            else
            {
                orderSimpleList.add(orderAssign.getOrderId());
            }
        }
        if(orderSimpleList.size()>0)
        {
            this.markingOrderAssign(orderSimpleList, OrderAssignConstants.RETRY_SIMPLE_ASSIGN, OrderAssignConstants.SIMPLE_ASSIGN);
        }
        if(orderHandList.size()>0)
        {
            this.markingOrderAssign(orderHandList, OrderAssignConstants.RETRY_HAND_ASSIGN, OrderAssignConstants.HAND_ASSIGN);
        }
        logger.debug("end reset assign");
    }

    private void markingOrderAssign(List<Long> orderIdList, String oldAssignFlag, String newAssignFlag) {
        int count = orderIdList.size() / batchSize;
        int lessCount = orderIdList.size() % batchSize;

        List<List<Long>> listList = new ArrayList<List<Long>>();
        for (int i = 0; i < count; i++) {
            List<Long> itemOrderIdList = new ArrayList<Long>();
            for (int j = 0; j < batchSize; j++) {
                itemOrderIdList.add(orderIdList.get(i * batchSize + j));
            }
            listList.add(itemOrderIdList);
        }
        if (lessCount > 0) {
            List<Long> itemOrderIdList = new ArrayList<Long>();
            for (int j = count * batchSize; j < orderIdList.size(); j++) {
                itemOrderIdList.add(orderIdList.get(j));
            }
            listList.add(itemOrderIdList);
        }

        for (List<Long> itemOrderIdList : listList) {
            orderDao.updateOrderAssignFlag(itemOrderIdList, oldAssignFlag, newAssignFlag);
        }
    }

    private void markingOrderAssignByDate(int days, String assignFlag, String orgAssignFlag)
    {
        orderDao.execUpdate("update Order set isassign=:newAssignFlag where crdt > SYSDATE-:days and isassign=:oldAssignFlag and status='1'",
                new ParameterString("newAssignFlag",assignFlag), new ParameterInteger("days",days), new ParameterString("oldAssignFlag",orgAssignFlag));
    }

    public void removeRetryOrders(List<Integer> indexList)
    {
        if(indexList==null||indexList.size()==0)
            return;
        if(retryOrderInfo.getOrderIdStampList()==null)
            return;
        for(int i=indexList.size()-1;i>=0;i--)
        {
            Integer index=indexList.get(i);
            retryOrderInfo.getOrderIdStampList().remove(index.intValue());
        }
        this.object2File(retryOrderInfo,fileName);

        logger.debug("remove orders");
    }
}
