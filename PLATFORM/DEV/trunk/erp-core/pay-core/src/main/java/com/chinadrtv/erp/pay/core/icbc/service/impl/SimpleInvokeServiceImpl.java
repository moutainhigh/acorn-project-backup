package com.chinadrtv.erp.pay.core.icbc.service.impl;

import com.chinadrtv.erp.pay.core.icbc.model.InvokeListener;
import com.chinadrtv.erp.pay.core.icbc.model.Marshal;
import com.chinadrtv.erp.pay.core.icbc.model.*;
import com.chinadrtv.erp.pay.core.icbc.service.SimpleInvokeService;
import com.chinadrtv.erp.pay.core.icbc.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class SimpleInvokeServiceImpl implements SimpleInvokeService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SimpleInvokeServiceImpl.class);

    public SimpleInvokeServiceImpl()
    {
        logger.debug("SimpleInvokeServiceImpl is created");
        System.out.println("SimpleInvokeServiceImpl is created");
        listeners=new ArrayList<InvokeListener>();
    }

    @Autowired
    private SocketService socketService;

    private String serverIp;
    private Integer serverPort;
    private Integer timeOut;

    private String encoding;

    private List<InvokeListener> listeners;

    private class Result
    {
        public Result(Object data, boolean bSucc)
        {
            this.data=data;
            this.bSucc=bSucc;
        }
        public boolean bSucc;
        public Object data;
    }

    public Result marshal(Marshal input) {
        MarshalEvent marshalEvent =new MarshalEvent(this, input);
        for (InvokeListener listener:listeners)
        {
            listener.marshaling(marshalEvent);
        }
        String strData= marshalEvent.getInput().marshal();

        TransformEvent transformEvent =new TransformEvent(this,strData);
        for (InvokeListener listener:listeners)
        {
            listener.marshaled(transformEvent);
        }

        return new Result(transformEvent.getData(),true);
    }

    public Result transform(String strData) {
        try{
            TransformEvent transformEvent =new TransformEvent(this,strData);
            for (InvokeListener listener:listeners)
            {
                listener.transforming(transformEvent);
            }
            byte[] data= transformEvent.getData().getBytes(/*encoding*/);

            InvokeEvent invokeEvent =new InvokeEvent(this,data);
            for (InvokeListener listener:listeners)
            {
                listener.transformed(invokeEvent);
            }
            return new Result(invokeEvent.getData(),true);
        }catch (Exception exp)
        {
            throw new RuntimeException(exp.getMessage());
        }
    }

    public Result invoke(byte[] data) {
        InvokeEvent invokeEvent =new InvokeEvent(this,data);
        for (InvokeListener listener:listeners)
        {
            listener.invoking(invokeEvent);
        }

        socketService.connect(serverIp,serverPort,timeOut);
        socketService.send(invokeEvent.getData());

        invokeEvent =new InvokeEvent(this,data);
        for (InvokeListener listener:listeners)
        {
            listener.invoking(invokeEvent);
        }

        return new Result(null,invokeEvent.isbSucc());
    }

    private Result reverseTransform(byte[] datas) {
        try{
            InvokeEvent invokeEvent=new InvokeEvent(this,datas);
            for (InvokeListener listener:listeners)
            {
                listener.reverseTransforming(invokeEvent);
            }
            if(!invokeEvent.isbSucc())
                return new Result(null,false);
            String str=new String(invokeEvent.getData(),"gb2312"/*, encoding*/);

            TransformEvent transformEvent=new TransformEvent(this, str);
            for (InvokeListener listener:listeners)
            {
                listener.reverseTransformed(transformEvent);
            }
            return new Result(transformEvent.getData(),transformEvent.isbSucc());
        }catch (Exception exp)
        {
            throw new RuntimeException(exp.getMessage());
        }
    }

    public Result unmarshal(Marshal output,String data) {
        TransformEvent transformEvent=new TransformEvent(this, data);
        for (InvokeListener listener:listeners)
        {
            listener.unmarshaling(transformEvent);
        }
        output.unmarshal(transformEvent.getData());
        MarshalEvent marshalEvent=new MarshalEvent(this,output);
        for (InvokeListener listener:listeners)
        {
            listener.unmarshaled(marshalEvent);
        }
        return new Result(marshalEvent.getInput(),marshalEvent.isbSucc());
    }

    public void callService(Marshal input,Marshal output)
    {
        Result result=null;
        //首先转换
        result=marshal(input);
        //转换成字节码
        result=transform((String)result.data);

        //SocketService socketService=null;
        //调用服务 注意socketService要多实例的，因为放置了非公用的socket
        invoke((byte[])result.data);

        boolean bFinish=false;
        do{
            bFinish=true;
            //TODO:触发接收前事件
            byte[] data = socketService.recv();
            //TODO:触发接收后事件
            if(data==null)
                return;

            //字节码转换成字符串
            result=reverseTransform(data);
            if(result.bSucc==false)
            {
                bFinish=false;
                continue;
            }
            else
            {
                //反序列化
                result=unmarshal(output,(String)result.data);
                if(result.bSucc==false)
                {
                    bFinish=false;
                    continue;
                }
            }

        }while (bFinish==false);
    }

    public void close()
    {
        if(socketService!=null)
        {
            socketService.close();;
        }
    }

    public void init(String serverIp,int port,int timeOut)
    {
        this.serverIp=serverIp;
        this.serverPort=port;
        this.timeOut=timeOut;
    }

    public void addListener(InvokeListener listener)
    {
        this.listeners.add(listener);
    }
}
