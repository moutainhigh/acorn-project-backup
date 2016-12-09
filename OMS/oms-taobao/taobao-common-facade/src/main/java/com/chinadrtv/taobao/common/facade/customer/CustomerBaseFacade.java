package com.chinadrtv.taobao.common.facade.customer;

import javax.jws.WebService;

import com.chinadrtv.taobao.common.facade.bean.customer.CustomerInfoReq;
import com.chinadrtv.taobao.common.facade.bean.customer.CustomerInfoRes;

@WebService
public interface CustomerBaseFacade {

    public CustomerInfoRes customerRegister(CustomerInfoReq customerInfoReq);

    public CustomerInfoRes queryCustInfoByLoginName(String loginName);

}
