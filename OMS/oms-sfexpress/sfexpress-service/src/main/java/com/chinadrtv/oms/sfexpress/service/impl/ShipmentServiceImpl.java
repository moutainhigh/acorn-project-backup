package com.chinadrtv.oms.sfexpress.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.stream.StreamSource;

import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.payload.JavaResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import com.chinadrtv.oms.sfexpress.dal.dao.ShipExpressInfoDao;
import com.chinadrtv.oms.sfexpress.dal.dao.WmsShipmentHeaderDao;
import com.chinadrtv.oms.sfexpress.dal.model.ShipExpressInfo;
import com.chinadrtv.oms.sfexpress.dal.model.WmsShipmentDetail;
import com.chinadrtv.oms.sfexpress.dto.ResponseHeaderDto;
import com.chinadrtv.oms.sfexpress.dto.ResponseOrderDto;
import com.chinadrtv.oms.sfexpress.service.ShipmentService;
import com.chinadrtv.service.util.PojoUtils;
import com.sf.integration.expressservice.service.IService;
import com.sf.integration.expressservice.service.IServiceProxy;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-2-13
 * Time: 下午5:13
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ShipmentServiceImpl implements ShipmentService {

    private static Logger logger  = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    @Value("${appKey}")
    private String appKey;
    @Value("${appSecret}")
    private String appSecret;
    @Value("${apiUrl}")
    private String apiUrl;
    @Value("${custId}")
    private String custId;

    @Autowired
    private WmsShipmentHeaderDao wmsShipmentHeaderDao;
    @Autowired
    private ShipExpressInfoDao shipExpressInfoDao;
    @Autowired
    private RestTemplate restTemplate;
    
    private Smooks smooks = null;
    
	
	public ShipmentServiceImpl() throws IOException, SAXException {
		smooks = new Smooks("smooks/smooks-config.xml");
	}
    
    @Override
    public void createWaybill() throws Exception {
    	
		// 从WMS库获取推送数据
		List<Map<String, Object>> wmsShipmentHeaders = wmsShipmentHeaderDao.findShipmentHeader();
		logger.info("get wmsShipmentHeaders size:" + wmsShipmentHeaders.size());
		if (wmsShipmentHeaders != null && wmsShipmentHeaders.size() > 0) {
			for (Map<String, Object> ws : wmsShipmentHeaders) { 
				this.postCreateWaybill(ws);
			}
		}
    }
    
    /**
	 * <p></p>
     * @throws Exception 
	 */
	private void postCreateWaybill(Map<String, Object> headerMap) throws Exception {
		
		logger.info(headerMap+" appKey: " + appKey + " appSecret: " + appSecret + " apiUrl: " + apiUrl);
		
        String orderid = null == headerMap.get("orderid") ? "" : headerMap.get("orderid").toString();
        String carrier = null == headerMap.get("carrier") ? "" : headerMap.get("carrier").toString();
        String express_type = null == headerMap.get("express_type") ? "" : headerMap.get("express_type").toString();
        String d_company = null == headerMap.get("d_company") ? "" : headerMap.get("d_company").toString();
        String d_contact = null == headerMap.get("d_contact") ? "" : headerMap.get("d_contact").toString();
        String d_tel = null == headerMap.get("d_tel") ? "" : headerMap.get("d_tel").toString();
        String d_mobile = null == headerMap.get("d_mobile") ? "" : headerMap.get("d_mobile").toString();
        String d_address = null == headerMap.get("d_address") ? "" : headerMap.get("d_address").toString();
     
        d_address = d_address.replaceAll("-", " ");
        d_address = d_address.replaceAll("<", "");
        d_address = d_address.replaceAll(">", "");
        d_address = d_address.replaceAll("&", "");
        d_address = d_address.replaceAll(",", "，");
        d_address = d_address.replaceAll("&", "");
        d_address = d_address.replaceAll("'", "");
        d_address = d_address.replaceAll("`", "");
        d_address = d_address.replaceAll("@", "");
        d_address = d_address.replaceAll("$", "");
        d_address = d_address.replaceAll("%", "");
        d_address = d_address.replaceAll("^", "");
        d_address = d_address.replaceAll("\\*", "");
        d_address = d_address.replaceAll("\\\\", "");
        d_address = d_address.replaceAll("/", "");
        
        String parcel_quantity = null == headerMap.get("parcel_quantity") ? "" : headerMap.get("parcel_quantity").toString();
        String remark = null == headerMap.get("remark") ? "" : headerMap.get("remark").toString();
        String pay_method = null == headerMap.get("pay_method") ? "" : headerMap.get("pay_method").toString();
       
        //重复下单检查
        ShipExpressInfo sei = (ShipExpressInfo) shipExpressInfoDao.queryObjectByPK(orderid);
        if(null != sei && null != sei.getShipmentId()) {
        	try {
        		Map<String, Object> params = new HashMap<String, Object>();
				params.put("mailNo", sei.getMailNo());
				params.put("shipmentId", orderid);
				wmsShipmentHeaderDao.updateShipmentHeader(params);
			} catch (Exception e) {
				logger.error("sign shipment_header status failed.", e);
			}
        	return;
        }
        
		//获取订单明细
        List<WmsShipmentDetail> shipmentDetailList = wmsShipmentHeaderDao.findDetails(orderid);
        
        String address = "";
        if(carrier.contains("华新顺丰")) {
        	address = "上海市青浦区华卫路18号";
        }else if(carrier.contains("闵行顺丰")) {
        	address = "上海市闵行区向阳路201弄55号";
        }
        
        StringBuffer sb = new StringBuffer();
        sb.append("<Request service=\"OrderService\" lang=\"zh-CN\">");
        sb.append("<Head>" + appKey + "," + appSecret + "</Head>");
        sb.append("<Body>");
        sb.append("<Order");
        sb.append(" orderid=\"" + orderid + "\"");
        sb.append(" express_type=\"" + express_type + "\"");
        sb.append(" custid=\"" + custId + "\"");
        sb.append(" j_company=\"橡果\"");
        sb.append(" j_contact=\"橡果\"");
        sb.append(" j_tel=\"400-6668888\"");
        sb.append(" j_address=\"" + address + "\"");
        
        sb.append(" d_company=\"" + d_company + "\"");
        sb.append(" d_contact=\"" + d_contact + "\"");
        
        
        sb.append(" d_tel=\"" + d_tel + "\"");
        sb.append(" d_mobile=\"" + d_mobile + "\"");
        sb.append(" d_address=\"" + d_address + "\"");
        sb.append(" parcel_quantity=\"" + parcel_quantity + "\"");
        sb.append(" pay_method=\"" + pay_method + "\"");
        sb.append(" remark=\"" + remark + "\"");
        sb.append(" />");
        
        for(WmsShipmentDetail wsd : shipmentDetailList) {
        	sb.append("<Cargo name=\"" + wsd.getKits() +"\" count=\"" + wsd.getTotalQty() + "\"></Cargo>");	
        }
        
        sb.append("<Order>");
        sb.append("</Body>");
        sb.append("</Request>");
        
        logger.info("call url : " + apiUrl);
        
        logger.info("call data : " + sb.toString());
        
        String response = null;
		IServiceProxy proxy = new IServiceProxy();
		proxy.setEndpoint(apiUrl);

		IService iservice = proxy.getIService();
		
		try {
			response = iservice.sfexpressService(sb.toString());
		} catch (RemoteException e) {
			logger.error("call sf web service failed : ", e);
		}
		
		logger.info("response data : " + response);
		
		this.processResponseResult(response, orderid);
	}

	
    //处理响应消息
    private void processResponseResult(String xml,String orderId) throws Exception{
    	
    	ExecutionContext executionContext = smooks.createExecutionContext();
		JavaResult result = new JavaResult();

		ByteArrayInputStream baInputStream = new ByteArrayInputStream(xml.getBytes());
		smooks.filterSource(executionContext, new StreamSource(baInputStream), result);

		ResponseHeaderDto responseDto = (ResponseHeaderDto) result.getBean("responseHeaderDto");
		if(null == responseDto || null == responseDto.getHead() || !"ok".equalsIgnoreCase(responseDto.getHead())) {
			if(null != responseDto.getError() && "重复下单".equals(responseDto.getError())) {
				try {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("mailNo", "-1");
					params.put("shipmentId", orderId);
					wmsShipmentHeaderDao.updateShipmentHeader(params);
				} catch (Exception e) {
					logger.error("sign shipment_header status failed.", e);
				}
			}else{
				logger.error("parse response failed: " + responseDto.getError());	
			}
		}else{
			//processBizLogic(orderId);
	    	
			ShipExpressInfo shipExpressInfo = null;
			
			try {
				ResponseOrderDto responseOrderDto = (ResponseOrderDto) result.getBean("responseOrderDto");
				
				shipExpressInfo = this.adapter(responseOrderDto);
				
				shipExpressInfoDao.insertData(shipExpressInfo);
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("mailNo", shipExpressInfo.getMailNo());
				params.put("shipmentId", orderId);
				
				wmsShipmentHeaderDao.updateShipmentHeader(params);
			} catch (Exception e) {
				logger.error("insert shipExpressInfo " + shipExpressInfo + " error : ", e);
			}
		}
    }

	/**
	 * <p>与顺风合作没有开通查询接口</p>
	 * @param shipmentId
	 * @return
	 */
    @Deprecated
	private void processBizLogic(String orderId) {
		StringBuffer sb = new StringBuffer();
        sb.append("<Request service=\"OrderService\" lang=\"zh-CN\">");
        sb.append("<Head>" + appKey + "," + appSecret + "</Head>");
        sb.append("<Body>");
        sb.append("<OrderSearch orderid=\"" + orderId + "\" ></OrderSearch>");
        sb.append("</Body>");
        sb.append("</Request>");
        
        logger.info("call url: " + apiUrl);
        logger.info("call data: " + sb.toString());
        
		String response = null;
		IServiceProxy proxy = new IServiceProxy();
		proxy.setEndpoint(apiUrl);

		IService iservice = proxy.getIService();

		try {
			response = iservice.sfexpressService(sb.toString());
		} catch (RemoteException e) {
			logger.error("call sf web service failed : ", e);
			return;
		}

		logger.info("response data : " + response);
		
		ExecutionContext executionContext = smooks.createExecutionContext();
		JavaResult queryResult = new JavaResult();

		ByteArrayInputStream responseBaInputStream = new ByteArrayInputStream(response.getBytes());
		smooks.filterSource(executionContext, new StreamSource(responseBaInputStream), queryResult);

		ResponseHeaderDto queryResponseDto = (ResponseHeaderDto) queryResult.getBean("responseHeaderDto");
		//ResponseOrderDto queryResponseOrderDto = (ResponseOrderDto) queryResult.getBean("responseOrderDto");
		if(null != queryResponseDto && null != queryResponseDto.getHead() && "ok".equalsIgnoreCase(queryResponseDto.getHead())){
			return;
		}
		
		ShipExpressInfo shipExpressInfo = null;
		
		try {
			ResponseOrderDto responseOrderDto = (ResponseOrderDto) queryResult.getBean("responseOrderDto");
			
			shipExpressInfo = this.adapter(responseOrderDto);
			
			shipExpressInfoDao.insertData(shipExpressInfo);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mailNo", shipExpressInfo.getMailNo());
			params.put("shipmentId", orderId);
			wmsShipmentHeaderDao.updateShipmentHeader(params);
		} catch (Exception e) {
			logger.error("insert shipExpressInfo " + shipExpressInfo + " error : ", e);
		}
	}

	/**
	 * <p></p>
	 * @param responseDto
	 * @return ShipExpressInfo
	 */
	private ShipExpressInfo adapter(ResponseOrderDto responseOrderDto) {
		ShipExpressInfo sei = (ShipExpressInfo) PojoUtils.convertDto2Pojo(responseOrderDto, ShipExpressInfo.class);
		sei.setShipmentId(responseOrderDto.getOrderId());
		return sei;
	}

}
