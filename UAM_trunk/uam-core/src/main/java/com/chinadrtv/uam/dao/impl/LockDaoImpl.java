package com.chinadrtv.uam.dao.impl;

import com.chinadrtv.uam.dao.LockDao;
import com.chinadrtv.uam.model.uam.UamLock;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-25
 * Time: 下午2:50
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class LockDaoImpl extends HibernateDaoImpl implements LockDao {
    @Override
    public UamLock getUamLockByName(String lockName) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("lockName", lockName);
        UamLock uamLock = (UamLock) findByHql("from UamLock t where t.lockName =:lockName", map);

        if (uamLock == null && StringUtils.isNotBlank(lockName)) {
            UamLock uamLock1 = new UamLock();
            uamLock1.setLockName(lockName);
            uamLock1.setVersion(0);
            Long id = save(uamLock1);
            uamLock1.setId(id);
            return uamLock1;
        }
        return uamLock;
    }

    @Override
    public Integer updateLockVersion(String lockName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("version",1);
        map.put("lockName",lockName);
        int value = executeByHql("update UamLock t  set t.version =:version where t.version=0 and  t.lockName =:lockName", map);
        return value;
    }
}
