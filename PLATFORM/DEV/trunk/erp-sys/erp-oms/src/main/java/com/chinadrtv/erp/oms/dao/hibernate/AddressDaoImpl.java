package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.oms.dao.AddressDao;
import org.springframework.stereotype.Repository;

/**
 * 地址访问
 * User: gdj
 * Date: 13-4-22
 * Time: 下午4:21
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AddressDaoImpl extends GenericDaoHibernate<Address, String> implements AddressDao {

    public AddressDaoImpl(){
        super(Address.class);
    }
}
