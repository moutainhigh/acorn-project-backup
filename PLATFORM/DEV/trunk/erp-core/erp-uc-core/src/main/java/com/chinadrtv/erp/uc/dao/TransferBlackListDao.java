package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.TransferBlackList;

/**
 * Created with IntelliJ IDEA.
 * Title: TransferBlackListDao
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface TransferBlackListDao extends GenericDao<TransferBlackList, String> {

    /**
     * 根据contactId获取有效的黑名单
     *
     * @param contactId
     * @return
     */
    public String getValidContactBlackList(String contactId);
}
