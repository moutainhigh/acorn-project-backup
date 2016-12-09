package com.chinadrtv.erp.sales.service;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Email:liuhaidong@chinadrtv.com
 * Date: 13-9-2
 * Time: 下午5:38
 * To add class comments
 */
public interface IntegrateService {
    void getMandatory(Long orderChangeId);
    void requiredSupportsNew(Long orderChangeId);
    void requiredTwo(Long orderChangeId);
    void requiredTwoException(Long orderChangeId);
    void requiredCatchException(Long orderChangeId);

    void requiredSupports(Long orderChangeId);
    void supportsReq(Long orderChangeId);
    void requiredNested(Long orderChangeId);
}
