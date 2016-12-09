package com.chinadrtv.erp.marketing.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.marketing.dao.NamesMarketingDao;
import com.chinadrtv.erp.marketing.dao.SmsAnswersDao;
import com.chinadrtv.erp.marketing.dto.SmsAnswerDto;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsAnswer;
import com.chinadrtv.erp.smsapi.model.SmsBlackList;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;

/****
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
 * @author cuiming
 * @version 1.0
 * @since 2013-1-18 下午2:47:10
 * 
 */
@Repository
public class SmsAnswersDaoImpl extends
		GenericDaoHibernate<SmsAnswer, java.lang.String> implements
		SmsAnswersDao {

	@Autowired
	private NamesMarketingDao namesMarketingDao;

	/*
	 * (非 Javadoc) <p>Title: save</p> <p>Description: </p>
	 * 
	 * @param object
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.core.dao.GenericDao#save(java.lang.Object)
	 */

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
	public SmsAnswersDaoImpl(Class<SmsAnswer> persistentClass) {
		super(persistentClass);
		// TODO Auto-generated constructor stub
	}

	public SmsAnswersDaoImpl() {
		super(SmsAnswer.class);
	}

	public SmsAnswer getSmsAnswerById(Long id) {
		Session session = getSession();
		return (SmsAnswer) session.get(SmsAnswer.class, id);
	}

	/*
	 * 返回短信回复数据 (非 Javadoc) <p>Title: query</p> <p>Description: </p>
	 * 
	 * @param smsAnswerDto
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsAnswerDao#query(com.chinadrtv.erp.
	 * marketing.dto.SmsAnswerDto,
	 * com.chinadrtv.erp.marketing.common.DataGridModel)
	 */

	public List<SmsAnswerDto> query(SmsAnswerDto smsAnswerDto,
			DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer(
				"select o.id ,o.mobile,o.receiveContent,o.receiveChannel,o.smsChildId,o.createtime,creator, o.receiveTime from SmsAnswer o where state = '0' or state is null");
		Map<String, Parameter> params = genSql(sql, smsAnswerDto);

		// sql.append(" AND o.addressid = a.addressId ");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by o.receiveTime desc ");
		List objList = findPageList(sql.toString(), params);
		List<SmsAnswerDto> result = new ArrayList<SmsAnswerDto>();
		Object[] obj = null;
		SmsAnswerDto smsAnswer = null;
		StringBuffer contactid = new StringBuffer();
		List<Map<String, Object>> contactidList = new ArrayList();
		for (int i = 0; i < objList.size(); i++) {
			obj = (Object[]) objList.get(i);
			smsAnswer = new SmsAnswerDto();
			smsAnswer.setId((Long) obj[0]);
			if (obj[1] != null) {
				smsAnswer.setMobile(obj[1].toString());
				contactidList = namesMarketingDao.queryContactidByPhone(obj[1]
						.toString());
				if (contactidList != null && !contactidList.isEmpty()) {
					for (int j = 0; j < contactidList.size(); j++) {
						contactid = contactid.append(contactidList.get(j).get(
								"contactid")
								+ ",");
						smsAnswer.setContactid(contactid.toString().substring(
								0, contactid.lastIndexOf(",")));
						contactid.setLength(0);
					}
				}
			}
			if (obj[2] != null) {
				smsAnswer.setReceiveContent(obj[2].toString());
			}
			if (obj[3] != null) {
				smsAnswer.setReceiveChannel(obj[3].toString());
			}
			if (obj[4] != null) {
				smsAnswer.setSmsChildId(obj[4].toString());
			}
			if (obj[5] != null) {
				smsAnswer.setReceiveTimes(DateTimeUtil.sim3.format(
						(Date) obj[5]).toString());
			}
			result.add(smsAnswer);
		}
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: queryCount</p> <p>Description: </p>
	 * 
	 * @param smsAnswerDto
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsAnswerDao#queryCount(com.chinadrtv
	 * .erp.marketing.dto.SmsAnswerDto)
	 */

	public Integer queryCount(SmsAnswerDto smsAnswerDto) {
		StringBuffer sql = new StringBuffer(
				"select count(o.id) from SmsAnswer o  where o.state = '0' or o.state is null");

		Map<String, Parameter> params = genSql(sql, smsAnswerDto);

		Integer result = findPageCount(sql.toString(), params);

		return result;

	}

	public Map<String, Parameter> genSql(StringBuffer sql,
			SmsAnswerDto smsAnswerDto) {
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (smsAnswerDto.getId() != null) {
			sql.append(" AND o.id=:id");
			Parameter id = new ParameterString("id", smsAnswerDto.getId()
					.toString());
			paras.put("id", id);
		}
		if (smsAnswerDto.getMobile() != null
				&& !("").equals(smsAnswerDto.getMobile())) {
			sql.append(" AND o.mobile=:mobile");
			Parameter mobile = new ParameterString("mobile", smsAnswerDto
					.getMobile().toString());
			paras.put("mobile", mobile);
		}
		if (smsAnswerDto.getReceiveChannel() != null
				&& !("").equals(smsAnswerDto.getReceiveChannel())) {
			sql.append(" AND o.receiveChannel=:receiveChannel");
			Parameter receiveChannel = new ParameterString("receiveChannel",
					smsAnswerDto.getReceiveChannel().toString());
			paras.put("receiveChannel", receiveChannel);
		}
		if (smsAnswerDto.getReceiveContent() != null
				&& !("").equals(smsAnswerDto.getReceiveContent())) {
			sql.append(" AND o.receiveContent=:receiveContent");
			Parameter receiveContent = new ParameterString("receiveContent",
					smsAnswerDto.getReceiveChannel().toString());
			paras.put("receiveContent", receiveContent);
		}
		if (smsAnswerDto.getSmsChildId() != null
				&& !("").equals(smsAnswerDto.getSmsChildId())) {
			sql.append(" AND o.smsChildId=:smsChildId");
			Parameter smsChildId = new ParameterString("smsChildId",
					smsAnswerDto.getSmsChildId().toString());
			paras.put("smsChildId", smsChildId);
		}
		if (smsAnswerDto.getState() != null
				&& !("").equals(smsAnswerDto.getState())) {
			sql.append(" AND o.state=:state");
			Parameter state = new ParameterString("state", smsAnswerDto
					.getReceiveChannel().toString());
			paras.put("state", state);
		}
		if (!StringUtils.isEmpty(smsAnswerDto.getStartTime())) {
			sql.append(" AND o.receiveTime >= TO_DATE(:startTime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter startTime = null;
			try {
				startTime = new ParameterString("startTime",
						smsAnswerDto.getStartTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paras.put("startTime", startTime);
		}
		if (!StringUtils.isEmpty(smsAnswerDto.getEndTime())) {
			sql.append(" AND o.receiveTime <= TO_DATE(:endTime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter endTime = null;
			try {
				endTime = new ParameterString("endTime",
						smsAnswerDto.getEndTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paras.put("endTime", endTime);
		}

		return paras;
	}

	public Map<String, Parameter> sendSqlSend(StringBuffer sql,
			SmssnedDto smssnedDto) {
		Map<String, Parameter> paras = new HashMap<String, Parameter>();

		if (!StringUtils.isEmpty(smssnedDto.getStarttime())) {
			sql.append(" AND o.createtime >= TO_DATE(:starttime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter starttime = null;
			try {
				starttime = new ParameterString("starttime",
						smssnedDto.getStarttime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paras.put("starttime", starttime);
		}
		if (!StringUtils.isEmpty(smssnedDto.getEndtime())) {
			sql.append(" AND o.createtime <= TO_DATE(:endtime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter endtime = null;
			try {
				endtime = new ParameterString("endtime",
						smssnedDto.getEndtime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paras.put("endtime", endtime);
		}
		if (!StringUtils.isEmpty(smssnedDto.getBatchId())) {
			sql.append(" AND o.batchId = :batchId");
			Parameter batchId = null;
			try {
				batchId = new ParameterString("batchId",
						smssnedDto.getBatchId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paras.put("batchId", batchId);
		}
		if (!StringUtils.isEmpty(smssnedDto.getGroupCode())) {
			sql.append(" AND o.groupCode = :groupCode");
			Parameter groupCode = null;
			try {
				groupCode = new ParameterString("groupCode",
						smssnedDto.getGroupCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paras.put("groupCode", groupCode);
		}
		if (!StringUtils.isEmpty(smssnedDto.getGroupName())) {
			sql.append(" AND o.groupName = :groupName");
			Parameter groupName = null;
			try {
				groupName = new ParameterString("groupName",
						smssnedDto.getBatchId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paras.put("groupName", groupName);
		}
		if (!StringUtils.isEmpty(smssnedDto.getSmsName())) {
			sql.append(" AND o.smsName = :smsName");
			Parameter smsName = null;
			try {
				smsName = new ParameterString("smsName",
						smssnedDto.getSmsName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paras.put("smsName", smsName);
		}
		if (!StringUtils.isEmpty(smssnedDto.getCreator())) {
			sql.append(" AND o.creator = :creator");
			Parameter creator = null;
			try {
				creator = new ParameterString("creator",
						smssnedDto.getCreator());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paras.put("creator", creator);
		}
		return paras;
	}

	/*
	 * 更改状态（逻辑删除） (非 Javadoc) <p>Title: deleteObject</p> <p>Description: </p>
	 * 
	 * @param smsAnswer
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsAnswerDao#deleteObject(com.chinadrtv
	 * .erp.marketing.model.SmsAnswer)
	 */
	public int deleteObject(SmsAnswer smsAnswer) {
		// TODO Auto-generated method stub
		int flag = 0;
		try {
			getHibernateTemplate().update(smsAnswer);
		} catch (Exception e) {
			flag = 1;
			e.printStackTrace();
		}
		return flag;
	}

	/*
	 * 插入黑名单 (非 Javadoc) <p>Title: insertBlcakList</p> <p>Description: </p>
	 * 
	 * @param smsBlackList
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsAnswerDao#insertBlcakList(com.chinadrtv
	 * .erp.marketing.model.SmsBlackList)
	 */
	public void insertBlcakList(SmsBlackList smsBlackList) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(smsBlackList);

	}

	/**
	 * <p>
	 * Title: query
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param smsSend
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsAnswersDao#query(com.chinadrtv.erp
	 *      .smsapi.module.SmsSend,
	 *      com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public List<SmssnedDto> query(SmssnedDto smsSend, DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer(
				"select o.batchId ,o.department,o.groupCode,o.groupName,o.smsName,o.createtime,o.creator,o.smsstopStatus,o.sendStatus from SmsSend o where o.type=2 ");
		Map<String, Parameter> params = sendSqlSend(sql, smsSend);

		// sql.append(" AND o.addressid = a.addressId ");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by o.createtime desc");
		List objList = findPageList(sql.toString(), params);
		List<SmssnedDto> result = new ArrayList<SmssnedDto>();
		Object[] obj = null;
		SmssnedDto smsSends = null;
		for (int i = 0; i < objList.size(); i++) {
			obj = (Object[]) objList.get(i);
			smsSends = new SmssnedDto();
			smsSends.setBatchId(obj[0].toString());
			if (obj[1] != null) {
				smsSends.setDepartment(obj[1].toString());
			}
			if (obj[2] != null) {
				smsSends.setGroupCode(obj[2].toString());
			}
			if (obj[3] != null) {
				smsSends.setGroupName(obj[3].toString());
			}
			if (obj[4] != null) {
				smsSends.setSmsName(obj[4].toString());
			}
			if (obj[5] != null) {
				smsSends.setCreateTime(DateTimeUtil.sim3
						.format((java.util.Date) obj[5]));
			}
			if (obj[6] != null) {
				smsSends.setCreator(obj[6].toString());
			}
			if (obj[7] != null) {
				smsSends.setSmsstopStatus(obj[7].toString());
			}
			if (obj[8] != null) {
				smsSends.setSendStatus(obj[8].toString());
			}
			result.add(smsSends);
		}
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: queryCounts</p> <p>Description: </p>
	 * 
	 * @param smsSend
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsAnswersDao#queryCounts(com.chinadrtv
	 * .erp.marketing.dto.SmssnedDto)
	 */
	public Integer queryCounts(SmssnedDto smsSend) {
		StringBuffer sql = new StringBuffer(
				"select count(o.id) from SmsSend o  where o.type='2' ");

		Map<String, Parameter> params = sendSqlSend(sql, smsSend);

		Integer result = findPageCount(sql.toString(), params);

		return result;
	}

}
