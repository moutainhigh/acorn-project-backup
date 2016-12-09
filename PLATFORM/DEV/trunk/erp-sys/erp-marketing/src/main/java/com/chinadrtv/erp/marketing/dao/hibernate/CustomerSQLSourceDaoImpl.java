package com.chinadrtv.erp.marketing.dao.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.marketing.dao.CustomerSQLSourceDao;
import com.chinadrtv.erp.model.marketing.CustomerSqlSource;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:19:43
 * 
 */
@Repository
public class CustomerSQLSourceDaoImpl extends
		GenericDaoHibernate<CustomerSqlSource, java.lang.Long> implements
		Serializable, CustomerSQLSourceDao {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public CustomerSQLSourceDaoImpl() {
		super(CustomerSqlSource.class);
	}

	public List<CustomerSqlSource> query(CustomerSqlSource sqlSource,
			DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer("from CustomerSqlSource "
				+ "where 1=1 ");
		// StringBuffer sql = new
		// StringBuffer("select o.orderid,o.address.province.chinese,o.address.city.cityname,o.address.county.countyname,o.address.area.areaname,o.address.addressDesc from Orderhist o where 1=1 ");

		Map<String, Parameter> params = genSql(sql, sqlSource);

		// sql.append(" AND o.addressid = a.addressId ");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);

		List<CustomerSqlSource> objList = findPageList(sql.toString(), params);
		return objList;
	}

	public Integer queryCount(CustomerSqlSource sqlSource) {
		StringBuffer sql = new StringBuffer(
				"select count(o.id) from CustomerSqlSource o where 1=1 ");

		Map<String, Parameter> params = genSql(sql, sqlSource);

		Integer result = findPageCount(sql.toString(), params);

		return result;
	}

	public Map<String, Parameter> genSql(StringBuffer sql,
			CustomerSqlSource sqlSource) {

		Map<String, Parameter> paras = new HashMap<String, Parameter>();

		return paras;
	}

	public Long querySQLCount(String sql) {

		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("SELECT COUNT(1) FROM (").append(sql).append(")");
		Long result = -1l;
		SQLQuery sqlQeury = getSession().createSQLQuery(sqlCount.toString());
		List list = sqlQeury.list();
		if (!list.isEmpty()) {
			BigDecimal count = (BigDecimal) list.get(0);
			result = Long.valueOf(count.toString());
		}
		return result;
	}

}
