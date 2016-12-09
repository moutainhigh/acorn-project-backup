package com.chinadrtv.erp.marketing.core.dao;

import com.chinadrtv.erp.model.marketing.BaseAcdInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi.gao
 * Date: 14-1-24
 * Time: 下午2:20
 * To change this template use File | Settings | File Templates.
 */
public interface BaseAcdInfoDao {
    List<BaseAcdInfo> getAllBaseAcds();
    List<BaseAcdInfo> getBaseAcdsByArea(String area);
    List<BaseAcdInfo> getBaseAcdsByDeptNo(String deptNo);
}
