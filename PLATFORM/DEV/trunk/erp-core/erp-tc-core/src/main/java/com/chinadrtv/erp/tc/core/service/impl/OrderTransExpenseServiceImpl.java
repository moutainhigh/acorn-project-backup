package com.chinadrtv.erp.tc.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.constant.OrderCode;
import com.chinadrtv.erp.tc.core.dao.OrderTransExpenseDao;
import com.chinadrtv.erp.tc.core.model.OrderTransExpenseReturnInfo;
import com.chinadrtv.erp.tc.core.service.OrderTransExpenseService;

/**
 * 订单运费计算服务实现类
 * User: liyu
 * Date: 13-1-28
 * Time: 下午4:32
 * 创建服务
 */
@Service
public class OrderTransExpenseServiceImpl extends GenericServiceImpl<Order,Long> implements OrderTransExpenseService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderTransExpenseServiceImpl.class);
    @Autowired
    private OrderTransExpenseDao orderTransExpenseDao;

    @Override
    protected GenericDao<Order,Long> getGenericDao()
    {
        return orderTransExpenseDao;
    }

    /**
     * 获取订单运费
     * @param orderhist 订单实体
     * @return 运费
     */
    public Double getOrderTransExpenseByOrder(Order orderhist)
    {
        return orderTransExpenseDao.getOrderTransExpenseByOrder(orderhist);
    }

    /**
     * 获取订单运费
     * @param ConditionStr 条件字符串
     * @return 运费
     */
    public OrderTransExpenseReturnInfo getOrderTransExpenseByAgent(String ConditionStr)
    {
        OrderTransExpenseReturnInfo returnInfo = new OrderTransExpenseReturnInfo();

        try
        {
            Double expense = orderTransExpenseDao.getOrderTransExpenseByAgent(ConditionStr);

            returnInfo.setExpense(expense);
            returnInfo.setCode(OrderCode.SUCC);

            return returnInfo;
        }
        catch (Exception exp)
        {
            returnInfo.setCode(OrderCode.SYSTEM_ERROR);
            returnInfo.setDesc(exp.getMessage());

            logger.error("",exp);
            return returnInfo;
        }
    }
}
