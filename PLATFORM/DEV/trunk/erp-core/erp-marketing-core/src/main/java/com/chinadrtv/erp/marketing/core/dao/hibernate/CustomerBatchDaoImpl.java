package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.core.dao.CustomerBatchDao;
import com.chinadrtv.erp.model.marketing.CustomerBatch;
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
public class CustomerBatchDaoImpl extends
		GenericDaoHibernate<CustomerBatch, java.lang.String> implements
		Serializable, CustomerBatchDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public CustomerBatchDaoImpl() {
		super(CustomerBatch.class);
	}

	public List<CustomerBatch> query(String groupCode, DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer(
				"from CustomerBatch where groupCode=:groupCodeParam ");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter group = new ParameterString("groupCodeParam", groupCode);
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("groupCodeParam", group);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by batchCode desc");
		List<CustomerBatch> objList = findPageList(sql.toString(), params);
		return objList;
	}

	public List<CustomerBatch> queryGroupList(String groupCode) {
		StringBuffer sql = new StringBuffer(
				"from CustomerBatch where groupCode=? order by batchCode desc");
		List<CustomerBatch> objList = getHibernateTemplate().find(
				sql.toString(), new Object[] { groupCode });
		return objList;
	}

	public Integer queryCount(String groupCode) {
		StringBuffer sql = new StringBuffer(
				"select count(o.batchCode) from CustomerBatch o where o.groupCode=:groupCodeParam ");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter group = new ParameterString("groupCodeParam", groupCode);
		params.put("groupCodeParam", group);

		Integer result = findPageCount(sql.toString(), params);

		return result;
	}

	public Object[] queryFooter(String groupCode) {

		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("SELECT sum(o.count),sum(o.available) FROM customer_batch o where o.group_Code=:groupCodeParam ");

		Object[] count = null;
		SQLQuery sqlQeury = getSession().createSQLQuery(sqlCount.toString());
		sqlQeury.setString("groupCodeParam", groupCode);
		List list = sqlQeury.list();
		if (!list.isEmpty()) {

			count = (Object[]) list.get(0);

		}
		return count;
	}

	public Map<String, Parameter> genSql(StringBuffer sql,
			CustomerBatch customerBatch) {

		Map<String, Parameter> paras = new HashMap<String, Parameter>();

		return paras;
	}

	public Long getSeqNextValue() {
		StringBuffer sql = new StringBuffer(
				"select SEQ_CUSTOMER_BATCH.Nextval from dual ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());

		List list = query.list();
		BigDecimal nextVal = (BigDecimal) list.get(0);
		return Long.valueOf(nextVal.toString());
	}

	public void genBatchDetail(String sqlContent, String jobId, String queueId,
			String batchId, String conSrc, String agentId, String startDate,
			String endDate, String contactInfo, String statisInfo, String valid) {
		Query q = getSession()
				.createSQLQuery(
						"{Call iagent.Marketing_IMPORT_CONTACT(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

		q.setString(0, sqlContent);
		q.setString(1, "");
		q.setInteger(2, 1);
		q.setString(3, jobId);
		q.setString(4, queueId);
		q.setString(5, batchId);
		q.setString(6, conSrc);
		q.setString(7, agentId);
		q.setString(8, startDate);
		q.setString(9, endDate);
		q.setString(10, contactInfo);
		q.setString(11, statisInfo);
		q.setString(12, valid);

		q.executeUpdate();
	}
	
	public void genBatchDetail(String sqlContent, String jobId, String queueId,
			String batchId, String conSrc, String agentId, String startDate,
			String endDate, String contactInfo, String statisInfo, String valid,Long campaignId) {
		Query q = getSession()
				.createSQLQuery(
						"{Call iagent.Marketing_IMPORT_CONTACT(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

		q.setString(0, sqlContent);
		q.setString(1, "");
		q.setInteger(2, 1);
		q.setString(3, jobId);
		q.setString(4, queueId);
		q.setString(5, batchId);
		q.setString(6, conSrc);
		q.setString(7, agentId);
		q.setString(8, startDate);
		q.setString(9, endDate);
		q.setString(10, contactInfo);
		q.setString(11, statisInfo);
		q.setString(12, valid);
		q.setLong(13, campaignId);

		q.executeUpdate();
	}

	public List<CustomerBatch> queryBatchNumList(String sql,String groupCode, Date lastDate,Date now) {


		Object[] params = null;
		if(lastDate==null){
			params = new Object[]{groupCode,now};
		}else{
			params = new Object[]{groupCode,lastDate,now};
		}
		
		return jdbcTemplate.query(sql, params, new RowMapper<CustomerBatch>() {

			public CustomerBatch mapRow(ResultSet resultSet, int arg1) throws SQLException {
				CustomerBatch customerBatch = new CustomerBatch();
				customerBatch.setBatchCode(resultSet.getString("batch_code"));
				return customerBatch;
			}
			
		});
	}
	
	public int update(String batchCode, Long count, Long available) {
		StringBuffer sql = new StringBuffer(
				"UPDATE CustomerBatch t SET t.available=:available,t.count=:count WHERE t.batchCode=:batchCode ");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter batchCodePara = new ParameterString("batchCode", batchCode);
		Parameter countPara = new ParameterLong("count", count);
		Parameter availablePara = new ParameterLong("available", count);

		params.put("batchCode", batchCodePara);
		params.put("count", countPara);
		params.put("available", availablePara);

		return this.execUpdate(sql.toString(), params);
	}

	/*
	 * (非 Javadoc) <p>Title: queryCountByBatchCodeList</p> <p>Description: </p>
	 * 
	 * @param batchCodeList
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.CustomerBatchDao#queryCountByBatchCodeList
	 * (java.util.List)
	 */
	public Long queryCountByBatchCodeList(String[] groupCodeArry) {
		// TODO Auto-generated method stub
		StringBuffer groupCode = new StringBuffer();
		for (int i = 0; i < groupCodeArry.length; i++) {
			groupCode.append("'" + groupCodeArry[i] + "',");
		}
		String groupCodes = groupCode.toString().substring(0,
				groupCode.toString().lastIndexOf(","));
		String sql = "select count(*)  from (select t2.contactid  from iagent.ob_contact t2  where exists "
				+ "(select 1  from customer_batch t3 where t3.batch_code = t2.batchid and t3.group_code in ("
				+ groupCodes
				+ " ) )"
				+ " and (t2.status ='0' or t2.status is null ) )";
		return jdbcTemplate.queryForLong(sql);
	}
}
