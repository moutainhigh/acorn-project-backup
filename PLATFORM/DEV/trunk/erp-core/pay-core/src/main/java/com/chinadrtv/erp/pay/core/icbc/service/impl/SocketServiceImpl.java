package com.chinadrtv.erp.pay.core.icbc.service.impl;

import com.chinadrtv.erp.pay.core.icbc.service.SocketService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class SocketServiceImpl implements SocketService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SocketServiceImpl.class);

    public SocketServiceImpl()
    {
        logger.debug("SocketServiceImpl is created");
        System.out.println("SocketServiceImpl is created");
    }

    private Socket socket=null;
    private InputStream inputStream=null;
    private byte[] buffer=new byte[1024];

    public byte[] recv() {
        try{
            int count=inputStream.read(buffer);
            if(count>0)
            {
            byte[] data=new byte[count];
            System.arraycopy(buffer,0,data,0,count);
            return data;
            }
            else
                return null;
        }catch (Exception exp)
        {
            throw new RuntimeException(exp.getMessage());
        }
    }

    public void send(byte[] data) {
        try{
            OutputStream os=socket.getOutputStream();
            os.write(data);
            os.flush();
        }catch (Exception exp)
        {
            throw new RuntimeException(exp.getMessage());
        }
    }

    public void connect(String ip, int port,int timeOut) {
        try{
            this.close();
            socket=new Socket(ip, port);
            inputStream=socket.getInputStream();
        }
        catch (Exception exp)
        {
            throw new RuntimeException(exp.getMessage());
        }
    }

    public void close()
    {
        if(socket!=null)
        {
            try{
                inputStream.close();
                socket.close();
            }
            catch (Exception exp)
            {

            }
            socket=null;
        }
    }

}
