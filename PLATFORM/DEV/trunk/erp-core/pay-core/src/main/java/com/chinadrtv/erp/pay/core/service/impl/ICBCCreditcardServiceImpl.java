package com.chinadrtv.erp.pay.core.service.impl;

import com.chinadrtv.erp.pay.core.icbc.model.*;
import com.chinadrtv.erp.pay.core.icbc.service.ICBCCallService;
import com.chinadrtv.erp.pay.core.icbc.service.ICBCCallServiceFactory;
import com.chinadrtv.erp.pay.core.model.PayResult;
import com.chinadrtv.erp.pay.core.model.PayType;
import com.chinadrtv.erp.pay.core.model.Payment;
import com.chinadrtv.erp.pay.core.service.MerchantsCreditcardTracenumService;
import com.chinadrtv.erp.pay.core.service.OnlinePayAdapterService;
import com.chinadrtv.erp.pay.core.service.TraceNumService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * 工行信用卡在线索权服务
 * User: 徐志凯
 * Date: 13-8-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("ICBCCreditcardServiceImpl")
public class ICBCCreditcardServiceImpl implements OnlinePayAdapterService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ICBCCreditcardServiceImpl.class);
    /**
     * 商户名称
     */
    @Value("${com.chinadrtv.erp.pay.core.icbc.username}")
    private String merchantNo;

    /**
     * 产品名称
     */
    @Value("${com.chinadrtv.erp.pay.core.icbc.prodname}")
    private String prodNo;

    /**
     * 工行类型标识
     */
    @Value("${com.chinadrtv.erp.sales.core.icbc.paytype}")
    private String paytypeName;

    @Value("${com.chinadrtv.erp.sales.core.icbc.server}")
    private String serverIP;

    @Value("${com.chinadrtv.erp.sales.core.icbc.timeout}")
    private Integer timeOut;

    @Autowired
    private TraceNumService traceNumService;

    private Integer getTraceNub()
    {
        Integer nub=traceNumService.getTraceNum();
        Date dt=new Date();
        int index=Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        return index*10000+nub;
    }

    @Autowired
    private ICBCCallServiceFactory icbcCallServiceFactory;


    public PayType getPayType() {
        return new PayType(paytypeName, "工行");
    }

    private ICBCCallService createCallService()
    {
        ICBCCallService icbcCallService=icbcCallServiceFactory.getICBCCallService();
        int iTimeOut=-1;
        if(timeOut!=null)
            iTimeOut=timeOut.intValue();
        int index=serverIP.indexOf(":");
        String server=serverIP;
        int port=8080;
        if(index>0)
        {
            server=serverIP.substring(0,index);
            port=Integer.parseInt(serverIP.substring(index+1));
        }
        icbcCallService.init(server,port,iTimeOut);

        return icbcCallService;
    }

    public PayResult pay(Payment payment) {
        if(!login())
        {
            PayResult payResult=new PayResult();
            payResult.setErrorMsg("签到不成功");
            payResult.setErrorCode("001");
            return payResult;
        }

        PayResult payResult=null;
        //数据转换
        if(payment.getStageNum()!=null&&payment.getStageNum().intValue()>1)
        {
            ICBCCallService icbcCallService=this.createCallService();
            try{
                ICBCReqPay icbcReqPay=this.getICBCPayFromPayment(payment);
                icbcReqPay.setTraceNum(this.getTraceNub());
                icbcReqPay.setProdNo(prodNo);
                icbcReqPay.setMerchantNo(merchantNo);
                ICBCResPay icbcResPay = icbcCallService.pay(icbcReqPay);
                payResult= this.getPayResultFromICBCResponse(icbcResPay);
                payResult.setTraceNum(icbcReqPay.getTraceNum());
            }catch (Exception exp)
            {
                logger.error("online pay system error:",exp);
                payResult=new PayResult();
                payResult.setErrorMsg(exp.getMessage());
                payResult.setErrorCode("001");
            }
            finally {
                icbcCallService.close();
            }
            return payResult;
        } else
        {
            ICBCReqAuthentication icbcReqAuthentication=new ICBCReqAuthentication();
            ICBCReqPayOnce icbcReqPayOnce=this.getICBCPayOnceFromPayment(payment);
            icbcReqPayOnce.setProdNo(prodNo);
            icbcReqPayOnce.setMerchantNo(merchantNo);

            icbcReqAuthentication.setMerchantNo(icbcReqPayOnce.getMerchantNo());
            icbcReqAuthentication.setProdNo(icbcReqPayOnce.getProdNo());
            icbcReqAuthentication.setTraceNum(this.getTraceNub());

            icbcReqAuthentication.setCardNo(icbcReqPayOnce.getCardNo());
            icbcReqAuthentication.setCredentialsNo(icbcReqPayOnce.getCredentialsNo());
            icbcReqAuthentication.setCredentialsTypeNum(icbcReqPayOnce.getCredentialsTypeNum());
            icbcReqAuthentication.setExpiryDate(icbcReqPayOnce.getExpiryDate());
            //icbcReqAuthentication.setMobile();

            icbcReqPayOnce.setTraceNum(this.getTraceNub());
            icbcReqAuthentication.setMobile(payment.getMobile());
            ICBCResBaseInfo icbcResBaseInfo=this.authenticationUser(icbcReqAuthentication);

            if(!icbcResBaseInfo.isbSucc())
            {
                payResult=new PayResult();
                payResult.setErrorCode("001");
                payResult.setErrorMsg(icbcResBaseInfo.getErrorMsg());
                return payResult;
            }else
            {
                ICBCCallService icbcCallService=this.createCallService();
                try{
                    //icbcReqPayOnce.setTraceNum(traceNumService.getTraceNum());
                    icbcReqPayOnce.setCredentialsTranNo(String.valueOf(icbcReqAuthentication.getTraceNum()));
                    ICBCResPayOnce icbcResPayOnce = icbcCallService.payOnce(icbcReqPayOnce);
                    payResult= this.getPayResultFromICBCResponseOnce(icbcResPayOnce);
                    payResult.setTraceNum(icbcReqPayOnce.getTraceNum());
                    payResult.setAuthID(String.valueOf(icbcReqPayOnce.getTraceNum()));
                    return payResult;
                }catch (Exception exp)
                {
                    logger.error("pay once system error:",exp);
                    payResult=new PayResult();
                    payResult.setErrorCode("001");
                    payResult.setErrorMsg(exp.getMessage());
                    return payResult;
                }
                finally {
                    icbcCallService.close();
                }
            }
        }
    }

    private ICBCResBaseInfo authenticationUser(ICBCReqAuthentication icbcReqAuthentication)
    {
        ICBCCallService icbcCallService=this.createCallService();
        try
        {
            return icbcCallService.authentication(icbcReqAuthentication);
        }
        catch (Exception exp)
        {
            logger.error("authentication system error:",exp);
            ICBCResBaseInfo icbcResBaseInfo=new ICBCResBaseInfo();
            icbcResBaseInfo.setbSucc(false);
            icbcResBaseInfo.setErrorMsg(exp.getMessage());
            return icbcResBaseInfo;
        }
        finally {
            icbcCallService.close();
        }
    }

    private ICBCReqPay getICBCPayFromPayment(Payment payment)
    {
        ICBCReqPay icbcReqPay=new ICBCReqPay();
        BeanUtils.copyProperties(payment,icbcReqPay);
        //TODO:卡类型转换-暂时是索引号减1
        if(payment.getCredentialsType()==null)
        {
            //TODO:证件是否必须，是否报错
        }
        else
        {
            icbcReqPay.setCredentialsTypeNum(String.valueOf(payment.getCredentialsType().getIndex()-1));
        }
        return icbcReqPay;
    }

    private ICBCReqPayOnce getICBCPayOnceFromPayment(Payment payment)
    {
        ICBCReqPayOnce icbcReqPayOnce=new ICBCReqPayOnce();
        BeanUtils.copyProperties(payment,icbcReqPayOnce);
        //TODO:卡类型转换-暂时是索引号减1
        if(payment.getCredentialsType()==null)
        {
            //TODO:证件是否必须，是否报错
        }
        else
        {
            icbcReqPayOnce.setCredentialsTypeNum(String.valueOf(payment.getCredentialsType().getIndex()-1));
        }
        return icbcReqPayOnce;
    }

    private PayResult getPayResultFromICBCResponse(ICBCResPay icbcResPay)
    {
        PayResult payResult=new PayResult();
        if(icbcResPay.isbSucc()==false)
        {
            payResult.setErrorCode("01");
            if(StringUtils.isEmpty(icbcResPay.getErrorMsg()))
            {
                payResult.setErrorMsg("系统错误");
            }
            else
            {
                payResult.setErrorMsg(icbcResPay.getErrorMsg());
            }
        }
        //payResult.setAuthID();
        payResult.setOrderNum(icbcResPay.getOrderNum());
        payResult.setRefNum(icbcResPay.getRefNum());
        //payResult.setTraceNum();
        payResult.setTransTime(icbcResPay.getTransTime());
        //payResult.setBatchNum();
        payResult.setHpAmount(icbcResPay.getHpAmount());
        payResult.setHpFee(icbcResPay.getHpFree());

        return payResult;
    }

    private PayResult getPayResultFromICBCResponseOnce(ICBCResPayOnce icbcResPayOnce)
    {
        PayResult payResult=new PayResult();
        if(icbcResPayOnce.isbSucc()==false)
        {
            payResult.setErrorCode("01");
            if(StringUtils.isEmpty(icbcResPayOnce.getErrorMsg()))
            {
                payResult.setErrorMsg("系统错误");
            }
            else
            {
                payResult.setErrorMsg(icbcResPayOnce.getErrorMsg());
            }
        }
        //payResult.setAuthID();
        payResult.setOrderNum(icbcResPayOnce.getOrderNum());
        //payResult.setRefNum(icbcResPayOnce.getRefNum());
        //payResult.setTraceNum();
        payResult.setTransTime(icbcResPayOnce.getTransTime());
        //payResult.setBatchNum();
        //payResult.setHpAmount(icbcResPayOnce.getHpAmount());
        //payResult.setHpFee(icbcResPayOnce.getHpFree());

        return payResult;
    }

    public PayResult cancelPay(Payment payment, PayResult prePayResult) {
        //TODO:暂不实现
        return null;
    }

    private boolean login(){
        ICBCCallService icbcCallService=this.createCallService();
        ICBCReqBaseInfo icbcReqBaseInfo=new ICBCReqBaseInfo();
        icbcReqBaseInfo.setMerchantNo(merchantNo);
        icbcReqBaseInfo.setProdNo(prodNo);
        icbcReqBaseInfo.setReqType(ICBCReqType.LOGIN);
        icbcReqBaseInfo.setTraceNum(this.getTraceNub());

        ICBCResBaseInfo icbcResBaseInfo = icbcCallService.signIn(icbcReqBaseInfo);
        icbcCallService.close();
        if(!icbcResBaseInfo.isbSucc())
        {
            return false;
        }
        return true;
    }

    private void logout(){}
}
