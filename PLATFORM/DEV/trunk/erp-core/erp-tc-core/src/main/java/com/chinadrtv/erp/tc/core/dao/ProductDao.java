package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Product;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-3
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ProductDao extends GenericDao<Product, String> {
    List<Product> getProductsFromProdIds(List<String> prodIdList);
}
