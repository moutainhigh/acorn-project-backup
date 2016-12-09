package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.Priority;
import com.chinadrtv.erp.uc.dao.PriorityDao;
import com.chinadrtv.erp.uc.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title: PriorityServiceImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Service("priorityServie")
public class PriorityServiceImpl extends GenericServiceImpl<Priority, String> implements PriorityService {

    @Autowired
    private PriorityDao priorityDao;

    @Override
    protected GenericDao<Priority, String> getGenericDao() {
        return priorityDao;
    }

    public List<Priority> findAll() {
        return priorityDao.getAll();
    }
}
