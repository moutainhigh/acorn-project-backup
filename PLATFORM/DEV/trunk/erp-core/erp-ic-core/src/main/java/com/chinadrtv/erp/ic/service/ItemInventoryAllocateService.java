package com.chinadrtv.erp.ic.service;

import com.chinadrtv.erp.model.inventory.AllocatedEvent;

/**
 * User: gaodejian
 * Date: 13-2-4
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public interface ItemInventoryAllocateService {
    void assign(AllocatedEvent event);
    void unassign(AllocatedEvent event);
}
