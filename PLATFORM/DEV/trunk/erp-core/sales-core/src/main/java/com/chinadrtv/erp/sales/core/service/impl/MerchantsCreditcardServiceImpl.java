package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.sales.core.Merchants.model.Response;
import com.chinadrtv.erp.sales.core.constant.MerchantsCreditcardCode;
import com.chinadrtv.erp.sales.core.model.*;
import com.chinadrtv.erp.sales.core.Merchants.model.ResultElement;
import com.chinadrtv.erp.sales.core.service.CreditcardOnlineAuthorizationService;
import com.chinadrtv.erp.sales.core.service.MerchantsCreditcardTracenumService;
import com.chinadrtv.erp.sales.core.util.CreditcardAuthorizationException;
import com.chinadrtv.erp.sales.core.Merchants.service.OnlineAuthorization;
import com.chinadrtv.erp.sales.core.Merchants.service.impl.OnlineAuthorizationService;
//import com.sun.xml.internal.ws.client.ClientTransportException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;
import java.math.BigDecimal;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 招行信用卡在线索权服务
 * User: 徐志凯
 * Date: 13-5-27
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class MerchantsCreditcardServiceImpl implements CreditcardOnlineAuthorizationService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MerchantsCreditcardServiceImpl.class);

    @Autowired
    private MerchantsCreditcardTracenumService merchantsCreditcardTracenumService;

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

    public MerchantsCreditcardServiceImpl()
    {
        //userName="3096";
        //password="123456";
        //onlineAuthorization=new OnlineAuthorizationService().getOnlineAuthorization();
    }

   //@PostConstruct
    public void initService()  throws Exception
    {
        try
        {
            URL url = new URL(strUrl);
            OnlineAuthorizationService onlineAuthorizationService=new OnlineAuthorizationService(url);
            logger.error("just init-1");
            onlineAuthorization = onlineAuthorizationService.getOnlineAuthorization();
            logger.error("just init-2");
        }catch (Exception exp)
        {
            onlineAuthorization=null;
            initErrorMsg=exp.getMessage();
            System.out.println("initService error");
            exp.printStackTrace();
            logger.error("init service error(x):",exp);
            throw exp;
        }

        logger.error("init service succ(x)!!!");
        System.out.println("initService succ(x)");
        //new OnlineAuthorizationService();
        //return "";
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

    /**
     * 分期交易
     */
    public CreditcardOnlineAuthorizationResponse hirePurchase(CreditcardOnlineAuthorization creditcardOnlineAuthorization)
    {
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
        CreditcardOnlineAuthorizationResponse creditcardOnlineAuthorizationResponse=new CreditcardOnlineAuthorizationResponse();
        if(onlineAuthorization==null)
        {
            creditcardOnlineAuthorizationResponse.setErrorCode(MerchantsCreditcardCode.InitError);
            creditcardOnlineAuthorizationResponse.setErrorMsg(initErrorMsg);
            return creditcardOnlineAuthorizationResponse;
        }
        logger.error("succ: here");
        String token=null;
        try
        {
            token = this.getLoginToken();
        }catch (CreditcardAuthorizationException exp)
        {
            logger.error("login is error:"+exp.getCreditcardOnlineAuthorizationBase().getErrorCode()+"-"+exp.getCreditcardOnlineAuthorizationBase().getErrorMsg());
            creditcardOnlineAuthorizationResponse.setErrorCode(exp.getCreditcardOnlineAuthorizationBase().getErrorCode());
            creditcardOnlineAuthorizationResponse.setErrorMsg(exp.getCreditcardOnlineAuthorizationBase().getErrorMsg());
            return creditcardOnlineAuthorizationResponse;
        }

        logger.error("succ login token:"+token);

        //从日期中取出年和月
        String expiryDate = this.getExpiryDateFromInput(creditcardOnlineAuthorization.getExpiryDate());
        String amount = this.getAmountFromInput(creditcardOnlineAuthorization.getAmount());
        String batchNum=this.getCurrentBatchNum();
        Integer traceNum=this.fetchTraceNum(batchNum);
        logger.error("succ begin call hirePurchase");
        try
        {
            logger.error("hirePurchase param token:"+token);
            logger.error("hirePurchase param batchNum:"+batchNum);
            //if(creditcardOnlineAuthorization==null)
            //    logger.error("hirePurchase param creditcardOnlineAuthorization is null");
            logger.error("hirePurchase param cardNo:"+creditcardOnlineAuthorization.getCardNo());
            logger.error("hirePurchase param expiryDate:"+expiryDate);
            logger.error("hirePurchase param traceNum:"+traceNum);
            logger.error("hirePurchase param amount:"+amount);
            if(creditcardOnlineAuthorization.getStageNum()==null)
                logger.error("hirePurchase param stagenum is null");
            logger.error("hirePurchase param stageNum:"+creditcardOnlineAuthorization.getStageNum().intValue());
            logger.error("token:"+token+"-batchNum:"+batchNum+"-cardno:"+creditcardOnlineAuthorization.getCardNo()+"-expiryDate:"+expiryDate+"-traceNum:"+traceNum+"-amount:"+amount+"stagenum"+creditcardOnlineAuthorization.getStageNum().intValue());
            logger.error("begin call hirePurchase indeed");
            Response response = onlineAuthorization.hirePurchase(token,batchNum,creditcardOnlineAuthorization.getCardNo(),expiryDate,traceNum.intValue(),amount,creditcardOnlineAuthorization.getStageNum().intValue());
            creditcardOnlineAuthorizationResponse=this.getCreditcardResponse(response);
            creditcardOnlineAuthorizationResponse.setTraceNum(traceNum);
            logger.error("call hirePurchase result(key:traceNum - value:"+traceNum+")");
            logger.error("call hirePurchase result(key:batchNum - value:"+batchNum+")");
        }
        catch (Exception exp)
        {
            logger.error("hirePurchase error:",exp);
            //捕获网络错误或者超时（目前不需要冲正，只返回状态码，之后根据状态码进行相应的处理动作）
            //异常处理
            creditcardOnlineAuthorizationResponse.setErrorCode(MerchantsCreditcardCode.SystemError);
            creditcardOnlineAuthorizationResponse.setErrorMsg(exp.getMessage());
            return creditcardOnlineAuthorizationResponse;
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

        return creditcardOnlineAuthorizationResponse;
    }

    private CreditcardOnlineAuthorizationResponse getCreditcardResponse(Response response)
    {
        CreditcardOnlineAuthorizationResponse creditcardOnlineAuthorizationResponse=new CreditcardOnlineAuthorizationResponse();
        if(response!=null&& StringUtils.isNotEmpty(response.getResult()))
            logger.error("end call hirePurchase with:"+response.getResult());
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
                logger.error("call hirePurchase result(key:"+element.getKey()+" - value:"+element.getValue()+")");
                if("TransTime".equalsIgnoreCase(element.getKey()))
                {
                     creditcardOnlineAuthorizationResponse.setTransTime(this.getTransTimeFromString(element.getValue()));
                }
                else if("AuthID".equalsIgnoreCase(element.getKey()))
                {
                    creditcardOnlineAuthorizationResponse.setAuthID(element.getValue());
                }
                else if("OrderNum".equalsIgnoreCase(element.getKey()))
                {
                    creditcardOnlineAuthorizationResponse.setOrderNum(element.getValue());
                }
                else if("HPFee".equalsIgnoreCase(element.getKey()))
                {
                    creditcardOnlineAuthorizationResponse.setHpFee(new BigDecimal(element.getValue()));
                }
                else if("HPAmount".equalsIgnoreCase(element.getKey()))
                {
                    creditcardOnlineAuthorizationResponse.setHpAmount(new BigDecimal(element.getValue()));
                }
                else if("TotalAmount".equalsIgnoreCase(element.getKey()))
                {
                    creditcardOnlineAuthorizationResponse.setTotalAmount(new BigDecimal(element.getValue()));
                }
                else if("RefNum".equalsIgnoreCase(element.getKey()))
                {
                    creditcardOnlineAuthorizationResponse.setRefNum(element.getValue());
                }
            }
        }
        else
        {
            logger.error("error call hirePurchase:"+response.getResult()+"- host error:"+response.getHostErrCode()+"- error code:"+response.getErrCode()+"- error msg:"+response.getErrMsg());
            creditcardOnlineAuthorizationResponse.setErrorCode(response.getErrCode()+"-"+response.getHostErrCode());
            creditcardOnlineAuthorizationResponse.setErrorMsg(response.getErrMsg());
        }

        return creditcardOnlineAuthorizationResponse;
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
    public CreditcardOnlineAuthorizationReturnResponse hirePurchaseReturn(CreditcardOnlineAuthorizationReturn creditcardOnlineAuthorizationReturn)
    {
        //调用招行接口，进行分期交易
        CreditcardOnlineAuthorizationReturnResponse creditcardOnlineAuthorizationReturnResponse=new CreditcardOnlineAuthorizationReturnResponse();
        if(onlineAuthorization==null)
        {
            creditcardOnlineAuthorizationReturnResponse.setErrorCode(MerchantsCreditcardCode.InitError);
            creditcardOnlineAuthorizationReturnResponse.setErrorMsg(initErrorMsg);
            return creditcardOnlineAuthorizationReturnResponse;
        }

        String token=null;
        try
        {
            token = this.getLoginToken();
        }catch (CreditcardAuthorizationException exp)
        {
            creditcardOnlineAuthorizationReturnResponse.setErrorCode(exp.getCreditcardOnlineAuthorizationBase().getErrorCode());
            creditcardOnlineAuthorizationReturnResponse.setErrorMsg(exp.getCreditcardOnlineAuthorizationBase().getErrorMsg());
            return creditcardOnlineAuthorizationReturnResponse;
        }

        String batchNum=this.getCurrentBatchNum();
        String transNum= batchNumDateFormat.format(creditcardOnlineAuthorizationReturn.getPreTransTime());
        String expiryDate = this.getExpiryDateFromInput(creditcardOnlineAuthorizationReturn.getExpiryDate());
        Integer traceNum=this.fetchTraceNum(batchNum);
        String strPreTransTime=this.getTransTimeFromDate(creditcardOnlineAuthorizationReturn.getPreTransTime());
        String strPreAmount=this.getAmountFromInput(creditcardOnlineAuthorizationReturn.getAmount());
        try
        {
            //1 如果交易发生在当日，那么调用分期交易取消服务
            if(batchNum.equals(transNum))
            {
                //调用分期交易撤销
                Response response = onlineAuthorization.hirePurchaseCancel(token,batchNum,creditcardOnlineAuthorizationReturn.getCardNo(),expiryDate,traceNum.intValue(),
                        creditcardOnlineAuthorizationReturn.getPreBatchNum(),strPreTransTime,creditcardOnlineAuthorizationReturn.getPreTraceNum().intValue(), creditcardOnlineAuthorizationReturn.getPreRefNum(),
                        strPreAmount,creditcardOnlineAuthorizationReturn.getPreAuthID(),creditcardOnlineAuthorizationReturn.getStageNum().intValue(),creditcardOnlineAuthorizationReturn.getOrderNum());
                creditcardOnlineAuthorizationReturnResponse = this.getCreditcardOnlineAuthorizationReturnFromResponse(response);
            }
            //2 如果非当日，那么调用分期退货交易
            else
            {
                Response response = onlineAuthorization.hirePurchaseReturn(token,batchNum,creditcardOnlineAuthorizationReturn.getCardNo(),expiryDate,traceNum.intValue(),
                        strPreAmount, creditcardOnlineAuthorizationReturn.getPreBatchNum(),strPreTransTime,creditcardOnlineAuthorizationReturn.getPreTraceNum().intValue(), creditcardOnlineAuthorizationReturn.getPreRefNum(),
                        creditcardOnlineAuthorizationReturn.getPreAuthID(),creditcardOnlineAuthorizationReturn.getStageNum().intValue(),creditcardOnlineAuthorizationReturn.getOrderNum());

                creditcardOnlineAuthorizationReturnResponse = this.getCreditcardOnlineAuthorizationReturnFromResponse(response);
            }
        }
        catch (Exception exp)
        {
            logger.error("hirePurchaseReturn error:", exp);
            //异常处理
            creditcardOnlineAuthorizationReturnResponse.setErrorCode(MerchantsCreditcardCode.SystemError);
            creditcardOnlineAuthorizationReturnResponse.setErrorMsg(exp.getMessage());
            return creditcardOnlineAuthorizationReturnResponse;
        }

        onlineAuthorization.logout(token);

        return creditcardOnlineAuthorizationReturnResponse;
    }

    private CreditcardOnlineAuthorizationReturnResponse getCreditcardOnlineAuthorizationReturnFromResponse(Response response)
    {
        CreditcardOnlineAuthorizationReturnResponse creditcardOnlineAuthorizationReturnResponse=new CreditcardOnlineAuthorizationReturnResponse();
        if("Y".equals(response.getResult()))
        {
            for(ResultElement element:response.getResultElements())
            {
                if("TransTime".equalsIgnoreCase(element.getKey()))
                {
                    creditcardOnlineAuthorizationReturnResponse.setTransTime(this.getTransTimeFromString(element.getValue()));
                }
                else if("RefNum".equalsIgnoreCase(element.getKey()))
                {
                    creditcardOnlineAuthorizationReturnResponse.setRefNum(element.getValue());
                }
            }
        }
        else
        {
            creditcardOnlineAuthorizationReturnResponse.setErrorCode(response.getErrCode()+"-"+response.getHostErrCode());
            creditcardOnlineAuthorizationReturnResponse.setErrorMsg(response.getErrMsg());
        }
        return creditcardOnlineAuthorizationReturnResponse;
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

    private Integer fetchTraceNum(String currentBatchNum)
    {
        //首先获取，如果获取不到(或者有并发插入)，等待一段时间重试
        for(int i=0;i<3;i++)
        {
            try
            {
                Integer traceNum = merchantsCreditcardTracenumService.calcTraceNum(currentBatchNum);
                if(traceNum!=null)
                {
                    return traceNum;
                }
                Thread.sleep(1000);
            }
            catch (Exception exp)
            {
                //如果是重复插入异常，那么忽略
                if(exp instanceof DataIntegrityViolationException)
                {

                }
                else
                {
                    //TODO:异常处理
                }
            }
        }

        //TODO:到此说明重试也无法避免冲突，那么直接返回错误
        return null;
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
                logger.error("begin login-");
                response = onlineAuthorization.login(userName, password);
                logger.error("end login-");
                if("Y".equals(response.getResult()))
                {
                    logger.error("login token-:"+response.getToken());
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
                    throw new CreditcardAuthorizationException(new CreditcardOnlineAuthorizationBase(response.getErrCode()+"-"+response.getHostErrCode(),response.getErrMsg()));
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
            throw new CreditcardAuthorizationException(new CreditcardOnlineAuthorizationBase(MerchantsCreditcardCode.SystemError,expLogin.getMessage()));
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
}
