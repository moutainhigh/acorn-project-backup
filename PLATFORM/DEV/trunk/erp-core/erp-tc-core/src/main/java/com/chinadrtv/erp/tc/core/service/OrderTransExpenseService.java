package com.chinadrtv.erp.tc.core.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.model.OrderTransExpenseReturnInfo;

/**
 *订单运费计算服务接口
 * User: liyu
 * Date: 13-1-28
 * Time: 下午4:19
 * 创建服务接口
 */
public interface OrderTransExpenseService extends GenericService<Order,Long> {
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
    OrderTransExpenseReturnInfo getOrderTransExpenseByAgent(String ConditionStr);
}
