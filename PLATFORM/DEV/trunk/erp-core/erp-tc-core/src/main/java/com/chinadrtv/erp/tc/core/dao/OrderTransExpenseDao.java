package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Order;

/**
 * 订单运费计算DAO.
 * User: liyu
 * Date: 13-1-28
 * Time: 下午4:44
 * 创建订单运费接口
 */
public interface OrderTransExpenseDao extends GenericDao<Order,Long>  {
    /**
     * 获取订单运费
     * @param orderhist 订单实体
     * @return 运费
     */
    Double getOrderTransExpenseByOrder(Order orderhist);

    /**
     * 获取订单运费
     * @param ConditionStr 条件字符串
     * @return 运费
     */
    Double getOrderTransExpenseByAgent(String ConditionStr);
}
