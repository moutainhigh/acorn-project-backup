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
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.dao.IdlCrmUsrFlagDao;
import com.chinadrtv.erp.marketing.model.bi.IdlCrmUsrFlag;
import com.chinadrtv.erp.util.DateUtil;

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
public class IdlCrmUsrFlagDaoImpl extends GenericDaoHibernate<IdlCrmUsrFlag, java.lang.String> implements  Serializable,IdlCrmUsrFlagDao{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	 @Qualifier("jdbcTemplateBI")
	private JdbcTemplate jdbcTemplate;


	public IdlCrmUsrFlagDaoImpl() {
	    super(IdlCrmUsrFlag.class);
	}

	
	public int queryCount(String scope) {
		String hql = "select count(*) from IdlCrmUsrFlag t where t.sync_flag=:syncFlag and t.data_date=:dataDate ";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter syncflag = new ParameterString("syncFlag","0");
		Parameter dataDate = new ParameterDate("dataDate",DateUtil.stringToDate(scope));
		
		
		params.put("syncflag", syncflag);
		params.put("dataDate", dataDate);
		int count = this.findPageCount(hql, params);
		return count;
	}
	

	/* (非 Javadoc)
	* <p>Title: queryList</p>
	* <p>Description: </p>
	* @param scope
	* @return
	* @see com.chinadrtv.erp.marketing.dao.IdlCrmUsrFlagDao#queryList(java.lang.String)
	*/ 
	public List<IdlCrmUsrFlag> queryList(String scope,String scopeEnd,Integer pageCount) {
		String hql = "select t from IdlCrmUsrFlag t where t.sync_flag=:syncFlag and t.data_date>=:dataDate and t.data_date<=:scopeEnd";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter syncflag = new ParameterString("syncFlag","0");
		Parameter dataDate = new ParameterDate("dataDate",DateUtil.stringToDate(scope));
		Parameter scopeEndDate = new ParameterDate("scopeEnd",DateUtil.stringToDate(scopeEnd));
		
		Parameter page = new ParameterInteger("page", 0);
		page.setForPageQuery(true);
		
		Parameter rows = new ParameterInteger("rows", pageCount);
		rows.setForPageQuery(true);
		
		params.put("page", page);
		params.put("rows", rows);
		params.put("syncflag", syncflag);
		params.put("dataDate", dataDate);
		params.put("scopeEnd", scopeEndDate);
		List<IdlCrmUsrFlag> list = this.findPageList(hql, params);
		return list;
	}
	
