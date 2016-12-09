package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.BlackList;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title: BlackListDao
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface BlackListDao extends GenericDao<BlackList, Long> {

    /**
     * 电话是否是第二个坐席增加到黑名单
     *
     * @param blackList
     * @return
     */
    boolean isSecondUserAdd(BlackList blackList);

    /**
     * 根据leadInteractionId查找是否有加黑记录
     *
     * @param leadInteractionId
     * @return
     */
    BlackList findByLeadInteractionId(String leadInteractionId);

    List<BlackList> findByBlackPhoneId(Long blackPhoneId);
}
