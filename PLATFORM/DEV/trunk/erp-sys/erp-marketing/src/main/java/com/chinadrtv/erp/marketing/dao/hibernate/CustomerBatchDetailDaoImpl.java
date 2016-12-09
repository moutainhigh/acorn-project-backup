package com.chinadrtv.erp.marketing.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.marketing.dao.CustomerBatchDetailDao;
import com.chinadrtv.erp.model.marketing.CustomerBatchDetail;
import com.chinadrtv.erp.model.marketing.CustomerGroup;
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
public class CustomerBatchDetailDaoImpl extends
		GenericDaoHibernate<CustomerBatchDetail, java.lang.String> implements
		CustomerBatchDetailDao {

	public CustomerBatchDetailDaoImpl() {
		super(CustomerBatchDetail.class);
	}

	@Autowired
	@Qualifier("jdbcTemplateMarketingBak")
	public JdbcTemplate jdbcTemplateMarketingBak;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<CustomerBatchDetail> query(
			CustomerBatchDetail customerBatchDetail, DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer("from CustomerBatchDetail "
				+ "where 1=1 ");
		// StringBuffer sql = new
		// StringBuffer("select o.orderid,o.address.province.chinese,o.address.city.cityname,o.address.county.countyname,o.address.area.areaname,o.address.addressDesc from Orderhist o where 1=1 ");

		Map<String, Parameter> params = genSql(sql, customerBatchDetail);

		// sql.append(" AND o.addressid = a.addressId ");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);

		List<CustomerBatchDetail> objList = findPageList(sql.toString(), params);
		return objList;
	}

	public Integer queryCount(CustomerBatchDetail customerBatchDetail) {
		StringBuffer sql = new StringBuffer(
				"select count(o.id) from CustomerBatchDetail o where 1=1 ");

		Map<String, Parameter> params = genSql(sql, customerBatchDetail);

		Integer result = findPageCount(sql.toString(), params);

		return result;
	}

	public Map<String, Parameter> genSql(StringBuffer sql,
			CustomerBatchDetail customerBatchDetail) {

		Map<String, Parameter> paras = new HashMap<String, Parameter>();

		return paras;
	}

	/**
	 * 
	 * @Description: 获得总数
	 * @param sql
	 * @param batchId
	 * @param group
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer getSqlCount(String sql) {
		String sqls = "select count (*) from( " + sql + ")";
		return jdbcTemplateMarketingBak.queryForInt(sqls);

	}

	/**
	 * 
	 * @Description: 查询seq
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer getCustomerDetailSeq() {
		// 查出seq
		String sqlSeq = "select seq_customer_batch_detail.nextval from dual ";
		return jdbcTemplateMarketingBak.queryForInt(sqlSeq);
	}

	/**
	 * 
	 * @Description: 备库查询sql
	 * @return
	 * @return List<CustomerSqlDto>
	 * @throws
	 */
	public List<Map<String, Object>> getSqlResult(String sql, Integer begin,
			Integer end) {
		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("select contactid, contactinfo, statisinfo from (");
		sqlCount.append("select rownum as no,contactid,contactinfo,statisinfo from  (select contactid,contactinfo,statisinfo ");
		sqlCount.append(" from ( ");
		sqlCount.append(sql);
		sqlCount.append(" ) ) ) where no >" + begin + " and no <=" + end);
		return jdbcTemplateMarketingBak.queryForList(sqlCount.toString());
	}

	/**
	 * 
	 * @Description: 分页保存信息
	 * @param batchId
	 * @param group
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer saveBatchDetials(String batchId, CustomerGroup group,
			List<Map<String, Object>> list, Date date) {
		final List<Map<String, Object>> lists = list;
		final CustomerGroup groupsCustomerGroup = group;
		final String batchIds = batchId;
		final Date dates = date;
		String sql = "insert into acoapp_marketing.customer_detail ( ID,BATCH_CODE,CUSTOMER_ID,CRT_DATE,GROUP_CODE,CONTACT_INFO,STATIS_INFO,JOBID,QUEUEID)"
				+ " values (seq_customer_batch_detail.nextval,?,?,?,?,?,?,?,?)";
		int[] result = jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ps.setString(1, batchIds);
						ps.setString(2, lists.get(i).get("contactid")
								.toString());
						ps.setTimestamp(3,
								new java.sql.Timestamp(dates.getTime()));
						ps.setString(4, groupsCustomerGroup.getGroupCode());
						ps.setString(5, "" + lists.get(i).get("contactinfo"));
						ps.setString(6, "" + lists.get(i).get("statisinfo"));
						ps.setString(7, "" + groupsCustomerGroup.getJobId());
						ps.setString(8, "" + groupsCustomerGroup.getQueneId());
					}

					public int getBatchSize() {
						return lists.size();
					}
				});

		return result.length;
	}

	/**
	 * 插入批次数据
	 * 
	 * TODO 更改查询数据源
	 */
	public int saveBatchDetail(String sql, String batchId, CustomerGroup group) {

		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("insert into customer_detail ");
		sqlCount.append("( ID,BATCH_CODE,CUSTOMER_ID,CRT_DATE,GROUP_CODE,CONTACT_INFO,STATIS_INFO,JOBID,QUEUEID) ");
		sqlCount.append("select seq_customer_batch_detail.nextval,:batchId,contactid,:nowDate,:groupCode,contactinfo,statisinfo,:jobId,:queueId ");
		sqlCount.append("from (");
		sqlCount.append(sql);
		sqlCount.append(")");
		int result = -1;
		SQLQuery sqlQeury = getSession().createSQLQuery(sqlCount.toString());
		sqlQeury.setString("batchId", batchId);
		sqlQeury.setTimestamp("nowDate", new Date());
		sqlQeury.setString("groupCode", group.getGroupCode());
		sqlQeury.setString("jobId", group.getJobId());
		sqlQeury.setString("queueId", group.getQueneId());
		result = sqlQeury.executeUpdate();
		return result;
	}

	/**
	 * 去重复
	 * 
	 * @Description: TODO
	 * @param sql
	 * @param batchId
	 * @param groupCode
	 * @return
	 * @return int
	 * @throws
	 */
	public int deleteRepeatDetail(String batchId, String groupCode, Integer days) {

		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("delete from customer_detail o  ");
		sqlCount.append("where o.batch_code=:batchId and o.group_code=:groupCode ");
		sqlCount.append("and exists ( ");
		sqlCount.append("select 1 from customer_detail d ");
		sqlCount.append("where d.customer_id=o.customer_id ");
		sqlCount.append("and d.batch_code<>:batchId2 and d.group_code=:groupCode2 ");
		sqlCount.append("and d.crt_date>=trunc(sysdate)-:days");
		sqlCount.append(")");

		int result = -1;
		SQLQuery sqlQeury = getSession().createSQLQuery(sqlCount.toString());
		sqlQeury.setString("batchId", batchId);
		sqlQeury.setString("groupCode", groupCode);
		sqlQeury.setString("batchId2", batchId);
		sqlQeury.setString("groupCode2", groupCode);
		sqlQeury.setInteger("days", days);
		result = sqlQeury.executeUpdate();
		return result;
	}

	/**
	 * 
	 * @Description: 更新customer_detail 表的优先级
	 * @param batchId
	 * @param orderCol
	 * @return
	 * @return int
	 * @throws
	 */
	public int updateDetailPriority(String batchId, String orderCol) {

		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("UPDATE customer_detail d ");
		sqlCount.append("SET d.priority= ");
		sqlCount.append("	(SELECT f.PRIORITY FROM customer_detail_temporary f");
		sqlCount.append("			WHERE d.id=f.id");
		sqlCount.append("	)");
		sqlCount.append("WHERE d.batch_code=:batchId");

		int result = -1;
		SQLQuery sqlQeury = getSession().createSQLQuery(sqlCount.toString());
		sqlQeury.setString("batchId", batchId);
		result = sqlQeury.executeUpdate();
		return result;
	}

	/**
	 * 
	 * @Description: 生成customer_detail 表的优先级插入临时表
	 * @param batchId
	 * @param orderCol
	 * @return
	 * @return int
	 * @throws
	 */
	public int insetDetailTemp(String batchId, String orderCol) {

		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("INSERT INTO customer_detail_temporary (id,PRIORITY) ");
		sqlCount.append("SELECT  a.id,ROWNUM nn FROM ");
		sqlCount.append("( ");
		sqlCount.append("	SELECT t.id FROM customer_detail t , crm_usr_flag c ");
		sqlCount.append("				WHERE t.batch_code=:batchId  ");
		sqlCount.append("				AND t.customer_id=c.contactid ");
		sqlCount.append("				ORDER BY " + orderCol + " nulls last");
		sqlCount.append(") a");

		int result = -1;
		SQLQuery sqlQeury = getSession().createSQLQuery(sqlCount.toString());
		sqlQeury.setString("batchId", batchId);
		// sqlQeury.setString("orderCol", "LST_PAID_ORDR_DATE DESC");
		result = sqlQeury.executeUpdate();
		return result;
	}

	/**
	 * 将未领用的customer从Customer_Detail中删除
	 * 
	 * @Description: TODO
	 * @param sql
	 * @param batchId
	 * @param groupCode
	 * @return
	 * @return int
	 * @throws
	 */
	public int deleteCustomerDetail(String groupCode) {

		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("delete from customer_detail t where t.group_code=:groupCode ");
		sqlCount.append("and exists (");
		sqlCount.append("	select 1 from iagent.ob_contact c ");
		sqlCount.append("			where c.batchid=t.batch_code ");
		sqlCount.append("				 and c.contactid=t.customer_id ");
		sqlCount.append("				 and (c.status ='0' or c.status is null)");
		sqlCount.append(")");

		int result = -1;
		SQLQuery sqlQeury = getSession().createSQLQuery(sqlCount.toString());
		sqlQeury.setString("groupCode", groupCode);
		result = sqlQeury.executeUpdate();
		return result;
	}

	/**
	 * 将未领用的customer删除
	 * 
	 * @Description: TODO
	 * @param sql
	 * @param batchId
	 * @param groupCode
	 * @return
	 * @return int
	 * @throws
	 */
	public int deleteObContact(String groupCode) {

		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("delete from  iagent.ob_contact c ");
		sqlCount.append("where exists (" + "select 1 from customer_batch b "
				+ "where b.batch_code=c.batchid "
				+ "and b.group_code=:groupCode" + ") ");
		sqlCount.append("and (c.status ='0' or c.status is null)");

		int result = -1;
		SQLQuery sqlQeury = getSession().createSQLQuery(sqlCount.toString());
		sqlQeury.setString("groupCode", groupCode);
		result = sqlQeury.executeUpdate();
		return result;
	}

	/*
	 * 
	 * 
	 * @param groupCode
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.CustomerBatchDetailDao#
	 * querBatchDetailsBygroupCode(java.lang.String)
	 */
	public List<CustomerBatchDetail> querBatchDetailsBygroupCode(
			String groupCode) {
		// TODO Auto-generated method stub
		String sql = " select  DISTINCT t.customerId  from CustomerBatchDetail t where t.groupCode= ? ";
		return getHibernateTemplate().find(sql, new Object[] { groupCode });

	}

	public Integer getCounts(String groupCode, String flag) {
		String sql = "select count (DISTINCT customerId) from CustomerBatchDetail t where t.groupCode= ? ";
		if (flag.equals("off")) {
			sql = "select count ( customerId) from CustomerBatchDetail t where t.groupCode= ? ";
		}
		return Integer.valueOf(getHibernateTemplate()
				.find(sql, new Object[] { groupCode }).get(0).toString());
	}

}
