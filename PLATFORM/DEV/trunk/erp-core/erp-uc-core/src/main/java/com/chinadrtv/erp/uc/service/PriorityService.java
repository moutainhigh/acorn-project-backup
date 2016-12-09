package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.Priority;

import java.util.List;

public interface PriorityService extends GenericService<Priority, String> {
    List<Priority> findAll();
}
