package com.chinadrtv.taobao.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.chinadrtv.taobao.common.util.BASE64;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.model.TradeModel;
import com.chinadrtv.taobao.service.OrderFetchService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TimeGetRequest;
import com.taobao.api.response.TimeGetResponse;

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

    /*private String format;
    private int connectTimeout;
    private int readTimeout;
    private long pageSize;
    private int adjustAmount;*/
    private String orderStatus;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${taobao_cloud_url}")
    private  String cloudUrl;

    public OrderFetchServiceImpl()
    {
        /*this.format = com.taobao.api.Constants.FORMAT_XML;
        this.connectTimeout = 1000 * 30;
        this.readTimeout = 1000 * 30;
        this.pageSize = 50L;
        this.adjustAmount = 300;*/
        orderStatus="WAIT_SELLER_SEND_GOODS"; //WAIT_SELLER_SEND_GOODS
    }

    @Override
    public Date getServerTime(TaobaoOrderConfig taobaoOrderConfig) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, -60);
        Date time = cal.getTime();

        TaobaoClient client = new DefaultTaobaoClient(taobaoOrderConfig.getUrl(), taobaoOrderConfig.getAppkey(), taobaoOrderConfig.getAppSecret());
        TimeGetRequest req = new TimeGetRequest();
        try {
            TimeGetResponse response = client.execute(req);
            if (response.isSuccess()) {
                cal.setTime(response.getTime());
                time = cal.getTime();
            }
        } catch (ApiException e) {
        }
        return time;
    }

    public List<String> getOrders(TaobaoOrderConfig taobaoOrderConfig,Date startDate, Date endDate) throws Exception
    {
        List<DateSection> dateSectionList=this.splitDateByDay(startDate,endDate);
        if(dateSectionList!=null&&dateSectionList.size()>0)
        {
            List<String> list=new ArrayList<String>();
            for(DateSection dateSection:dateSectionList)
            {
                list.addAll(this.getTradeList(taobaoOrderConfig, dateSection.getBeginDate(), dateSection.getEndDate(), orderStatus));
            }
            return list;
        }
        else
        {
            logger.warn("input date is error:begin-"+startDate+"-end-"+endDate);
            return null;
        }
    }

    private class DateSection
    {
        public DateSection(Date dtBegin,Date dtEnd)
        {
            beginDate=dtBegin;
            endDate=dtEnd;
        }

        public Date getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(Date beginDate) {
            this.beginDate = beginDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        private Date beginDate;
        private Date endDate;
    }
    private List<DateSection> splitDateByDay(Date startDate, Date endDate)
    {
        if(startDate.compareTo(endDate)>=0)
            return null;
        List<DateSection> dateSectionList=new ArrayList<DateSection>();
        do{
            Date dt= DateUtils.addDays(startDate, 1);
            if(dt.compareTo(endDate)>0)
            {
                dt=endDate;
            }
            DateSection dateSection=new DateSection(startDate,dt);
            dateSectionList.add(dateSection);
            startDate=dt;
        }while (startDate.compareTo(endDate)<0);
        return dateSectionList;
    }

    private List<String> getTradeList(TaobaoOrderConfig taobaoOrderConfig, Date startModified, Date endModified, String status) throws Exception{
        TradeModel tradeModel = new TradeModel();
        tradeModel.setTaobaoOrderConfig(taobaoOrderConfig);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
        tradeModel.setStartModified( simpleDateFormat.format(startModified));
        tradeModel.setEndModified(simpleDateFormat.format(endModified));

        tradeModel.setStatus(status);

        HttpEntity request = getHttpEntity(tradeModel);
        logger.info("获取交易明细","getTradeList : "+simpleDateFormat.format(new Date()));

        List<String> trades = null;
        
        ResponseEntity<List> response = null;
        
        try {
			response = restTemplate.postForEntity(cloudUrl+"/getTradeList", request, List.class, request);
		} catch (RestClientException e) {
			logger.error("post error", e);
		}
        
        if(null ==response || response.getStatusCode().value() != 200) {
    		throw new Exception("post url error");
    	}
        
        trades = response.getBody();
        //List<String> trades = restTemplate.postForObject(cloudUrl+"/getTradeList", request, List.class);

        if(trades!=null){
            logger.info("获取交易明细数量","明细数量: "+trades.size());

            return decryptTrades(trades);
        }
        logger.info("获取交易明细数量为空","");
        return new ArrayList<String>();
    }

    private List<String> decryptTrades(List<String> trades) {
        if(trades==null || trades.isEmpty()){
            return trades;
        }
        List<String> list = new ArrayList<String>();
        for(String trade:trades){
            list.add(BASE64.DecryptDES(trade));
        }

        return list;
    }

    private HttpEntity getHttpEntity(Object obj) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        String JSONInput = JSONObject.fromObject(obj).toString();
        
        logger.info(JSONInput);
        
        return new HttpEntity( JSONInput, headers );
    }


    /*private List<String> getTradeList(TaobaoOrderConfig taobaoOrderConfig, Date startModified, Date endModified, String status){
        List<String> trades = new ArrayList<String>();
        String tradeId = "";
        String strXml = "";

        long pageNo = 1L;

        TaobaoClient client = new DefaultTaobaoClient(taobaoOrderConfig.getUrl(), taobaoOrderConfig.getAppkey(), taobaoOrderConfig.getAppSecret(), format, connectTimeout, readTimeout);
        TradesSoldIncrementGetRequest req = new TradesSoldIncrementGetRequest();
        req.setFields("tid");
        req.setStartModified(startModified);
        req.setEndModified(endModified);
        if (status != null) {
            req.setStatus(status);
        }
        req.setPageSize(pageSize);
        req.setUseHasNext(true); // true 效率高, 不返回totalResults
        req.setIsAcookie(false);

        try
        {
            TradesSoldIncrementGetResponse response = null;
            do {
                req.setPageNo(pageNo);
                response = client.execute(req, taobaoOrderConfig.getSessionKey());
                if (response.isSuccess()) {
                    if ((response.getTrades() != null) && (!response.getTrades().isEmpty())) {
                        for (Trade trade : response.getTrades()) {
                            strXml = this.getTradeInfo(client,taobaoOrderConfig.getSessionKey(), trade.getTid().toString(), status);
                            if (strXml != null)
                                trades.add(strXml);
                        }
                    }
                } else {
                    logger.error("fetch order response error: code-"+response.getErrorCode()+"-msg-"+response.getMsg()+"-submsg-"+response.getSubMsg());

                    return trades;
                    //throw new RuntimeException(response.getErrorCode()+":"+response.getMsg());
                }
                pageNo++;
            } while ((response != null) && (response.getHasNext()));
        }catch (Exception exp)
        {
            logger.error("fetch order error:",exp);
            return trades;
            //throw new RuntimeException(exp.getMessage(),exp);
        }

        return trades;
    }

    private String getTradeInfo(TaobaoClient client, String sessionKey, String tradeNo, String status) throws Exception {
        String strXml = null;

        *//*if (client == null){
            client = new DefaultTaobaoClient(url, appKey, appSecret, format, connectTimeout, readTimeout);
        }*//*
        TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
        req.setFields("seller_nick, buyer_nick, title, type, created, tid, seller_rate,buyer_flag, buyer_rate, status, payment, adjust_fee, post_fee, total_fee, pay_time, end_time, modified, consign_time, buyer_obtain_point_fee, point_fee, real_point_fee, received_payment, commission_fee, buyer_memo, seller_memo, alipay_no,alipay_id,buyer_message, pic_path, num_iid, num, price, buyer_alipay_no, receiver_name, receiver_state, receiver_city, receiver_district, receiver_address, receiver_zip, receiver_mobile, receiver_phone,seller_flag, seller_alipay_no, seller_mobile, seller_phone, seller_name, seller_email, available_confirm_fee, has_post_fee, timeout_action_time, snapshot_url, cod_fee, cod_status, shipping_type, trade_memo, is_3D,buyer_email,buyer_area, trade_from,is_lgtype,is_force_wlb,is_brand_sale,buyer_cod_fee,discount_fee,seller_cod_fee,express_agency_fee,invoice_name,service_orders,credit_cardfee,step_trade_status,step_paid_fee,mark_desc,eticket_ext,orders,promotion_details,invoice_name");
        req.setTid(Long.parseLong(tradeNo));
        TradeFullinfoGetResponse response = client.execute(req, sessionKey);
        if (response.isSuccess()) {
            if (response.getTrade() != null)
            {
                if (status == null || "".equals(status) || status.equals(response.getTrade().getStatus()))
                    strXml = response.getBody();
            }
        } else {
            throw new Exception(response.getErrorCode() + "/"
                    + response.getMsg() + ":" + response.getSubMsg());
        }
        return strXml;
    }*/
}
