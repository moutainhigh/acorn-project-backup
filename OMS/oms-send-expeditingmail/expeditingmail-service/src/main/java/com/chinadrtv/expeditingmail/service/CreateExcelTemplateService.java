package com.chinadrtv.expeditingmail.service;

import com.chinadrtv.expeditingmail.common.dal.model.DeliveryMailDetail;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-21
 * Time: 下午2:19
 * To change this template use File | Settings | File Templates.
 */
public interface CreateExcelTemplateService {
    //创建数据模板
    byte[] createExcel(List<DeliveryMailDetail> list) throws Exception;
}
