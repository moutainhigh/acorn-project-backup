package com.chinadrtv.erp.oms.dao;

import java.util.Date;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.OrderPaymentConfirm;
import com.chinadrtv.erp.oms.dto.OrderPaymentDto;
import com.chinadrtv.erp.oms.util.Page;

/**
 * zhangguosheng
 */
public interface OrderPaymentConfirmDao extends GenericDao<OrderPaymentConfirm, Long> {

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
	Page<OrderPaymentDto> queryOrderPayment(String orderId, String payUserName, String payUserTel, String payType, String orderDateS, String orderDateE, int index, int size);

	/**
	 * 付款信息确认
	 * @param orderPaymentConfirmId
	 * @return
	 */
	void confirm(String orderId, String payNo, String payUser, Date payDate);

}
