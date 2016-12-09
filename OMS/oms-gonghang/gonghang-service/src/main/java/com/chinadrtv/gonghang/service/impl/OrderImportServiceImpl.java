package com.chinadrtv.gonghang.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.payload.JavaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import com.chinadrtv.gonghang.dal.dto.OrderDetailDto;
import com.chinadrtv.gonghang.dal.dto.OrderHeaderDto;
import com.chinadrtv.gonghang.dal.dto.ResponseHeaderDto;
import com.chinadrtv.gonghang.dal.model.OrderConfig;
import com.chinadrtv.gonghang.service.OrderFeedbackService;
import com.chinadrtv.gonghang.service.OrderImportService;
import com.chinadrtv.gonghang.util.EncryptUtil;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.service.oms.PreTradeImportService;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-4-22
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OrderImportServiceImpl implements OrderImportService {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderImportServiceImpl.class);
    
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PreTradeImportService preTradeImportService;
    @Autowired
    private OrderFeedbackService orderFeedbackService;
    
    private Smooks smooks = null;
    
    /**
	 * <p>Title: </p>
	 * <p>Description: </p>
     * @throws SAXException 
     * @throws IOException 
	 */ 
	public OrderImportServiceImpl() throws IOException, SAXException {
		super();
		smooks = new Smooks("smooks/smooks-config.xml");
	}


	/** 
	 * <p>Title: input</p>
	 * <p>Description: </p>
	 * @param gongHangOrderConfigList
	 * @param startDate
	 * @param endDate
	 * @throws Exception 
	 * @see com.chinadrtv.gonghang.service.IcbcOrderInputService#input(java.util.List, java.util.Date, java.util.Date)
	 */ 
	@Override
	public void input(List<OrderConfig> configList, Date startDate, Date endDate) throws Exception {
		
		for(OrderConfig config : configList) {
			List<OrderHeaderDto> orderHeaderList = this.postOrderHeader(config, startDate, endDate);
			
			if(null == orderHeaderList || orderHeaderList.size() == 0) {
				continue;
			}
			
			PreTradeLotDto preTradeLotDto = this.postOrderDetailList(config, orderHeaderList);
			
			if(null != preTradeLotDto) {
				preTradeImportService.importPretrades(preTradeLotDto);	
			}
		}
	}
    
	/**
	 * <p>
	 * </p>
	 * @param orderIdList
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	private PreTradeLotDto postOrderDetailList(OrderConfig config, List<OrderHeaderDto> orderHeaderList) throws Exception {
		PreTradeLotDto preTradeLotDto = new PreTradeLotDto();
		List<PreTradeDto> preTradeDtoList = new ArrayList<PreTradeDto>();
		
		for(OrderHeaderDto dto : orderHeaderList) {
			PreTradeDto preTradeDto = this.postOrderDetail(config, dto);
			if (null != preTradeDto) {
				preTradeDtoList.add(preTradeDto);
			}
		}
		
		if(preTradeDtoList.size() > 0) {
			preTradeLotDto.setPreTrades(preTradeDtoList);
			return preTradeLotDto;
		}
		
		return null;
	}

	/**
	 * <p></p>
	 * @param config
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	private List<OrderHeaderDto> postOrderHeader(OrderConfig config, Date startDate, Date endDate) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String method = "icbcb2c.order.list";
		String reqData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						 "<body>" +
						 "<create_start_time>" + sdf.format(startDate) + "</create_start_time>" +
						 "<create_end_time>" + sdf.format(endDate) + "</create_end_time>" +
						 "<modify_time_from></modify_time_from>" +
						 "<modify_time_to></modify_time_to>" +
						 "<order_status>02</order_status>" +
						 "</body>";
		
		String requestUrl = EncryptUtil.generateRequestUrl(config, method, reqData);

		logger.debug(requestUrl);
		
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity(requestUrl, new Object(), String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(null ==response || response.getStatusCode().value() != 200) {
    		throw new Exception("request ICBC web service error!");
    	}
		
		String content = new String(response.getBody().getBytes("ISO-8859-1"), "UTF-8");
		logger.debug(content);
		
		List<OrderHeaderDto> orderHeaderList = this.orderHeaderAdapter(content);
		
		return orderHeaderList;
	}

	/**
	 * <p></p>
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private List<OrderHeaderDto> orderHeaderAdapter(String content) throws Exception {
		List<OrderHeaderDto> orderHeaderList = new ArrayList<OrderHeaderDto>();
		
		ExecutionContext executionContext = smooks.createExecutionContext();
		JavaResult result = new JavaResult();

		ByteArrayInputStream baInputStream = new ByteArrayInputStream(content.getBytes());
		smooks.filterSource(executionContext, new StreamSource(baInputStream), result);

		ResponseHeaderDto responseHeader = (ResponseHeaderDto) result.getBean("responseHeader");
		if(null == responseHeader || null == responseHeader.getRetMsg() || !"ok".equalsIgnoreCase(responseHeader.getRetMsg())) {
			throw new Exception("post ICBC service failed.");
		}
		orderHeaderList = (List<OrderHeaderDto>) result.getBean("orderIdList");	            
		
		return orderHeaderList;
	}

	/**
	 * <p></p>
	 * @param content
	 * @return
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	private PreTradeDto postOrderDetail(OrderConfig config, OrderHeaderDto dto) throws Exception {
		
		String method = "icbcb2c.order.detail";
		String reqData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						 "<body>" +
						 "<order_ids>" + dto.getOrderId() + "</order_ids>" +
						 "</body>";
		
		String requestUrl = EncryptUtil.generateRequestUrl(config, method, reqData);

		logger.debug(requestUrl);
		
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity(requestUrl, new Object(), String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(null ==response || response.getStatusCode().value() != 200) {
    		throw new Exception("request ICBC web service error!");
    	}
		
		String content = new String(response.getBody().getBytes("ISO-8859-1"), "UTF-8");
		logger.debug(content);
		
		PreTradeDto preTradeDto = this.preTradeDtoAdapter(config, content);
		
		return preTradeDto;
	}

	/**
	 * <p></p>
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	private PreTradeDto preTradeDtoAdapter(OrderConfig config, String content) throws Exception {
		ExecutionContext executionContext = smooks.createExecutionContext();
		JavaResult result = new JavaResult();

		ByteArrayInputStream baInputStream = new ByteArrayInputStream(content.getBytes());
		smooks.filterSource(executionContext, new StreamSource(baInputStream), result);

		ResponseHeaderDto responseHeader = (ResponseHeaderDto) result.getBean("responseHeader");
		if(null == responseHeader || null == responseHeader.getRetMsg() || !"ok".equalsIgnoreCase(responseHeader.getRetMsg())) {
			throw new Exception("post ICBC service failed.");
		}
		
		OrderHeaderDto orderHeaderDto = (OrderHeaderDto) result.getBean("orderHeaderDto");
		
		if(null == orderHeaderDto) {
			return null;
		}
		PreTradeDto preTradeDto = this.preTradeDtoAdapter(config, orderHeaderDto);
		
		return preTradeDto;
	}

	/**
	 * <p></p>
	 * @param orderHeaderDto
	 * @return
	 */
	private PreTradeDto preTradeDtoAdapter(OrderConfig config, OrderHeaderDto orderHeaderDto) {
		StringBuffer sb = new StringBuffer();
		//Double discountFee = 0D;
		
		PreTradeDto preTradeDto = new PreTradeDto();
	
		preTradeDto.setBuyerId(orderHeaderDto.getOrderBuyerId());
		preTradeDto.setBuyerMessage(orderHeaderDto.getOrderBuyerRemark());
		preTradeDto.setBuyerNick(orderHeaderDto.getOrderBuyerName());
		
		String invoiceTitle = orderHeaderDto.getInvoiceTitle();
		String invoiceComment = "";
		if(null != invoiceTitle && invoiceTitle.equals("无")) {
			invoiceComment = "0";
		}else{
			invoiceComment = "2";
		}
		preTradeDto.setInvoiceComment(invoiceComment);
		preTradeDto.setInvoiceTitle(invoiceTitle);
		
		preTradeDto.setMailType(orderHeaderDto.getOrderChannel());
		preTradeDto.setTradeId(orderHeaderDto.getOrderId());
		preTradeDto.setOpsTradeId(orderHeaderDto.getOrderId());
		preTradeDto.setOutCrdt(orderHeaderDto.getOrderCreateTime());
		
		preTradeDto.setReceiverAddress(orderHeaderDto.getConsigneeAddress());
		preTradeDto.setReceiverCity(orderHeaderDto.getConsigneeCity());
		preTradeDto.setReceiverCounty(orderHeaderDto.getConsigneeDistrict());
		preTradeDto.setReceiverMobile(orderHeaderDto.getMobile());
		preTradeDto.setReceiverName(orderHeaderDto.getConsigneeName());
		preTradeDto.setReceiverPhone(orderHeaderDto.getPhone());
		preTradeDto.setReceiverPostCode(orderHeaderDto.getZipcode());
		preTradeDto.setReceiverProvince(orderHeaderDto.getConsigneeProvince());
		preTradeDto.setSellerMessage(orderHeaderDto.getOrderBuyerRemark());
		
		Double orderAmount = orderHeaderDto.getOrderAmount().doubleValue();
		BigDecimal orderOtherDiscount = orderHeaderDto.getOrderOtherDiscount();
		
		if(null != orderHeaderDto.getOrderCouponAmount() && 0 != orderHeaderDto.getOrderCouponAmount().doubleValue()) {
			sb.append("电子券抵扣" + orderHeaderDto.getOrderCouponAmount().doubleValue() + "; ");
		}
		if(null != orderHeaderDto.getOrderCreditAmount() && 0 != orderHeaderDto.getOrderCreditAmount().doubleValue()) {
			sb.append("积分抵扣" + orderHeaderDto.getOrderCreditAmount() + "; ");
		}
		if(null != orderOtherDiscount && orderOtherDiscount.doubleValue() != 0) {
			sb.append("其它优惠" + orderOtherDiscount.doubleValue() + "; ");
			orderAmount -= orderOtherDiscount.doubleValue();
		}
		preTradeDto.setPayment(orderAmount);
		preTradeDto.setShippingFee(orderHeaderDto.getOrderFreight().doubleValue());

		preTradeDto.setCustomerId(config.getCustomerId());
		preTradeDto.setSourceId(config.getSourceId());
		preTradeDto.setTradeType(config.getTradeType());
		preTradeDto.setTradeFrom(config.getTradeFrom());
		preTradeDto.setTmsCode(config.getTmsCode());
		
		sb.append("可开票金额" + orderHeaderDto.getOrderPayAmount().doubleValue());
		preTradeDto.setBuyerMessage(sb.toString());
		
		List<PreTradeDetail> preTradeDetails = new ArrayList<PreTradeDetail>();
		
		for(OrderDetailDto orderDetailDto : orderHeaderDto.getOrderDetails()) {
			PreTradeDetail pd = new PreTradeDetail();
			
			BigDecimal qty =  new BigDecimal(orderDetailDto.getProductNumber());
			BigDecimal payment = (BigDecimal) (null == orderDetailDto.getProductPrice() ? 0 :orderDetailDto.getProductPrice().multiply(qty));
			
			pd.setTradeId(orderHeaderDto.getOrderId());
			pd.setPayment(payment.doubleValue());
			pd.setPrice(null == orderDetailDto.getProductPrice() ? 0 : orderDetailDto.getProductPrice().doubleValue());
			pd.setUpPrice(orderDetailDto.getProductPrice().doubleValue() - orderDetailDto.getProductDiscount().doubleValue());
			pd.setQty(orderDetailDto.getProductNumber());
			pd.setSkuId(Long.parseLong(orderDetailDto.getProductSkuId()));
			pd.setOutSkuId(orderDetailDto.getProductCode());
			pd.setSkuName(orderDetailDto.getProductName());
			pd.setDiscountFee(orderDetailDto.getProductDiscount().doubleValue());
			
			preTradeDetails.add(pd);
		}
		
		preTradeDto.setPreTradeDetails(preTradeDetails);
		
		return preTradeDto;
	}


}
