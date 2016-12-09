package com.chinadrtv.service.oms.impl;

import com.chinadrtv.dal.oms.dao.ShipmentHeaderDao;
import com.chinadrtv.model.oms.ShipmentHeader;
import com.chinadrtv.service.oms.ShipmentHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-1
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */
@Service("shipmentHeaderService")
public class ShipmentHeaderServiceImpl implements ShipmentHeaderService {
    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;

    public List<ShipmentHeader> findByMailId(String mailId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
