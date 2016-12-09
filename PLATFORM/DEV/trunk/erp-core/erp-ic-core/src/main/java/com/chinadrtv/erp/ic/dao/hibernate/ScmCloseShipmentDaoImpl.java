package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.ScmCloseShipmentDao;
import com.chinadrtv.erp.model.inventory.ScmCloseShipment;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-19
 * Time: 下午5:41
 * To change this template use File | Settings | File Templates.
 */
@Repository("scmCloseShipmentDao")
public class ScmCloseShipmentDaoImpl extends GenericDaoHibernateBase<ScmCloseShipment,Long> implements ScmCloseShipmentDao {

    private static final Logger LOG = LoggerFactory.getLogger(ScmCloseShipmentDaoImpl.class);

    public ScmCloseShipmentDaoImpl()
    {
        super(ScmCloseShipment.class);
    }
    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    /**
     * 获取数据
     * @return
     */
    public List<ScmCloseShipment> getScmCloseShipment() {
        String sql = " from ScmCloseShipment a where a.batchId is null";
        Query q = getSession().createQuery(sql);
        q.setMaxResults(1000);
        return q.list();
    }

    /**
     * 数据回写
     * @param ruid
     * @param date
     * @return
     */
    public boolean updateScmCloseShipment(Long ruid, Date date) {
        boolean b = false;
        /*
        try
        {
            ScmCloseShipment scmCloseShipment = getHibernateTemplate().load(ScmCloseShipment.class,ruid);
            scmCloseShipment.setBatchId(""+date.getTime());
            scmCloseShipment.setBatchDate(date);
            this.getHibernateTemplate().update(scmCloseShipment);

            b = true;
        }catch (Exception ex)
        {
            LOG.error(ex.getMessage());
        }
        */

        Session session = getSession();
        Query query = session.createQuery("update ScmCloseShipment a set a.batchId = :batchId, a.batchDate = :batchDate where a.ruId = :ruid and a.batchId is null");
        //Query query = session.createSQLQuery("update SCM_CLOSE_SHIPMENT a set a.BATCH_ID = :batchId, a.BATCH_DATE = sysdate where a.RUID = :ruid and a.BATCH_ID is null");
        query.setParameter("ruid", ruid);
        query.setParameter("batchId", String.valueOf(date.getTime()));
        query.setParameter("batchDate", date);
        b = query.executeUpdate() > 0;
        if(!b){
            throw new RuntimeException("可能多IC程序运行，ScmCloseShipmentDaoImpl被多次执行：RUID("+ruid+")");
        }

        return b;
    }
}
