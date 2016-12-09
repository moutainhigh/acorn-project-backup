package com.chinadrtv.erp.marketing.core.service;

import com.chinadrtv.erp.model.marketing.UserBpm;

/**
 * Created with IntelliJ IDEA.
 * Title: UserBpmService
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface UserBpmService {
    /**
     * 根据ID查找
     * @param id
     * @return
     */
    UserBpm get(Long id);
}
