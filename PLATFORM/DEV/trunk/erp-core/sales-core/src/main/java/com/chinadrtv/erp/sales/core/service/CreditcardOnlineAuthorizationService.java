package com.chinadrtv.erp.sales.core.service;

import com.chinadrtv.erp.sales.core.model.CreditcardOnlineAuthorization;
import com.chinadrtv.erp.sales.core.model.CreditcardOnlineAuthorizationResponse;
import com.chinadrtv.erp.sales.core.model.CreditcardOnlineAuthorizationReturn;
import com.chinadrtv.erp.sales.core.model.CreditcardOnlineAuthorizationReturnResponse;

/**
 * 信用卡在线索权服务
 * 注意：在进行相关操作前以及操作完成后记录日志，这样程序崩溃或者停止后重启，可以进行恢复操作
 * User: 徐志凯
 * Date: 13-5-27
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface CreditcardOnlineAuthorizationService {
    /**
     * 分期交易
     * @param creditcardOnlineAuthorization
     * @return
     */
    CreditcardOnlineAuthorizationResponse hirePurchase(CreditcardOnlineAuthorization creditcardOnlineAuthorization);

    /**
     * 分期交易撤销
     */
    CreditcardOnlineAuthorizationReturnResponse hirePurchaseReturn(CreditcardOnlineAuthorizationReturn creditcardOnlineAuthorizationReturn);
}
