package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.ToService;
import com.chinadrtv.erp.model.ToserviceId;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title: ToServiceDao
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface ToServiceDao extends GenericDao<ToService, ToserviceId> {
    int findComplainPageCount(String contactId);

    List<ToService> findComplain(String contactId, DataGridModel dataGridModel);
}
