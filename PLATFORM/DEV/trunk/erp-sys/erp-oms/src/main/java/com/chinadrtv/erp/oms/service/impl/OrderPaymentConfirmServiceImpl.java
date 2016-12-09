package com.chinadrtv.erp.oms.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.trade.OrderPaymentConfirm;
import com.chinadrtv.erp.oms.dao.OrderPaymentConfirmDao;
import com.chinadrtv.erp.oms.dto.OrderPaymentDto;
import com.chinadrtv.erp.oms.service.OrderPaymentConfirmService;
import com.chinadrtv.erp.oms.service.SourceConfigure;
import com.chinadrtv.erp.oms.util.HttpUtil;
import com.chinadrtv.erp.oms.util.Page;

/**
 * zhangguosheng
 */
@Service("orderPaymentConfirmService")
public class OrderPaymentConfirmServiceImpl extends GenericServiceImpl<OrderPaymentConfirm, Long> implements OrderPaymentConfirmService {

	@Autowired
	private OrderPaymentConfirmDao orderPaymentConfirmDao;
  
	@Autowired
	private SourceConfigure sourceConfigure;
	
	@Override
	protected GenericDao<OrderPaymentConfirm, Long> getGenericDao() {
		return orderPaymentConfirmDao;
	}

	@Override
	public Page<OrderPaymentDto> queryOrderPayment(String orderId, String payUserName, String payUserTel, String payType, String orderDateS, String orderDateE, int index, int size) {
		return orderPaymentConfirmDao.queryOrderPayment(orderId, payUserName, payUserTel, payType, orderDateS, orderDateE, index, size);
	}

	@Transactional(readOnly=false)
	@Override
	public void confirm(String orderId, String payNo, String payUser, Date payDate) throws BizException{
		if(StringUtils.isEmpty(orderId)) throw new BizException("订单号不能为空！");
		if(StringUtils.isEmpty(payNo)) throw new BizException("汇款单号不能为空！");
		if(StringUtils.isEmpty(payUser)) throw new BizException("付款人不能为空！");
		if(payDate==null) throw new BizException("付款日期错误！");
		orderPaymentConfirmDao.confirm(orderId, payNo, payUser, payDate);
	}

	/**
	 * 请求EMS
	 * @throws IOException 
	 */
	@Override
	public String omsRequestCarrierList(String timeS, String timeE, String isEms, int pageNo, int pageSize) throws IOException {
		pageNo = pageNo-1;
		String p = "startDate=" + timeS + "&endDate=" + timeE + "&pageNo=" + pageNo + "&pageSize=" + pageSize + "&isEms=" + isEms;
		String jsonData = HttpUtil.getUrl(sourceConfigure.getSalseHome() + "/task/queryDistributionChangeQuery?" + p);
		return jsonData;
	}

	/**
	 * 请求ems，同意与拒绝
	 * op, 1:同意，0：拒绝
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Override
	public String omsRequestCarrierApproval(String ids, String orderIds, String designedEntityIds, String designedWarehouseIds, String op, String userId) throws ClientProtocolException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("op", op);
		params.put("ids", ids);
		params.put("orderIds", orderIds);
		params.put("designedEntityIds", designedEntityIds);
		params.put("designedWarehouseIds", designedWarehouseIds);
		params.put("userId", userId);
		String url = sourceConfigure.getSalseHome() + "/task/batchAction";
		String result = HttpUtil.postUrl(url, params);
		return result;
	}

}
