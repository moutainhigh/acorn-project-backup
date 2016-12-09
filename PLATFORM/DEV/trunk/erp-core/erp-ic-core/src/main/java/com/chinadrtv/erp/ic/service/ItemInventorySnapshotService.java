package com.chinadrtv.erp.ic.service;

/**
 * User: gaodejian
 * Date: 13-2-4
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public interface ItemInventorySnapshotService {
    void capture(String batchUser);
    void captureEx(String batchUser);
}
