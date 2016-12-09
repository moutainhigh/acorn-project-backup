package com.chinadrtv.erp.oms.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.EsbAuditLog;
import com.chinadrtv.erp.oms.dao.EsbAuditLogDao;
import com.chinadrtv.erp.oms.util.StringUtil;

/**
 * 
 * @author haoleitao
 * @date 2013-2-20 上午9:52:02
 *
 */
@Repository
public class EsbAuditLogDaoImpl extends
		GenericDaoHibernateBase<EsbAuditLog, Long> implements EsbAuditLogDao {
	public EsbAuditLogDaoImpl() {
		super(EsbAuditLog.class);
	}

	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}
	
	public int listCount(String esbName,String esbDesc,String errorCode,String errorDesc,String companyId,String remark,Date sdt,Date edt){
		   StringBuilder sb = new StringBuilder();
	        sb.append(" select count(a.id) from EsbAuditLog a  where 1=1  ");
	        Query q = initQuery(esbName,esbDesc,errorCode,errorDesc, companyId, remark, sdt,edt,sb);
	        int count = Integer.valueOf(q.list().get(0).toString());
	        return count;
	}
	public List<EsbAuditLog> list(String esbName,String esbDesc,String errorCode,String errorDesc,String companyId,String remark,Date sdt,Date edt,int index, int size){
		StringBuilder sb = new StringBuilder();
		sb.append("from EsbAuditLog a  where 1=1  ");
		Query q = initQuery(esbName,esbDesc,errorCode,errorDesc, companyId, remark, sdt,edt,sb);
		q.setFirstResult(index * size);
		q.setMaxResults(size);
		return q.list();
	}
	
	 private Query initQuery(String esbName,String esbDesc,String errorCode,String errorDesc,String companyId,String remark,Date sdt,Date edt, StringBuilder sb) {

         if(! StringUtil.isNullOrBank(esbName)){
         	sb.append( " and a.esbName like :esbName ");
         }
         if(! StringUtil.isNullOrBank(esbDesc)){
          	sb.append( " and a.esbDesc like :esbDesc ");
          }
         if(! StringUtil.isNullOrBank(errorCode)){
           	sb.append( " and a.errorCode = :errorCode ");
           }
         if(! StringUtil.isNullOrBank(companyId)){
            	sb.append( " and a.errorDesc = :companyId ");
            }
         if(sdt != null){
         	sb.append( " and a.crtDate >= :sdt  ");
         }
         if(edt != null){
         	sb.append( " and a.crtDate <= :edt ");
         }
         
         
	        sb.append(" order by a.crtDate desc");
	        
	        Query q = getSession().createQuery(sb.toString());
	        if(! StringUtil.isNullOrBank(esbName)){
	         	  q.setString("esbName", "%"+esbName+"%");
	         }
	         if(! StringUtil.isNullOrBank(esbDesc)){
	            q.setString("esbDesc", "%"+esbDesc+"%");
	          }
	         if(! StringUtil.isNullOrBank(errorCode)){
	            q.setString("errorCode", errorCode);
	           }
	         if(! StringUtil.isNullOrBank(companyId)){
	        	 q.setString("companyId", companyId);
	            }
	         if(sdt != null){
	         	q.setTimestamp("sdt",sdt);
	         }
	         if(edt != null){
	        	 q.setTimestamp("edt",edt);
	         }
	        return q;
	    }

}
