package com.chinadrtv.erp.oms.service;

import java.io.IOException;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;

import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.trade.OrderPaymentConfirm;
import com.chinadrtv.erp.oms.dto.OrderPaymentDto;
import com.chinadrtv.erp.oms.util.Page;
import com.chinadrtv.erp.user.model.AgentUser;

/**
 * zhangguosheng
 */
public interface OrderPaymentConfirmService extends GenericService<OrderPaymentConfirm,Long> {

	/**
	 * 查询待确认的订单付款信息
	 * @param orderId 订单编号
	 * @param payUserName 订购人姓名
	 * @param payUserTel 订购人电话
	 * @param payType 付款方式
	 * @param orderDateS 订购日期左区间
	 * @param orderDateE 订购日期右区间
	 * @return 订购付款信息
	 */
	public Page<OrderPaymentDto> queryOrderPayment(String orderId, String payUserName, String payUserTel, String payType, String orderDateS, String orderDateE, int index, int size);

	/**
	 * 付款确认
	 * @param orderId
	 * @param payNo
	 * @param payUser
	 * @param payDate
	 * @return
	 */
	public void confirm(String orderId, String payNo, String payUser, Date payDate) throws BizException;

	/**
	 * 请求EMS发货
	 * @param timeS
	 * @param timeE
	 * @param requestEms
	 * @param i
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	public String omsRequestCarrierList(String timeS, String timeE, String isEms, int i, int rows) throws IOException;

	/**
	 * 审批
	 * @param ids:以逗号分隔
	 * @param orderIds:以逗号分隔
	 * @param designedEntityIds:指定送货公司以逗号分隔
	 * @param designedWarehouseIds:指定仓库以逗号分隔
	 * @param op
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public String omsRequestCarrierApproval(String ids, String orderIds, String designedEntityIds, String designedWarehouseIds, String op, String userId) throws ClientProtocolException, IOException;
	
}
