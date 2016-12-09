package com.chinadrtv.erp.ic.dao;

/**
 * 渠道商品价格
 * User: gdj
 * Date: 13-5-14
 * Time: 上午10:46
 * To change this template use File | Settings | File Templates.
 */
public interface ProductGrpLimitDao {
    Boolean isValidPrice(String grpId, String prodId, Double price);
    Double getLpPrice(String grpId, String prodId);
}
