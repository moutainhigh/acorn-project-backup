package com.chinadrtv.erp.uc.dao.hibernate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.dao.query.ParameterTimestamp;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.model.agent.CallHist;
import com.chinadrtv.erp.model.agent.CallHistLeadInteraction;
import com.chinadrtv.erp.uc.dao.CallHistDao;
import com.chinadrtv.erp.uc.dto.CallHistDto;
import com.chinadrtv.erp.uc.dto.TCallHistDto;

/**
 * 
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
 * @author dengqianyong@chinadrtv.com
 * @version 1.0
 * @since 2013-5-14 上午10:44:24 
 *
 */
@Repository("callHistDao")
public class CallHistDaoImpl extends GenericDaoHibernate<CallHist, Long> implements CallHistDao {

	public CallHistDaoImpl() {
		super(CallHist.class);
	}

	public List<CallHist> getCallHistByContactId(String contactId, Date sdt,Date edt, int index, int size) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("from CallHist t where 1=1 ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter page = new ParameterInteger("page", index);
		page.setForPageQuery(true);
		Parameter rows = new ParameterInteger("rows", size);
		rows.setForPageQuery(true);
		CallHistDto dto = new CallHistDto();
		dto.setContactid(contactId);
		dto.setEdt(edt);
		dto.setSdt(sdt);
		initQuery(sql,params,dto);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by t.stTm desc");
		List<CallHist> objList = findPageList(sql.toString(), params);
		return objList;

	}

	public int getCallHistByContactIdCount(String contactId, Date sdt, Date edt) throws Exception {
		// TODO Auto-generated method stub
    	StringBuffer sql = new StringBuffer("select count(t.callId) from CallHist t  where 1=1  ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		CallHistDto dto = new CallHistDto();
		dto.setContactid(contactId);
		dto.setEdt(edt);
		dto.setSdt(sdt);
		initQuery(sql,params,dto);
		Integer count = findPageCount(sql.toString(), params);
		return count;
	}

    public Long getCallHistLeadInteraction(String contactId) {
        Query q = getSession().createQuery("select count(*)  from CallHistLeadInteraction c where c.contactId =:contactId");
        q.setParameter("contactId",contactId);
        Object count = q.uniqueResult();
        if(count==null){
            return  0L;
        }
        return Long.parseLong(count.toString());
    }

    public List<CallHistLeadInteraction> getCallHistLeadInteraction(String contactId, int startRow, int rows) {
        Query query = getSession().createQuery("from CallHistLeadInteraction c where c.contactId =:contactId order by c.createDate desc");
        query.setParameter("contactId",contactId);
        query.setFirstResult(startRow);
        query.setMaxResults(rows);
        return query.list();
    }

    public void updateCallHistContact(String contactId, String potentialContactId) {
        Query query = getSession().createQuery("from CallHist c where c.contactId =:potentialContactId");
        query.setParameter("potentialContactId",potentialContactId);
        List list = query.list();
        if(list !=null && !list.isEmpty()){
            for(Object obj :list) {
                CallHist callHist = (CallHist)obj;
                callHist.setContactId(contactId);
                getSession().update(callHist);
            }
        }
    }

    protected void initQuery(StringBuffer sql,Map<String, Parameter> params, CallHistDto callHistDto) throws Exception {
   
		if (null != callHistDto.getSdt()) {
		    long between=(new Date().getTime() - callHistDto.getSdt().getTime());
		    long days=between/(24*3600);
		    if(days<365){
			    sql.append(" and to_char(t.stTm,'yyyy-mm-dd hh24:mi:ss') >=:sdt");
			    Parameter sdtParams = new ParameterTimestamp("sdt", callHistDto.getSdt());
			    params.put("sdtParams", sdtParams);
		    }else{
		    	throw new Exception("查询时间超出1年");
		    }
		}else{
//			sql.append(" and to_char(t.stTm,'yyyy-mm-dd hh24:mi:ss') <=:sdt");
//			//sql.append(" and t.stTm <= :sdt");
//			Date date = new Date();
//			date.setYear(date.getYear()-1);
//			System.out.println("date:"+date.getMonth());
//			Parameter sdtParams = new ParameterTimestamp("sdt", date);	
//			
//			params.put("sdtParams", sdtParams);
		}
		
		if (null != callHistDto.getEdt()) {
			sql.append(" and to_char(t.stTm,'yyyy-mm-dd hh24:mi:ss') <=:edt");
			Parameter edtParams = new ParameterTimestamp("edt", callHistDto.getEdt());	
			params.put("edtParams", edtParams);
		}
		
		if (StringUtils.isNotBlank(callHistDto.getContactid())) {
			sql.append(" and t.contactId =:contactid");
			Parameter contactIdParams = new ParameterString("contactid", callHistDto.getContactid());
			params.put("contactIdParams", contactIdParams);
		}
		
	}

}
