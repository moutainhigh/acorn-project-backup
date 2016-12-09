package com.chinadrtv.erp.tc.core.service;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.constant.model.OrderhistAssignInfo;

import java.util.List;

/**
 * 简单地址匹配
 * User: xuzk
 * Date: 13-2-18
 */
public interface OrderhistCompanyAssignService {
    @Deprecated
    OrderhistAssignInfo findCompanyFromRule(Order orderhist);

    /**
     * 根据订单地址匹配仓库与送货公司
     * @Description:
     * @param orderhist
     * @return
     * @return OrderhistAssignInfo
     * @throws
     */
    OrderhistAssignInfo queryDefaultAssignInfo(Order orderhist);

    /**
     * 承运商复杂地址匹配
     * @param orderhistList
     * @return
     */
    List<OrderhistAssignInfo> findOrderMatchRule(List<Order> orderhistList);
}