	public List<IdlCrmUsrFlag> queryListByJdbc(String scope,Integer startNm,Integer endNum) {
		String sql = "select * from                                                                                "+   
				"(                                                                                                 "+
				"select rownum numb,  CONTACTID,  NAMES,  GENDER ,  ID_NUM ,  BIRTHDAY ,  PROVINCE ,               "+
				"  CITY ,  COUNTY ,  AREA ,  CITY_LEV ,  FST_PAID_ORDR_DATE ,                                      "+
				"  FST_PAID_PROD,  FST_PAID_PROD_CAT,  FST_PAID_MEDIA_CO,  FST_PAID_MEDIA_PROD,                    "+
				"  FST_PAID_ORDR_AMT,  INTST_CAT,  MBR_TYPE ,  CONTACT_LEV,  IS_BIND_OLD_CUSTM,                    "+
				"  LST_PAID_ORDR_DATE ,  LST_ORDR_DATE,  LST_PAID_PROD,  LST_PAID_PROD_CAT,                        "+
				"   LST_PAID_MEDIA_CO,  LST_PAID_MEDIA_PROD,  LST_PAID_ORDR_AMT,  LST_CALLOUT_DATE ,               "+
				"   ACC_PAID_ORDR_QTY,  ACC_PAID_ORDR_AMT,  LST_INB_PAID_MOBILE_DATE ,                             "+
				"   LST_INB_PAID_CLCTN_DATE,  LST_INB_PAID_DGTL_DATE ,  LST_INB_PAID_SPORT_DATE,                   "+
				"   LST_INB_PAID_WATCH_DATE,  ACC_2011_PAID_MOBILE_QTY ,  ACC_2011_PAID_CLCTN_QTY,                 "+
				"   ACC_2011_PAID_DGTL_QTY ,  ACC_2011_PAID_SPORT_QTY,  ACC_2011_PAID_WATCH_QTY,                   "+
				"   ACC_PAID_MOBILE_QTY,  ACC_PAID_CLCTN_QTY ,  ACC_PAID_DGTL_QTY,  ACC_PAID_SPORT_QTY ,           "+
				"   ACC_PAID_WATHC_QTY ,  DATA_DATE,  SYNC_FLAG,  IMPORT_DATE from  acorn_dm.idl_crm_usr_flag_ft t "+
				"where  t.data_date=to_date(?,'yyyy-MM-dd') and t.sync_flag='0'                         		   "+
				"order by rowid                                                                                    "+
				")                                                                                                 "+
				"where  numb>? and numb<=?";
		
		 Object[] paramsObj = new Object[] {scope,startNm,endNum};  
		  int[] types = new int[] {Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		  
		  List<IdlCrmUsrFlag> list = jdbcTemplate.query(sql, paramsObj,types,new RowMapper(){

			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				IdlCrmUsrFlag dest = new IdlCrmUsrFlag();
				
				dest.setAcc_2011_paid_clctn_qty(rs.getInt("Acc_2011_paid_clctn_qty"));
				dest.setAcc_2011_paid_dgtl_qty(rs.getInt("Acc_2011_paid_dgtl_qty"));
				dest.setAcc_2011_paid_mobile_qty(rs.getInt("Acc_2011_paid_mobile_qty"));
				dest.setAcc_2011_paid_sport_qty(rs.getInt("Acc_2011_paid_sport_qty"));
				dest.setAcc_2011_paid_watch_qty(rs.getInt("Acc_2011_paid_watch_qty"));
				dest.setAcc_paid_clctn_qty(rs.getInt("Acc_paid_clctn_qty"));
				dest.setAcc_paid_dgtl_qty(rs.getInt("Acc_paid_dgtl_qty"));
				dest.setAcc_paid_mobile_qty(rs.getInt("Acc_paid_mobile_qty"));
				dest.setAcc_paid_ordr_amt(rs.getInt("Acc_paid_ordr_amt"));
				dest.setAcc_paid_ordr_qty(rs.getInt("Acc_paid_ordr_qty"));
				dest.setAcc_paid_sport_qty(rs.getInt("Acc_paid_sport_qty"));
				dest.setAcc_paid_wathc_qty(rs.getInt("Acc_paid_wathc_qty"));
				dest.setArea(rs.getString("Area"));
				dest.setBirthday(rs.getString("Birthday"));
				dest.setCity(rs.getString("City"));
				dest.setCity_lev(rs.getString("City_lev"));
				dest.setContact_lev(rs.getString("Contact_lev"));
				dest.setContactid(rs.getString("Contactid"));
				dest.setData_date(rs.getDate("Data_date"));
				dest.setCounty(rs.getString("County"));
				dest.setFst_paid_media_co(rs.getString("Fst_paid_media_co"));
				dest.setFst_paid_media_prod(rs.getString("Fst_paid_media_prod"));
				dest.setFst_paid_ordr_amt(rs.getInt("Fst_paid_ordr_amt"));
				dest.setFst_paid_ordr_date(rs.getDate("Fst_paid_ordr_date"));
				dest.setFst_paid_prod(rs.getString("Fst_paid_prod"));
				dest.setFst_paid_prod_cat(rs.getString("Fst_paid_prod_cat"));
				dest.setGender(rs.getString("Gender"));
				dest.setId_num(rs.getString("Id_num"));
				dest.setImport_date(new Date());
				dest.setIntst_cat(rs.getString("Intst_cat"));
				dest.setIs_bind_old_custm(rs.getString("Is_bind_old_custm"));
				dest.setLst_callout_date(rs.getDate("Lst_callout_date"));
				dest.setLst_inb_paid_clctn_date(rs.getDate("Lst_inb_paid_clctn_date"));
				dest.setLst_inb_paid_dgtl_date(rs.getDate("Lst_inb_paid_dgtl_date"));
				dest.setLst_inb_paid_mobile_date(rs.getDate("Lst_inb_paid_mobile_date"));
				dest.setLst_inb_paid_sport_date(rs.getDate("Lst_inb_paid_sport_date"));
				dest.setLst_inb_paid_watch_date(rs.getDate("Lst_inb_paid_watch_date"));
				dest.setLst_ordr_date(rs.getDate("Lst_ordr_date"));
				dest.setLst_paid_media_co(rs.getString("Lst_paid_media_co"));
				dest.setLst_paid_media_prod(rs.getString("Lst_paid_media_prod"));
				dest.setLst_paid_ordr_amt(rs.getInt("Lst_paid_ordr_amt"));
				dest.setLst_paid_ordr_date(rs.getDate("Lst_paid_ordr_date"));
				dest.setLst_paid_prod(rs.getString("Lst_paid_prod"));
				dest.setLst_paid_prod_cat(rs.getString("Lst_paid_prod_cat"));
				dest.setMbr_type(rs.getString("Mbr_type"));
				dest.setNames(rs.getString("Names"));
				dest.setProvince(rs.getString("Province"));
				dest.setSync_flag(rs.getString("Sync_flag"));
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
	 public int updateSyncFlag(String scopeStart,String newFlag,String origFlag) {
		
		int result = -1 ;
		String hql = "update IdlCrmUsrFlag set sync_flag=:syncFlag where data_date=:scopeStart and sync_flag=:oldSyncFlag";
		
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter syncflag = new ParameterString("syncFlag",newFlag);
		Parameter oldSyncFlag = new ParameterString("oldSyncFlag",origFlag);
		Parameter scopeStartDate = new ParameterDate("scopeStart",DateUtil.stringToDate(scopeStart));
		
		params.put("syncflag", syncflag);
		params.put("oldSyncFlag", oldSyncFlag);
		params.put("scopeStart", scopeStartDate);
		
		result = this.execUpdate(hql, params);
		
		return result;
	}
	
	

}
