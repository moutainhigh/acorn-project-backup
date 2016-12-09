package com.chinadrtv.erp.ic.service;

/**
 * 渠道商品价格
 * User: gaodejian
 * Date: 13-5-14
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates.
 */
public interface ProductGrpLimitService {
    Boolean isValidPrice(String grpId, String prodId, Double price);
    Double getLpPrice(String grpId, String prodId);
}
