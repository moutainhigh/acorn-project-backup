package com.chinadrtv.erp.marketing.core.service;

import com.chinadrtv.erp.model.marketing.BaseAcdInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-24
 * Time: 下午2:31
 * To change this template use File | Settings | File Templates.
 */
public interface BaseAcdInfoService {
    List<BaseAcdInfo> getAllBaseAcds();
    List<BaseAcdInfo> getBaseAcdsByArea(String area);
    List<BaseAcdInfo> getBaseAcdsByDeptNo(String deptNo);
}
