package com.chinadrtv.erp.task.service;
import java.util.List;

import com.chinadrtv.erp.task.core.service.BaseService;
import com.chinadrtv.erp.task.entity.PreTradeDetail;

/**
 * 前置订单详细服务
 * @author haoleitao
 * @date 2013-4-27 上午11:26:18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PreTradeDetailService extends BaseService<PreTradeDetail, Long>{
	
    List<PreTradeDetail> getAllPreTradeDetailByPerTradeID(String preTradeID);

}
