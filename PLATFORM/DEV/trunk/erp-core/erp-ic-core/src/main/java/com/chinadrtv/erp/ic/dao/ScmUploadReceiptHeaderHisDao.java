package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.inventory.ScmUploadReceiptHeaderHis;

import java.util.List;

/**
 * 结算单数据访问接口
 * User: Administrator
 * Date: 13-1-11
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */
public interface ScmUploadReceiptHeaderHisDao extends GenericDao<ScmUploadReceiptHeaderHis,Long> {
    List<ScmUploadReceiptHeaderHis> getUnhandledReceipts();
    void batchLog(ScmUploadReceiptHeaderHis receipt);
}
