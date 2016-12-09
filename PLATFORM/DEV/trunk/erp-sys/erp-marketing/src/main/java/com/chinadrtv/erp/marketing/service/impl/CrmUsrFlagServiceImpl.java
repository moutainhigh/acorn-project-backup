package com.chinadrtv.erp.marketing.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dao.CrmUsrFlagDao;
import com.chinadrtv.erp.marketing.service.CrmUsrFlagService;
import com.chinadrtv.erp.model.marketing.CrmUsrFlag;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 客户群管理--服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理-客户标签-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:52:50
 * 
 */
@Service("crmUsrFlagService")
public class CrmUsrFlagServiceImpl implements Serializable, CrmUsrFlagService {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private CrmUsrFlagDao crmUsrFlagDao;

	/*
	 * (非 Javadoc) <p>Title: saveOrUpdate</p> <p>Description: </p>
	 * 
	 * @param crmUsrFlag
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.CrmUsrFlagService#saveOrUpdate(com
	 * .chinadrtv.erp.marketing.model.CrmUsrFlag)
	 */
	public void saveOrUpdate(CrmUsrFlag crmUsrFlag) {
		// TODO Auto-generated method stub
		crmUsrFlagDao.saveOrUpdate(crmUsrFlag);
	}

}
