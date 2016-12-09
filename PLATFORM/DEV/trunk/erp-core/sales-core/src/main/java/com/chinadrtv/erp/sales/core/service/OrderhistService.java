package com.chinadrtv.erp.sales.core.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.marketing.core.common.AuditTaskType;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.model.agent.Card;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.OrderHistory;
import com.chinadrtv.erp.model.trade.ShipmentChangeHis;
import com.chinadrtv.erp.sales.core.model.OrderCreateResult;
import com.chinadrtv.erp.sales.core.model.OrderExtDto;
import com.chinadrtv.erp.tc.core.dto.OrderAuditExtDto;
import com.chinadrtv.erp.tc.core.dto.OrderAuditQueryDto;
import com.chinadrtv.erp.tc.core.dto.OrderQueryDto;
import com.chinadrtv.erp.tc.core.model.OrderAutoChooseInfo;
import com.chinadrtv.erp.tc.core.model.PreTradeRest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	 */
	void deleteOrderLogic(Order orderhist) throws Exception;

	/**
	 * 物理删除订单
	 * @param orderhist
	 * @throws Exception
	 */
	void deleteOrderPhysical(Order orderhist) throws Exception;

    /**
     * 更新订单索权号
     * @param orderhist
     * @return Integer
     * @throws Exception
     */
	int updateOrderRightNum(Order orderhist) throws Exception;

	/**
	 * 更新订单子订单号
	 * @param orderhist
	 * @return Integer
	 * @throws Exception
	 */
	int updateOrderChild(Order orderhist) throws Exception;

    /**
     * 取消分拣状态
     * @param params
     * @return Integer
     * @throws Exception
     */
	int cancelSortStatus(Map<String, Object> params) throws Exception;

	/**
	 * SR3.5_1.11 修改订单订购类型
	 * @param orderhist
	 * @return Integer
	 * @throws Exception
	 */
	int updateOrderType(Order orderhist) throws Exception;

	/**
	 * SR3.5_1.13 更新订购方式和送货公司
	 * @param orderhist
	 * @return Integer
	 * @throws Exception
	 */
	int updateOrderTypeAndDelivery(Order orderhist) throws Exception;

    /**
     * 修改订单的收货人
     * @param params
     * @return Integer
     * @throws Exception
     */
	int updateOrderConsignee(Map<String, Object> params) throws Exception;

	/**
	 * 修改订单的付款人
	 * @param orderhist
	 * @return Integer
	 * @throws Exception
	 */
	int updateOrderPayer(Order orderhist) throws Exception;

	/**
	 * 订单回访取消订单
	 * @param orderhist
	 * @return Integer
	 * @throws Exception
	 */
	int callbackCancelOrder(Order orderhist) throws Exception;

	/**
	 * 订单回访保存时更新订单信息
	 * @param orderhist
	 * @return Integer
	 * @throws Exception
	 */
	int callbackUpdateOrder(Order orderhist) throws Exception;

    /**
     * SR3.5_1.5更新订单明细
     * @param orderdet
     * @return Integer
     * @throws Exception
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




    //new interface for sales
    /**
     * 检查是否有相关订单
     * @param orderId
     * @param bAddressChange
     * @param bContactChange
     * @param bPhoneChange
     * @return
     */
    List<Order> checkCorrelativeOrder(String orderId, boolean bAddressChange, boolean bContactChange, boolean bPhoneChange) throws ErpException;

    /**
     * 获取联系人关联未出库订单
     * @param addressId  如果为null，那么不检查关联地址
     * @param contactId  如果为null，那么不检查关联联系人
     * @return 关联的订单对象列表，如果没有，可返回null
     */
    List<Order> checkCorrelativeOrderByContact(String addressId,String contactId) throws ErpException;

    int queryOrderTotalCount(OrderQueryDto orderQueryDto) throws ErpException;
    /**
     * 查询订单
     * @param orderQueryDto
     * @return
     */
    List<OrderExtDto> queryOrder(OrderQueryDto orderQueryDto) throws ErpException;

    /**
     * 保存修改请求
     * @param order
     * @param address
     * @param contact
     * @param phoneList
     * @param creditCard    信用卡修改
     * @param identityCard  身份证修改
     * @param correlativeOrderList
     * @param remark
     * @return
     * @throws ErpException
     */
    Map<String,List<String>> saveOrderModifyRequest(Order order, Address address, Contact contact, List<Phone> phoneList, Card creditCard,  Card identityCard, List<String> correlativeOrderList, String remark) throws ErpException;

    /**
     * 根据修改请求来更新订单
     * @param procInstId --流程实例编号
     * @param approvUserId  --审核人Id
     * @param remark  --备注
     */
    void updateOrderFromChangeRequest(String procInstId, AuditTaskType auditTaskType, UserBpmTaskType userBpmTaskType, String orderId, String approvUserId, String remark) throws ErpException;

    /**
     * 审核ems
     * @param procInstId 流程实例Id
     * @param emsCompanyId 具体ems送货公司Id
     * @param auditTaskType 审核类型
     * @param orderId  订单号
     * @param approvUserId  审批人
     * @param remark  备注
     * @throws ErpException
     */
    void auditOrderEms(String procInstId, String emsCompanyId, AuditTaskType auditTaskType,String orderId, String approvUserId, String remark) throws ErpException;

    /**
     * 提交订单取消请求
     * @param orderId
     * @param remark
     * @param currentUsrId
     * @return 是否需要启动流程
     * @throws ErpException
     */
    boolean saveOrderCancelRequest(String orderId, String remark,String currentUsrId) throws ErpException;

    /**
     * 审核确认订单取消请求
     * @param orderId
     */
    void updateOrderCancel(String procInstId, String orderId, String remark, String currentUsrId) throws ErpException;

    /**
     * 根据订单号查询发运单信息
     * 注意：老订单没有对应的发运单，所以返回可能为null
     * @param orderId
     * @return
     */
    List<ShipmentChangeHis> queryShipmentFromOrderId(String orderId) throws ErpException;


    /**
     *  计算订单运费
     */
    BigDecimal calcOrderMailPrice(Order order, String departmentId) throws ErpException;

    /**
     *
     * @param grpId
     * @return
     */
    List<GrpOrderType> queryGrpOderType(String grpId) throws ErpException;


    /**
     * 检查订单信用卡是否有效
     * @param order
     * @return
     */
    boolean checkOrderCard(Order order) throws ErpException;

    /**
     * 查询信用卡可选分期数
     * @param cardType
     * @return
     */
    List<Amortisation> queryCardAmortisation(String cardType) throws ErpException;

    /**
     * 修改是否需要审批流程
     * @param order
     * @param address
     * @param contact
     * @param phoneList
     * @return
    boolean isNeedApprovalProcedure(Order order, Address address, Contact contact, List<Phone> phoneList) throws ErpException;*/

    Map<Order,List<String>> checkOrderRights(List<Order> orderList,String currentUsrId);

    List<OrderAuditExtDto> queryAuditOrder(OrderAuditQueryDto orderAuditQueryDto) throws ErpException;
    int queryOrderAuditTotalCount(OrderAuditQueryDto orderAuditQueryDto) throws ErpException;

    /**
     * 检查相关信息是否在走流程
     * @param addressId
     * @param contactId
     * @return
     * @throws ErpException
     */
    boolean checkCorrelativeInBmp(String addressId, String contactId) throws ErpException;

    /**
     * 创建订单
     * @param order 订单对象
     * @param newMailPrice 修改后的运费（如果运费不变化，那么可以传递null）
     * @return true-表示订单不需要审批 false-表示订单需要审批
     * @throws ErpException
     */
    boolean createOrder(Order order, BigDecimal newMailPrice) throws ErpException;

    boolean isOrderModifiable(String orderId);

    boolean isOrderDeletable(String orderId);

    boolean isOrderConsultable(String orderId);

    boolean consultOrder(String orderId) throws ErpException;

    void rejectOrderModify(String orderId, Long orderChangeId, String usrId, AuditTaskType auditTaskType, UserBpmTaskType userBpmTaskType) throws Exception;

    /**
     * 订单创建时审批结果处理
     * @param orderId  订单号
     * @param bPass   是否批准
     * @throws Exception
     */
    void auditResultOnOrderCreate(String orderId, boolean bPass, String usrId) throws ErpException;
    /**
     * 新增客户审批通过，检查相关订单
     * @param contactId 联系人
     * @param usrId  当前审批用户ID
     */
    void checkOrderByContactAudit(String contactId,String usrId) throws ErpException;

    /**
     * 订单审批通过，规则检查
     * @param order
     * @throws ErpException
     */
    void checkAuditedOrder(Order order) throws ErpException;

    void auditFinishOnOrderModify(String orderId, boolean bPass, String usrId) throws ErpException;

    boolean canAddNote(String orderId);

    void addOrderNote(String orderId, String note, Date dtCreate,String usrId, String usrName);

    List<String> queryN400ByDnis(String dnis);

    List<GrpOrderType> queryOrderType(List<String> n400List, String area);

    void cancelOrderAudit(String batchId,String orderId,String remark, String userId) throws Exception;

    OrderCreateResult createOrderExt(Order order, BigDecimal newMailPrice) throws ErpException;
}
