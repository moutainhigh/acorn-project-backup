package com.chinadrtv.logistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.chinadrtv.logistics.dal.bak.dao.MenberliveDao;
import java.util.List;
import org.springframework.stereotype.Service;
import com.chinadrtv.logistics.dal.model.Menberlive;
import com.chinadrtv.logistics.service.ContactMenberliveService;

/**
 * Created by ZXL on 14-12-24.
 */
@Service
public class ContactMenberliveImpl  implements ContactMenberliveService {

    @Autowired
    private MenberliveDao menberliveDao;

    public List<Menberlive> queryMenberlive(String contactid){
        return  this.menberliveDao.queryMenberlive(contactid);
    }
}
