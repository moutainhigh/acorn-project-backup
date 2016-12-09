/*
 * @(#)ObContactDaoImpl.java 1.0 2013-5-9下午3:00:38
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import static com.chinadrtv.erp.uc.constants.CustomerConstant.OB_CUSTOMER_TYPE_SELF;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.ObContact;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dao.ObContactDao;
import com.chinadrtv.erp.uc.dao.OldContactexDao;
import com.chinadrtv.erp.uc.dto.ObCustomerDto;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-5-9 下午3:00:38 
 * 
 */
@Repository("ucObContactDao")
public class ObContactDaoImpl extends GenericDaoHibernate<ObContact, String> implements ObContactDao {
	
	@Autowired
	private OldContactexDao oldContactexDao;

	public ObContactDaoImpl() {
		super(ObContact.class);
	}

	/** 
	 * <p>Title: 分页查询行数</p>
	 * <p>Description: </p>
	 * @param obCustomerDto
	 * @return Integer
	 * @see com.chinadrtv.erp.uc.dao.ObContactDao#queryPageCount(com.chinadrtv.erp.uc.dto.ObCustomerDto)
	 */ 
	public int queryPageCount(ObCustomerDto cDto) {
		StringBuffer sb =  new StringBuffer();
		
		String sql = "select count(distinct c.contactid) " +
				 	 " from iagent.contact c " +
				 	 " inner join iagent.ob_contact ob on ob.contactid = c.contactid "+
					 " left join iagent.phone p on c.contactid = p.contactid "+
					 " left join iagent.membertype mt on c.membertype=mt.id and mt.status='-1' " +
					 " left join iagent.vm_customer vc on c.contactid = vc.contactid " +
					 " left join iagent.memberservice m on vc.memberlevelid=m.memberlevelid " +
					 " where 1=1 ";
		
		sb.append(sql);
		
		String whereCause = this.whereCause(cDto);
		sb.append(whereCause);
		
		sb.append(" order by c.crdt desc");
		
		Query query = this.getSession().createSQLQuery(sb.toString());
		this.parameterizedQuery(query, cDto);
		BigDecimal rs = (BigDecimal) query.list().get(0);
		
		return rs.intValue();
	}
	
	
	/** 
	 * <p>Title: 分页查询</p>
	 * <p>Description: </p>
	 * @param dataGridModel
	 * @param obCustomerDto
	 * @return List<ObCustomerDto>
	 * @see com.chinadrtv.erp.uc.dao.ObContactDao#queryPageList(com.chinadrtv.erp.uc.common.DataGridModel, com.chinadrtv.erp.uc.dto.ObCustomerDto)
	 */ 
	@SuppressWarnings("unchecked")
	public List<ObCustomerDto> queryPageList(DataGridModel dataModel, ObCustomerDto cDto) {
		
		StringBuffer sb =  new StringBuffer();
		sb.append(" select * from (select row_.*, rownum rownum_  from ( ");
		
		String sql = " select distinct c.*," +
					 " mt.dsc MEMBERTYPE_LABEL, m.memberlevel MEMBERLEVEL_LABEL " +
				     " from iagent.contact c " +
				     " inner join iagent.ob_contact ob on ob.contactid = c.contactid "+
					 " left join iagent.phone p on c.contactid = p.contactid "+
					 " left join iagent.membertype mt on c.membertype=mt.id and mt.status='-1' " +
					 " left join iagent.vm_customer vc on c.contactid = vc.contactid " +
					 " left join iagent.memberservice m on vc.memberlevelid=m.memberlevelid " +
					 " where 1=1 ";
		
		sb.append(sql);
		String whereCause = this.whereCause(cDto);
		sb.append(whereCause);
		
		sb.append("  ) row_ where rownum <= :endRow ) where rownum_ > :beginRow ");
		
		Integer beginRow = dataModel.getStartRow();
		Integer endRow = dataModel.getRows() + beginRow;
		
		SQLQuery query = this.getSession().createSQLQuery(sb.toString());
		this.parameterizedQuery(query, cDto);
		query.setParameter("endRow", endRow);
		query.setParameter("beginRow", beginRow);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		List<Map<String, Object>> mapList = query.list();
		List<ObCustomerDto> dtoList = new ArrayList<ObCustomerDto>();
		
		AgentUser user = SecurityHelper.getLoginUser();
		for(Map<String, Object> map : mapList){	
			ObCustomerDto dto = oldContactexDao.convert2ObCustomerDto(map);
			dto.setCustomerFrom(OB_CUSTOMER_TYPE_SELF);
			dto.setCustomerOwner(oldContactexDao.queryCustomerOwner(dto, user.getUserId()));
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	
	/**
	 * <p>拼接where条件</p>
	 * @param cDto
	 * @return String
	 */
	private String whereCause(ObCustomerDto cDto) {
		String contactId = cDto.getContactid();
		String name = cDto.getName();
		String memberType = cDto.getMembertype();
		String memberLevel = cDto.getMemberlevel();
		String phoneType = cDto.getPhoneType();
		String phoneNo = cDto.getPhoneNo();
		String agentId = cDto.getAgentId();
		StringBuffer sb = new StringBuffer();
		
		sb.append(" and trunc(ob.assigntime) >= trunc(to_date(:beginDateStr , 'yyyy-mm-dd')) ");
		sb.append(" and trunc(ob.assigntime) <= trunc(to_date(:endDateStr , 'yyyy-mm-dd')) ");
		sb.append(" and ob.status='-1' ");
		sb.append(" and ((sysdate >= ob.startdate or ob.startdate is null) and (sysdate <= ob.assigntime + 7 or sysdate <= ob.enddate )) ");
		
		if(null!=contactId && !"".equals(contactId)){
			sb.append(" and c.contactid=:contactId ");
		}
		if(null!=name && !"".equals(name)){
			sb.append(" and c.name like :name ");
		}
		if(null!=memberType && !"".equals(memberType)){
			sb.append(" and c.membertype=:memberType ");
		}
		if(null!=memberLevel && !"".equals(memberLevel)){
			sb.append(" and c.memberlevel=:memberLevel ");
		}
		if(null!=phoneNo && !"".equals(phoneNo)){
			Integer _phoneType = Integer.parseInt(phoneType);
			if(_phoneType == CustomerConstant.PHONE_TYPE_CELL){
				sb.append(" and p.phonetypid='4' and p.phone_num=:phoneNo ");
			}else{
				sb.append(" and ((p.phonetypid='1' or " +
						"p.phonetypid='2') and p.phone_num=:phoneNo )");
			}
		}
		if(null!=agentId && !"".equals(agentId)){
			sb.append( " and ob.assignagent=:agentId ");
		}
		
		return sb.toString();
	}
	
	/**
	 * <p>参数化query</p>
	 * @param query
	 */
	private void parameterizedQuery(Query query, ObCustomerDto cDto) {
		String contactId = cDto.getContactid();
		String name = cDto.getName();
		String memberType = cDto.getMembertype();
		String memberLevel = cDto.getMemberlevel();
		String phoneType = cDto.getPhoneType();
		String phoneNo = cDto.getPhoneNo();
		String agentId = cDto.getAgentId();
		String resultCode = cDto.getResultCode();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String beginDateStr = sdf.format(cDto.getBeginDate());
		String endDateStr = sdf.format(cDto.getEndDate());
		
		query.setParameter("beginDateStr", beginDateStr);
		query.setParameter("endDateStr", endDateStr);
		if(null!=resultCode && !"".equals(resultCode)){
			query.setParameter("resultCode", resultCode);
		}
		if(null!=contactId && !"".equals(contactId)){
			query.setParameter("contactId", contactId);
		}
		if(null!=name && !"".equals(name)){
			query.setParameter("name", "%"+name+"%");
		}
		if(null!=memberType && !"".equals(memberType)){
			query.setParameter("memberType", memberType);
		}
		if(null!=memberLevel && !"".equals(memberLevel)){
			query.setParameter("memberLevel", memberLevel);
		}
		if(null!=phoneNo && !"".equals(phoneNo)){
			Integer _phoneType = Integer.parseInt(phoneType);
			if(_phoneType == CustomerConstant.PHONE_TYPE_CELL){
				query.setParameter("phoneNo", phoneNo);
			}else{
				query.setParameter("phoneNo", phoneNo);
			}
		}
		if(null!=agentId && !"".equals(agentId)){
			query.setParameter("agentId", agentId);
		}
	}


	/** 
	 * <p>Title: queryAgentUser</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return String
	 * @see com.chinadrtv.erp.uc.dao.ObContactDao#queryAgentUser(java.lang.String)
	 */ 
	@SuppressWarnings("unchecked")
	public String queryAgentUser(String contactId) {
		String sql = "select distinct ob.assignagent from iagent.ob_contact ob "+
					 " where ob.contactid = :contactId " +
					 " and ((sysdate >= ob.startdate or ob.startdate is null) " +
					 " and (sysdate <= ob.assigntime + 7 or sysdate <= ob.enddate )) ";
		
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter("contactId", contactId);
		List<String> rsList = query.list();
		if(null!=rsList){
			for(String agentId : rsList){
				if(null!=agentId && !"".equals(agentId)){
					return agentId;
				}
				continue;
			}
		}
		return null;
	}

}
