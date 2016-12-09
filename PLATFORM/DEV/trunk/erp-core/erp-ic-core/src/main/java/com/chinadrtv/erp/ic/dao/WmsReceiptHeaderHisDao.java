package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.inventory.WmsReceiptHeaderHis;

import java.util.List;

/**
 * 结算单数据访问接口
 * User: gaodejian
 * Date: 13-1-11
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */
public interface WmsReceiptHeaderHisDao extends GenericDao<WmsReceiptHeaderHis,Long> {
    List<WmsReceiptHeaderHis> getUnhandledReceipts();
    List<WmsReceiptHeaderHis> getArrivedReceipts(List<String> receiptIds);
    WmsReceiptHeaderHis getArrivedReceipt(String receiptId);
    Double getTotalQty(String warehouse, String receiptId, String receiptType, String productCode);
    void batchLog(WmsReceiptHeaderHis receipt);
}
