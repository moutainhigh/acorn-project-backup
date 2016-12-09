package com.chinadrtv.yhd.service.impl;

import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.service.oms.PreTradeImportService;
import com.chinadrtv.yhd.model.YhdOrderConfig;
import com.chinadrtv.yhd.service.YhdOrderInputService;
import com.yhd.YhdClient;
import com.yhd.object.order.*;
import com.yhd.request.order.OrdersDetailGetRequest;
import com.yhd.request.order.OrdersGetRequest;
import com.yhd.response.order.OrdersDetailGetResponse;
import com.yhd.response.order.OrdersGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-3-20
 * Time: 上午10:37
 * To change this template use File | Settings | File Templates.
 */
@Service("yhdOrderInputService")
public class YhdOrderInputServiceImpl implements YhdOrderInputService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(YhdOrderInputServiceImpl.class);
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private PreTradeImportService preTradeImportService;
    /**
     * 1号店 前置订单导入
     * @param yhdOrderConfigList
     * @param startDate
     * @param endDate
     */
    @Override
    public void input(List<YhdOrderConfig> yhdOrderConfigList,Date startDate,Date endDate) {
        //获取前置订单信息
        List<PreTradeDto> orderList =new ArrayList<PreTradeDto>();
        for(YhdOrderConfig yhdOrderConfig : yhdOrderConfigList){
               List<PreTradeDto> preTradeList = this.fetchOrders(yhdOrderConfig,startDate,endDate);
               if(preTradeList != null && preTradeList.size() > 0){
                    orderList.addAll(preTradeList);
               }
        }
        logger.info("get orderList size:"+orderList.size());
        //导入到前置订单
        if(orderList!=null && orderList.size()>0)
        {
            logger.info("preTradeImportService import begin......");
            PreTradeLotDto preTradeLotDto = new PreTradeLotDto();
            preTradeLotDto.setPreTrades(orderList);
            preTradeImportService.importPretrades(preTradeLotDto);
            logger.info("preTradeImportService import end......");
        }

    }

    /**
     * 获取订单信息
     * @param yhdOrderConfig
     * @param startDate
     * @param endDate
     * @return
     */
    private List<PreTradeDto> fetchOrders(YhdOrderConfig yhdOrderConfig,Date startDate,Date endDate){
           List<PreTradeDto> preTradeList = new ArrayList<PreTradeDto>();
           PreTradeDto preTradeDto = null;
           try{
               //请求订单数据
               YhdClient yhd = new YhdClient(yhdOrderConfig.getUrl(),yhdOrderConfig.getAppkey(),yhdOrderConfig.getAppSecret());
               OrdersGetRequest request = new OrdersGetRequest();
               request.setStartTime ( df.format(startDate));
               request.setOrderStatusList ( yhdOrderConfig.getOrderState()); // 订单状态（逗号分隔）
               request.setEndTime ( df.format(endDate));
               OrdersGetResponse response = yhd.excute(request,yhdOrderConfig.getSessionKey());
               if(response == null ){
                   logger.info("response is null !!!");
                    return preTradeList;
               }

               int totalCount = response.getTotalCount();
               if(totalCount > 0){
                   List<Order> orderList = response.getOrderList().getOrder();

                   for(Order order : orderList){
                       //获取订单明细
                       OrdersDetailGetRequest requestDetail = new OrdersDetailGetRequest();
                       requestDetail.setOrderCodeList (order.getOrderCode()); //订单编码列表（逗号分隔）,最大长度为50
                       OrdersDetailGetResponse responseDetail = yhd.excute(requestDetail, yhdOrderConfig.getSessionKey());

                       int detailTotalCount = responseDetail.getTotalCount();
                       if(detailTotalCount > 0){
                           List<OrderInfo> orderInfoList = responseDetail.getOrderInfoList().getOrderInfo();
                           if(orderInfoList.size() > 0){
                               for(OrderInfo orderInfo : orderInfoList){
                                   //取订单明细
                                   OrderDetail orderDetail = orderInfo.getOrderDetail();
                                   preTradeDto = new PreTradeDto();
                                   logger.info("transformPreTrade and transformPreTradeDetail begin......");
                                   preTradeDto = this.transformPreTrade(orderDetail,preTradeDto,yhdOrderConfig);
                                   //取商品明细
                                   List<PreTradeDetail> preTradeDetailList = this.transformPreTradeDetail(orderInfo,orderDetail.getOrderCode());
                                   logger.info("transformPreTrade and transformPreTradeDetail end......");
                                   preTradeDto.setPreTradeDetails(preTradeDetailList);
                               }
                               preTradeList.add(preTradeDto);
                           }
                       }
                   }
               }

           }catch (Exception e){
               logger.error("get order Info Exception:",e);
           }
           return preTradeList;
    }

    /**
     * 订单表头
     * @param orderDetail
     * @param preTradeDto
     * @param yhdOrderConfig
     * @return
     * @throws Exception
     */
    private PreTradeDto transformPreTrade(OrderDetail orderDetail,PreTradeDto preTradeDto,YhdOrderConfig yhdOrderConfig) throws Exception{
        logger.info("order trade_id:"+orderDetail.getOrderCode());
        preTradeDto.setTradeId(orderDetail.getOrderCode());
        preTradeDto.setOpsTradeId(orderDetail.getOrderCode());

        String orderCreateTime =  orderDetail.getOrderCreateTime();
        preTradeDto.setCrdt(df.parse(orderCreateTime));
        preTradeDto.setCustomerId(yhdOrderConfig.getCustomerId());
        preTradeDto.setTradeType(yhdOrderConfig.getTradeType());
        preTradeDto.setTradeFrom("YIHAODIAN");
        preTradeDto.setTmsCode(yhdOrderConfig.getTmsCode());
        String orderPaymentConfirmDate = orderDetail.getOrderPaymentConfirmDate();
        preTradeDto.setOutCrdt(df.parse(orderPaymentConfirmDate));
        //发票需求
        int needInvoice = orderDetail.getOrderNeedInvoice();
        if(needInvoice == 0){
            preTradeDto.setInvoiceComment("0");
            preTradeDto.setInvoiceTitle("不需要发票");
        }if(needInvoice == 1 || needInvoice == 2){
            preTradeDto.setInvoiceComment("1");
            preTradeDto.setInvoiceTitle(orderDetail.getInvoiceTitle());
        }if(needInvoice == 3){
            preTradeDto.setInvoiceComment("2");
            preTradeDto.setInvoiceTitle(orderDetail.getInvoiceTitle());
        }
        preTradeDto.setBuyerId(String.valueOf(orderDetail.getEndUserId()));
        preTradeDto.setReceiverName(orderDetail.getGoodReceiverName());
        preTradeDto.setReceiverProvince(orderDetail.getGoodReceiverProvince());
        preTradeDto.setReceiverCity(orderDetail.getGoodReceiverCity());
        preTradeDto.setReceiverCounty(orderDetail.getGoodReceiverCounty());
        preTradeDto.setReceiverAddress(orderDetail.getGoodReceiverAddress());
        preTradeDto.setReceiverMobile(orderDetail.getGoodReceiverMoblie());
        preTradeDto.setReceiverPhone(orderDetail.getGoodReceiverPhone());
        preTradeDto.setShippingFee(orderDetail.getOrderDeliveryFee());
        preTradeDto.setPayment(orderDetail.getOrderAmount());
        double couponDiscount = orderDetail.getOrderCouponDiscount();
        double promotionDiscount = orderDetail.getOrderPromotionDiscount();
        double platformDiscount = orderDetail.getOrderPlatformDiscount();
        preTradeDto.setDiscountFee(couponDiscount+promotionDiscount+platformDiscount);
        preTradeDto.setSourceId(Long.parseLong(String.valueOf(yhdOrderConfig.getSourceId())));
        preTradeDto.setPaytype(String.valueOf(orderDetail.getPayServiceType()));

        return preTradeDto;
    }

    /**
     * 订单明细
     * @param orderInfo
     * @param orderCode
     * @return
     * @throws Exception
     */
    private List<PreTradeDetail> transformPreTradeDetail(OrderInfo orderInfo,String orderCode) throws Exception{
        List<OrderItem> orderItemList = orderInfo.getOrderItemList().getOrderItem();
        List<PreTradeDetail> preTradeDetailList = new ArrayList<PreTradeDetail>();
        for(OrderItem orderItem : orderItemList){
            PreTradeDetail preTradeDetail = new PreTradeDetail();
            preTradeDetail.setTradeId(orderCode);
            preTradeDetail.setSkuId(orderItem.getId());
            preTradeDetail.setOutSkuId(orderItem.getOuterId());
            preTradeDetail.setQty(orderItem.getOrderItemNum());
            preTradeDetail.setSkuName(orderItem.getProductCName());
            preTradeDetail.setPrice(orderItem.getOriginalPrice());
            preTradeDetail.setUpPrice(orderItem.getOrderItemPrice());
            //分摊金额
            double d1 = orderItem.getDeliveryFeeAmount();
            double d2 = orderItem.getPromotionAmount();
            double d3 = orderItem.getCouponAmountMerchant();
            double d4 = orderItem.getCouponPlatformDiscount();
            preTradeDetail.setDiscountFee(d1+d2+d3+d4);
            preTradeDetail.setIsActive(true);

            preTradeDetailList.add(preTradeDetail);
        }
        return preTradeDetailList;
    }

}
