package com.chinadrtv.uam.dao;

import com.chinadrtv.uam.model.uam.UamLock;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-25
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 */
public interface LockDao {

    public UamLock getUamLockByName(String lockName);

    Integer updateLockVersion(String lockName);
}
