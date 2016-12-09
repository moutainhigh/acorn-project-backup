package com.chinadrtv.erp.oms.service;
import com.chinadrtv.erp.model.PreTradeDetail;
import java.util.*;
/**
 * 前置订单详细服务
 *  
 * @author haoleitao
 * @date 2013-4-27 上午11:26:18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PreTradeDetailService{
    PreTradeDetail getCompanyById(String preTradeDetailId);
    List<PreTradeDetail> getAllPreTradeDetail();
    List<PreTradeDetail> getAllPreTradeDetail(int index, int size);
    int getPreTradeDetailCount();
    void savePreTradeDetail(PreTradeDetail preTradeDetail);
    void addPreTradeDetail(PreTradeDetail preTradeDetail);
    void removePreTradeDetail(PreTradeDetail preTradeDetail);
    List<PreTradeDetail> getAllPreTradeDetailByPerTradeID(String preTradeID);
}
