package com.chinadrtv.expeditingmail.common.dal.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.expeditingmail.common.dal.model.DeliveryMailDetail;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-21
 * Time: 下午1:26
 * To change this template use File | Settings | File Templates.
 */
public interface DeliveryMailDetailDao extends BaseDao<DeliveryMailDetail> {

    //调用存储过程
    void execOmsProDeliveryOvertime(Map map);
    //修改状态
    int updateDeliveryMailDetail(String companyId);
    //List<DeliveryMailDetail> findDeliveryMailDetail(String companyId);

}
