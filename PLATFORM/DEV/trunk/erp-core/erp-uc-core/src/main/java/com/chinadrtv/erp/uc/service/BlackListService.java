package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.BlackList;
import com.chinadrtv.erp.model.marketing.LeadInteraction;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title: BlackListService
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface BlackListService extends GenericService<BlackList, Long> {

    /**
     * 增加黑名单
     *
     * @param blackList
     */
    public void addBlackList(BlackList blackList);

    /**
     * 增加黑名单
     */
    public void addBlackPhone(String customerId, String customerType, LeadInteraction leadInteraction, Long phoneId, String phoneNum);

    Map<String, Object> queryWithDetails(String phoneNum, Integer addTimes, Integer status, DataGridModel dataGridModel);

    void addToBlackPhone(Long blackPhoneId);

    void removeFromBlackPhone(Long blackPhoneId);

    /**
     * 把某个时间段内创建的订单计算配送黑名单
     * 为定时任务提供的接口
     * @param minOrderCreateDate 不能为空 格式 yyyy-MM-dd hh:mm:ss
     * @param maxOrderCreateDate 不能为空 格式 yyyy-MM-dd hh:mm:ss
     */
//    public void calculateOrderTransferBlackList(Date minOrderCreateDate, Date maxOrderCreateDate);
}
