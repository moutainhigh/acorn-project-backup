package com.chinadrtv.erp.tc.core.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import com.chinadrtv.erp.tc.core.dao.SqlDao;
import com.chinadrtv.erp.tc.core.model.ConTicket;
import com.chinadrtv.erp.tc.core.service.CouponService;

@Service("coupontService")
public class CouponServiceImpl extends GenericServiceImpl<ConTicket, String> implements CouponService {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CouponServiceImpl.class);

	@Autowired
    private SchemaNames schemaNames;
	@Autowired
	private SqlDao sqlDao;

	protected GenericDao<ConTicket, String> getGenericDao() {
		return null;
	}

	/**
	 * 执行赠券的新增存储过程
	 */
	public String getCouponproc(ConTicket conTicket){
		/*logger.debug("begin getCouponproc");
		return this.couponDao.getCouponproc(conTicket);
		*/
		
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(conTicket.getStype());
		paramList.add(conTicket.getNcurprodprice());
		paramList.add(conTicket.getCurticketprice());
		paramList.add(conTicket.getSorderid());
		paramList.add(conTicket.getScontactid());
		paramList.add(conTicket.getScrusr());
		
		String rs = "";
		try {
			List<Object> rsList = sqlDao.exeStoredProcedure(schemaNames.getAgentSchema() + "P_N_ConTicket", paramList, 1);
			if(rsList != null && rsList.size()>0){
				rs = null==rsList.get(0) ? null : rsList.get(0).toString();
			}
		} catch (SQLException e) {
			logger.error("调用赠券服务失败", e);
			e.printStackTrace();
			throw new BizException("调用赠券服务失败");
		}
		
		return rs;
	}
}