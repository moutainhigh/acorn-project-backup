package com.chinadrtv.logistics.dal.bak.dao;

import com.chinadrtv.logistics.dal.model.Menberlive;

import java.util.List;

/**
 * Created by Net on 14-12-24.
 */
public interface MenberliveDao {
    public List<Menberlive> queryMenberlive(String contactid);
}
