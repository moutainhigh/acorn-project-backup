package com.chinadrtv.erp.marketing.core.service.impl;

import com.chinadrtv.erp.marketing.core.dao.UserBpmDao;
import com.chinadrtv.erp.marketing.core.service.UserBpmService;
import com.chinadrtv.erp.model.marketing.UserBpm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Title: UserBpmServiceImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Service("userBpmService")
public class UserBpmServiceImpl implements UserBpmService {
    @Autowired
    private UserBpmDao userBpmDao;
    @Override
    public UserBpm get(Long id) {
        return userBpmDao.get(id);
    }
}
