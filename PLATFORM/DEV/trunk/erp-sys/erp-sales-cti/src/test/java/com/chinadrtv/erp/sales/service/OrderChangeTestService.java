package com.chinadrtv.erp.sales.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.OrderChange;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-13
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderChangeTestService extends GenericService<OrderChange, Long> {
    OrderChange getOrderChange(Long orderChangeId);
    OrderChange requiredOrderChange(Long orderChangeId);
    void getOrderChangeEmbed(Long orderChangeId);
    void requiredOrderChangeEmbed(Long orderChangeId);
    void getOrderChangeException(Long orderChangeId);
    void requiredOrderChangeException(Long orderChangeId);
    void getForUpdate(Long orderChangeId);
    void update(Long orderChangeId);
    void updateException(Long orderChangeId);

    //required
    void requiredForUpdate(Long orderChangeId);
    void requiredUpdatePostFee(Long orderChangeId);
    void requiredUpdatePostFeeException(Long orderChangeId);

    //supports
    void supportsUpdatePostFee(Long orderChangeId);
    void supportsUpdatePostFeeException(Long orderChangeId);

    //requiresNew
    void requiresNewPostFee(Long orderChangeId);

    void mandatoryUpdatePostFee(Long orderChangeId);
    OrderChange mergeBatchOrderChanges(List<Long> orderChangeIdList);

    //nested
    void nestedInvoiceTitle(Long orderChangeId);
    void nestedInvoiceTitleException(Long orderChangeId);
    void nestedPostFee(Long orderChangeId);

    //
    void updateNew();
    void updateRemove();
    List<OrderChange> getOrderChangeList();

}
