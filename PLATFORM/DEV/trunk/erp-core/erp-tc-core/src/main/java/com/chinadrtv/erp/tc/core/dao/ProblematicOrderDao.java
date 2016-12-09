/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.tc.core.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.ProblematicOrder;
import com.chinadrtv.erp.tc.core.dto.ProblematicOrderDto;

/**
 * 2013-7-24 下午2:19:14
 * @version 1.0.0
 * @author yangfei
 *
 */
public interface ProblematicOrderDao extends GenericDao<ProblematicOrder, String>{
	List<Map<String, Object>> query(ProblematicOrderDto problematicOrderDto, DataGridModel dataModel);
	
	Integer queryCount(ProblematicOrderDto problematicOrderDto);
	
	int replyProblematicOrder(ProblematicOrderDto problematicOrderDto);
}
