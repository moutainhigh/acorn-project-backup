package com.chinadrtv.erp.sales.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.model.agent.Telephone;
import com.chinadrtv.erp.sales.core.dao.TelephoneDao;
import com.chinadrtv.erp.sales.dto.CtiLoginDto;
import com.chinadrtv.erp.sales.service.TelephoneService;
import com.chinadrtv.erp.user.model.DepartmentInfo;
import com.chinadrtv.erp.user.service.UserService;

@Service
public class TelephoneServiceImpl implements TelephoneService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TelephoneServiceImpl.class);
	@Autowired
	private TelephoneDao telephoneDao;
	
	@Autowired
	private UserService userService;

	/* (非 Javadoc)
	* <p>Title: queryCtiLoginInfo</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.sales.service.TelephoneService#queryCtiLoginInfo()
	*/ 
	@Override
	public CtiLoginDto queryCtiLoginInfo(String agentId,String ip) {
		
		CtiLoginDto result = new CtiLoginDto();
		
		Telephone telephone = telephoneDao.query(ip);
		if(telephone != null){
			result.setTelephone(telephone.getTelephone());
			result.setIpaddress(telephone.getIpaddress());
            result.setPhoneType(telephone.getPhoneType());
		}

        try {
            result.setAreaCode(userService.getGroupAreaCode(agentId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e); //e.printStackTrace();
        }

        return result;
	}
	
	
	
}
