package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.ToService;
import com.chinadrtv.erp.uc.dao.ToServiceDao;
import com.chinadrtv.erp.uc.service.ToServiceService;
import com.chinadrtv.erp.model.ToserviceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title: ToServiceServiceImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Service
public class ToServiceServiceImpl extends
        GenericServiceImpl<ToService, ToserviceId> implements ToServiceService {
    @Autowired
    private ToServiceDao toServiceDao;

    @Override
    protected GenericDao<ToService, ToserviceId> getGenericDao() {
        return toServiceDao;
    }

    @Override
    public Map queryComplainPageList(String contactId, DataGridModel dataGridModel) {
        Map result = new HashMap();
        result.put("rows", toServiceDao.findComplain(contactId, dataGridModel));
        result.put("total", toServiceDao.findComplainPageCount(contactId));
        return result;
    }
}
