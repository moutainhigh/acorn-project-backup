package com.chinadrtv.logistics.dal.bak.dao;

import com.chinadrtv.logistics.dal.model.OmsBackorder;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OmsBackorderDao {
    public List<OmsBackorder> queryBackOrdersByManager(Integer page, Integer rows, String departId);
}
