package com.chinadrtv.erp.oms.service;
import com.chinadrtv.erp.model.EdiClear;
import com.chinadrtv.erp.oms.dto.EdiClearDto;

import java.util.*;

/**

/**
 * 承运商手工反馈服务
 *  
 * @author haoleitao
 * @date 2013-4-27 上午11:27:17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderFeedbackService{
	EdiClear getOrderFeedbackById(String orderFeedbackId);
    List<EdiClear> getAllOrderFeedback();
    List<EdiClearDto> getAllOrderFeedback(String companyid,int state,int settleType,Date beginDate,Date endDate,int index, int size,Boolean remark);
    int getOrderFeedbackCount(String companyid,int state,int settleType,Date beginDate,Date endDate,Boolean remark);
    void saveOrderFeedback(EdiClear orderFeedback);
    void addOrderFeedback(EdiClear orderFeedback);
    void removeOrderFeedback(EdiClear orderFeedback);
    List accountShipment(String url, String clearIds, String companyId, String username);
}
