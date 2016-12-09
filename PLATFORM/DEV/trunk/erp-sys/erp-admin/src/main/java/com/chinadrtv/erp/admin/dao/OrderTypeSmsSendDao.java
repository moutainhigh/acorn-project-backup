package com.chinadrtv.erp.admin.dao;


import com.chinadrtv.erp.admin.model.OrderTypeSmsSend;
import com.chinadrtv.erp.core.dao.GenericDao;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 12-11-13
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
        public interface OrderTypeSmsSendDao extends GenericDao<OrderTypeSmsSend, Integer> {
          public List<OrderTypeSmsSend> searchPaginatedOrderTypeSmsSendByAppDate(String v_smsType,String v_ordertype,String v_setProName,String v_smsname,
                                                                                 String usid,String paytype,Date beginDate, Date endDate,int startIndex, Integer numPerPage);
          public void saveOrUpdate(OrderTypeSmsSend orderTypeSmsSend);
          public int getOrderTypeSmsSendCountByAppDate(String v_smsType,String v_ordertype,String v_setProName,String v_smsname,
                                                       String usid,String paytype,Date beginDate, Date endDate);
           public void addOrderTypeSmsSend(OrderTypeSmsSend orderTypeSmsSend);

    }
