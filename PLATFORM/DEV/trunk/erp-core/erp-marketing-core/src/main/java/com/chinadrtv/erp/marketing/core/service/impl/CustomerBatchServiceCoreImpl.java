package com.chinadrtv.erp.marketing.core.service.impl;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dao.CustomerBatchDao;
import com.chinadrtv.erp.marketing.core.service.CustomerBatchCoreService;
import com.chinadrtv.erp.model.marketing.CustomerBatch;

/**
 *
 */
@Service("customerBatchCoreService")
public class CustomerBatchServiceCoreImpl implements Serializable,
        CustomerBatchCoreService {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomerBatchServiceCoreImpl.class);
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomerBatchDao customerBatchDao;


    @Override
    public List<CustomerBatch> querBatchBygroupCode(String groupCode) {

        return customerBatchDao.queryGroupList(groupCode);
    }
}
