package com.chinadrtv.amazon.common.facade.customer;

import javax.jws.WebService;

import com.chinadrtv.amazon.common.facade.bean.customer.CustomerInfoReq;
import com.chinadrtv.amazon.common.facade.bean.customer.CustomerInfoRes;

@WebService
public interface CustomerBaseFacade {

    public CustomerInfoRes customerRegister(CustomerInfoReq customerInfoReq);

    public CustomerInfoRes queryCustInfoByLoginName(String loginName);

}
