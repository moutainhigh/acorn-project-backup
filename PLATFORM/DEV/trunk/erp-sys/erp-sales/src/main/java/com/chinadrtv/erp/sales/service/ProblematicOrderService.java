/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.service;

import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.ProblematicOrder;
import com.chinadrtv.erp.tc.core.dto.ProblematicOrderDto;

/**
 * 2013-7-25 上午10:22:28
 * @version 1.0.0
 * @author yangfei
 *
 */
public interface ProblematicOrderService extends GenericService<ProblematicOrder, String>{
	Map<String, Object> query(ProblematicOrderDto problematicOrderDto, DataGridModel dataModel) throws Exception;
	
	boolean replyProblematicOrder(ProblematicOrderDto problematicOrderDto);
	
	boolean markProblematicOrder(String userId, String orderId, String problemType, String problemDsc);
	
	ProblematicOrder get(String id);
}
