package com.chinadrtv.erp.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.AuditLogDao;
import com.chinadrtv.erp.core.model.AuditLog;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-8-10
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AuditLogDaoImpl extends GenericDaoHibernateBase<AuditLog, Long> implements AuditLogDao {

    public AuditLogDaoImpl() {
        super(AuditLog.class);
    }

    private Query initQuery(String appName, String funcName, String treadid, Date beginDate, Date endDate, StringBuilder sb) {
        if (appName != null && !appName.equals("")) {
            sb.append(" and  a.appName  like :appName ");
        }
        if (funcName != null && !funcName.equals("")) {
            sb.append(" and  a.funcName  like :funcName ");
        }
        
        if (treadid != null && !treadid.equals("")) {
            sb.append(" and  a.treadid  like :treadid ");
        }
        if (beginDate != null) {
            sb.append(" and  a.logDate  >= :beginDate ");
        }

        if (endDate != null) {
            sb.append(" and  a.logDate  <= :endDate ");
        }
   
        sb.append( " order by a.logDate desc ");
        
        Query q = getSession().createQuery(sb.toString());

        if (appName != null && !appName.equals("")) {
            q.setString("appName", "%" + appName + "%");
        }
        if (funcName != null && !funcName.equals("")) {
            q.setString("funcName", "%" + funcName + "%");
        }
        
        if (treadid != null && !treadid.equals("")) {
            q.setString("treadid", treadid);
        }
        if (beginDate != null) {
            q.setTimestamp("beginDate",beginDate);
        }
        if (endDate != null) {
            q.setTimestamp("endDate", endDate);
        }
        return q;
    }

    public int getAuditLogCountByAppDate(String appName, String funcName, String treadid,Date beginDate, Date endDate) {
        //String hql = "from AuditLog a where ";
       // Search qTest = getSession().createQuery(hql);
        StringBuilder sb = new StringBuilder();

        sb.append(" select count(a.id) from AuditLog a  where 1=1  ");
        Query q = initQuery(appName, funcName,treadid ,beginDate, endDate, sb);
        int count = Integer.valueOf(q.list().get(0).toString());
        return count;
    }



    public List<AuditLog> searchPaginatedAuditLogByAppDate(String appName, String funcName,String treadid, Date beginDate, Date endDate,
                                                      int startIndex, Integer numPerPage) {
        StringBuilder sb = new StringBuilder();
        sb.append("from AuditLog a  where 1=1  ");
        Query q = initQuery(appName, funcName, treadid,beginDate, endDate, sb);
        q.setFirstResult(startIndex*numPerPage);
        q.setMaxResults(numPerPage);
        return q.list();
    }

    public void saveOrUpdate(AuditLog auditLog) {
        getSession().saveOrUpdate(auditLog);
    }

	@Override
	@Autowired
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}
}
