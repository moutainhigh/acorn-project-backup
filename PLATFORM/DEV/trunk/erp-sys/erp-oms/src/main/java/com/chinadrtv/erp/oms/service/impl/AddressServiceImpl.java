package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.oms.dao.AddressDao;
import com.chinadrtv.erp.oms.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 地址服务
 * User: gdj
 * Date: 13-4-22
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
@Service("addressService")
public class AddressServiceImpl extends GenericServiceImpl<Address, String> implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    protected GenericDao<Address, String> getGenericDao() {
        return addressDao;
    }

    public Address getAddress(String addressId){
        try
        {
            return addressDao.get(addressId);
        }
        catch (Exception ex){
            return null;
        }
    }
}
