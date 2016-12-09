/*
 * @(#)NamesDaoImpl.java 1.0 2013-1-22下午2:24:24
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.core.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.ProductDao;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.Product;

/**
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
 * @author zhaizy
 * @version 1.0
 * @since 2013-1-22 下午2:24:24
 * 
 */
@Repository
public class ProductDaoImpl extends
		GenericDaoHibernate<Product, java.lang.String> implements ProductDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param persistentClass
	 */
	public ProductDaoImpl() {
		super(Product.class);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (非 Javadoc) <p>Title: queryNamesList</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.NamesDao#queryNamesList()
	 */
	public List<Product> queryList() {
		String hql = "select t from Product t";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		List<Product> list = this.findList(hql, params);
		return list;
	}

	public List<Product> query(String productName, Integer startRow,
			Integer rows) {
		StringBuffer sql = new StringBuffer("from Product o where 1=1 ");

		Map<String, Parameter> params = genSql(sql, productName);

		// sql.append(" AND o.addressid = a.addressId ");
		Parameter page = new ParameterInteger("page", startRow);
		page.setForPageQuery(true);

		Parameter rowsParams = new ParameterInteger("rows", rows);
		rowsParams.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rowsParams);
		List<Product> objList = findPageList(sql.toString(), params);
		return objList;
	}

	public Integer queryCount(String productName) {
		StringBuffer sql = new StringBuffer(
				"select count(o.prodid) from Product o where 1=1 ");

		Map<String, Parameter> params = genSql(sql, productName);

		Integer result = findPageCount(sql.toString(), params);

		return result;
	}

	public Map<String, Parameter> genSql(StringBuffer sql, String productName) {

		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (!StringUtils.isEmpty(productName)) {
			sql.append(" AND (o.prodname like :productName  OR o.scode like :scode OR o.prodid = :prodid) ");
			Parameter productNameParam = new ParameterString("productName", "%"
					+ productName.toUpperCase() + "%");
			Parameter scodeParam = new ParameterString("scode", "%"
					+ productName.toUpperCase() + "%");
			Parameter prodid = new ParameterString("prodid",
					productName.toUpperCase());
			paras.put("productName", productNameParam);
			paras.put("scode", scodeParam);
			paras.put("prodid", prodid);
		}

		return paras;

	}

	/**
	 * 根据productid查询产品信息
	 * 
	 */
	public Product query(String productId) {
//		String hql = "select t.PRODID,t.PRODNAME,t.DISCOUNT from iagent.product t where t.prodid=:productId";
//
//		Object[] paramsObj = new Object[] { productId };
//		int[] types = new int[] { Types.VARCHAR };
//
//		List<Product> list = jdbcTemplate.query(hql, paramsObj, types,
//				new RowMapper() {
//
//					public Object mapRow(ResultSet rs, int arg1)
//							throws SQLException {
//						Product p = new Product();
//						p.setProdid(rs.getString("PRODID"));
//						p.setProdname(rs.getString("PRODNAME"));
//						p.setDiscount(rs.getBigDecimal("DISCOUNT"));
//						return p;
//					}
//				});

		// Map<String, Parameter> params = new HashMap<String, Parameter>();
		// Parameter product = new ParameterString("productId",productId);
		// params.put("productId", product);
		//
		// List<Product> list = this.findList(hql, params);
        String hql = " from Product t where t.prodid =:productId";
        Query hqlQuery = getSession().createQuery(hql);
        hqlQuery.setParameter("productId",productId);
        List list = hqlQuery.list();
		if (!list.isEmpty())
			return (Product) list.get(0);

		return null;
	}

}
