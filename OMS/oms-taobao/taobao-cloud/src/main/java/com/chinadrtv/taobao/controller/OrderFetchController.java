package com.chinadrtv.taobao.controller;

import com.chinadrtv.taobao.common.util.BASE64;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.model.TradeModel;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradesSoldIncrementGetResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-6
 * Time: 下午4:07
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class OrderFetchController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(OrderFetchController.class);

    private String format = com.taobao.api.Constants.FORMAT_XML;
    private int connectTimeout = 1000 * 30;
    private int readTimeout = 1000 * 30;
    private long pageSize = 50L;

    @RequestMapping(value = "/getTradeList", method = RequestMethod.POST, consumes="application/json",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<String> getTradeList(@RequestBody TradeModel tradeModel ){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMdd HHmmss");
        logger.info("获取交易列表 getTradeList"+simpleDateFormat.format(new Date()));

        TaobaoOrderConfig taobaoOrderConfig = tradeModel.getTaobaoOrderConfig();
        String status = tradeModel.getStatus();
        Date startModified = null;
        Date endModified = null;
        try {
            startModified = simpleDateFormat.parse(tradeModel.getStartModified());
            endModified = simpleDateFormat.parse(tradeModel.getEndModified()) ;
        } catch (Exception e) {
            logger.error("时间转换错误",e);
        }


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
        //FIXME
        //req.setIsAcookie(false);

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

                    return encryptList(trades) ;
                    //throw new RuntimeException(response.getErrorCode()+":"+response.getMsg());
                }
                pageNo++;
            } while ((response != null) && (response.getHasNext()));
        }catch (Exception exp)
        {
            logger.error("fetch order error:",exp);
            return encryptList(trades) ;
            //throw new RuntimeException(exp.getMessage(),exp);
        }

        return encryptList(trades) ;
    }

    private List<String> encryptList(List<String> trades) {
       if(trades==null || trades.isEmpty()){
           return trades;
       }
        List<String> list = new ArrayList<String>();
        for(String trade:trades){
            list.add(BASE64.EncryptDES(trade));
        }

        return list;
    }

    private String getTradeInfo(TaobaoClient client, String sessionKey, String tradeNo, String status) throws Exception {
        logger.info("获取交易明细getTradeInfo tradeNo:"+tradeNo);

        String strXml = null;

        /*if (client == null){
            client = new DefaultTaobaoClient(url, appKey, appSecret, format, connectTimeout, readTimeout);
        }*/
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
    }
}
