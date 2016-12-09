package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.model.agent.ContactinsureHistory;
import com.chinadrtv.erp.uc.dao.ContactinsureHistoryDao;
import com.chinadrtv.erp.uc.service.ContactinsureHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Date: 14-4-23
 * Time: 下午3:55
 * To change this template use File | Settings | File Templates.
 */
@Service("contactInsureHistoryService")
public class ContactinsureHistoryServiceImpl implements ContactinsureHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(ContactinsureHistoryServiceImpl.class);
    @Autowired
    private ContactinsureHistoryDao contactinsureHistoryDao;

    @Override
    public void save(ContactinsureHistory contactinsureHistory) {
        contactinsureHistoryDao.save(contactinsureHistory);
    }
}
