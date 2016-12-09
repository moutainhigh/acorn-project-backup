package com.chinadrtv.order.download.dal.iagent.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;

/**
 * 订单Dao层
 * 订单的添加、更新和删除
 */
public interface OrderhistDao extends GenericDao<Order,Long> {

    /**
     * 根据业务订单号获取订单
    * @Description: 
    * @param orderId
    * @return Orderhist
    * @throws
     */
    Order getOrderHistByOrderid(String orderId);

    /**
     *
     * @param orderdet
     * @return
     */
    String getProdType(OrderDetail orderdet);

    /**
     *
     * @param prodId
     * @param prodType
     * @return
     */
    String getProdScmCode(String prodId, String prodType);

    /**
     *
     * @param prodId
     * @param prodType
     * @return
     */
    String getProdSuite(String prodId, String prodType);

    /**
     *
     * @param pluCode
     * @return
     */
    String getProdSpellName(String pluCode);

}
