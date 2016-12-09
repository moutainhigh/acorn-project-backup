package com.chinadrtv.erp.marketing.dao.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.dao.query.ParameterTimestamp;
import com.chinadrtv.erp.marketing.dao.CustomerGroupDao;
import com.chinadrtv.erp.marketing.dto.CustomerGroupDto;
import com.chinadrtv.erp.model.marketing.CustomerGroup;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.user.util.PermissionHelper;
import com.chinadrtv.erp.util.DateUtil;

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
public class CustomerGroupDaoImpl extends
		GenericDaoHibernate<CustomerGroup, java.lang.String> implements
		Serializable, CustomerGroupDao {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public CustomerGroupDaoImpl() {
		super(CustomerGroup.class);
	}

	public List<CustomerGroup> query(CustomerGroupDto customerGroup,
			DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer("from CustomerGroup o where 1=1 ");
		// StringBuffer sql = new
		// StringBuffer("select o.orderid,o.address.province.chinese,o.address.city.cityname,o.address.county.countyname,o.address.area.areaname,o.address.addressDesc from Orderhist o where 1=1 ");

		Map<String, Parameter> params  = genSql(sql, customerGroup);

		
		// sql.append(" AND o.addressid = a.addressId ");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);
		
		String whereCondition = PermissionHelper.getFilterCondition();
		if(!StringUtils.isEmpty(whereCondition)){
			sql.append(" and ").append(whereCondition);
			Map<String, Parameter> configParams = PermissionHelper.getFilterParameter();
			params.putAll(configParams);
		}
		
		sql.append(" order by o.groupCode desc");
		List<CustomerGroup> objList = findPageList(sql.toString(), params);
		return objList;
	}

	public Integer queryCount(CustomerGroupDto customerGroup) {
		StringBuffer sql = new StringBuffer(
				"select count(o.groupCode) from CustomerGroup o where 1=1 ");

		Map<String, Parameter> params = genSql(sql, customerGroup);

		String whereCondition = PermissionHelper.getFilterCondition();
		if(!StringUtils.isEmpty(whereCondition)){
			sql.append(" and ").append(whereCondition);
			Map<String, Parameter> configParams = PermissionHelper.getFilterParameter();
			params.putAll(configParams);
		}
		
		Integer result = findPageCount(sql.toString(), params);

		return result;
	}

	public Map<String, Parameter> genSql(StringBuffer sql,
			CustomerGroupDto customerGroup) {

		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (!StringUtils.isEmpty(customerGroup.getGroupCode())) {
			sql.append(" AND o.groupCode=:groupCode");
			Parameter groupCode = new ParameterString("groupCode",
					customerGroup.getGroupCode());
			paras.put("groupCode", groupCode);
		}

		if (!StringUtils.isEmpty(customerGroup.getStartDate())) {
			sql.append(" AND o.crtDate >= :startDate");
			Parameter startDate = null;
			try {
				startDate = new ParameterTimestamp("startDate",
						DateUtil.string2Date(customerGroup.getStartDate(),
								"yyyy-MM-dd HH:mm:ss"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			paras.put("startDate", startDate);
		}

		if (!StringUtils.isEmpty(customerGroup.getEndDate())) {
			sql.append(" AND o.crtDate <= :endDate");
			Parameter endDate = null;
			try {
				endDate = new ParameterTimestamp("endDate",
						DateUtil.string2Date(customerGroup.getEndDate(),
								"yyyy-MM-dd HH:mm:ss"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			paras.put("endDate", endDate);
		}

		if (!StringUtils.isEmpty(customerGroup.getUser())) {
			sql.append(" AND o.crtUser = :crtUser");
			Parameter crtUser = new ParameterString("crtUser",
					customerGroup.getUser());
			paras.put("crtUser", crtUser);
		}

		if (!StringUtils.isEmpty(customerGroup.getGroupName())) {
			sql.append(" AND o.groupName like :groupName");
			Parameter groupName = new ParameterString("groupName", "%"
					+ customerGroup.getGroupName() + "%");
			paras.put("groupName", groupName);
		}
		if (!StringUtils.isEmpty(customerGroup.getExt1())) {
			sql.append(" AND o.ext1 = :ext1");
			Parameter ext1 = new ParameterString("ext1",
					customerGroup.getExt1());
			paras.put("ext1", ext1);
		}
		if (!StringUtils.isEmpty(customerGroup.getStatus())) {
			sql.append(" AND o.status = :status");
			Parameter status = new ParameterString("status",
					customerGroup.getStatus());
			paras.put("status", status);
		}
		if (!StringUtils.isEmpty(customerGroup.getJobId())) {
			sql.append(" AND o.jobId = :jobId");
			Parameter jobid = new ParameterString("jobId",
					customerGroup.getJobId());
			paras.put("jobId", jobid);
		}
		return paras;
	}

	public Long getSeqNextValue() {
		StringBuffer sql = new StringBuffer(
				"select SEQ_CUSTOMER_GROUP.Nextval from dual ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());

		List list = query.list();
		BigDecimal nextVal = (BigDecimal) list.get(0);
		return Long.valueOf(nextVal.toString());
	}

	/*
	 * 查询所有用户组 (非 Javadoc) <p>Title: queryList</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.CustomerGroupDao#queryList()
	 */
	public List<CustomerGroup> queryList() {
		// TODO Auto-generated method stub
		String hql = "from CustomerGroup o where 1=1";
		List<CustomerGroup> list = getHibernateTemplate().find(hql);
		return list;
	}

}
