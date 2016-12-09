package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.TransferBlackList;
import com.chinadrtv.erp.uc.dao.TransferBlackListDao;
import com.chinadrtv.erp.uc.service.TransferBlackListService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Title: TransferBlackListServiceImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Service
public class TransferBlackListServiceImpl extends
        GenericServiceImpl<TransferBlackList, String> implements TransferBlackListService {
    @Autowired
    private TransferBlackListDao transferBlackListDao;

    protected GenericDao<TransferBlackList, String> getGenericDao() {
        return transferBlackListDao;
    }

    @Override
    public boolean checkContactBlackList(String contactId) {
        String transferContactId = transferBlackListDao.getValidContactBlackList(contactId);
        return StringUtils.isBlank(transferContactId) ? false : true;
    }
}
