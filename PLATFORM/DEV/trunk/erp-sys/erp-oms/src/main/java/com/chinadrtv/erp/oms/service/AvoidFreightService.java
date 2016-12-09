package com.chinadrtv.erp.oms.service;

import java.util.Map;

import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.AvoidFreightDto;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-27
 * Time: 下午12:56
 * To change this template use File | Settings | File Templates.
 * 免运费登记Service
 */
public interface AvoidFreightService {

    //分页显示数据
    public Map<String, Object> getAvoidFreightDtoList(AvoidFreightDto avoidFreightDto, DataGridModel dataModel);

    //免运费登记
    public void registerAvoidFreight(Long id,String afterFreight) throws BizException;

}
