package com.chinadrtv.erp.pay.core.icbc.service.impl;

import com.chinadrtv.erp.pay.core.icbc.model.InvokeListener;
import com.chinadrtv.erp.pay.core.icbc.model.*;
import com.chinadrtv.erp.pay.core.icbc.service.ICBCCallService;
import com.chinadrtv.erp.pay.core.icbc.service.ICBCCryptographyService;
import com.chinadrtv.erp.pay.core.icbc.service.SimpleInvokeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service("ICBCCallServiceImpl")
public class ICBCCallServiceImpl implements ICBCCallService,InvokeListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ICBCCallServiceImpl.class);

    public ICBCCallServiceImpl()
    {
        logger.debug("ICBCCallServiceImpl is created");
        System.out.println("ICBCCallServiceImpl is created");
    }
    @Autowired
    private SimpleInvokeService simpleInvokeService;
    @Autowired
    private ICBCCryptographyService icbcCryptographyService;

    private byte[] responseData;

    public void init(String serverIp, int port,int timeOut)
    {
        simpleInvokeService.init(serverIp, port, timeOut);
        simpleInvokeService.addListener(this);
    }

    public ICBCResPay pay(ICBCReqPay icbcReqPay) {
        responseData=null;
        ICBCResPay icbcResPay=new ICBCResPay();
        simpleInvokeService.callService(icbcReqPay,icbcResPay);
        return icbcResPay;
    }

    public ICBCResPayOnce payOnce(ICBCReqPayOnce icbcReqPayOnce)
    {
        responseData=null;
        ICBCResPayOnce icbcResPayOnce=new ICBCResPayOnce();
        simpleInvokeService.callService(icbcReqPayOnce,icbcResPayOnce);
        return icbcResPayOnce;
    }

    public ICBCResBaseInfo authentication(ICBCReqAuthentication icbcReqAuthentication)
    {
        responseData=null;
        ICBCResBaseInfo icbcResBaseInfo=new ICBCResBaseInfo();
        simpleInvokeService.callService(icbcReqAuthentication,icbcResBaseInfo);
        return icbcResBaseInfo;
    }

    public void marshaling(MarshalEvent marshalEvent) {
    }

    public void marshaled(TransformEvent transformEvent) {
        //增加长度标识
        String str=transformEvent.getData();
        String length=String.format("%05d",str.length()+1);
        transformEvent.setData(length+ "|"+str);
    }

    public void transforming(TransformEvent transformEvent) {
        String ss=transformEvent.getData();
        if (ss.getBytes().length % 8 != 0) {
            int flag = ss.getBytes().length + 8;
            while ((ss.getBytes().length <= flag) && (ss.getBytes().length % 8 != 0)) {
                ss = ss + " ";
            }
        }
        transformEvent.setData(ss);
    }

    public void transformed(InvokeEvent invokeEvent) {
        //TODO:加密前是否需要8字节对齐
        //加密
        invokeEvent.setData(icbcCryptographyService.encryptDES(invokeEvent.getData()));
    }

    public void invoking(InvokeEvent invokeEvent) {
    }

    public void invoked(InvokeEvent invokeEvent) {
    }

    public void reverseTransforming(InvokeEvent invokeEvent) {
        //解密-首先保留所有的接收数据，这样可以后续解密成功完整的数据
        byte[] recvData=null;
        if(responseData==null)
        {
            recvData=invokeEvent.getData();
        }else
        {
            recvData=new byte[responseData.length+invokeEvent.getData().length];
            System.arraycopy(responseData,0,recvData,0,responseData.length);
            System.arraycopy(invokeEvent.getData(),0,recvData,responseData.length,invokeEvent.getData().length);
        }
        responseData=recvData;

        if(recvData.length%8!=0)
        {
/*            if(recvData.length==17)
            {
                byte[] buffer=new byte[16];
                System.arraycopy(buffer,0,recvData,0,16);
                recvData=buffer;
            }*/
            /*int paddingLen=8-recvData.length%8;
            byte[] paddings=new byte[paddingLen];
            for(int i=0;i<paddingLen;i++)
            {
                paddings[i]=0;
            }
            byte[] buff=new byte[recvData.length+paddingLen];
            System.arraycopy(buff,0,recvData,0,recvData.length);
            System.arraycopy(buff,recvData.length,paddings,0,paddings.length);
            recvData=buff; */

        }
        try{
            invokeEvent.setData(icbcCryptographyService.decryptDES(recvData));
        }catch (Exception exp)
        {
            invokeEvent.setbSucc(false);
        }
    }

    public void reverseTransformed(TransformEvent transformEvent) {
    }

    public void unmarshaling(TransformEvent transformEvent) {
        //将前缀元数据信息去掉，并且判断是否数据完整
        String str=transformEvent.getData();
        if(StringUtils.isEmpty(str))
        {
            transformEvent.setbSucc(false);
            return;
        }
        if(str.length()<=6)
        {
            transformEvent.setbSucc(false);
            return;
        }
        int index=transformEvent.getData().indexOf("|");
        if(index!=5)
        {
            throw new RuntimeException("接收数据不合法");
        }
        String prefix=str.substring(0,5);
        int length=Integer.parseInt(prefix);
        String data=str.substring(6);
        if(length-12>data.length())
        {
            //继续接收
            transformEvent.setbSucc(false);
        }
        else
        {
            transformEvent.setData(data);
            //transformEvent.setData("RES12|0||1|test|test|98|99|20131010|1500|1000|1|10000|8000|0|100|");
        }
    }

    public void unmarshaled(MarshalEvent marshalEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void close()
    {
        if(simpleInvokeService!=null)
        {
            simpleInvokeService.close();
        }
    }

    public ICBCResBaseInfo signIn(ICBCReqBaseInfo reqBaseInfo)
    {
        responseData=null;
        ICBCResBaseInfo icbcResBaseInfo=new ICBCResBaseInfo();
        simpleInvokeService.callService(reqBaseInfo,icbcResBaseInfo);
        return icbcResBaseInfo;
    }

    public ICBCResBaseInfo signOut(ICBCReqBaseInfo reqBaseInfo)
    {
        //TODO:后期实现
        return null;
    }
}
