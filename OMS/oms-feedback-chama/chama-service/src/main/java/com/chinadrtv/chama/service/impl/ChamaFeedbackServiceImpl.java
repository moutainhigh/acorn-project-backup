package com.chinadrtv.chama.service.impl;

import com.chinadrtv.chama.dal.dao.TradeFeedbackDao;
import com.chinadrtv.chama.dal.model.TradeFeedback;
import com.chinadrtv.chama.service.ChamaFeedbackService;
import com.chinadrtv.model.oms.PreTrade;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-6
 * Time: 下午1:21
 * To change this template use File | Settings | File Templates.
 */
@Service("ChamaFeedbackService")
public class ChamaFeedbackServiceImpl implements ChamaFeedbackService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ChamaFeedbackServiceImpl.class);
    @Autowired
    private TradeFeedbackDao tradeFeedbackDao;

    /**
     *获取反馈信息
     * @param orderType
     * @return
     */
    public List<TradeFeedback> searchOrderByType(List<String> orderType) {
        return tradeFeedbackDao.findByOpsId(orderType);
    }

    /**
     * 更改状态
     * @param preTrade
     * @return
     */
    public boolean updateOrderFeedbackStatus(PreTrade preTrade) {
        boolean b = false;
        try{
            int result = tradeFeedbackDao.updateOrderFeedbackStatus(preTrade);
            if(result > 0){
                b = true;
            }
        }catch (Exception e){
            b = false;
        }
        return b;
    }

    /**
     * 反馈信息到茶马
     * @param url
     * @param tradefeedback
     * @return
     */
    public boolean updateTradeStatus(String url, TradeFeedback tradefeedback) {
        String updateLogisticsURI = url + "updateLogisitics";

        boolean flag = false;

        String opsTradeId = tradefeedback.getOpsTradeId();
        String tmsCode = tradefeedback.getTmsCode();
        String tmsLogisiticsIds = tradefeedback.getTmsLogisiticsIds();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createDat = sdf.format(tradefeedback.getCreateDat());

        String updateLogisticsContent = "<ops_update_logisitics_request><ops_trade_id>" + opsTradeId + "</ops_trade_id>" + "<tms_code>" + tmsCode + "</tms_code><tms_logisitics_ids>" + tmsLogisiticsIds + "</tms_logisitics_ids><weight>0</weight>" + "<created>" + createDat + "</created></ops_update_logisitics_request>";
        logger.info("updateLogisticsURI="+updateLogisticsURI);
        logger.info("updateLogisticsContent="+updateLogisticsContent);
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(updateLogisticsURI);

            StringEntity input = new StringEntity(updateLogisticsContent, "UTF-8");
            input.setContentType("application/xml");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() == 200) {//如果状态码为200,就是正常返回
                //得到返回的字符串
                String str = EntityUtils.toString(response.getEntity());
                int s = str.indexOf("<result>");
                int e = str.indexOf("</result>");
                String result = str.substring(s + 8, e);

                if (result.equals("true")) {
                    flag = true;
                }
                if (result.equals("false")) {
                    flag = false;
                }
            }
        }catch (Exception e){
            logger.error("ChamaFeedbackServiceImpl is updateTradeStatus error:"+e.getMessage());
        }

        return flag;
    }
}
