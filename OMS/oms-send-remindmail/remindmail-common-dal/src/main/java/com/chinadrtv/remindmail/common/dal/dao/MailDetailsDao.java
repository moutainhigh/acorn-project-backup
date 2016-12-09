package com.chinadrtv.remindmail.common.dal.dao;

import com.chinadrtv.remindmail.common.dal.model.MailDetails;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-27
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
public interface MailDetailsDao {
    //按申请日期时间段获取信息
    List<MailDetails> findMailDetailsByAppDate(Map map);

    //修改状态
    int updateOrderurgentapplication(Map map);
}
