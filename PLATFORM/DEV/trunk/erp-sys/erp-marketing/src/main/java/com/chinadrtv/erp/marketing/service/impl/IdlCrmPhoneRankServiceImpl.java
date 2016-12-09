package com.chinadrtv.erp.marketing.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.dao.IdlCrmPhoneRankDao;
import com.chinadrtv.erp.marketing.model.bi.IdlCrmPhoneRank;
import com.chinadrtv.erp.marketing.service.IdlCrmPhoneRankService;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 电话号码清晰同步-服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 电话号码清晰同步-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:50:47
 * 
 */
@Service("idlCrmPhoneRankService")
public class IdlCrmPhoneRankServiceImpl implements Serializable, IdlCrmPhoneRankService {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IdlCrmPhoneRankDao idlCrmPhoneRankDao;

	/*
	 * (非 Javadoc) <p>Title: queryListByJdbc</p> <p>Description: </p>
	 * 
	 * @param startNm
	 * 
	 * @param endNum
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.IdlCrmPhoneRankService#queryListByJdbc
	 * (java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<IdlCrmPhoneRank> queryListByJdbc(Integer startNm, Integer endNum) {
		return idlCrmPhoneRankDao.queryListByJdbc(startNm, endNum);
	}

	/*
	 * (非 Javadoc) <p>Title: updateSyncFlag</p> <p>Description: </p>
	 * 
	 * @param phoneid
	 * 
	 * @param phoneRank
	 * 
	 * @param lastDate
	 * 
	 * @param newFlag
	 * 
	 * @param origFlag
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.IdlCrmPhoneRankService#updateSyncFlag
	 * (java.lang.String, java.lang.String, java.util.Date, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int updateSyncFlag(String phoneid, String phoneRank, Date lastDate, String newFlag,
			String origFlag) {
		return idlCrmPhoneRankDao.updateSyncFlag(phoneid, phoneRank, lastDate, newFlag, origFlag);
	}

}
