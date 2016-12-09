package com.chinadrtv.erp.tc.core.service.impl;

import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.model.UserProduct;
import com.chinadrtv.erp.tc.core.dao.UserProductDao;
import com.chinadrtv.erp.tc.core.service.UserProductService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 13-7-8
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
@Service("userProductService")
public class UserProductServiceImpl implements UserProductService {

    @Autowired
    private UserProductDao userProductDao;

    @Override
    public List<UserProduct> getUserProducts(String userId) {
        return userProductDao.getUserProducts(userId);
    }

    @Override
    public void addFavorite(String userId, String nccode){
        UserProduct userProduct = new UserProduct();
        userProduct.setProductId(nccode);
        userProduct.setUserId(userId);
        userProduct.setCreateUser(userId);
        userProduct.setCreateDate(new Date());
        userProductDao.saveOrUpdate(userProduct);
    }

    @Override
    public void addFavorite(String userId, String nccode, String ncfree, String ncfreename){
        Long userProdCount= userProductDao.getUserProductCount(userId);
        if(userProdCount >= 20){
            throw new BizException(String.format("当前用户%s关注商品不能超过20条记录",userId));
        }
        UserProduct userProduct;
        if(StringUtils.isNotBlank(ncfreename)){
            userProduct = userProductDao.getUserProduct(userId, nccode, ncfreename);
            if(userProduct != null){
                userProduct.setCreateDate(new Date());
                userProductDao.saveOrUpdate(userProduct);
                return;
                //throw new BizException(String.format("商品%s[%s]已经添加,不能重复添加关注", nccode, ncfreename));
            }
        } else {
            userProduct = userProductDao.getUserProduct(userId, nccode);
            if(userProduct != null){
                userProduct.setCreateDate(new Date());
                userProductDao.saveOrUpdate(userProduct);
                return;
                //throw new BizException(String.format("商品%s已经添加,不能重复添加关注", nccode));
            }
        }
        userProduct = new UserProduct();
        userProduct.setProductId(nccode);
        userProduct.setProductType(ncfreename);
        userProduct.setUserId(userId);
        userProduct.setCreateUser(userId);
        userProduct.setCreateDate(new Date());
        userProductDao.saveOrUpdate(userProduct);
    }

    @Override
    public void removeFavorite(String userId, Long favoriteId) {
        userProductDao.remove(favoriteId);
    }
}
