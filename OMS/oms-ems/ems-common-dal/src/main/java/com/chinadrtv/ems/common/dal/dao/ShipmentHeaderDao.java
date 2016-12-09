package com.chinadrtv.ems.common.dal.dao;

import com.chinadrtv.ems.common.dal.model.ShipmentHeaderDO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-10-24
 * Time: 下午3:19
 * To change this template use File | Settings | File Templates.
 */
public interface ShipmentHeaderDao {

    //获取entityId
    public List<ShipmentHeaderDO> queryForList(String mailId);
}
