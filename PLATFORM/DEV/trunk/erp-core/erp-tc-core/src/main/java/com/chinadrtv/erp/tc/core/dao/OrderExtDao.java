package com.chinadrtv.erp.tc.core.dao;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.OrderExt;
import com.chinadrtv.erp.model.agent.Order;


public interface OrderExtDao extends GenericDao<OrderExt,java.lang.String>{

    
    /**
     * 根据订单和仓库号插入
    * @Description: 
    * @param orderhist
    * @param warehouseId
    * @return void
    * @throws
     */
    void insertOrUpdateByOrderhist(Order orderhist, String warehouseId);

    void saveOrUpdateFrom(OrderExt orderExt);
}
