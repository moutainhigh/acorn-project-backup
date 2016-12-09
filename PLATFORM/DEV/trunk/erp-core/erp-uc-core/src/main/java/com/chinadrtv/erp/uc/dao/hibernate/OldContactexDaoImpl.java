/*
 * @(#)OldContactexDaoImpl.java 1.0 2013-5-9下午3:19:47
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import static com.chinadrtv.erp.uc.constants.CustomerConstant.OB_CUSTOMER_TYPE_OLD;
import static com.chinadrtv.erp.uc.constants.CustomerConstant.OB_CUSTOMER_TYPE_ORDER;
import static com.chinadrtv.erp.uc.constants.CustomerConstant.OB_CUSTOMER_TYPE_SELF;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterDate;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.OldContactex;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dao.OldContactexDao;
import com.chinadrtv.erp.uc.dto.ObCustomerDto;
import com.chinadrtv.erp.user.dao.AgentUserDao;
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
 * @since 2013-5-9 下午3:19:47 
 * 
 */
@Repository
public class OldContactexDaoImpl extends GenericDaoHibernate<OldContactex, String> implements OldContactexDao {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OldContactexDaoImpl.class);
    @Autowired
	private AgentUserDao agentUserDao;

	public OldContactexDaoImpl() {
		super(OldContactex.class);
	}

	/** 
	 * <p>Title: 分页查询行数</p>
	 * <p>Description: </p>
	 * @param obCustomerDto
	 * @return Integer
	 * @see com.chinadrtv.erp.uc.dao.OldContactDao#queryPageCount(com.chinadrtv.erp.uc.dto.ObCustomerDto)
	 */ 
	public Integer queryPageCount(ObCustomerDto cDto) {
		
		StringBuffer sb =  new StringBuffer();
		
		String sql = "select count(distinct c.contactid) " +
					 " from iagent.contact c " +
					 " inner join iagent.oldcontactex o on o.contactid = c.contactid "+
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
	 * @see com.chinadrtv.erp.uc.dao.OldContactDao#queryPageList(com.chinadrtv.erp.uc.common.DataGridModel, com.chinadrtv.erp.uc.dto.ObCustomerDto)
	 */ 
	@SuppressWarnings("unchecked")
	public List<ObCustomerDto> queryPageList(DataGridModel dataModel, ObCustomerDto cDto) {
		
		StringBuffer sb =  new StringBuffer();
		
		sb.append(" select * from (select row_.*, rownum rownum_  from ( ");
		
		String sql = " select distinct c.*," +
					 " mt.dsc MEMBERTYPE_LABEL, m.memberlevel MEMBERLEVEL_LABEL, o.stime, o.etime, o.order_total, o.finish_total  " +
					 " from iagent.contact c " +
					 " inner join iagent.oldcontactex o on o.contactid = c.contactid "+
					 " left join iagent.phone p on c.contactid = p.contactid "+
					 " left join iagent.membertype mt on c.membertype=mt.id and mt.status='-1' " +
					 " left join iagent.vm_customer vc on c.contactid = vc.contactid " +
					 " left join iagent.memberservice m on vc.memberlevelid=m.memberlevelid " +
					 " where 1=1 ";
		
		sb.append(sql);
		
		String whereCause = this.whereCause(cDto);
		sb.append(whereCause);
		sb.append(" order by c.crdt desc");
		
		sb.append("  ) row_ where rownum <= :endRow) where rownum_ > :beginRow ");
		
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
			ObCustomerDto dto = convert2ObCustomerDto(map);
			dto.setCustomerFrom(OB_CUSTOMER_TYPE_OLD);
			dto.setCustomerOwner(this.queryCustomerOwner(dto, user.getUserId()));
			
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	/**
	 * <p>拼接SQL</p>
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

		StringBuffer sb =  new StringBuffer();
		
		sb.append("  and trunc(o.stime) <= trunc(sysdate) and trunc(o.etime) >= trunc(sysdate) ");
		sb.append(" and o.status='-1' ");
		
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
			sb.append(" and m.memberlevelid=:memberLevel ");
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
			sb.append( " and o.usrid=:agentId ");
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
	 * <p>查询客户归属</p>
	 * @param dto
	 * @param userId
	 * @return Integer
	 */
	public Integer queryCustomerOwner(ObCustomerDto dto, String userId) {
		String contactId = dto.getContactid();
		Integer customerFrom = dto.getCustomerFrom();
		OldContactex oldContact = this.queryByContactId(contactId);
		//老客户
		if(customerFrom == OB_CUSTOMER_TYPE_OLD){
			return CustomerConstant.CUSTOMER_OWNER_DEDICATED;
		}else if(customerFrom == OB_CUSTOMER_TYPE_SELF || customerFrom == OB_CUSTOMER_TYPE_ORDER){
			if(null==oldContact || null==oldContact.getUsrid() || "".equals(oldContact.getUsrid()) || userId.equals(oldContact.getUsrid())){
				return CustomerConstant.CUSTOMER_OWNER_AVALIABLE;
			}else{
				return CustomerConstant.CUSTOMER_OWNER_LOCKED;
			}
		}
		
		return -1;
	}

	/**
	 * <p></p>
	 * @param contactId
	 * @return
	 */
	private OldContactex queryByContactId(String contactId) {
		String hql = "select o from OldContactex o where o.contactid=:contactId";
		return this.find(hql, new ParameterString("contactId", contactId));
	}

	public ObCustomerDto convert2ObCustomerDto(Map<String, Object> map){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		ObCustomerDto dto = new ObCustomerDto();
		dto.setContactid(map.get("CONTACTID").toString());
		dto.setName(null==map.get("NAME") ? "" : map.get("NAME").toString());
		dto.setSex(null==map.get("SEX") ? "" : map.get("SEX").toString());
		dto.setTitle(null==map.get("TITLE") ? "" : map.get("TITLE").toString());
		dto.setDept(null==map.get("DEPT") ? "" : map.get("DEPT").toString());
		dto.setContacttype(null==map.get("CONTACTTYPE") ? "" : map.get("CONTACTTYPE").toString());
		dto.setEmail(null==map.get("EMAIL") ? "" : map.get("EMAIL").toString());
		dto.setWebaddr(null==map.get("WEBADDR") ? "" : map.get("WEBADDR").toString());
		try {
			dto.setCrdt(null==map.get("CRDT") ? null : sdf.parse(map.get("CRDT").toString()));
			dto.setCrtm(null==map.get("CRTM") ? null : sdf.parse(map.get("CRTM").toString()));
			dto.setMddt(null==map.get("MDDT") ? null : sdf.parse(map.get("MDDT").toString()));
			dto.setMdtm(null==map.get("MDTM") ? null : sdf.parse(map.get("MDTM").toString()));
			dto.setBirthday(null==map.get("BIRTHDAY") ? null : sdf.parse(map.get("BIRTHDAY").toString()));
			dto.setChildrenage(null==map.get("CHILDRENAGE") ? null : sdf.parse(map.get("CHILDRENAGE").toString()));
			dto.setLastdate(null==map.get("LAST_UPDATE_TIME") ? null : sdf.parse(map.get("LAST_UPDATE_TIME").toString()));
			
			dto.setBindBeginDate(null==map.get("STIME") ? null : sdf.parse(map.get("STIME").toString()));
			dto.setBindEndDate(null==map.get("ETIME") ? null : sdf.parse(map.get("ETIME").toString()));
		} catch (ParseException e1) {
			logger.error(e1.getMessage(),e1); //e1.printStackTrace();
		}
		dto.setCrusr(null==map.get("CRUSR") ? "" : map.get("CRUSR").toString());
		
		dto.setMdusr(null==map.get("MDUSR") ? "" : map.get("MDUSR").toString());
		dto.setEntityid(null==map.get("ENTITYID") ? "" : map.get("ENTITYID").toString());
		
		dto.setAges(null==map.get("AGES") ? "" : map.get("AGES").toString());
		dto.setEducation(null==map.get("EDUCATION") ? "" : map.get("EDUCATION").toString());
		dto.setIncome(null==map.get("INCOME") ? "" : map.get("INCOME").toString());
		dto.setMarriage(null==map.get("MARRIAGE") ? "" : map.get("MARRIAGE").toString());
		dto.setOccupation(null==map.get("OCCUPATION") ? "" : map.get("OCCUPATION").toString());
		dto.setConsumer(null==map.get("CONSUMER") ? "" : map.get("CONSUMER").toString());
		dto.setPin(null==map.get("PIN") ? "" : map.get("PIN").toString());
		dto.setTotal(null==map.get("TOTAL") ? null : new Long(map.get("TOTAL").toString()));
		dto.setPurpose(null==map.get("PURPOSE") ? "" : map.get("PURPOSE").toString());
		dto.setNetcontactid(null==map.get("NETCONTACTID") ? "" : map.get("NETCONTACTID").toString());
		dto.setJifen(null==map.get("JIFEN") ? "" : map.get("JIFEN").toString());
		dto.setAreacode(null==map.get("AREACODE") ? "" : map.get("AREACODE").toString());
		dto.setTicketvalue(null==map.get("TICKETVALUE") ? null : new Long(map.get("TICKETVALUE").toString()));
		dto.setFancy(null==map.get("FANCY") ? "" : map.get("FANCY").toString());
		dto.setIdcardFlag(null==map.get("IDCARD_FLAG") ? "" : map.get("IDCARD_FLAG").toString());
		dto.setAttitude(null==map.get("ATTITUDE") ? "" : map.get("ATTITUDE").toString());
		dto.setMembertype(null==map.get("MEMBERTYPE") ? "" : map.get("MEMBERTYPE").toString());
		dto.setMemberlevel(null==map.get("MEMBERLEVEL") ? "" : map.get("MEMBERLEVEL").toString());
		try {
			dto.setLastdate(null==map.get("LASTDATE") ? null : sdf.parse(map.get("LASTDATE").toString()));
		} catch (ParseException e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
		}
		dto.setTotalfrequency(null==map.get("TOTALFREQUENCY") ? null : new Integer(map.get("TOTALFREQUENCY").toString()));
		dto.setTotalmonetary(null==map.get("TOTALMONETARY") ? null : new Long(map.get("TOTALFREQUENCY").toString()));
		dto.setSundept(null==map.get("SUNDEPT") ? "" : map.get("SUNDEPT").toString());
		dto.setCredit(null==map.get("CREDIT") ? null : new Integer(map.get("CREDIT").toString()));
		dto.setCustomersource(null==map.get("CUSTOMERSOURCE") ? "" : map.get("CUSTOMERSOURCE").toString()); 
		dto.setDatatype(null==map.get("DATATYPE") ? "" : map.get("DATATYPE").toString());
		dto.setCar(null==map.get("CAR") ? "" : map.get("CAR").toString());
		dto.setCreditcard(null==map.get("CREDITCARD") ? "" : map.get("CREDITCARD").toString());
		dto.setChildren(null==map.get("CHILDREN") ? "" : map.get("CHILDREN").toString());
		
		dto.setCarmoney1(null==map.get("CARMONEY1") ? null : new Integer(map.get("CARMONEY1").toString()));
		dto.setCarmoney2(null==map.get("CARMONEY2") ? null : new Integer(map.get("CARMONEY2").toString()));
		dto.setDnis(null==map.get("DNIS") ? "" : map.get("DNIS").toString());
		dto.setCaseid(null==map.get("CASEID") ? "" : map.get("CASEID").toString()); 
		//map.get("LAST_LOCK_SEQID") ? "" : map.get("LAST_LOCK_SEQID").toString()
		//map.get("LAST_UPDATE_SEQID") ? "" : map.get("LAST_UPDATE_SEQID").toString()
		
		dto.setOccupation(null==map.get("OCCUPATION_STATUS") ? "" : map.get("OCCUPATION_STATUS").toString());
		//map.get("IS_HAS_ELDER") ? "" : map.get("IS_HAS_ELDER").toString()
		//map.get("ELDER_AGE") ? "" : map.get("ELDER_AGE").toString()
		//map.get("PICK_UP_ADDRESS") ? "" : map.get("PICK_UP_ADDRESS").toString()
		
		dto.setComments(null==map.get("COMMENTS") ? "" : map.get("COMMENTS").toString());
		dto.setResultCode(null==map.get("RESULT_CODE") ? "" : map.get("RESULT_CODE").toString());
		dto.setResultDesc(null==map.get("RESULT_DESCRIPTION") ? "" : map.get("RESULT_DESCRIPTION").toString());
		try {
			dto.setLastCallDate(null==map.get("CREATE_DATE") ? null : sdf.parse(map.get("CREATE_DATE").toString()));
		} catch (ParseException e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
		}
		dto.setMembertypeLabel(null==map.get("MEMBERTYPE_LABEL") ? "" : map.get("MEMBERTYPE_LABEL").toString());
		dto.setMemberlevelLabel(null==map.get("MEMBERLEVEL_LABEL") ? "" : map.get("MEMBERLEVEL_LABEL").toString());
		
		//审批列表相关信息
		dto.setInstId(null==map.get("BATCH_ID") ? null : Integer.parseInt(map.get("BATCH_ID").toString()));
		dto.setInstStatus(null==map.get("BATCH_STATUS") ? "" : map.get("BATCH_STATUS").toString());
		
		dto.setTotalOrderCount(null==map.get("ORDER_TOTAL") ? 0 : Integer.parseInt(map.get("ORDER_TOTAL").toString()));
		dto.setTotalFinishCount(null==map.get("FINISH_TOTAL") ? 0 : Integer.parseInt(map.get("FINISH_TOTAL").toString()));
		
		return dto;
	}

	/** 
	 * <p>Title: queryBindAgentUser</p>
	 * <p>Description: 查询老客户绑定坐席</p>
	 * @param contactId
	 * @return AgentUser
	 * @see com.chinadrtv.erp.uc.dao.OldContactDao#queryBindAgentUser(java.lang.String)
	 */ 
	public AgentUser queryBindAgentUser(String contactId) {
		String hql = "select o from OldContactex o where o.contactid=:contactId " +
					 " and o.status='-1' and trunc(o.stime) <= trunc(:date) and trunc(o.etime) >= trunc(:date) ";
		OldContactex oldContact = this.find(hql, new ParameterString("contactId", contactId), new ParameterDate("date", new Date()));
		if(null!=oldContact){
			String userId = oldContact.getUsrid();
			AgentUser user = agentUserDao.get(userId);
			return user;
		}
		return null;
	}

}
