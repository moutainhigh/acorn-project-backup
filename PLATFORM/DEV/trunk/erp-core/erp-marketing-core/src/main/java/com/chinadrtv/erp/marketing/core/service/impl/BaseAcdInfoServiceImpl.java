package com.chinadrtv.erp.marketing.core.service.impl;

import com.chinadrtv.erp.marketing.core.dao.BaseAcdInfoDao;
import com.chinadrtv.erp.marketing.core.service.BaseAcdInfoService;
import com.chinadrtv.erp.model.marketing.BaseAcdInfo;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi.gao
 * Date: 14-1-24
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
@Service("baseAcdInfoService")
public class BaseAcdInfoServiceImpl implements BaseAcdInfoService {

    @Autowired
    private BaseAcdInfoDao baseAcdInfoDao;

    @ReadThroughSingleCache(namespace="com.chinadrtv.erp.marketing.core.service.baseAcdInfoService.getAllBaseAcds",expiration=1800)
    public List<BaseAcdInfo> getAllBaseAcds() {
        return baseAcdInfoDao.getAllBaseAcds();
    }

    @ReadThroughSingleCache(namespace="com.chinadrtv.erp.marketing.core.service.baseAcdInfoService.getBaseAcdsByArea",expiration=1800)
    public List<BaseAcdInfo> getBaseAcdsByArea(
            @ParameterValueKeyProvider String area) {
        return baseAcdInfoDao.getBaseAcdsByArea(area);
    }

    @ReadThroughSingleCache(namespace="com.chinadrtv.erp.marketing.core.service.baseAcdInfoService.getBaseAcdsByDeptNo",expiration=1800)
    public List<BaseAcdInfo> getBaseAcdsByDeptNo(
            @ParameterValueKeyProvider String deptNo) {
        return baseAcdInfoDao.getBaseAcdsByDeptNo(deptNo);
    }
}
