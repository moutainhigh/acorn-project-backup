package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.TransferBlackList;

/**
 * Created with IntelliJ IDEA.
 * Title: TransferBlackListService
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface TransferBlackListService extends GenericService<TransferBlackList, String> {
    /**
     * 检查是否是黑名单客户
     *
     * @param contactId
     * @return
     */
    public boolean checkContactBlackList(String contactId);
}
