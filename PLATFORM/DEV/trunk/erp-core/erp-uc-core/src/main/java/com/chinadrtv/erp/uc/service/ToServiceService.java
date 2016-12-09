package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.ToService;
import com.chinadrtv.erp.model.ToserviceId;

import java.util.Map;

public interface ToServiceService extends GenericService<ToService, ToserviceId> {
    Map queryComplainPageList(String contactId, DataGridModel dataGridModel);
}
