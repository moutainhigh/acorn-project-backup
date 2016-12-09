package com.chinadrtv.yhd.service.impl;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.yhd.common.dal.dao.TradeFeedbackDao;
import com.chinadrtv.yhd.common.dal.model.TradeFeedback;
import com.chinadrtv.yhd.model.YhdOrderConfig;
import com.chinadrtv.yhd.service.YhdFeedbackService;
import com.yhd.YhdClient;
import com.yhd.object.logistics.LogisticsInfo;
import com.yhd.request.logistics.LogisticsDeliverysCompanyGetRequest;
import com.yhd.request.logistics.LogisticsOrderShipmentsUpdateRequest;
import com.yhd.response.logistics.LogisticsDeliverysCompanyGetResponse;
import com.yhd.response.logistics.LogisticsOrderShipmentsUpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-3-21
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
@Service("yhdFeedbackService")
public class YhdFeedbackServiceImpl implements YhdFeedbackService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(YhdFeedbackServiceImpl.class);

    @Autowired
    private TradeFeedbackDao tradeFeedbackDao;

    @Override
    public void orderFeedback(List<YhdOrderConfig> yhdOrderConfigList) {
        logger.info("YhdFeedbackServiceImpl begin......");
        List<TradeFeedback> tradeFeedbackList = null;
        PreTrade preTrade = null;
        boolean b = false;
        StringBuilder errMsg = null;
        for(YhdOrderConfig config : yhdOrderConfigList){
            try{
                //获取订单反馈信息
                tradeFeedbackList = tradeFeedbackDao.findFeedBacks(config.getTradeType());
                logger.info("get tradeFeedbackList size:"+tradeFeedbackList.size());
                if(tradeFeedbackList == null || tradeFeedbackList.size() == 0){
                    continue;
                }
                //循环遍历数据
                for(TradeFeedback tradeFeedback : tradeFeedbackList){
                    errMsg = new StringBuilder();    //错误信息记录
                    YhdClient yhd = new YhdClient(config.getUrl(),config.getAppkey(),config.getAppSecret());
                    LogisticsOrderShipmentsUpdateRequest request = new LogisticsOrderShipmentsUpdateRequest();

                    //  配送商ID(从获取物流信息接口中获取)
                    Long supplierId = this.getDeliverySupplierId(config, tradeFeedback.getCompanyCode());
                    logger.info("get DeliverySupplierId:"+supplierId);
                    request.setDeliverySupplierId (supplierId);

                    request.setOrderCode (tradeFeedback.getTradeId());   //订单号(订单编码)
                    request.setExpressNbr (tradeFeedback.getMailId());   // 运单号(快递编号)
                    LogisticsOrderShipmentsUpdateResponse response = yhd.excute(request, config.getSessionKey());

                    int errorCount = response.getErrorCount();
                    if(errorCount > 0){  //反馈失败
                        String desc = response.getErrInfoList().getErrDetailInfo().get(0).getErrorDes();
                        String pkInfo = response.getErrInfoList().getErrDetailInfo().get(0).getPkInfo();
                        errMsg.append(pkInfo);
                        errMsg.append("/");
                        errMsg.append(desc);
                        b = false;
                       logger.info(tradeFeedback.getTradeId()+" feedback fail:"+errMsg.toString());
                    }

                    int updateCount = response.getUpdateCount();
                    if(updateCount > 0){  //反馈成功
                        b = true;
                        logger.info("order feedback response success!!");
                    }
                    //回写数据库
                    logger.info("update PreTrade begin......");
                    preTrade = new PreTrade();
                    preTrade.setTradeId(tradeFeedback.getTradeId());
                    preTrade.setOpsTradeId(tradeFeedback.getTradeId());
                    preTrade.setFeedbackStatus(b ? "2" : "4");
                    if(b){
                        errMsg.append("反馈成功");
                    }else{
                        errMsg.append("反馈失败，原因：" + ((errMsg == null) ? "未知错误" : errMsg));
                    }

                    preTrade.setFeedbackStatusRemark(errMsg.toString());
                    preTrade.setFeedbackUser("order-feedback-yihaodian");
                    preTrade.setFeedbackDate(new Date());
                    int updateResult = tradeFeedbackDao.updateOrderFeedbackStatus(preTrade);
                    logger.info("update preTrade end result:"+updateResult);
                }

            }catch (Exception e){
                logger.error("feedback service Exception:"+e);
            }
        }
        logger.info("YhdFeedbackServiceImpl end......");


    }

    /**
     *  配送商ID(从获取物流信息接口中获取)
     * @param config
     * @param mapCode
     * @return
     * @throws Exception
     */
    private Long getDeliverySupplierId(YhdOrderConfig config,String mapCode) throws Exception{
        YhdClient yhd = new YhdClient(config.getUrl(),config.getAppkey(),config.getAppSecret());
        LogisticsDeliverysCompanyGetRequest request = new LogisticsDeliverysCompanyGetRequest();
        LogisticsDeliverysCompanyGetResponse response = yhd.excute(request, config.getSessionKey());
        //错误信息
        int errorCount = response.getErrorCount();

        if(errorCount > 0){
            String errorDesc = response.getErrInfoList().getErrDetailInfo().get(0).getErrorDes();
            logger.error("feedback fail:"+errorDesc);
            throw new Exception(errorDesc);
        }
        //获取配送商id
        int totalCount = response.getTotalCount();
        if(totalCount > 0){
            List<LogisticsInfo> logisticsInfos = response.getLogisticsInfoList().getLogisticsInfo();
            for(LogisticsInfo tmp : logisticsInfos){
                if(tmp.getStatus() == 1){    //筛选启用数据 状态0:启用1:关闭
                    continue;
                }
                if(mapCode.equals(tmp.getCompanyName())){
                    return tmp.getId();
                }
            }
        }
        return 0l;
    }
}
