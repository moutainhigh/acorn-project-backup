package com.chinadrtv.erp.oms.dao.hibernate;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterDate;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dao.OrderhistDao;
import com.chinadrtv.erp.oms.dto.OrderhistDto;
import com.chinadrtv.erp.oms.util.StringUtil;
import com.chinadrtv.erp.util.DateUtil;

/**
 * OrderhistDaoImpl
 * @author haoleitao
 *
 */
@Repository
public class OrderhistDaoImpl extends GenericDaoHibernate<Orderhist, java.lang.String> implements OrderhistDao{

	public OrderhistDaoImpl() {
	    super(Orderhist.class);
	}
	
	
	public Orderhist getOrderhistById(String orderhistId) {
        Session session = getSession();
        try
        {
            return (Orderhist)session.get(Orderhist.class, orderhistId);
        } catch (Exception ex){
            return null;
        }
    }

    public List<Orderhist> getAllOrderhist(int state,Date beginDate,Date endDate,String orderid,String mailid,String status,String paytype,String mailtype,String ordertype,String result,String companyid,String cardtype,String cityid,int index, int size)
    {

        StringBuilder sb = new StringBuilder();
		sb.append("from Orderhist as a  where 1=1  ");
		Query q = initQuery(state,beginDate,endDate, orderid,mailid,status,paytype, mailtype, ordertype, result, companyid, cardtype, cityid,sb);
        q.setFirstResult(index*size);
        q.setMaxResults(size);
        List<Orderhist> list = q.list();
        return list;
    }
	
	
    public void saveOrUpdate(Orderhist orderhist){
        getSession().saveOrUpdate(orderhist);
    }



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.dao.OrderhistDao#getOrderhistCount(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int getOrderhistCount(int state, Date beginDate,Date endDate,String orderid,String mailid, 
			String status,String paytype, String mailtype, String ordertype, String result,
			String companyid, String cardtype, String cityid) {
        StringBuilder sb = new StringBuilder();
		sb.append("select count(a.orderid) from Orderhist a where 1=1  ");
		Query q = initQuery(state,beginDate,endDate, orderid,mailid, paytype, status,mailtype, ordertype, result, companyid, cardtype, cityid,sb);
		
        int count = Integer.valueOf(q.list().get(0).toString());
        return count;

	}



	 /**
     * 提供下载excel查询
     * @param orderhist
     * @return
     */
    public List<OrderhistDto> queryOrderhistList(OrderhistDto orderhist) {
    	StringBuffer sql = new StringBuffer("select tmp.id,tmp.shipment_id,p.chinese,c.cityname,u.countyname,e.areaname" +
    			",a.addrdesc,tmp.account_status_id, o.totalprice, str_concat(od.prodname||'') products   from " +
											"(" +
													"select rownum num,sh.id,sh.order_id,sh.shipment_id,sh.account_status_id " +
													" from shipment_header sh " +
													"where sh.logistics_status_id='0' ");
			
			Map<String,Parameter> params = genSql(sql, orderhist);
			sql.append(")  tmp                         "+
			"left join iagent.orderhist o   "+
			"on tmp.order_id=o.orderid      "+
			"left join iagent.address_ext a "+
			"on o.addressid=a.addressid     "+
			"left join iagent.province p    "+
			"on a.provinceid=p.provinceid   "+
			"left join iagent.city_all c    "+
			"on a.cityid=c.cityid           "+
			"left join iagent.county_all u  "+
			"on a.countyid=u.countyid       "+
			"left join iagent.area_all e    "+
			"on a.areaid=e.areaid           "+
			" left join iagent.orderdet od on o.orderid = od.orderid " +
			" group by tmp.id, tmp.shipment_id, p.chinese, c.cityname, u.countyname, e.areaname," +
			" a.addrdesc, tmp.account_status_id, o.totalprice");
			
			
			SQLQuery sqlQuery = this.getSession().createSQLQuery(sql.toString());
			
			if(!StringUtils.isEmpty(orderhist.getOrderId())){
			sqlQuery.setString("orderId", orderhist.getOrderId());
			}
			
			if(!StringUtils.isEmpty(orderhist.getStartDate())){
			sqlQuery.setDate("startDate",DateUtil.stringToDate(orderhist.getStartDate()));
			}
			
			if(!StringUtils.isEmpty(orderhist.getEndDate())){
			sqlQuery.setDate("endDate",DateUtil.stringToDate(orderhist.getEndDate()));
			}
			
			if(!StringUtils.isEmpty(orderhist.getEntityId())){
			sqlQuery.setString("entityId", orderhist.getEntityId());
			}
			
			if(!StringUtils.isEmpty(orderhist.getStatus())){
			sqlQuery.setString("status", orderhist.getStatus());
			}
			
			if(orderhist.getWareHouse()!=null){
			sqlQuery.setString("wareHouse", orderhist.getWareHouse());
			}
			
			
			List objList = sqlQuery.list();
			return objList;
	}
    
	public List<OrderhistDto> query(OrderhistDto orderhist, DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer("select tmp.id,tmp.shipment_id,p.chinese,c.cityname,u.countyname,e.areaname" +
				",a.addrdesc,tmp.account_status_id, o.totalprice, str_concat(od.prodname||'') products   from " +
											"(" +
												"select * from " +
												"(" +
													"select rownum num,sh.id,sh.order_id,sh.shipment_id,sh.account_status_id " +
													" from shipment_header sh " +
													"where sh.logistics_status_id='0' ");
		
		Map<String,Parameter> params = genSql(sql, orderhist);
		sql.append(") where num >:page and num<=:rows     "+
				")  tmp                         "+
				"left join iagent.orderhist o   "+
				"on tmp.order_id=o.orderid      "+
				"left join iagent.address_ext a "+
				"on o.addressid=a.addressid     "+
				"left join iagent.province p    "+
				"on a.provinceid=p.provinceid   "+
				"left join iagent.city_all c    "+
				"on a.cityid=c.cityid           "+
				"left join iagent.county_all u  "+
				"on a.countyid=u.countyid       "+
				"left join iagent.area_all e on a.areaid=e.areaid" +
				" left join iagent.orderdet od on o.orderid = od.orderid " + 
				" where (o.isassign != '5' or o.isassign is null)" +
				" group by tmp.id, tmp.shipment_id, p.chinese, c.cityname, u.countyname, e.areaname," +
				" a.addrdesc, tmp.account_status_id, o.totalprice");
		
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql.toString());
		
		if(!StringUtils.isEmpty(orderhist.getOrderId())){
			sqlQuery.setString("orderId", orderhist.getOrderId());
		}
		
		if(!StringUtils.isEmpty(orderhist.getStartDate())){
			sqlQuery.setDate("startDate",DateUtil.stringToDate(orderhist.getStartDate()));
		}
		
		if(!StringUtils.isEmpty(orderhist.getEndDate())){
			sqlQuery.setDate("endDate",DateUtil.stringToDate(orderhist.getEndDate()));
		}
		
		if(!StringUtils.isEmpty(orderhist.getEntityId())){
			sqlQuery.setString("entityId", orderhist.getEntityId());
		}
		
		if(!StringUtils.isEmpty(orderhist.getStatus())){
			sqlQuery.setString("status", orderhist.getStatus());
		}
		
		if(orderhist.getWareHouse()!=null){
			sqlQuery.setString("wareHouse", orderhist.getWareHouse());
		}
		
		sqlQuery.setInteger("page", dataModel.getStartRow());
		sqlQuery.setInteger("rows", dataModel.getStartRow()+dataModel.getRows());
		
		List objList = sqlQuery.list();
		List<OrderhistDto> result = new ArrayList<OrderhistDto>();
		Object[] obj = null;
		OrderhistDto orderhistDto = null;
		for(int i=0;i<objList.size();i++){
			obj = (Object[])objList.get(i);
			orderhistDto = new OrderhistDto();
//			orderhistDto.setOrderId(order.getOrderid());
//			orderhistDto.setProvince(order.getAddress().getProvince().getChinese());
			orderhistDto.setId(Long.valueOf(((BigDecimal)obj[0]).toString()));
			orderhistDto.setOrderId((String)obj[1]);
			orderhistDto.setProvince((String)obj[2]);
			orderhistDto.setCity((String)obj[3]);
			orderhistDto.setCounty((String)obj[4]);
			orderhistDto.setArea((String)obj[5]);
			orderhistDto.setAddress((String)obj[6]);
			orderhistDto.setStatus((String)obj[7]);
			orderhistDto.setTotalPrice((BigDecimal)obj[8]);
			orderhistDto.setProducts(null == obj[9] ? "" : String.valueOf(obj[9]));
			result.add(orderhistDto);
		}
        return result;
	}


	public Integer queryCount(OrderhistDto orderhist) {
		StringBuffer sql = new StringBuffer("select count(sh.id) " +
													" from shipment_header sh,iagent.orderhist oh " +
													"where sh.logistics_status_id='0'" +
                " AND sh.order_id = oh.orderid AND (oh.isassign != '5' or oh.isassign is null) ");
		
		Map<String,Parameter> params = genSql(sql, orderhist);
		
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql.toString());
		
		if(!StringUtils.isEmpty(orderhist.getOrderId())){
			sqlQuery.setString("orderId", orderhist.getOrderId());
		}
		
		if(!StringUtils.isEmpty(orderhist.getStartDate())){
			sqlQuery.setDate("startDate",DateUtil.stringToDate(orderhist.getStartDate()));
		}
		
		if(!StringUtils.isEmpty(orderhist.getEndDate())){
			sqlQuery.setDate("endDate",DateUtil.stringToDate(orderhist.getEndDate()));
		}
		
		if(!StringUtils.isEmpty(orderhist.getEntityId())){
			sqlQuery.setString("entityId", orderhist.getEntityId());
		}
		
		if(!StringUtils.isEmpty(orderhist.getStatus())){
			sqlQuery.setString("status", orderhist.getStatus());
		}
		
		if(orderhist.getWareHouse()!=null){
			sqlQuery.setString("wareHouse", orderhist.getWareHouse());
		}
		
		List list = sqlQuery.list();
		Integer result = ((BigDecimal)list.get(0)).intValue();
		
        return result;
	}
	
	
	public Map<String,Parameter> genSql(StringBuffer sql,OrderhistDto orderhist){
		
		Map<String,Parameter> paras = new HashMap<String, Parameter>();
		if(!StringUtils.isEmpty(orderhist.getOrderId())){
			sql.append(" AND sh.order_id=:orderId");
			Parameter orderId = new ParameterString("orderId", orderhist.getOrderId());
			paras.put("orderId", orderId);
		}
		
		if(!StringUtils.isEmpty(orderhist.getStartDate())){
			sql.append(" AND sh.crdt >= :startDate");
			Parameter startDate;
			try {
				startDate = new ParameterDate("startDate",  DateUtil.string2Date(orderhist.getStartDate()+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
				paras.put("startDate", startDate);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		if(!StringUtils.isEmpty(orderhist.getEndDate())){
			sql.append(" AND sh.crdt <= :endDate");
			Parameter endDate;
			try {
				endDate = new ParameterDate("endDate", DateUtil.string2Date(orderhist.getEndDate()+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
				paras.put("endDate", endDate);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		if(!StringUtils.isEmpty(orderhist.getEntityId())){
			sql.append(" AND sh.entity_id = :entityId");
			Parameter entityId = new ParameterString("entityId", orderhist.getEntityId());
			paras.put("entityId", entityId);
		}
		
		if(!StringUtils.isEmpty(orderhist.getStatus())){
			sql.append(" AND sh.account_status_id = :status");
			Parameter status = new ParameterString("status", orderhist.getStatus());
			paras.put("status", status);
		}
		
		if(orderhist.getWareHouse()!=null){
			sql.append(" AND sh.warehouse_id = :wareHouse");
			Parameter wareHouse = new ParameterString("wareHouse", orderhist.getWareHouse());
			paras.put("wareHouse", wareHouse);
		}
		
		return paras;
	}

	 private Query initQuery(int state,Date beginDate,Date endDate,String orderid,String mailid,String status,String paytype,String mailtype,String ordertype,String result,String companyid,String cardtype,String cityid, StringBuilder sb) {

		 
		if (state == 0) {
			if (beginDate != null)
			//	sb.append(" and  a.crdt  >= :beginDate ");
			sb.append(" and  a.crdt  >= to_date(:beginDate,'yyyy-MM-dd HH24:mi:ss') ");
			
			if (endDate != null)
				//sb.append(" and  a.crdt  <= :endDate ");
				sb.append(" and  a.crdt  <= to_date(:endDate,'yyyy-MM-dd HH24:mi:ss') ");
		}
		if (state == 1) {
			if (beginDate != null)
				sb.append(" and  a.parcdt  >= to_date(:beginDate,'yyyy-MM-dd HH24:mi:ss') ");
				//sb.append(" and  a.parcdt  >= :beginDate ");
			if (endDate != null)
				//sb.append(" and  a.parcdt  <= :endDate ");
			sb.append(" and  a.parcdt  <= to_date(:endDate,'yyyy-MM-dd HH24:mi:ss') ");
		}
		if (state == 2) {
			if (beginDate != null)
				//sb.append(" and  a.fbdt  >= :beginDate ");
			sb.append(" and  a.fbdt  >= to_date(:beginDate,'yyyy-MM-dd HH24:mi:ss') ");
			if (endDate != null)
				sb.append(" and  a.fbdt  <= to_date(:endDate,'yyyy-MM-dd HH24:mi:ss') ");
				//sb.append(" and  a.fbdt  <= :endDate ");
		}

		if (state == 3) {
			if (beginDate != null)
				//sb.append(" and  a.rfoutdat  >= :beginDate ");
			sb.append(" and  a.rfoutdat  >= to_date(:beginDate,'yyyy-MM-dd HH24:mi:ss') ");
			if (endDate != null)
				sb.append(" and  a.rfoutdat  <= to_date(:endDate,'yyyy-MM-dd HH24:mi:ss') ");
				//sb.append(" and  a.rfoutdat  <= :endDate ");
		}
		 
         
         if (orderid != null && !orderid.equals("")) {
             sb.append(" and  a.orderid  = :orderid ");
         }
         
         if (status != null && !status.equals("")) {
             sb.append(" and  a.status  = :status ");
         }
         
         if (mailid != null && !mailid.equals("")) {
             sb.append(" and  a.mailid  = :mailid ");
         }

	        if (paytype != null && !paytype.equals("")) {
	            sb.append(" and  a.paytype  = :paytype ");
	        }
	        if (mailtype != null && !mailtype.equals("")) {
	            sb.append(" and  a.mailtype  = :mailtype ");
	        }
	        
	        if (ordertype != null && !ordertype.equals("")) {
	            sb.append(" and  a.ordertype  = :ordertype ");
	        }
	        if (result != null && !result.equals("")) {
	            sb.append(" and  a.result  = :result ");
	        }else{
	        	sb.append(" and  (a.result  = '2' or a.result = '3') ");
	        }
	        if (cardtype != null && !cardtype.equals("")) {
	            sb.append(" and  a.cardtype  = :cardtype ");
	        }
	        if (cityid != null && !cityid.equals("")) {
	            sb.append(" and  a.cityid  = :cityid ");
	        }
	               
	        if(! StringUtil.isNullOrBank(companyid)) sb.append(" and a.companyid = :companyid ") ;
	        
	      //  if(autoSort != null ) sb.append(" and a.autoSort is not null ");
	        
	        //if(isChangeFindbackDate != null ) sb.append("and a.isChangeFindbackDate is not null ");
	      
	        
	        //sb.append(" order by a.outCrdt desc");
	        
	        Query q = getSession().createQuery(sb.toString());

      if (!StringUtil.isNullOrBank(orderid)){
          q.setString("orderid", orderid);
      }  
      
      if (!StringUtil.isNullOrBank(mailid)){
          q.setString("mailid", mailid);
      }  
      
      if (!StringUtil.isNullOrBank(status)){
          q.setString("status", status);
      }  
      if (!StringUtil.isNullOrBank(paytype)) {
          q.setString("paytype", paytype);
      }
	        
	        if (!StringUtil.isNullOrBank(mailtype)) {
	            q.setString("mailtype", mailtype);
	        }
	        if (!StringUtil.isNullOrBank(ordertype)) {
	            q.setString("ordertype", ordertype);
	        }
	        if (!StringUtil.isNullOrBank(result)) {
	            q.setString("result",result);
	        }
	        if (!StringUtil.isNullOrBank(companyid)) {
	            q.setString("companyid", companyid);
	        }
	        if (!StringUtil.isNullOrBank(cardtype)) {
	            q.setString("cardtype",cardtype);
	        }
	        if (!StringUtil.isNullOrBank(cityid)) {
	            q.setString("cityid", cityid);
	        }
	        if (beginDate != null) {
	        	try {
					q.setString("beginDate", DateUtil.date2FormattedString(beginDate, "yyyy-MM-dd HH:mm:ss"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        if (endDate != null) {
	        	try {
					q.setString("endDate", DateUtil.date2FormattedString(endDate, "yyyy-MM-dd HH:mm:ss"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    
	            
	        }
	        
	        return q;
	    }


	/**
	* <p>Title: queryForOldData</p>
	* <p>Description: 查询老订单数据,分页查询</p>
	* @param orderhistDto
	* @param dataModel
	* @return
	* @see com.chinadrtv.erp.oms.dao.OrderhistDao#queryForOldData(com.chinadrtv.erp.oms.dto.OrderhistDto, com.chinadrtv.erp.oms.common.DataGridModel)
	*/ 
	public List<OrderhistDto> queryForOldData(OrderhistDto orderhist,
			DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer("select o.orderid,o.orderid,province.chinese," +
				"city.cityname,county.countyname,area.areaname,a.addressDesc,o.status " +
			"from Orderhist o  ,OrderExt e " +
				"left join o.address a " +
				"left join a.province province " +
				"left join a.city city " +
				"left join a.county county " +
				"left join a.area area " +
			"where 1=1 and (o.isassign != '5' or o.isassign is null) and o.orderid=e.orderId and e.warehouseIdExt=:wareHouse ");
		// StringBuffer sql = new
		// StringBuffer("select o.orderid,o.address.province.chinese,o.address.city.cityname,o.address.county.countyname,o.address.area.areaname,o.address.addressDesc from Orderhist o where 1=1 ");

		Map<String, Parameter> params = genSqlForOldData(sql, orderhist);

		// sql.append(" AND o.addressid = a.addressId ");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);

		List objList = findPageList(sql.toString(), params);
		List<OrderhistDto> result = new ArrayList<OrderhistDto>();
		Object[] obj = null;
		OrderhistDto orderhistDto = null;
		for (int i = 0; i < objList.size(); i++) {
			obj = (Object[]) objList.get(i);
			orderhistDto = new OrderhistDto();
			// orderhistDto.setOrderId(order.getOrderid());
			// orderhistDto.setProvince(order.getAddress().getProvince().getChinese());
			orderhistDto.setId(-1l);
			orderhistDto.setOrderId((String)obj[1]);
			orderhistDto.setProvince((String)obj[2]);
			orderhistDto.setCity((String)obj[3]);
			orderhistDto.setCounty((String)obj[4]);
			orderhistDto.setArea((String)obj[5]);
			orderhistDto.setAddress((String)obj[6]);
			orderhistDto.setStatus((String)obj[7]);
			result.add(orderhistDto);
		}
		return result;
	}


	/**
	* <p>Title: queryCountForOldData</p>
	* <p>Description: 查询老订单数据总记录数</p>
	* @param orderhistDto
	* @return
	* @see com.chinadrtv.erp.oms.dao.OrderhistDao#queryCountForOldData(com.chinadrtv.erp.oms.dto.OrderhistDto)
	*/ 
	public Integer queryCountForOldData(OrderhistDto orderhist) {
		StringBuffer sql = new StringBuffer("select count(o.orderid) from Orderhist o ,OrderExt e where 1=1 and (o.isassign != '5' or o.isassign is null) and o.orderid=e.orderId and e.warehouseIdExt=:wareHouse ");
		
		Map<String,Parameter> params = genSqlForOldData(sql, orderhist);
		
		Integer result = findPageCount(sql.toString(), params);
		
        return result;
	}


	/**
	* <p>Title: queryOrderhistListForOldData</p>
	* <p>Description: 查询老订单数据,提供excel下载</p>
	* @param orderhist
	* @return
	* @see com.chinadrtv.erp.oms.dao.OrderhistDao#queryOrderhistListForOldData(com.chinadrtv.erp.oms.dto.OrderhistDto)
	*/ 
	public List<OrderhistDto> queryOrderhistListForOldData(
			OrderhistDto orderhist) {
			StringBuffer sql = new StringBuffer("select o.orderid,o.orderid,province.chinese," +
					"city.cityname,county.countyname,area.areaname,a.addressDesc,o.status " +
				"from Orderhist o ,OrderExt e " +
					"left join o.address a " +
					"left join a.province province " +
					"left join a.city city " +
					"left join a.county county " +
					"left join a.area area " +
				"where 1=1 and o.orderid=e.orderId and e.warehouseIdExt=:wareHouse ");
			//StringBuffer sql = new StringBuffer("select o.orderid,o.address.province.chinese,o.address.city.cityname,o.address.county.countyname,o.address.area.areaname,o.address.addressDesc from Orderhist o where 1=1 ");
			
			Map<String,Parameter> params = genSqlForOldData(sql, orderhist);
			
			
			List objList = findList(sql.toString(), params);
			
			return objList;
	}
	
	/**
	 * 
	* @Description: 生成老订单数据sql
	* @param sql
	* @param orderhist
	* @return
	* @return Map<String,Parameter>
	* @throws
	 */
	public Map<String,Parameter> genSqlForOldData(StringBuffer sql,OrderhistDto orderhist){
		
		Map<String,Parameter> paras = new HashMap<String, Parameter>();
		if(!StringUtils.isEmpty(orderhist.getOrderId())){
			sql.append(" AND o.orderid=:orderId");
			Parameter orderId = new ParameterString("orderId", orderhist.getOrderId());
			paras.put("orderId", orderId);
		}
		
		if(!StringUtils.isEmpty(orderhist.getStartDate())){
			sql.append(" AND o.crdt >= :startDate");
			Parameter startDate;
			try {
				startDate = new ParameterDate("startDate", DateUtil.string2Date(orderhist.getStartDate()+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
				paras.put("startDate", startDate);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		if(!StringUtils.isEmpty(orderhist.getEndDate())){
			sql.append(" AND o.crdt <= :endDate");
			Parameter endDate;
			try {
				endDate = new ParameterDate("endDate", DateUtil.string2Date(orderhist.getEndDate()+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
				paras.put("endDate", endDate);
			} catch (SQLException e) {
				e.printStackTrace();
			}
//			Parameter endDate = new ParameterString("endDate", orderhist.getEndDate());
			
		}
		
		if(!StringUtils.isEmpty(orderhist.getEntityId())){
			sql.append(" AND o.entityid = :entityId");
			Parameter entityId = new ParameterString("entityId", orderhist.getEntityId());
			paras.put("entityId", entityId);
		}
		
		if(!StringUtils.isEmpty(orderhist.getStatus())){
			sql.append(" AND o.status = :status");
			Parameter status = new ParameterString("status", orderhist.getStatus());
			paras.put("status", status);
		}
		
		if(orderhist.getWareHouse()!=null){
			Parameter wareHouse = new ParameterInteger("wareHouse", Integer.valueOf(orderhist.getWareHouse()));
			paras.put("wareHouse", wareHouse);
		}
		
		return paras;
	}

}
