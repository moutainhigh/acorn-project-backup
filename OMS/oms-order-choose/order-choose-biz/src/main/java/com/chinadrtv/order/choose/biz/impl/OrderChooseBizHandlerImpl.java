package com.chinadrtv.order.choose.biz.impl;

import com.chinadrtv.erp.model.trade.ShipmentDownControl;
import com.chinadrtv.erp.tc.core.constant.AccountStatusConstants;
import com.chinadrtv.erp.tc.core.constant.LogisticsStatusConstants;
import com.chinadrtv.erp.tc.core.constant.cache.OrderAssignErrorCode;
import com.chinadrtv.erp.tc.core.constant.model.OrderhistAssignInfo;
import com.chinadrtv.erp.tc.core.service.CompanyAssignQuantityService;
import com.chinadrtv.order.choose.biz.OrderChooseBizHandler;
import com.chinadrtv.order.choose.dal.model.OrderAssign;
import com.chinadrtv.order.choose.dal.model.OrderChooseQueryParm;
import com.chinadrtv.order.choose.service.OrderChooseAutomaticService;
import com.chinadrtv.order.choose.service.OrderRetryAssignService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderChooseBizHandlerImpl implements OrderChooseBizHandler {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderChooseBizHandlerImpl.class);

    @Value("${QUERY_LIMIT}")
    private Integer limit;
    @Value("${ASSIGN_DAYS}")
    private Integer nDays;

    @Autowired
    private CompanyAssignQuantityService companyAssignQuantityService;
    @Autowired
    private OrderChooseAutomaticService orderChooseAutomaticService;
    @Autowired
    private OrderRetryAssignService orderRetryAssignService;


    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Override
    public boolean orderChoose(Date startDate) {
        //如果有在处理，那么直接忽略此消息
        if(isRun.compareAndSet(false,true))
        {
            logger.info("begin order choose");
            try
            {
                //重置内存缓存的承运商配额信息，直接从数据库中读取
                companyAssignQuantityService.resetCompanyAssignQuantity();
                //匹配之前进行必要的初始化工作（获取添加会话级缓存、承运商当天包裹数量等）
                //获取循环获取需要处理的记录，然后进行查找，最后更新订单
                //对于没有匹配的数据（包括更新时出错的数据），先打标记（ISASSIGN），最后定时将标记重置回来
                //定时将重试标记重置

                List<OrderhistAssignInfo> orderhistAssignInfoList = null;

                do {

                    List<OrderAssign> orderAssignList=new ArrayList<OrderAssign>();

                    OrderChooseQueryParm queryParm=new OrderChooseQueryParm();
                    queryParm.setLimit(limit);
                    queryParm.setnDays(nDays);

                    logger.debug("order choose token 1");

                    orderhistAssignInfoList = orderChooseAutomaticService.automaticAssignOrder(queryParm);

                    logger.debug("order size:"+ orderhistAssignInfoList.size());

                    for (OrderhistAssignInfo orderhistAssignInfo : orderhistAssignInfoList) {
                        String orderNum=orderhistAssignInfo.getOrder().getOrderid();
                        Long orderId=orderhistAssignInfo.getOrder().getId();
                        String assignFlag=orderhistAssignInfo.getOrder().getIsassign();

                        logger.debug("order choose result:"+orderhistAssignInfo.getOrder().getOrderid()+"-"+ orderhistAssignInfo.isSucc());
                        if(!orderhistAssignInfo.isSucc())
                            logger.error(orderhistAssignInfo.getOrder().getOrderid()+":"+ orderhistAssignInfo.getErrorId()+":"+orderhistAssignInfo.getErrorMsg());

                        try {
                            orderChooseAutomaticService.assignOrderCompany(orderhistAssignInfo);
                        } catch (RuntimeException rtExp) {
                            logger.error("assignOrderCompany error order id:" + orderhistAssignInfo.getOrder().getId(), rtExp);
                            //检查异常类型，如果是并发错误，那么稍后重试，不需要打标记
                            if (rtExp instanceof HibernateOptimisticLockingFailureException) {
                                continue;
                            }
                            orderhistAssignInfo.setSucc(false);
                            orderhistAssignInfo.setErrorId(OrderAssignErrorCode.SYSTEM_ERROR);
                            String strError=rtExp.getMessage();
                            if(StringUtils.isNotEmpty(strError))
                            {
                                if(strError.length()>255)
                                    strError=strError.substring(0,255);
                            }
                            orderChooseAutomaticService.saveErrorOrderAndLog(orderId, orderNum, assignFlag, strError);
                        }

                        logger.debug("order choose token 2");

                        if(!orderhistAssignInfo.isSucc() && !OrderAssignErrorCode.MATCH_SPEC_CONDITION.equals(orderhistAssignInfo.getErrorId()))
                        {
                            OrderAssign orderAssign=new OrderAssign();
                            orderAssign.setAssignFlag(orderhistAssignInfo.getOrder().getIsassign());
                            orderAssign.setOrderId(orderhistAssignInfo.getOrder().getId());

                            orderAssignList.add(orderAssign);
                        }

                        logger.debug("order choose token 3");

                        //如果成功分拣，记得更新缓存数据
                        if(orderhistAssignInfo.isSucc())
                        {
                            companyAssignQuantityService.updateCompanyAssignQuantity(getCompanyFromOrderAssignInfo(orderhistAssignInfo),1L,orderhistAssignInfo.getOrder().getTotalprice());
                        }

                        logger.debug("order choose token 4");
                    }

                    //记录重试订单
                    List<Integer> indexList=orderRetryAssignService.addRetryOrders(orderAssignList);
                    orderRetryAssignService.removeRetryOrders(indexList);

                    logger.debug("order choose token 5");

                } while (orderhistAssignInfoList.size() >= limit.intValue());

                logger.debug("end auto choose");
            }
            catch (Exception exp)
            {
                logger.error("order choose error:", exp);
                throw new RuntimeException(exp.getMessage());
            }
            finally {
                isRun.set(false);
                logger.info("end order choose");
            }
            return true;
        }
        else
        {
            logger.error("order choose is running!!!");
            return false;
        }
    }

    private Long getCompanyFromOrderAssignInfo(OrderhistAssignInfo orderhistAssignInfo)
    {
        if(StringUtils.isNotEmpty(orderhistAssignInfo.getEntityId()))
        {
            return Long.parseLong(orderhistAssignInfo.getEntityId());
        }
        else
        {
            return Long.parseLong(orderhistAssignInfo.getOrder().getEntityid());
        }
    }
}
