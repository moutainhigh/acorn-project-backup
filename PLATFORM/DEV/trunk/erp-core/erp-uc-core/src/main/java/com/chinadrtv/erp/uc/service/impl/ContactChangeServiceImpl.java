package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.ContactChange;
import com.chinadrtv.erp.uc.dao.ContactChangeDao;
import com.chinadrtv.erp.uc.service.ContactChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Title: ContactChangeServiceImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Service
public class ContactChangeServiceImpl extends GenericServiceImpl<ContactChange, Long> implements ContactChangeService {

    @Autowired
    private ContactChangeDao contactChangeDao;

    @Override
    protected GenericDao<ContactChange, Long> getGenericDao() {
        return contactChangeDao;
    }

    @Override
    public ContactChange queryByBpmInstID(String bpmInstID) {
        return contactChangeDao.queryByBpmInstID(bpmInstID);
    }
}
