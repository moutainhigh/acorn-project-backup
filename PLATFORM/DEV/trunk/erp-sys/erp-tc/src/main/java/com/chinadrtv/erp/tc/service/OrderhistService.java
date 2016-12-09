package com.chinadrtv.erp.tc.service;

import java.util.Map;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.OrderHistory;
import com.chinadrtv.erp.tc.core.model.OrderAutoChooseInfo;
import com.chinadrtv.erp.tc.core.model.PreTradeRest;

/**
 * Created with IntelliJ IDEA. User: xuzk Date: 13-1-24
 */
public interface OrderhistService extends GenericService<Order, Long> {
	
	String addOrderhist(PreTradeRest preTradeRest) throws Exception;

	int updateOrderhist(String setStr, String treadid) throws Exception;

	void updateOrderhist(Order orderhist, String entityId, String wardhouseId) throws Exception;

	/**
	 * 更新订单
	 * 
	 * @param orderhist
	 *            订单信息 是否根据传入的明细，来同时更新 如果为true（注意不存在的明细删除，新的插入，相同的更新）
	 *            如果为false，那么不处理
	 */
	void updateOrderhist(Order orderhist) throws Exception;

	void deleteOrderhist(Order orderhist);

	Order getOrderhist(Long id);

	//Orderhist getOrderhistFromOrderId(String orderId);

	void justTestAudit(Long id, String mailId);

	String getOrderId();

	int updateOrderhistBySql(String setStr, String treadid) throws Exception;

	Order getOrderHistByMailId(String mailId);

	Order getOrderHistByNetOrderId(String netOrderId);

	void updateOrderhistOrderdet(OrderDetail orderdet) throws Exception;
	
	@Deprecated
	boolean isNeedNewRevision(Order orderhist);
	

	
	/*===========================================================================*/
	
	
	

	/**
	 * 根据业务订单号获取订单
	 * @param orderId
	 * @return Orderhist
	 */
	Order getOrderHistByOrderid(String orderId);

	/**
	 * 逻辑删除订单
	 * @param orderhist
	 * @throws Exception
	 * @return void
	 * @throws
	 */
	void deleteOrderLogic(Order orderhist) throws Exception;

	/**
	 * 物理删除订单
	 * @Description: 物理删除
	 * @param orderhist
	 * @throws Exception
	 * @return void
	 * @throws
	 */
	void deleteOrderPhysical(Order orderhist) throws Exception;

	/**
	 * 更新订单索权号
	 * @Description:
	 * @param orderid
	 * @param cardrightnum
	 * @return void
	 * @throws
	 */
	int updateOrderRightNum(Order orderhist) throws Exception;

	/**
	 * 更新订单子订单号
	 * @Description:
	 * @param orderhist
	 * @return void
	 * @throws
	 */
	int updateOrderChild(Order orderhist) throws Exception;

	/**
	 * 取消分拣状态
	 * @Description: 取消分拣状态
	 * @param orderhist
	 * @return
	 * @return int
	 * @throws
	 */
	int cancelSortStatus(Map<String, Object> params) throws Exception;

	/**
	 * SR3.5_1.11 修改订单订购类型
	 * @Description: 修改订单订购类型
	 * @param orderhist
	 * @return
	 * @return int
	 * @throws
	 */
	int updateOrderType(Order orderhist) throws Exception;

	/**
	 * SR3.5_1.13 更新订购方式和送货公司
	 * @Description: 更新订购方式和送货公司
	 * @param orderhist
	 * @return
	 * @return int
	 * @throws
	 */
	int updateOrderTypeAndDelivery(Order orderhist) throws Exception;

	/**
	 * 修改订单的收货人
	 * @Description: 修改订单的收货人
	 * @param orderhist
	 * @return
	 * @return int
	 * @throws
	 */
	int updateOrderConsignee(Map<String, Object> params) throws Exception;

	/**
	 * 修改订单的付款人
	 * @Description: 修改订单的付款人
	 * @param orderhist
	 * @return
	 * @return int
	 * @throws
	 */
	int updateOrderPayer(Order orderhist) throws Exception;

	/**
	 * 订单回访取消订单
	 * @Description: 订单回访取消订单
	 * @param orderhist
	 * @return
	 * @return int
	 * @throws
	 */
	int callbackCancelOrder(Order orderhist) throws Exception;

	/**
	 * 订单回访保存时更新订单信息
	 * @Description: 订单回访保存时更新订单信息
	 * @param orderhist
	 * @return
	 * @return int
	 * @throws
	 */
	int callbackUpdateOrder(Order orderhist) throws Exception;

	/**
	 * SR3.5_1.5更新订单明细
	 * @Description: 更新订单明细
	 * @param orderhist
	 * @return
	 * @return int
	 * @throws
	 */
	int updateOrderDetail(OrderDetail orderdet) throws Exception;

	/**
	 * 判断订单是否已出库
	 * @Description: true 已出库， false 未出库
	 * @param customerStatus
	 * @return boolean
	 */
	boolean judgeOrderStatus(String customerStatus);


	/**
	 * 获取非套装产品信息
	 * @param orderdet
	 * @return Map<String,Object>
	 */
	Map<String, Object> queryProdNonSuiteInfo(OrderDetail orderdet);

	/**
	 * 插入历史表
	 * @param orderhist
	 * @return OrderHistory
	 * @throws Exception
	 */
    OrderHistory insertTcHistory(Order orderhist) throws Exception;
	
    /**
     * <p>更改订单运费</p>
     */
	void updateOrderFreight(Map<String, Object> params) throws Exception;









    void autoChooseOrder(OrderAutoChooseInfo orderAutoChooseInfo);
}
