package com.chinadrtv.erp.tc.core.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.MediaDnis;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.dto.OrderAuditExtDto;
import com.chinadrtv.erp.tc.core.dto.OrderAuditQueryDto;
import com.chinadrtv.erp.tc.core.dto.OrderQueryDto;
import com.chinadrtv.erp.tc.core.model.CompanyAssignQuantity;

/**
 * 订单Dao层
 * 订单的添加、更新和删除
 */
public interface OrderhistDao extends GenericDao<Order,Long> {
    void saveOrUpdate(Order orderhist);

    String getOrderId();

    Order get(Long id);

    /**
     * 根据业务订单号获取订单
    * @Description: 
    * @param orderId
    * @return Orderhist
    * @throws
     */
    Order getOrderHistByOrderid(String orderId);
    
    int updateOrderhist(String setStr, String treadid) throws Exception;

	/**
	 * 获取非套装产品信息
	* @Description: 
	* @param orderdet
	* @return Map<String,Object>
	* @throws
	*/ 
	Map<String, Object> queryProdNonSuiteInfo(OrderDetail orderdet);

	
	Order getOrderHistByMailId(String mailId);

    List<CompanyAssignQuantity> getCurrentCompanyAssignQuantity();

    List<Order> findCorrelativeOrders(Order order);

    List<Order> queryOrder(OrderQueryDto orderQueryDto);

    int getTotalCount(OrderQueryDto orderQueryDto);

    List<OrderAuditExtDto> queryAuditOrder(OrderAuditQueryDto orderAuditQueryDto);

    int getAuditTotalCount(OrderAuditQueryDto orderAuditQueryDto);

    void flush();

    List<String> queryN400ByDnis(String dnis);

    List<MediaDnis> queryOrderType(List<String> n400List, String area);
}
