package com.chinadrtv.erp.oms.service.impl;
import com.chinadrtv.erp.model.EdiClear;
import com.chinadrtv.erp.oms.service.OrderFeedbackService;
import com.chinadrtv.erp.oms.dao.OrderFeedbackDao;
import com.chinadrtv.erp.oms.dto.EdiClearDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * 订单反馈服务
 *  
 * @author haoleitao
 * @date 2013-4-27 上午11:20:31
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("orderFeedbackService")
public class OrderFeedbackServiceImpl implements OrderFeedbackService{

    @Autowired
    private OrderFeedbackDao orderFeedbackDao;

    @Autowired
    private RestTemplate template;


    public void addOrderFeedback(EdiClear orderFeedback) {
        orderFeedbackDao.save(orderFeedback);
    }



    public void saveOrderFeedback(EdiClear orderFeedback) {
        orderFeedbackDao.saveOrUpdate(orderFeedback);
    }

    public void delOrderFeedback(Long id) {
        orderFeedbackDao.remove(id);
    }
	
	public EdiClear getOrderFeedbackById(String orderFeedbackId){
		return orderFeedbackDao.get(Long.valueOf(orderFeedbackId));
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.OrderFeedbackService#getAllOrderFeedback()
	 */
	public List<EdiClear> getAllOrderFeedback() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.OrderFeedbackService#getAllOrderFeedback(int, int)
	 */
	public List<EdiClearDto> getAllOrderFeedback(String companyid,int state,int settleType,Date beginDate,Date endDate,int index, int size,Boolean remark) {
		// TODO Auto-generated method stub
		return orderFeedbackDao.getAllOrderFeedback(companyid,state,settleType, beginDate, endDate, index, size,remark);
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.OrderFeedbackService#getOrderFeedbackCount()
	 */
	public int getOrderFeedbackCount(String companyid,int state,int settleType,Date beginDate,Date endDate,Boolean remark) {
		// TODO Auto-generated method stub
		return orderFeedbackDao.getOrderFeedbackCount(companyid,state, settleType,beginDate, endDate,remark);
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.OrderFeedbackService#removeOrderFeedback(com.chinadrtv.erp.model.OrderFeedback)
	 */
	public void removeOrderFeedback(EdiClear orderFeedback) {
		// TODO Auto-generated method stub
		
	}

    public List accountShipment(String url, String clearIds, String companyId, String userId) {
        Map params = new HashMap();
        params.put("companyid", companyId);
        params.put("userId", userId);
        List list = new ArrayList();
        for (String clearId : clearIds.split(",")) {
            params.put("clearid", clearId.split("&")[0]);
            params.put("orderid", clearId.split("&")[1]);
            list.add(template.postForObject(url, params, Map.class));

        }
        return list;
    }
}
