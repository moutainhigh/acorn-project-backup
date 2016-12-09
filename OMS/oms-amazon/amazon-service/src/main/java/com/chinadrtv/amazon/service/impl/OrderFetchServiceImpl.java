package com.chinadrtv.amazon.service.impl;

import com.amazonservices.mws.orders.MarketplaceWebServiceOrders;
import com.amazonservices.mws.orders.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders.MarketplaceWebServiceOrdersConfig;
import com.amazonservices.mws.orders.MarketplaceWebServiceOrdersException;
import com.amazonservices.mws.orders.model.*;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.amazon.model.AmazonOrderConfig;
import com.chinadrtv.amazon.service.ImportTransformService;
import com.chinadrtv.amazon.service.OrderFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderFetchServiceImpl implements OrderFetchService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderImportServiceImpl.class);

    public OrderFetchServiceImpl()
    {
    }

    @Autowired
    private ImportTransformService importTransformService;

    public List<PreTradeDto> getOrders(AmazonOrderConfig amazonOrderConfig,Date startDate, Date endDate)
    {
        try {
            MarketplaceWebServiceOrdersConfig config = new MarketplaceWebServiceOrdersConfig();
            config.setServiceURL(amazonOrderConfig.getConfigstr());
            MarketplaceWebServiceOrders service=new MarketplaceWebServiceOrdersClient(
                    amazonOrderConfig.getAccessKeyId(), amazonOrderConfig.getSecretAccessKey(),amazonOrderConfig.getApplicationName(),amazonOrderConfig.getApplicationVersion(), config );
            List<PreTradeDto> preTradeDtoList=new LinkedList<PreTradeDto>();
            return this.getOrderInfo(amazonOrderConfig, startDate, endDate, amazonOrderConfig.getTradeTypeName(),service,amazonOrderConfig.getSellerId(),amazonOrderConfig.getMarketplaceId());
            //List<PreTradeDto> preTradeDtoList2= this.getOrderInfo(amazonOrderConfig, startDate, endDate, "MFN",service,amazonOrderConfig.getSellerId(),amazonOrderConfig.getMarketplaceId());
            /*if(preTradeDtoList1!=null){
                preTradeDtoList.addAll(preTradeDtoList1);
            }*/

            //return preTradeDtoList;
        }catch(Exception e){
            logger.error("fetch amazon order error:", e);
            return null;
        }
    }



   private List<PreTradeDto> getOrderInfo(AmazonOrderConfig amazonOrderConfig, Date startTime, Date endTime, String orderState,MarketplaceWebServiceOrders service,String sellerId,String marketplaceId) throws Exception {
        try {
            ListOrdersRequest request = new ListOrdersRequest();
            request.setSellerId(sellerId);
            MarketplaceIdList marketplaceIdList = new MarketplaceIdList();
            List<String> list = new LinkedList<String>();
            list.add(marketplaceId);
            marketplaceIdList.setId(list);
            request.setMarketplaceId(marketplaceIdList);
            FulfillmentChannelList fulfilllist = new FulfillmentChannelList();

            if (orderState.equals("AFN")) {
                List<OrderStatusEnum> orderStatusEnums = new ArrayList<OrderStatusEnum>();
                fulfilllist.getChannel().add(FulfillmentChannelEnum.AFN);
                OrderStatusList startlist = new OrderStatusList();
                orderStatusEnums.add(OrderStatusEnum.SHIPPED);
                orderStatusEnums.add(OrderStatusEnum.INVOICE_UNCONFIRMED);
                startlist.setStatus(orderStatusEnums);
                request.setOrderStatus(startlist);
            } else if (orderState.equals("MFN")) {
                List<OrderStatusEnum> orderStatusEnums = new ArrayList<OrderStatusEnum>();
                fulfilllist.getChannel().add(FulfillmentChannelEnum.MFN);
                OrderStatusList startlist = new OrderStatusList();
                orderStatusEnums.add(OrderStatusEnum.UNSHIPPED);
                orderStatusEnums.add(OrderStatusEnum.PARTIALLY_SHIPPED);
                startlist.setStatus(orderStatusEnums);
                request.setOrderStatus(startlist);
            }
            request.setFulfillmentChannel(fulfilllist);

            XMLGregorianCalendar xmlstart = null, xmlend = null;
            xmlstart = convertToXMLGregorianCalendar(startTime);
            xmlend = convertToXMLGregorianCalendar(endTime);
            request.setLastUpdatedAfter(xmlstart);
            request.setLastUpdatedBefore(xmlend);
            return invokeListOrders(amazonOrderConfig, service, request);
        } catch (Exception e) {
            logger.error("read order error:", e);
        }
        return null;
    }

    /**
     * 获得卓越的订单信息
     *
     * @param service
     * @param request
     */
    private List<PreTradeDto> invokeListOrders(AmazonOrderConfig amazonOrderConfig, MarketplaceWebServiceOrders service, ListOrdersRequest request) {
        try {
            ListOrdersResponse response = service.listOrders(request);
            List<PreTradeDto> listpretrade =importTransformService.transform(amazonOrderConfig, response.toJSON());
            List<PreTradeDto> listpre = new LinkedList<PreTradeDto>();
            if (listpretrade != null) {
                for (PreTradeDto pretrade : listpretrade) {
                    ListOrderItemsRequest itemsrequest = new ListOrderItemsRequest();
                    itemsrequest.setSellerId(amazonOrderConfig.getSellerId());
                    itemsrequest.setAmazonOrderId(pretrade.getTradeId());
                    ListOrderItemsResponse itemsresponse = service.listOrderItems(itemsrequest);
                    List<PreTradeDetail> hashSet = new ArrayList<PreTradeDetail>();
                    Double shippingPrice = 0.00;// 商品的配送费用
                    Double shippingDiscount = 0.00;//商品配送费用所享折扣
                    String invoiceTitle = "";//发票标题
                    String invoiceComment = "";//发票内容
                    String invoiceRequirement = "" ;  //是否需要发票 0不需要  null/1需要发票  2特殊要求
                    for (OrderItem orderItem : itemsresponse.getListOrderItemsResult().getOrderItems().getOrderItem()) {
                        PreTradeDetail preTradeDetail = new PreTradeDetail();
                        if (orderItem.getQuantityOrdered() != 0) {
                            preTradeDetail.setSkuName(orderItem.getTitle() == null ? "" : orderItem.getTitle());//商品名称
                            //preTradeDetail.setSkuId(Long.parseLong(orderItem.getOrderItemId()== null ? "0" : orderItem.getSellerSKU()));//亚马逊所定义的订单商品编码
                            try
                            {
                                preTradeDetail.setSkuId(Long.parseLong(orderItem.getOrderItemId()== null ? "0" : orderItem.getOrderItemId()));//亚马逊所定义的订单商品编码
                            }catch (Exception exp)
                            {
                                logger.error("error order item id:"+orderItem.getOrderItemId());
                                preTradeDetail.setSkuId(0L);
                            }
                            preTradeDetail.setOutSkuId( orderItem.getSellerSKU()== null ? "" : orderItem.getSellerSKU()); //商品的卖家 SKU
                            preTradeDetail.setPrice(Double.parseDouble(orderItem.getItemPrice()== null ? "0" : orderItem.getItemPrice().getAmount()));// 商品售价
                            preTradeDetail.setQty(orderItem.getQuantityOrdered());//商品数量
                            preTradeDetail.setShippingFee(Double.parseDouble(orderItem.getShippingPrice()== null ? "0" : orderItem.getShippingPrice().getAmount())); //配送运费
                            preTradeDetail.setDiscountFee(Double.parseDouble(orderItem.getShippingDiscount()== null ? "0" : orderItem.getShippingDiscount().getAmount())); //配送费用折扣
                            hashSet.add(preTradeDetail); //添加明细信息
                            shippingPrice += preTradeDetail.getShippingFee();
                            shippingDiscount +=preTradeDetail.getDiscountFee();//配送运费折扣
                            if ("".equals(invoiceRequirement))
                            {
                                if ("MustNotSend".equals(orderItem.getInvoiceData().getInvoiceRequirement()))
                                    invoiceRequirement = "0" ;
                                else
                                {
                                    invoiceRequirement = "1" ;
                                    invoiceTitle = orderItem.getInvoiceData().getInvoiceTitle() == null ? "" : orderItem.getInvoiceData().getInvoiceTitle();//发票抬头
                                    invoiceComment = orderItem.getInvoiceData().getBuyerSelectedInvoiceCategory() == null ? "" : orderItem.getInvoiceData().getBuyerSelectedInvoiceCategory();//发票内容
                                }
                            }
                        }
                    }

                    //订单总信息
                    pretrade.setInvoiceTitle(invoiceTitle);// 买家指定的发票抬头
                    pretrade.setInvoiceComment(invoiceRequirement); //发票需求
                    pretrade.setShippingFee(shippingPrice);
                    pretrade.setDiscountFee(shippingDiscount);//折扣费用
                    pretrade.setPreTradeDetails(hashSet);  //输入明细信息
                    if (pretrade.getPreTradeDetails().size() != 0) {
                        listpre.add(pretrade);
                    }
                }
            }
            return listpre;
        } catch (MarketplaceWebServiceOrdersException ex) {
            logger.error("Caught Exception: " + ex.getMessage());
            logger.error("Response Status Code: " + ex.getStatusCode());
            logger.error("Error Code: " + ex.getErrorCode());
            logger.error("Error Type: " + ex.getErrorType());
            logger.error("Request ID: " + ex.getRequestId());
            logger.error("XML: " + ex.getXML());
            logger.error("ResponseHeaderMetadata: " + ex.getResponseHeaderMetadata());
            logger.error("marketplace web service error:",ex);
        }
        return null;
    }

    /**
     * 转换日期类型为xml格式作为卓越参数
     *
     * @param date
     * @return
     */
    public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            logger.error("generate xml date error:", e);
        }
        return gc;
    }

}
