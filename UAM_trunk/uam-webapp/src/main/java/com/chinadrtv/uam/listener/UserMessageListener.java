package com.chinadrtv.uam.listener;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.uam.model.uam.UamLock;
import com.chinadrtv.uam.service.LockService;
import com.chinadrtv.uam.sync.service.SyncService;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-25
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 */
public class UserMessageListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LockService lockService;
    @Autowired
    private SyncService syncService;

    @Override
    public void onMessage(Message message) {
        String messageLock = "MESSAGE_LOCK";
        UamLock uamLock = lockService.getLockByName(messageLock);

        Integer updateCount = lockService.updateLockVersion(messageLock);
        if (updateCount > 0) {
            try {
                syncService.syncLdapInfo();

                syncService.syncLdapRoles();
            } catch (Exception e) {
                logger.error("同步信息失败", e);
                uamLock.setVersion(0);
            }
            uamLock.setVersion(0);
            lockService.update(uamLock);
        } else {
            System.out.println("未抢到执行1111");
        }


    }
}
