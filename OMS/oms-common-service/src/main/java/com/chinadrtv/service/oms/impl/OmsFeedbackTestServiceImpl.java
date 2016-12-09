package com.chinadrtv.service.oms.impl;

import com.chinadrtv.dal.iagent.dao.ProvinceDao;
import com.chinadrtv.model.iagent.Province;
import com.chinadrtv.service.oms.OmsFeedbackTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-19
 * Time: 下午1:26
 * To change this template use File | Settings | File Templates.
 */
@Service("OmsFeedbackTestService")
public class OmsFeedbackTestServiceImpl implements OmsFeedbackTestService {
    @Autowired
    private ProvinceDao provinceDao;
    @Override
    public int omsTestConnectDate() {
        int result = 0;
        Province c = provinceDao.queryById("01");
        if(c != null){
            result = 1;
        }
        return result;
    }
}
