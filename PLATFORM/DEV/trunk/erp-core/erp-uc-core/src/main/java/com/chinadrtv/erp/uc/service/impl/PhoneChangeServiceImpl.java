package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.PhoneChange;
import com.chinadrtv.erp.uc.dao.PhoneChangeDao;
import com.chinadrtv.erp.uc.service.PhoneChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Title: PhoneChangeServiceImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Service
public class PhoneChangeServiceImpl extends GenericServiceImpl<PhoneChange, Long> implements PhoneChangeService {

    @Autowired
    private PhoneChangeDao phoneChangeDao;

    @Override
    protected GenericDao<PhoneChange, Long> getGenericDao() {
        return phoneChangeDao;
    }

    @Override
    public PhoneChange queryByBpmInstID(String bpmInstID) {
        return phoneChangeDao.queryByBpmInstID(bpmInstID);
    }
}
