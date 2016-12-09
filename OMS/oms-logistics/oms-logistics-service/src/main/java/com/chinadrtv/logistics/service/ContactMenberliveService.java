package com.chinadrtv.logistics.service;

import com.chinadrtv.logistics.dal.model.Menberlive;

import java.util.List;

/**
 * Created by Net on 14-12-24.
 */
public interface ContactMenberliveService {
    public List<Menberlive> queryMenberlive(String contactid);
}
