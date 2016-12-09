package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterList;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.core.dao.CampaignReceiverDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignReceiverDto;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignReceiver;

/**
 * 
 * <dl>
 * <dt><b>Title:营销计划执行结果</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:营销计划执行结果</b></dt>
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
public class CampaignReceiverDaoImpl extends
		GenericDaoHibernate<CampaignReceiver, java.lang.Long> implements
		Serializable, CampaignReceiverDao {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplateAgent;

	public CampaignReceiverDaoImpl() {
		super(CampaignReceiver.class);
	}

	/**
	 * 插入营销记录表
	 */
	public Integer insertCampaignReceiver(Campaign campaign, Date now) {
		StringBuilder sql = new StringBuilder("insert into campaign_receiver ");

		sql.append("  (ID,CUSTOMER_ID,CAMPAIGN_ID,BATCH_CODE,CREATE_DATE,CREATE_USER,GROUP_CODE,CONTACT_INFO,STATIS_INFO,JOBID,QUEUEID,PRIORITY)");
		sql.append("  select seq_campaign_receiver.nextval,");
		sql.append("         t.customer_id,");
		sql.append("         ?,");
		sql.append("         t.batch_code,");
		sql.append("         sysdate,");
		sql.append("         ?,");
		sql.append("         t.group_code,");
		sql.append("         t.contact_info,");
		sql.append("         t.statis_info,");
		sql.append("         t.jobid,");
		sql.append("         t.queueid,");
		sql.append("         t.priority");
		sql.append("    from Customer_Detail t");
		sql.append("   where group_code = ?");
		sql.append("     and not exists (select 1");
		sql.append("            from campaign_receiver c");
		sql.append("           where c.customer_id = t.customer_id");
		sql.append("             and c.batch_code = t.batch_code");
		sql.append("             and c.campaign_id = ?)");
		sql.append("     and t.crt_date <= ?");

		return jdbcTemplateAgent.update(sql.toString(),
				new Object[] { campaign.getId(), campaign.getCreateUser(),
						campaign.getAudience(), campaign.getId(), now });
	}

	/** 
	 * <p>Title: queryAssignmentByGroup</p>
	 * <p>Description: </p>
	 * @param crDto
	 * @return Model list
	 * @see com.chinadrtv.erp.marketing.core.dao.CampaignReceiverDao#queryAssignmentByGroup(com.chinadrtv.erp.marketing.core.dto.CampaignReceiverDto)
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<CampaignReceiver> queryAssignmentByGroup(CampaignReceiverDto crDto) {
		String hql = "select cr from CampaignReceiver cr " +
					 " where cr.status='1' and  cr.batchCode=:batchCode and cr.jobid=:jobid and cr.campaignId=:campaignId " +
					 " and cr.assignGroup in (:agentGroup) ";
		
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("batchCode", crDto.getCustomerBatch());
		query.setParameter("jobid", crDto.getJobNum());
		query.setParameter("campaignId", crDto.getCampaignId());
		query.setParameterList("agentGroup", crDto.getAgentGroupList());
		
		List<CampaignReceiver> rsList = query.list();
		return rsList;
	}

    @Override
    public int getAssigntotle(CampaignReceiverDto dto) {
        StringBuffer hql = new StringBuffer(" select count(cr.id) from CampaignReceiver cr where 1=1 ");
        if(StringUtils.isNotBlank(dto.getJobid())) {
        	hql.append(" and cr.jobid=:jobid");
        }
        if(dto.getCampaignId() != null) {
        	hql.append(" and cr.campaignId=:campaignId");
        }
        if(StringUtils.isNotBlank(dto.getBatchCode())) {
        	hql.append(" and cr.batchCode=:batchCode");
        }
        if(StringUtils.isNotBlank(dto.getGroupId())) {
        	 hql.append(" and cr.assignGroup=:groupId");
        }
        if(StringUtils.isNotBlank(dto.getStatus())) {
        	hql.append(" and nvl(cr.status,0)=:status" );
        } else if(dto.getStatuses() != null && dto.getStatuses().size() > 0) {
        	hql.append(" and cr.status in(:statuses)");
        }

        Map<String, Parameter> params = new HashMap<String, Parameter>();
        if(StringUtils.isNotBlank(dto.getBatchCode())) {
        	params.put("batchCode", new ParameterString("batchCode", dto.getBatchCode()));
        }
        if(StringUtils.isNotBlank(dto.getJobid())) {
        	params.put("jobid", new ParameterString("jobid", dto.getJobid()));
        }
        if(dto.getCampaignId() != null) {
        	params.put("campaignId", new ParameterLong("campaignId", dto.getCampaignId()));
        }
        if(StringUtils.isNotBlank(dto.getStatus())) {
        	params.put("status",new ParameterString("status",dto.getStatus()));
        } else if(dto.getStatuses() != null && dto.getStatuses().size() > 0) {
        	params.put("statuses",new ParameterList("statuses",dto.getStatuses()));
        }
        if(StringUtils.isNotBlank(dto.getGroupId())) {
        	params.put("groupId",new ParameterString("groupId",dto.getGroupId()));
        }
        return findPageCount(hql.toString(),params);
    }


    public  List<CampaignReceiver> getAssignList(CampaignReceiverDto dto,int start ,int rows) {
        StringBuffer hql = new StringBuffer(" select cr from CampaignReceiver cr where ");
        if(dto.getCampaignId() != null) hql.append("cr.campaignId=:campaignId");
        if(! StringUtil.isNullOrBank(dto.getBatchCode()))  hql.append(" and cr.batchCode=:batchCode");
        if(! StringUtil.isNullOrBank(dto.getJobid())) hql.append(" and cr.jobid=:jobid");
         hql.append(" and nvl(cr.status,0)=:status" );

        Query q = getSession().createQuery(hql.toString());
        if(dto.getCampaignId() != null)  q.setLong("campaignId",dto.getCampaignId());
        if(! StringUtil.isNullOrBank(dto.getBatchCode())) q.setString("batchCode", dto.getBatchCode());
        if(! StringUtil.isNullOrBank(dto.getJobid()))  q.setString("jobid", dto.getJobid());
        q.setString("status",dto.getStatus());
        q.setFirstResult(start);
        q.setMaxResults(rows);

        return  q.list();

    }
    
    public  List<CampaignReceiver> getAssignList(CampaignReceiverDto dto) {
        StringBuffer hql = new StringBuffer(" select cr from CampaignReceiver cr where ");
        if(dto.getCampaignId() != null) hql.append("cr.campaignId=:campaignId");
        if(! StringUtil.isNullOrBank(dto.getBatchCode()))  hql.append(" and cr.batchCode=:batchCode");
        if(! StringUtil.isNullOrBank(dto.getJobid())) hql.append(" and cr.jobid=:jobid");
         hql.append(" and nvl(cr.status,0)=:status" );

        Query q = getSession().createQuery(hql.toString());
        if(dto.getCampaignId() != null)  q.setLong("campaignId",dto.getCampaignId());
        if(! StringUtil.isNullOrBank(dto.getBatchCode())) q.setString("batchCode", dto.getBatchCode());
        if(! StringUtil.isNullOrBank(dto.getJobid()))  q.setString("jobid", dto.getJobid());
        q.setString("status",dto.getStatus());

        return  q.list();

    }

	/** 
	 * <p>Title: queryByLeadContact</p>
	 * <p>Description: </p>
	 * @param leadId
	 * @param contactId
	 * @return CampaignReceiver
	 * @see com.chinadrtv.erp.marketing.core.dao.CampaignReceiverDao#queryByLeadContact(long, java.lang.String)
	 */ 
	@Override
	public CampaignReceiver queryByLeadContact(long leadId, String contactId) {
		String hql = "select cr from CampaignReceiver cr where cr.leadId=:leadId and cr.customerId=:contactId";
		return this.find(hql, new ParameterLong("leadId", leadId), new ParameterString("contactId", contactId));
	}

	/** 
	 * <p>Title: queryUnStartByBatch</p>
	 * <p>Description: </p>
	 * @param batchCode
	 * @return
	 * @see com.chinadrtv.erp.marketing.core.dao.CampaignReceiverDao#queryUnStartByBatch(java.lang.String)
	 */ 
	@Override
	public List<CampaignReceiver> queryRecyclableByBatch(String batchCode) {
		String hql = "select cr from CampaignReceiver cr where cr.batchCode=:batchCode and cr.status in('1', '2') ";
		return this.findList(hql, new ParameterString("batchCode", batchCode));
	}

	/** 
	 * <p>Title: queryByBpmInstId</p>
	 * <p>Description: </p>
	 * @param bpmInstId
	 * @return
	 * @see com.chinadrtv.erp.marketing.core.dao.CampaignReceiverDao#queryByBpmInstId(long)
	 */ 
	@Override
	public CampaignReceiver queryByBpmInstId(long bpmInstId) {
		String hql = "select cr from CampaignReceiver cr where cr.bpmInstId=:bpmInstId ";
		return this.find(hql, new ParameterLong("bpmInstId", bpmInstId));
	}

	/** 
	 * <p>Title: queryTotalByBatchCode</p>
	 * <p>Description: </p>
	 * @param batchCode
	 * @return Integer
	 * @see com.chinadrtv.erp.marketing.core.dao.CampaignReceiverDao#queryTotalByBatchCode(java.lang.String)
	 */ 
	@SuppressWarnings("rawtypes")
	@Override
	public Integer queryTotalQtyByBatch(String batchCode) {
		String hql = "select count(*) from CampaignReceiver cr where cr.batchCode=:batchCode ";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		params.put("batchCode", new ParameterString("batchCode", batchCode));
		
		return this.findPageCount(hql, params);
	}

	/** 
	 * <p>Title: queryLeftByBatchCode</p>
	 * <p>Description: </p>
	 * @param batchCode
	 * @return Integer
	 * @see com.chinadrtv.erp.marketing.core.dao.CampaignReceiverDao#queryLeftByBatchCode(java.lang.String)
	 */ 
	@SuppressWarnings("rawtypes")
	@Override
	public Integer queryRecyclableQtyByBatch(String batchCode) {
		String hql = "select count(*) from CampaignReceiver cr where cr.batchCode=:batchCode and cr.status in ('1', '2') ";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		params.put("batchCode", new ParameterString("batchCode", batchCode));
		
		return this.findPageCount(hql, params);
	}

}
