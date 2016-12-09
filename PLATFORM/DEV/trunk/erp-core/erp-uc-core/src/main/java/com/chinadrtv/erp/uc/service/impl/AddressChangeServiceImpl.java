package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.AddressChange;
import com.chinadrtv.erp.uc.dao.AddressChangeDao;
import com.chinadrtv.erp.uc.service.AddressChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Title: AddressChangeServiceImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Service
public class AddressChangeServiceImpl extends GenericServiceImpl<AddressChange, Long> implements AddressChangeService {

    @Autowired
    private AddressChangeDao addressChangeDao;

    @Override
    public AddressChange queryByBpmInstID(String bpmInstID) {
        return addressChangeDao.queryByBpmInstID(bpmInstID);
    }


    @Override
    protected GenericDao<AddressChange, Long> getGenericDao() {
        return addressChangeDao;
    }
}
