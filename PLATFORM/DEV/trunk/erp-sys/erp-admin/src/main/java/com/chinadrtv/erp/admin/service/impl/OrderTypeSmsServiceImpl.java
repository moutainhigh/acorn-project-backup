package com.chinadrtv.erp.admin.service.impl;

import com.chinadrtv.erp.admin.dao.OrderTypeSmsSendDao;
import com.chinadrtv.erp.admin.model.OrderTypeSmsSend;
import com.chinadrtv.erp.admin.service.OrderTypeSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 12-11-13
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
@Service("OrderTypeSmsService")
public class OrderTypeSmsServiceImpl implements OrderTypeSmsService{
    @Autowired
    private OrderTypeSmsSendDao orderTypeSmsSendDao;
    /*
      添加数据
     */
    public void addOrderTypeSmsSend(OrderTypeSmsSend orderTypeSmsSend) {
        orderTypeSmsSendDao.save(orderTypeSmsSend);
    }
    /*
     获得数据行
    */
    public int getOrderTypeSmsSendCountByAppDate(String v_smsType,String  v_ordertype,String v_setProName,String v_smsname,
                                                 String usid,String paytype,Date beginDate, Date endDate) {
        return   orderTypeSmsSendDao.getOrderTypeSmsSendCountByAppDate(v_smsType,v_ordertype,v_setProName,v_smsname,
                 usid,paytype,beginDate, endDate);  //To change body of implemented methods use File | Settings | File Templates.
    }
    /*
     获取绑定数据信息
    */
    public List<OrderTypeSmsSend> searchPaginatedOrderTypeSmsSendByAppDate(String v_smsType,String  v_ordertype,String v_setProName,String v_smsname,
                                                                           String usid,String paytype,Date beginDate, Date endDate, int startIndex, Integer numPerPage) {
        return orderTypeSmsSendDao.searchPaginatedOrderTypeSmsSendByAppDate(v_smsType,v_ordertype,v_setProName,v_smsname,usid,paytype,beginDate,endDate,startIndex,numPerPage);  //To change body of implemented methods use File | Settings | File Templates.
    }
    /*
     添加修改数据
    */
    public void saveOrderTypeSmsSend(OrderTypeSmsSend orderTypeSmsSend) {
        orderTypeSmsSendDao.saveOrUpdate(orderTypeSmsSend);
    }
    /*
     删除数据按编号
    */
    public void delOrderTypeSmsLog(Integer id){
        orderTypeSmsSendDao.remove(id);
    }

}
