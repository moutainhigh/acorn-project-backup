package com.chinadrtv.erp.marketing.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.dao.CustomerSQLSourceHisDao;
import com.chinadrtv.erp.model.marketing.CustomerSqlSourceHis;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * sql数据源历史记录表</dd>
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
public class CustomerSQLSourceHisDaoImpl extends
		GenericDaoHibernate<CustomerSqlSourceHis, java.lang.String> implements
		CustomerSQLSourceHisDao {

	public CustomerSQLSourceHisDaoImpl() {
		super(CustomerSqlSourceHis.class);
	}

	public List<CustomerSqlSourceHis> query(String groupCode,
			DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer("from CustomerSqlSourceHis "
				+ "where groupCode=:groupCodeParam ");
		// StringBuffer sql = new
		// StringBuffer("select o.orderid,o.address.province.chinese,o.address.city.cityname,o.address.county.countyname,o.address.area.areaname,o.address.addressDesc from Orderhist o where 1=1 ");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter group = new ParameterString("groupCodeParam", groupCode);
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("groupCodeParam", group);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by id desc");
		List<CustomerSqlSourceHis> objList = findPageList(sql.toString(),
				params);
		return objList;
	}

	public Integer queryCount(String groupCode) {
		StringBuffer sql = new StringBuffer(
				"select count(o.id) from CustomerSqlSourceHis o where groupCode=:groupCodeParam ");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter group = new ParameterString("groupCodeParam", groupCode);
		params.put("groupCodeParam", group);

		Integer result = findPageCount(sql.toString(), params);

		return result;
	}

	public Map<String, Parameter> genSql(StringBuffer sql,
			CustomerSqlSourceHis sqlSourceHis) {

		Map<String, Parameter> paras = new HashMap<String, Parameter>();

		return paras;
	}

}
