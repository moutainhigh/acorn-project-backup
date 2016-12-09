package com.chinadrtv.erp.marketing.dao.hibernate;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterDate;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.dao.IdlCrmPhoneRankDao;
import com.chinadrtv.erp.marketing.model.bi.IdlCrmPhoneRank;

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
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:19:43 
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class IdlCrmPhoneRankDaoImpl extends GenericDaoHibernate<IdlCrmPhoneRank, java.lang.String> implements  Serializable,IdlCrmPhoneRankDao{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	 @Qualifier("jdbcTemplateBI")
	private JdbcTemplate jdbcTemplate;


	public IdlCrmPhoneRankDaoImpl() {
	    super(IdlCrmPhoneRank.class);
	}

	
	public List<IdlCrmPhoneRank> queryListByJdbc(Integer startNm,Integer endNum) {
		String sql = "select * from                                                                                "+   
				"(                                                                                                 "+
				"select rownum numb,  phoneid,  phone_rank,  last_modify_date ,  dml_type ,  sync_flag 				"+
				" from  acorn_dm.IDL_APP_CRM_PHONERANK_FT t 														"+
				" where  t.sync_flag='0'  and t.phoneid='799384'                       		   											"+
				")                                                                                                 "+
				"where  numb>? and numb<=?";
		
		 Object[] paramsObj = new Object[] {startNm,endNum};  
		  int[] types = new int[] {Types.INTEGER,Types.INTEGER};
		  
		  List<IdlCrmPhoneRank> list = jdbcTemplate.query(sql, paramsObj,types,new RowMapper(){

			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				IdlCrmPhoneRank dest = new IdlCrmPhoneRank();
				
				dest.setPhoneid(rs.getString("phoneid"));
				dest.setPhone_rank(rs.getString("phone_rank"));
				dest.setLast_modify_date(rs.getDate("last_modify_date"));
				dest.setDml_type(rs.getString("dml_type"));
				dest.setSync_flag(rs.getString("sync_flag"));
				return dest;
			}
			
		});
		
		return list;
	}
	


	 @Autowired
	 @Required
	 @Qualifier("sessionFactoryBI")
	 public void setSessionFactory(SessionFactory sessionFactory){
		 this.doSetSessionFactory(sessionFactory);
	 }


	/**
	 * 
	 */
	 public int updateSyncFlag(String phoneid,String phoneRank,Date lastDate,String newFlag,String origFlag) {
		
		int result = -1 ;
		String hql = "update IdlCrmPhoneRank set sync_flag=:syncFlag where phoneid=:phoneid and phone_rank=:phoneRank  and sync_flag=:oldSyncFlag";
		
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter syncflag = new ParameterString("syncFlag",newFlag);
		Parameter phoneidParm = new ParameterString("phoneid",phoneid);
		Parameter phoneRankParm = new  ParameterString("phoneRank",phoneRank);
//		Parameter lastDateParm = new  ParameterDate("lastDate",lastDate);
		Parameter oldSyncFlag = new ParameterString("oldSyncFlag",origFlag);
		
		
		params.put("syncflag", syncflag);
		params.put("oldSyncFlag", oldSyncFlag);
		params.put("phoneid", phoneidParm);
		params.put("phoneRank", phoneRankParm);
//		params.put("lastDate", lastDateParm);
		
		
		result = this.execUpdate(hql, params);
		
		return result;
	}
	
	

}
