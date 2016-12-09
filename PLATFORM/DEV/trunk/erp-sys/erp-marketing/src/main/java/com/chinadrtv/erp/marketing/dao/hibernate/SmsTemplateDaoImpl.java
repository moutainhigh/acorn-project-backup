/**
 * 
 */
package com.chinadrtv.erp.marketing.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.marketing.constants.SmsConstant;
import com.chinadrtv.erp.marketing.core.dao.SqlDao;
import com.chinadrtv.erp.marketing.dao.SmsTemplateDao;
import com.chinadrtv.erp.marketing.dto.SmsTemplateDto;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsTemplate;

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
 * @author andrew
 * @version 1.0
 * @since 2013-1-22 下午2:04:59
 * 
 */
@Repository
public class SmsTemplateDaoImpl extends
		GenericDaoHibernate<SmsTemplate, java.lang.Long> implements
		SmsTemplateDao {

	@Autowired
	private SqlDao sqlDao;

	@Autowired
	@Qualifier("jdbcTemplateSms")
	private JdbcTemplate jdbcTemplateSms;

	/**
	 * @param persistentClass
	 */
	public SmsTemplateDaoImpl() {
		super(SmsTemplate.class);
	}

	@Override
	@Autowired
	@Qualifier("sessionFactorySms")
	public void setSessionFactory(SessionFactory sessionFactory) {
		// TODO Auto-generated method stub
		super.setSessionFactory(sessionFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsTemplateDao#findById(java.lang.Long)
	 */
	public SmsTemplate findById(Long id) {
		return this.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsTemplateDao#loadDeptList(java.lang
	 * .Integer)
	 */
	public List<Map<String, Object>> loadDeptList(Integer uid) {
		String sql = "select t.id,t.dsc from names t where t.tid='GRPDEPT' order by to_number(id)";
		return sqlDao.getResultMapList(sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsTemplateDao#query(com.chinadrtv.erp
	 * .marketing.dto.SmsTemplateDto,
	 * com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public List<SmsTemplateDto> query(SmsTemplateDto smsTemplateDto,
			DataGridModel dataModel) {
		StringBuffer hql = new StringBuffer();
		hql.append("select s from SmsTemplate s where s.isdel="
				+ SmsConstant.IS_DEL_FALSE + " ");
		if (null != smsTemplateDto.getCode()
				&& !"".equals(smsTemplateDto.getCode().trim())) {
			hql.append(" and s.code like '%" + smsTemplateDto.getCode() + "%'");
		}
		if (null != smsTemplateDto.getThemeTemplate()
				&& !"".equals(smsTemplateDto.getThemeTemplate().trim())) {
			hql.append(" and s.themeTemplate = '"
					+ smsTemplateDto.getThemeTemplate().trim() + "'");
		}
		if (null != smsTemplateDto.getName()
				&& !"".equals(smsTemplateDto.getName().trim())) {
			hql.append(" and s.name like '%" + smsTemplateDto.getName() + "%'");
		}
		if (null != smsTemplateDto.getStatus()
				&& !"".equals(smsTemplateDto.getStatus())) {
			hql.append(" and s.status = '" + smsTemplateDto.getStatus() + "'");
		}
		hql.append(" order by s.crttime desc");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);

		List stDtoList = this.findPageList(hql.toString(), params);

		return stDtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsTemplateDao#queryCount(com.chinadrtv
	 * .erp.marketing.dto.SmsTemplateDto)
	 */
	public int queryCount(SmsTemplateDto smsTemplateDto) {
		StringBuffer hql = new StringBuffer();
		hql.append("select count(s) from SmsTemplate s where s.isdel="
				+ SmsConstant.IS_DEL_FALSE + " ");
		if (null != smsTemplateDto.getCode()
				&& !"".equals(smsTemplateDto.getCode().trim())) {
			hql.append(" and s.code like '%" + smsTemplateDto.getCode() + "%'");
		}
		if (null != smsTemplateDto.getName()
				&& !"".equals(smsTemplateDto.getName().trim())) {
			hql.append(" and s.name like '%" + smsTemplateDto.getName() + "%'");
		}
		if (null != smsTemplateDto.getThemeTemplate()
				&& !"".equals(smsTemplateDto.getThemeTemplate().trim())) {
			hql.append(" and s.themeTemplate = '"
					+ smsTemplateDto.getThemeTemplate().trim() + "'");
		}
		if (null != smsTemplateDto.getStatus()
				&& !"".equals(smsTemplateDto.getStatus())) {
			hql.append(" and s.status = '" + smsTemplateDto.getStatus() + "'");
		}
		hql.append(" order by s.crttime desc");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Integer count = this.findPageCount(hql.toString(), params);

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsTemplateDao#saveSmsTemplate(com.chinadrtv
	 * .erp.marketing.model.SmsTemplate)
	 */
	public void saveSmsTemplate(SmsTemplate smsTemplate) throws Exception {
		if (smsTemplate.getId() == null) {
			this.insert(smsTemplate);
		} else {
			this.getHibernateTemplate().update(smsTemplate);
		}
	}

	/**
	 * 
	 * @Description: SQL Insert
	 * @param smsTemplate
	 * @return void
	 * @throws
	 */
	private void insert(SmsTemplate smsTemplate) {
		getHibernateTemplate().save(smsTemplate);
		// String sql = "insert into sms_template "
		// +
		// " (id, code, name, content, cost, status, deptid, crttime, crtuid, isdel) values "
		// + " (SEQ_SMS_TEMPLATE.NEXTVAL, " + " FN_SMS_CODE("
		// + smsTemplate.getDeptid() + ")" + " , '"
		// + smsTemplate.getName() + "'" + " , '"
		// + smsTemplate.getContent() + "'" + " , "
		// + smsTemplate.getCost() + " , '" + smsTemplate.getStatus()
		// + "'" + " , " + smsTemplate.getDeptid() + " , sysdate" + " , "
		// + smsTemplate.getCrtuid() + " , 0)";
		//
		// Session session = this.sessionFactory.openSession();
		// SQLQuery sqlQuery = session.createSQLQuery(sql);
		// sqlQuery.executeUpdate();
		// session.close();
	}

	public SqlDao getSqlDao() {
		return sqlDao;
	}

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

	/*
	 * (非 Javadoc) <p>Title: querList</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsTemplateDao#querList()
	 */
	public List<SmsTemplate> querList() {
		// TODO Auto-generated method stub
		String hql = "from SmsTemplate s  where s.isdel="
				+ SmsConstant.IS_DEL_FALSE + " and s.status=1";
		List<SmsTemplate> list = getHibernateTemplate().find(hql);
		return list;
	}

	/*
	 * (非 Javadoc) <p>Title: getSeqNextValue</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsTemplateDao#getSeqNextValue()
	 */
	public Long getSeqNextValue() {
		// TODO Auto-generated method stub
		String sql = "select SEQ_SMS_TEMPLATE.NEXTVAL from dual";
		return jdbcTemplateSms.queryForLong(sql);
	}

}
