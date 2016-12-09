package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.ScmOnhandAdjustmentHisDao;
import com.chinadrtv.erp.model.inventory.ScmOnhandAdjustmentHis;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 库存调整单
 * User: gaodejian
 * Date: 13-2-1
 * Time: 下午1:01
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ScmOnhandAdjustmentHisDaoDaoImpl extends GenericDaoHibernateBase<ScmOnhandAdjustmentHis, Long> implements ScmOnhandAdjustmentHisDao {

    public ScmOnhandAdjustmentHisDaoDaoImpl() {
        super(ScmOnhandAdjustmentHis.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<ScmOnhandAdjustmentHis> getAdjustedItems() {
        Session session = getSession();
        Query query = session.createQuery("from ScmOnhandAdjustmentHis a where a.batchId is null");
        query.setMaxResults(1000); //最多一千笔,否则处理时间很长
        return query.list();
    }

    /**
     * 记录批号(检查是否被修改过)
     * @param adjustment
     */
    public void batchLog(ScmOnhandAdjustmentHis adjustment){
        //saveOrUpdate(adjustment);

        Session session = getSession();
        session.evict(adjustment);

        Query query = session.createQuery("update ScmOnhandAdjustmentHis a set a.batchId = :batchId, a.batchDate = :batchDate where a.ruid = :ruid and a.batchId is null");
        query.setParameter("ruid", adjustment.getRuid());
        query.setParameter("batchId", adjustment.getBatchId());
        query.setParameter("batchDate", adjustment.getBatchDate());
        if(query.executeUpdate() == 0){
            throw new RuntimeException("可能多IC程序运行，库存调整变更被多次执行："+adjustment.getAdjustmentId());
        }
    }
}
