package com.chinadrtv.uam.service;

import com.chinadrtv.uam.model.uam.UamLock;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-25
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
public interface LockService extends  ServiceSupport {
    /**
     * 通过名称获取锁，数据库为空时创建一条并返回
     * @param lockName
     * @return
     */
    public UamLock getLockByName(String lockName);

    /**
     * 更新锁，当锁为0时可以更新
     * @param lockName
     * @return
     */
    public Integer updateLockVersion(String lockName);
}
