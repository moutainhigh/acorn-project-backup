package com.chinadrtv.service.iagent.impl;

import com.chinadrtv.dal.iagent.dao.ProvinceDao;
import com.chinadrtv.model.iagent.Province;
import com.chinadrtv.service.iagent.DetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-27
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates.
 */
@Service("detectService")
public class DetectServiceImpl implements DetectService {
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
