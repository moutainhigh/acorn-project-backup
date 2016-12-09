package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.AddressExtChange;
import com.chinadrtv.erp.uc.dao.AddressExtChangeDao;
import com.chinadrtv.erp.uc.service.AddressExtChangeService;
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
public class AddressExtChangeServiceImpl extends GenericServiceImpl<AddressExtChange, Long> implements AddressExtChangeService {

    @Autowired
    private AddressExtChangeDao addressExtChangeDao;

    @Override
    public AddressExtChange queryByBpmInstID(String bpmInstID) {
        return addressExtChangeDao.queryByBpmInstID(bpmInstID);
    }


    @Override
    protected GenericDao<AddressExtChange, Long> getGenericDao() {
        return addressExtChangeDao;
    }
}
