/*
 * @(#)DiscourseDaoImpl.java 1.0 2013-4-8上午9:42:38
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.core.dao.DiscourseDao;
import com.chinadrtv.erp.marketing.core.dto.DiscourseDto;
import com.chinadrtv.erp.model.marketing.Discourse;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-8 上午9:42:38
 * 
 */
@Repository
public class DiscourseDaoImpl extends
		GenericDaoHibernate<Discourse, java.lang.String> implements
		DiscourseDao {

	public DiscourseDaoImpl() {
		super(Discourse.class);
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param persistentClass
	 * @param sessionFactory
	 */
	public DiscourseDaoImpl(Class<Discourse> persistentClass,
			SessionFactory sessionFactory) {
		super(persistentClass, sessionFactory);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @Description: 根据productCode查询话术
	 * @param productCode
	 * @return
	 * @return Discourse
	 * @throws
	 */
	public Discourse queryByProductCode(String productCode) {
		String hql = "from  Discourse where status= '0' AND productCode=? ";
		Session session = getSession();
		List<Discourse> list = getHibernateTemplate().find(hql,
				new Object[] { productCode });
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/*
	 * (非 Javadoc) <p>Title: query</p> <p>Description: </p>
	 * 
	 * @param discourse
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.DiscourseDao#query(com.chinadrtv.erp.
	 * marketing.dto.DiscourseDto,
	 * com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public List<DiscourseDto> query(DiscourseDto discourse,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select o.id ,o.discourseName,o.createTime,o.creator,o.status,o.productCode,o.productName,o.discourseHtmlUrl,o.discourseHtmlContent,o.department from Discourse o where o.status= '0' ");

		Map<String, Parameter> params = genSql(sql, discourse);

		// sql.append(" AND o.addressid = a.addressId ");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by o.createTime desc ");
		List objList = findPageList(sql.toString(), params);
		List<DiscourseDto> result = new ArrayList<DiscourseDto>();
		Object[] obj = null;
		DiscourseDto discourses = null;
		for (int i = 0; i < objList.size(); i++) {
			obj = (Object[]) objList.get(i);
			discourses = new DiscourseDto();
			discourses.setId((Long) obj[0]);
			if (obj[1] != null) {
				discourses.setDiscourseName(obj[1].toString());
			}
			if (obj[2] != null) {
				discourses.setCreateTime(DateTimeUtil.sim3
						.format((Date) obj[2]));
			}
			if (obj[3] != null) {
				discourses.setCreator(obj[3].toString());
			}
			if (obj[4] != null) {
				discourses.setStatus(obj[4].toString());
			}
			if (obj[5] != null) {
				discourses.setProductCode(obj[5].toString());
			}
			if (obj[6] != null) {
				discourses.setProductName(obj[6].toString());
			}
			if (obj[7] != null) {
				discourses.setDiscourseHtmlUrl(obj[7].toString());
			}
			if (obj[8] != null) {
				discourses.setDiscourseHtmlContent(obj[8].toString());
			}
			if (obj[9] != null) {
				discourses.setDepartment(obj[9].toString());
			}
			result.add(discourses);
		}
		return result;
	}

	public Map<String, Parameter> genSql(StringBuffer sql,
			DiscourseDto discourse) {
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (discourse.getId() != null) {
			sql.append(" AND o.id=:id");
			Parameter id = new ParameterString("id", discourse.getId()
					.toString());
			paras.put("id", id);
		}
		if (discourse.getDiscourseName() != null
				&& !("").equals(discourse.getDiscourseName())) {
			sql.append(" AND o.discourseName=:discourseName");
			Parameter discourseName = new ParameterString("discourseName",
					discourse.getDiscourseName().toString());
			paras.put("discourseName", discourseName);
		}
		if (discourse.getStartTime() != null
				&& !("").equals(discourse.getStartTime())) {
			sql.append(" AND o.createTime >= TO_DATE(:startTime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter startTime = null;
			try {
				startTime = new ParameterString("startTime",
						discourse.getStartTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paras.put("startTime", startTime);
		}
		if (discourse.getEndTime() != null
				&& !("").equals(discourse.getEndTime())) {
			sql.append(" AND o.createTime <= TO_DATE(:endTime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter endTime = null;
			try {
				endTime = new ParameterString("endTime", discourse.getEndTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paras.put("endTime", endTime);
		}
		if (discourse.getStatus() != null
				&& !("").equals(discourse.getStatus())) {
			sql.append(" AND o.status=:status");
			Parameter status = new ParameterString("status",
					discourse.getStatus());
			paras.put("status", status);
		}
		if (discourse.getProductCode() != null
				&& !("").equals(discourse.getProductCode())) {
			sql.append(" AND o.productCode=:productCode");
			Parameter productCode = new ParameterString("productCode",
					discourse.getProductCode());
			paras.put("productCode", productCode);
		}
		return paras;
	}

	/*
	 * (非 Javadoc) <p>Title: queryCounts</p> <p>Description: </p>
	 * 
	 * @param discourse
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.DiscourseDao#queryCounts(com.chinadrtv
	 * .erp.marketing.dto.DiscourseDto)
	 */
	public Integer queryCounts(DiscourseDto discourse) {
		StringBuffer sql = new StringBuffer(
				"select count(o.id) from Discourse o  where o.status=0 ");

		Map<String, Parameter> params = genSql(sql, discourse);

		Integer result = findPageCount(sql.toString(), params);

		return result;
	}

	/*
	 * 保存Discourse
	 * 
	 * @param discourse
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.DiscourseDao#saveDiscourse(com.chinadrtv
	 * .erp.marketing.model.Discourse)
	 */
	public String saveDiscourse(Discourse discourse) {
		// TODO Auto-generated method stub
		String flag = "0";
		try {
			getHibernateTemplate().save(discourse);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			flag = "1";
		}
		return flag;
	}

	/*
	 * (非 Javadoc) <p>Title: getById</p> <p>Description: </p>
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.DiscourseDao#getById(java.lang.Long)
	 */
	public Discourse getById(Long id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(Discourse.class, id);
	}

	/*
	 * (非 Javadoc) <p>Title: update</p> <p>Description: </p>
	 * 
	 * @param discourse
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.DiscourseDao#update(com.chinadrtv.erp
	 * .marketing.model.Discourse)
	 */
	public Boolean update(Discourse discourse) {
		// TODO Auto-generated method stub
		Boolean flag = true;
		try {
			getHibernateTemplate().update(discourse);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/*
	 * (非 Javadoc) <p>Title: queryByProductCode</p> <p>Description: </p>
	 * 
	 * @param product
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.DiscourseDao#queryByProductCode(java.
	 * lang.String)
	 */
	public List<Discourse> queryByProductCode(String product, String department) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("from Discourse where status = 0 ");
		if (department != null && !("").equals(department)) {
			sql.append(" and departmentCode = " + "'" + department + "'");
		}
		if (product != null && !("").equals(product)) {
			sql.append(" and productCode = " + "'" + product + "'");
		}
		List<Discourse> list = getHibernateTemplate().find(sql.toString());
		if (list != null && !list.isEmpty()) {
			return list;
		} else {
			return null;
		}
	}
}
