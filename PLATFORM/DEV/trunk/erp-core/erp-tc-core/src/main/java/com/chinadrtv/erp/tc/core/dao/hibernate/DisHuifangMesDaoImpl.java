package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.dao.DisHuifangMesDao;
import com.chinadrtv.erp.tc.core.service.impl.DisHuifangMesServiceImpl;

@Repository
@Deprecated
public class DisHuifangMesDaoImpl extends GenericDaoHibernateBase<Order, String> implements DisHuifangMesDao {
	private static final Logger logger = LoggerFactory.getLogger(DisHuifangMesServiceImpl.class);

	public DisHuifangMesDaoImpl() {
		super(Order.class);
	}

	/**
	 * 批处理回访信息
	 * 
	 * @throws Exception
	 */
	public boolean upOrderHistByfive(Map<String, Object> map) throws Exception {
		logger.debug("start updateOrderhist");
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//Date dfromdt = null, dtodt = null;
		//String mdate = df.format(new Date());
		String remark = "", adusr = "", fromdt = "", todt = "", ordertype = "", adjusttrans = "";
		String mailtype = "", adjustall = "", adjusturgent = "", ordertype_renamed = "";
		if (map.size() == 10) {
			remark = map.get("remark").toString();
			if ("".equals(remark))
				return false;
			adusr = map.get("adusr").toString();
			if ("".equals(adusr))
				return false;
			fromdt = map.get("fromdt").toString();
			if ("".equals(fromdt))
				return false;
			//dfromdt = df.parse(fromdt);
			todt = map.get("todt").toString();
			if (todt.equals(""))
				return false;
			//dtodt = df.parse(todt);
			ordertype = map.get("ordertype").toString();
			adjusttrans = map.get("adjusttrans").toString();
			mailtype = map.get("mailtype").toString();
			adjustall = map.get("adjustall").toString();
			adjusturgent = map.get("adjusturgent").toString();
			ordertype_renamed = map.get("ordertype_renamed").toString();
		}
		String hql = "update Order set remark=:remark,addt=sysdate,adusr=:adusr ";
		hql += " Where crdt between to_date(:fromdt,'yyyy-mm-dd hh24:mi:ss') and to_date(:todt,'yyyy-mm-dd hh24:mi:ss') and remark is null ";
		if (!ordertype.equals("")) {
			hql += " and ordertype = :ordertype ";
		}

		if (adjusttrans.equals("false")) {
			hql += " and status in ('1','2','7') ";
		} else {
			hql += " and status in ('1','7') ";
		}

		if (!mailtype.equals("")) {
			hql += " and mailtype = :mailtype ";
		}
		if (adjustall.equals("false")) {
			hql += " and ordertype in (" + ordertype_renamed + ") ";

		} else {
			hql += " and ordertype in (select a.id.tid from Names a where a.id.tid='ORDERTYPEADJUST' and a.valid='-1') ";
		}

		if (adjusturgent == "false") {
			hql += " and urgent = '0' ";
		}

		Query query = this.getSession().createQuery(hql);
		query.setString("remark", remark);
		query.setString("adusr", adusr);
		query.setString("fromdt", fromdt);
		query.setString("todt", todt);
		if (!ordertype.equals("")) {
			query.setString("ordertype", ordertype);
		}
		if (!mailtype.equals("")) {
			query.setString("mailtype", mailtype);
		}
		int upid = query.executeUpdate();
		if (upid > 0) {
			return true;
		}
		return false;

	}

	// 处理存储过程
	@Deprecated
	public void callProcedure(final String sql, final List<ParameterString> parmList) {
		this.hibernateTemplate.executeWithNewSession(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				for (ParameterString parm : parmList) {
					sqlQuery.setParameter(parm.getName(), parm.getValue());
				}
				sqlQuery.executeUpdate();
				return null;
			}
		});
	}

	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}

}