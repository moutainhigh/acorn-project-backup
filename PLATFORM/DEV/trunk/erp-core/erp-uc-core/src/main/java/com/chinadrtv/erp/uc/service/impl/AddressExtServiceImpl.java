package com.chinadrtv.erp.uc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.uc.dao.AddressExtDao;
import com.chinadrtv.erp.uc.service.AddressExtService;

/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-5-3 下午3:16:45 
 *
 */
@Service
public class AddressExtServiceImpl extends GenericServiceImpl<AddressExt,String> implements AddressExtService {

	@Autowired
	private AddressExtDao addressExtDao;
	
    @Override
    protected GenericDao<AddressExt, String> getGenericDao() {
        return addressExtDao;
    }

    @Override
    public AddressExt get(String id) {
        return addressExtDao.get(id);
    }
}
