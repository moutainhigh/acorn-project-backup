package com.chinadrtv.erp.ic.service.impl;

import com.chinadrtv.erp.ic.dao.ProductGrpLimitDao;
import com.chinadrtv.erp.ic.service.ProductGrpLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 13-5-14
 * Time: 上午11:07
 * To change this template use File | Settings | File Templates.
 */
@Service("productGrpLimitService")
public class ProductGrpLimitServiceImpl implements ProductGrpLimitService {

    @Autowired
    private ProductGrpLimitDao productGrpLimitDao;

    /**
     * 是否有效价格
     * @param grpId
     * @param prodId
     * @param price
     * @return
     */
    public Boolean isValidPrice(String grpId, String prodId, Double price) {
        return productGrpLimitDao.isValidPrice(grpId, prodId, price);
    }

    /**
     * 获取渠道商品价格
     * @param grpId
     * @param prodId
     * @return
     */
    public Double getLpPrice(String grpId, String prodId)
    {
        return productGrpLimitDao.getLpPrice(grpId, prodId);
    }
}
