package com.chinadrtv.service.oms;

import com.chinadrtv.model.oms.ShipmentHeader;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-1
 * Time: 上午10:32
 * To change this template use File | Settings | File Templates.
 */
public interface ShipmentHeaderService {
    List<ShipmentHeader> findByMailId(String mailId);
}
