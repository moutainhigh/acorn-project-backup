package com.chinadrtv.runtime.ws;

import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.runtime.ws.bean.WsAddressBean;

public class WsServiceAddressListener extends JmsListener<WsAddressBean> {

    @Override
    public void messageHandler(WsAddressBean msg) throws Exception {
        // TODO Auto-generated method stub

    }

}
