package com.chinadrtv.erp.admin.service.impl;

import com.chinadrtv.erp.admin.service.OnlieShopService;
import com.chinadrtv.erp.admin.dao.OnlieShopDao;
import com.chinadrtv.erp.admin.model.OnlieShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
@Service("OnlieShopService")
public class OnlieShopServiceImpl implements OnlieShopService {
    @Autowired
    private OnlieShopDao dao;

    public List<OnlieShop> getAllOnliShop() {
        return dao.getAllOnliShop();
    }


}
