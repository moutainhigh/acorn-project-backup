package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.service.Cusnote;
import com.chinadrtv.erp.model.service.CusnoteId;
import com.chinadrtv.erp.uc.dao.CusnoteDao;
import com.chinadrtv.erp.uc.dto.CusnoteDto;
import com.chinadrtv.erp.uc.service.CusnoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xieguoqiang on 14-4-23.
 */
@Service
public class CusnoteServiceImpl extends
        GenericServiceImpl<Cusnote, CusnoteId> implements CusnoteService {

    @Autowired
    private CusnoteDao cusnoteDao;
    @Override
    protected GenericDao<Cusnote, CusnoteId> getGenericDao() {
        return cusnoteDao;
    }
    @Override
    public Map<String, Object> getCusnoteByOrderid(String orderId) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("total", cusnoteDao.getOrderCusnoteCountByOrderid(orderId));
        pageMap.put("rows", cusnoteDao.getOrderCusnoteByOrderid(orderId));
        return pageMap;
    }

    @Override
    public int getCusnoteCountByOrderid(String orderId) {
        return cusnoteDao.getOrderCusnoteCountByOrderid(orderId);
    }

    @Override
    public Map<String, Object> queryCusnote(CusnoteDto cusnoteDto, DataGridModel dataGrid) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("total", cusnoteDao.queryCusnoteCount(cusnoteDto));
        pageMap.put("rows", cusnoteDao.queryCusnote(cusnoteDto, dataGrid));
        return pageMap;
    }

    @Override
    public Cusnote queryCusnoteById(CusnoteId cusnoteId) {
        return cusnoteDao.get(cusnoteId);
    }

    @Override
    public boolean haveNoRepalyNotice(String userId, String customerId) {
        return cusnoteDao.haveNoRepalyNotice(userId, customerId);
    }

    public int queryCountNoRepalyNotice(String userId) {
        return cusnoteDao.queryCountNoRepalyNotice(userId);
    }
}
