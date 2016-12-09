package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.UserProduct;

import java.util.List;

/**
 * 用户关注
 * User: gdj
 * Date: 13-7-8
 * Time: 上午11:01
 * To change this template use File | Settings | File Templates.
 */
public interface UserProductDao extends GenericDao<UserProduct, Long> {
    List<UserProduct> getUserProducts(String userId);
    UserProduct getUserProduct(String userId, String productId);
    UserProduct getUserProduct(String userId, String productId, String productType);
    Long getUserProductCount(String userId);
}
