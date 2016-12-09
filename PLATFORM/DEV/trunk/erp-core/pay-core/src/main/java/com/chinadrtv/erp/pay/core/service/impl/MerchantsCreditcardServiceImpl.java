package com.chinadrtv.erp.pay.core.service.impl;

import com.chinadrtv.erp.pay.core.constant.CreditcardCode;
import com.chinadrtv.erp.pay.core.exception.OnlineException;
import com.chinadrtv.erp.pay.core.model.PayResult;
import com.chinadrtv.erp.pay.core.model.PayType;
import com.chinadrtv.erp.pay.core.model.Payment;
import com.chinadrtv.erp.pay.core.service.OnlinePayAdapterService;
import com.chinadrtv.erp.pay.core.merchants.model.Response;
import com.chinadrtv.erp.pay.core.merchants.model.ResultElement;
import com.chinadrtv.erp.pay.core.merchants.service.OnlineAuthorization;
import com.chinadrtv.erp.pay.core.merchants.service.impl.OnlineAuthorizationService;
import com.chinadrtv.erp.pay.core.service.TraceNumService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;
import java.math.BigDecimal;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

//import com.sun.xml.internal.ws.client.ClientTransportException;

/**
 * 招行信用卡在线索权服务
 * User: 徐志凯
 * Date: 13-5-27
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("MerchantsCreditcardServiceImpl")
public class MerchantsCreditcardServiceImpl implements OnlinePayAdapterService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MerchantsCreditcardServiceImpl.class);

    @Autowired
    private TraceNumService traceNumService;
    /**
     * 招行连接用户名
     */
    @Value("${com.chinadrtv.erp.sales.core.merchants.userName}")
    private String userName;
    /**
     * 招行连接密码
     */
    @Value("${com.chinadrtv.erp.sales.core.merchants.password}")
    private String password;

    @Value("${com.chinadrtv.erp.sales.core.merchants.url}")
    private String strUrl;

    @Value("${com.chinadrtv.erp.sales.core.merchants.paytype}")
    private String paytypeName;

    /**
     *  交易跟踪号，每天不能重复，最大为6位数字
     *  如果多个服务，那么需要数据库并发控制
     */
    //private Integer traceNum;

    /**
     * 交易批次号
     * 6位 格式：yyMMdd
     */
    //private String batchNum;

    private OnlineAuthorization onlineAuthorization;
    private String initErrorMsg;

    public void initService()  throws Exception
    {
        try
        {
            URL url = new URL(strUrl);
            OnlineAuthorizationService onlineAuthorizationService=new OnlineAuthorizationService(url);
            logger.warn("just init-1");
            onlineAuthorization = onlineAuthorizationService.getOnlineAuthorization();
            logger.warn("just init-2");
        }catch (Exception exp)
        {
            onlineAuthorization=null;
            initErrorMsg=exp.getMessage();
            //System.out.println("initService error");
            //exp.printStackTrace();
            logger.error("init service error(x):",exp);
            throw exp;
        }

        logger.warn("init service succ(x)!!!");
        //System.out.println("initService succ(x)");
    }

    private SimpleDateFormat batchNumDateFormat=new SimpleDateFormat("yyMMdd");

    private SimpleDateFormat expiryDateDateFormat=new SimpleDateFormat("yyMM");

    private SimpleDateFormat transTimeDateFormat=new SimpleDateFormat("yyyyMMddhhmmss");

    private Date getTransTimeFromString(String time)
    {
        try
        {
         return transTimeDateFormat.parse(time);
        }catch (Exception exp)
        {
            return null;
        }
    }

    private String getTransTimeFromDate(Date time)
    {
        return transTimeDateFormat.format(time);
    }

    public PayType getPayType() {
       return new PayType(this.paytypeName,"招行");
    }

    /**
     * 分期交易
     */
    public PayResult pay(Payment payment)
    {
        if(payment==null)
            logger.warn("hirePurchase param creditcardOnlineAuthorization is null");
        synchronized (this)
        {
            if(onlineAuthorization==null)
            {
                try
                {
                    this.initService();
                }catch (Exception exp)
                {
                    exp.printStackTrace();
                }
            }
        }
        //调用招行接口，进行分期交易
        PayResult payResult=new PayResult();
        if(onlineAuthorization==null)
        {
            payResult.setErrorCode(CreditcardCode.InitError);
            payResult.setErrorMsg(initErrorMsg);
            return payResult;
        }
        logger.warn("succ: here");
        String token=null;
        try
        {
            token = this.getLoginToken();
        }catch (OnlineException exp)
        {
            logger.error("login is error:"+exp.getErrorCode()+"-"+exp.getErrorMessage());
            payResult.setErrorCode(exp.getErrorCode());
            payResult.setErrorMsg(exp.getErrorMessage());
            return payResult;
        }

        logger.warn("succ login token:"+token);

        //从日期中取出年和月
        String expiryDate = this.getExpiryDateFromInput(payment.getExpiryDate());
        String amount = this.getAmountFromInput(payment.getAmount());
        String batchNum=this.getCurrentBatchNum();
        Integer traceNum=traceNumService.getTraceNum();
        logger.warn("succ begin call hirePurchase");
        try
        {
            logger.warn("hirePurchase param token:"+token);
            logger.warn("hirePurchase param batchNum:"+batchNum);
            logger.warn("hirePurchase param cardNo:"+payment.getCardNo());
            logger.warn("hirePurchase param expiryDate:"+expiryDate);
            logger.warn("hirePurchase param traceNum:"+traceNum);
            logger.warn("hirePurchase param amount:"+amount);
            if(payment.getStageNum()==null)
                logger.error("hirePurchase param stagenum is null");
            logger.warn("hirePurchase param stageNum:"+payment.getStageNum().intValue());
            logger.warn("token:"+token+"-batchNum:"+batchNum+"-cardno:"+payment.getCardNo()+"-expiryDate:"+expiryDate+"-traceNum:"+traceNum+"-amount:"+amount+"stagenum"+payment.getStageNum().intValue());
            logger.warn("begin call hirePurchase indeed");
            Response response = onlineAuthorization.hirePurchase(token,batchNum,payment.getCardNo(),expiryDate,traceNum.intValue(),amount,payment.getStageNum().intValue());
            payResult=this.getCreditcardResponse(response);
            payResult.setTraceNum(traceNum);
            payResult.setBatchNum(batchNum);
            logger.warn("call hirePurchase result(key:traceNum - value:"+traceNum+")");
            logger.warn("call hirePurchase result(key:batchNum - value:"+batchNum+")");
        }
        catch (Exception exp)
        {
            logger.error("hirePurchase error:",exp);
            //捕获网络错误或者超时（目前不需要冲正，只返回状态码，之后根据状态码进行相应的处理动作）
            //异常处理
            payResult.setErrorCode(CreditcardCode.SystemError);
            payResult.setErrorMsg(exp.getMessage());
            return payResult;
        }

        onlineAuthorization.logout(token);

        //1 交易成功-记录日志并返回

        //2 交易失败-记录日志
        //2-1 需要冲正，那么调用交易冲正接口，进行冲正
        //2-1-1  冲正不成功，如果交易日更换了，那么需要执行分期退货交易
        //                如果其他错误，需要重试
        //2-1-2  冲正成功了

        //2-2 不需要冲正，那么返回

        //2-3 最后判断是否可以重试的错误，如果可以重试，那么再次提交交易（放在外面调用方做）

        return payResult;
    }

    private PayResult getCreditcardResponse(Response response)
    {
        PayResult payResult=new PayResult();
        if(response!=null&& StringUtils.isNotEmpty(response.getResult()))
            logger.warn("end call hirePurchase with:"+response.getResult());
        else
        {
            if(response==null)
            {
                logger.error("end call hirePurchase with null response");
            }
            else
            {
                logger.error("end call hirePurchase with null result");
            }
        }
        if("Y".equals(response.getResult()))
        {
            for(ResultElement element:response.getResultElements())
            {
                logger.warn("call hirePurchase result(key:"+element.getKey()+" - value:"+element.getValue()+")");
                if("TransTime".equalsIgnoreCase(element.getKey()))
                {
                    payResult.setTransTime(this.getTransTimeFromString(element.getValue()));
                }
                else if("AuthID".equalsIgnoreCase(element.getKey()))
                {
                    payResult.setAuthID(element.getValue());
                }
                else if("OrderNum".equalsIgnoreCase(element.getKey()))
                {
                    payResult.setOrderNum(element.getValue());
                }
                else if("HPFee".equalsIgnoreCase(element.getKey()))
                {
                    payResult.setHpFee(new BigDecimal(element.getValue()));
                }
                else if("HPAmount".equalsIgnoreCase(element.getKey()))
                {
                    payResult.setHpAmount(new BigDecimal(element.getValue()));
                }
                else if("TotalAmount".equalsIgnoreCase(element.getKey()))
                {
                    payResult.setTotalAmount(new BigDecimal(element.getValue()));
                }
                else if("RefNum".equalsIgnoreCase(element.getKey()))
                {
                    payResult.setRefNum(element.getValue());
                }
            }
        }
        else
        {
            logger.error("error call hirePurchase:"+response.getResult()+"- host error:"+response.getHostErrCode()+"- error code:"+response.getErrCode()+"- error msg:"+response.getErrMsg());
            payResult.setErrorCode(response.getErrCode()+"-"+response.getHostErrCode());
            String strMsg=getErrorMessage(response.getErrCode(), response.getHostErrCode(), response.getErrMsg());
            if(StringUtils.isBlank(strMsg))
            {
                payResult.setErrorMsg(response.getErrMsg());
            }
            else
            {
                payResult.setErrorMsg(strMsg);
            }
        }

        return payResult;
    }

    private String getAmountFromInput(BigDecimal amount)
    {
        BigDecimal decimal=amount.multiply(new BigDecimal(100));
        decimal=decimal.setScale(0, BigDecimal.ROUND_HALF_UP);
        Integer i=decimal.intValue();
        return  String.format("%012d",i);
    }

    private String getExpiryDateFromInput(Date expiryDate)
    {
        return expiryDateDateFormat.format(expiryDate);
    }

    private String getCurrentBatchNum()
    {
        return batchNumDateFormat.format(new Date());
    }


    /**
     * 取消分期交易
     */
    public PayResult cancelPay(Payment payment, PayResult prePayResult)
    {
        //调用招行接口，进行分期交易
        PayResult payResult=new PayResult();
        if(onlineAuthorization==null)
        {
            payResult.setErrorCode(CreditcardCode.InitError);
            payResult.setErrorMsg(initErrorMsg);
            return payResult;
        }

        String token=null;
        try
        {
            token = this.getLoginToken();
        }catch (OnlineException exp)
        {
            payResult.setErrorCode(exp.getErrorCode());
            payResult.setErrorMsg(exp.getErrorMessage());
            return payResult;
        }

        String batchNum=this.getCurrentBatchNum();
        String transNum= batchNumDateFormat.format(prePayResult.getTransTime());
        String expiryDate = this.getExpiryDateFromInput(payment.getExpiryDate());
        Integer traceNum=traceNumService.getTraceNum();
        String strPreTransTime=this.getTransTimeFromDate(prePayResult.getTransTime());
        String strPreAmount=this.getAmountFromInput(payment.getAmount());
        try
        {
            //1 如果交易发生在当日，那么调用分期交易取消服务
            if(batchNum.equals(transNum))
            {
                //调用分期交易撤销
                Response response = onlineAuthorization.hirePurchaseCancel(token,batchNum,payment.getCardNo(),expiryDate,traceNum.intValue(),
                        prePayResult.getBatchNum(),strPreTransTime,prePayResult.getTraceNum().intValue(), prePayResult.getRefNum(),
                        strPreAmount,prePayResult.getAuthID(),payment.getStageNum().intValue(),prePayResult.getOrderNum());
                payResult = this.getCreditcardOnlineAuthorizationReturnFromResponse(response);
            }
            //2 如果非当日，那么调用分期退货交易
            else
            {
                Response response = onlineAuthorization.hirePurchaseReturn(token,batchNum,payment.getCardNo(),expiryDate,traceNum.intValue(),
                        strPreAmount, prePayResult.getBatchNum(),strPreTransTime,prePayResult.getTraceNum().intValue(), prePayResult.getRefNum(),
                        prePayResult.getAuthID(),payment.getStageNum().intValue(),prePayResult.getOrderNum());

                payResult = this.getCreditcardOnlineAuthorizationReturnFromResponse(response);
            }
        }
        catch (Exception exp)
        {
            logger.error("hirePurchaseReturn error:", exp);
            //异常处理
            payResult.setErrorCode(CreditcardCode.SystemError);
            payResult.setErrorMsg(exp.getMessage());
            return payResult;
        }

        onlineAuthorization.logout(token);

        return payResult;
    }

    private PayResult getCreditcardOnlineAuthorizationReturnFromResponse(Response response)
    {
        PayResult payResult=new PayResult();
        if("Y".equals(response.getResult()))
        {
            for(ResultElement element:response.getResultElements())
            {
                if("TransTime".equalsIgnoreCase(element.getKey()))
                {
                    payResult.setTransTime(this.getTransTimeFromString(element.getValue()));
                }
                else if("RefNum".equalsIgnoreCase(element.getKey()))
                {
                    payResult.setRefNum(element.getValue());
                }
            }
        }
        else
        {
            payResult.setErrorCode(response.getErrCode()+"-"+response.getHostErrCode());
            payResult.setErrorMsg(response.getErrMsg());
        }
        return payResult;
    }

    /**
     * 分期交易冲正
     */
    private void  hirePurchaseRev()
    {
        //1 冲正成功，直接返回

        //2 如果冲正不成功
        //2-1 需要调用撤销冲正，那么调用撤销冲正
        //2-2 不需要，直接返回错误
    }

    /**
     * 分期交易取消冲正
     */
    private void  hirePurchaseCancelRev()
    {
    }

    private String getLoginToken()
    {
        //重试登陆一次
        Response response=null;
        Exception expLogin=null;
        for(int i=0;i<2;i++)
        {
            try
            {
                logger.warn("begin login-");
                response = onlineAuthorization.login(userName, password);
                logger.warn("end login-");
                if("Y".equals(response.getResult()))
                {
                    logger.warn("login token-:"+response.getToken());
                    if(StringUtils.isEmpty(response.getToken()))
                    {
                        logger.error("login succ but token is null:");
                        logger.error("error code:"+response.getErrCode());
                        logger.error("error msg:"+response.getErrMsg());
                        logger.error("error host code:"+response.getHostErrCode());
                    }
                    return response.getToken();
                }
                else
                {
                    logger.error("login is error-:"+response.getErrCode()+"-"+response.getHostErrCode(),response.getErrMsg());
                    throw new OnlineException(response.getErrCode()+"-"+response.getHostErrCode(),response.getErrMsg());
                }
            }catch (Exception exp)
            {
                logger.error("login error:",exp);
                expLogin=exp;
                //ClientTransportException clientTransportException=null;//服务器停止的异常
                //SOAPFaultException soapFaultException=null;//服务器抛异常
                //WebServiceException a 先可以连接，中途断开 内部异常 ConnectException b.超时 内部异常 SocketTimeoutException
                if(this.canRetryException(exp))
                    continue;
            }
        }

        if(expLogin!=null)
        {
            throw new OnlineException(CreditcardCode.SystemError,expLogin.getMessage());
        }
        logger.error("login error return null");
        return null;
    }

    private boolean canRetryException(Exception exp)
    {
        //服务器程序抛异常
        if(exp instanceof SOAPFaultException)
        {
            return true;
        }
        /*else if(exp instanceof ClientTransportException)
        {
             return true;
        }*/
        else if(exp instanceof WebServiceException)
        {
             return true;
        }
        else if(exp instanceof RemoteException)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static final HashMap<String,String> errorMsgMap=new HashMap<String, String>();
    private static final HashMap<String,String> hostMsgMap=new HashMap<String, String>();

    public MerchantsCreditcardServiceImpl()
    {
        errorMsgMap.put("8000","登录信息失效,需要重新登陆");
        errorMsgMap.put("8001","登录校验失败：%s");
        errorMsgMap.put("8002","Token校验失败：%s");
        errorMsgMap.put("8003","修改密码失败：%s");
        errorMsgMap.put("8004","登出系统失败");
        errorMsgMap.put("8005","无权使用该交易");
        errorMsgMap.put("8006","无权使用该卡号");
        errorMsgMap.put("8007","IP地址校验失败");

        errorMsgMap.put("8101","向数据库写入交易记录失败：%s");

        errorMsgMap.put("8201","用户名不是4位数字");
        errorMsgMap.put("8202","密码不是6位合法字符");
        errorMsgMap.put("8203","卡号不是16位数字");
        errorMsgMap.put("8204","卡片有效期不合法");
        errorMsgMap.put("8205","交易跟踪号超出范围");
        errorMsgMap.put("8206","交易金额不是12位数字");
        errorMsgMap.put("8207","原批次号不是6位数字");
        errorMsgMap.put("8208","原交易时间不是14位数字");
        errorMsgMap.put("8209","原交易跟踪号超出范围");

        errorMsgMap.put("8210","原交易金额不是12位数字");
        errorMsgMap.put("8211","原交易流水号不是12位数字");
        errorMsgMap.put("8212","原授权号不是6位数字");
        errorMsgMap.put("8213","操作类型必须是1位合法字符");
        errorMsgMap.put("8214","分期数超出范围");
        errorMsgMap.put("8215","订单号不是15位数字");
        errorMsgMap.put("8216","积分输入方式必须是1位数字");
        errorMsgMap.put("8217","交易积分超出范围");
        errorMsgMap.put("8218","旧密码不是6位合法字符");
        errorMsgMap.put("8219","新密码不是6位合法字符");

        errorMsgMap.put("8220","批次号不是6位数字");
        errorMsgMap.put("8221","证件类型不是2位数字，'01' 身份证，'02' 护照，'03' 其他");
        errorMsgMap.put("8222","证件号码不合法");
        errorMsgMap.put("8223","电话号码类型不是2位数字，'01' 座机，'02' 手机");
        errorMsgMap.put("8224","电话号码不合法");

        errorMsgMap.put("8310","冲正的原交易不存在/原交易未成功/原交易已冲正");
        errorMsgMap.put("8311","原交易成功但无法冲正，将返回原交易的参数");
        errorMsgMap.put("8312","原交易冲正失败");

        errorMsgMap.put("9010","预授权交易处理失败");
        errorMsgMap.put("9011","预授权冲正交易处理失败 ");
        errorMsgMap.put("9012","预授权完成交易处理失败");
        errorMsgMap.put("9013","预授权完成冲正交易处理失败");
        errorMsgMap.put("9014","预授权撤销交易处理失败");
        errorMsgMap.put("9015","预授权撤销冲正交易处理失败");
        errorMsgMap.put("9016","预授权完成撤销交易处理失败");
        errorMsgMap.put("9017","预授权完成撤销冲正交易处理失败");

        errorMsgMap.put("9020","分期交易处理失败");
        errorMsgMap.put("9021","分期交易冲正处理失败");
        errorMsgMap.put("9022","分期交易撤销处理失败");
        errorMsgMap.put("9023","分期交易撤销冲正处理失败");

        errorMsgMap.put("9030","积分交易处理失败");
        errorMsgMap.put("9031","积分交易冲正处理失败");
        errorMsgMap.put("9032","积分交易撤销处理失败");
        errorMsgMap.put("9033","积分交易撤销冲正处理失败");

        errorMsgMap.put("9040","卡片查询交易处理失败");
        errorMsgMap.put("9041","积分查询交易处理失败");

        errorMsgMap.put("9050","退货交易处理失败");
        errorMsgMap.put("9051","退货撤销交易处理失败");
        errorMsgMap.put("9052","分期退货交易处理失败");
        errorMsgMap.put("9053","分期退货撤销交易处理失败");
        errorMsgMap.put("9054","积分消费退货交易处理失败");
        errorMsgMap.put("9055","积分消费退货撤销交易处理失败");

        errorMsgMap.put("9101","向数据库更新交易记录失败");
        errorMsgMap.put("9200","处理请求失败");
        errorMsgMap.put("9201","处理返回结果失败");
        errorMsgMap.put("9300","请求处理超时");



        // host error code
        hostMsgMap.put("01","查相应发卡机构");
        hostMsgMap.put("02","查相应发卡机构的特殊条件");
        hostMsgMap.put("04","卡片状态异常");
        hostMsgMap.put("05","主机不支持");
        hostMsgMap.put("06","错误");
        hostMsgMap.put("12","无效交易");
        hostMsgMap.put("13","无效金额");
        hostMsgMap.put("14","无此卡号");
        hostMsgMap.put("33","过期的卡(没收卡)");
        hostMsgMap.put("34","有作弊嫌疑(没收卡)");
        hostMsgMap.put("36","卡片受管制");
        hostMsgMap.put("38","超出密码错误次数限制");
        hostMsgMap.put("41","挂失卡(没收卡)");
        hostMsgMap.put("43","被窃卡(没收卡)");
        hostMsgMap.put("51","额度不足");
        hostMsgMap.put("54","卡片过期");
        hostMsgMap.put("55","密码错误");
        hostMsgMap.put("56","无卡片记录");
        hostMsgMap.put("57","不允许持卡人进行的交易");
        hostMsgMap.put("58","终端不支持此交易");
        hostMsgMap.put("61","超出取现额度限制");
        hostMsgMap.put("62","卡片受管制");
        hostMsgMap.put("65","超出交易次数限制");
        hostMsgMap.put("75","超出密码错误次数限制");
        hostMsgMap.put("90","系统日期切换正在处理");
        hostMsgMap.put("96","交易错误");
        hostMsgMap.put("97","无效终端号");
        hostMsgMap.put("N2","该卡号的预授权次数达到上限");
        hostMsgMap.put("O4","超过交易额度");
        hostMsgMap.put("O6,O8","伪冒");
        hostMsgMap.put("T5","卡片受管制(未开卡)");
        hostMsgMap.put("X6","身份校验不通过(分期授权时)");

    }

    private String getErrorMessage(int errorCode,String hostCode,String orgMsg)
    {
        String strMsg="";
        if(hostMsgMap.containsKey(hostCode))
        {
            strMsg+=String.format(hostMsgMap.get(hostCode),orgMsg);
        }
        String strErrorCode=String.valueOf(errorCode);
        if(errorMsgMap.containsKey(strErrorCode))
        {
            if(StringUtils.isNotBlank(strMsg))
                strMsg+="-";
            strMsg+=String.format(errorMsgMap.get(strErrorCode),orgMsg);
        }
        return strMsg;
    }
}
