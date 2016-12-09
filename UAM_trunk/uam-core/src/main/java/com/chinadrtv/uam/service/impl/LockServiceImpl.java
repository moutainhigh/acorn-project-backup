package com.chinadrtv.uam.service.impl;

import com.chinadrtv.uam.dao.LockDao;
import com.chinadrtv.uam.model.uam.UamLock;
import com.chinadrtv.uam.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-25
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class LockServiceImpl extends  ServiceSupportImpl implements LockService {

    @Autowired
    public LockDao uamLockDao;

    @Override
    public UamLock getLockByName(String lockName) {
        return uamLockDao.getUamLockByName(lockName);
    }

    @Override
    public Integer updateLockVersion(String lockName) {
        return uamLockDao.updateLockVersion(lockName);

    }


}
