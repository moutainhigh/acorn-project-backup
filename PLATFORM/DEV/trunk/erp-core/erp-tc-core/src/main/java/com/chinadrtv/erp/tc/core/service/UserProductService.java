package com.chinadrtv.erp.tc.core.service;

import com.chinadrtv.erp.model.UserProduct;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-7-8
 * Time: 上午11:09
 * To change this template use File | Settings | File Templates.
 */
public interface UserProductService {
    List<UserProduct> getUserProducts(String userId);
    void addFavorite(String userId, String nccode);
    void addFavorite(String userId, String nccode, String ncfree, String ncfreename);
    void removeFavorite(String userId, Long favoriteId);
}
