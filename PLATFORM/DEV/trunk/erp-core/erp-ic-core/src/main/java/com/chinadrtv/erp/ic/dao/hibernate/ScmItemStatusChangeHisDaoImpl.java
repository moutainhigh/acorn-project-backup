package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.ScmItemStatusChangeHisDao;
import com.chinadrtv.erp.model.inventory.ScmItemStatusChangeHis;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 库存状态变更
 * User: gaodejian
 * Date: 13-2-1
 * Time: 下午1:01
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ScmItemStatusChangeHisDaoImpl extends GenericDaoHibernateBase<ScmItemStatusChangeHis, Long> implements ScmItemStatusChangeHisDao {

    public ScmItemStatusChangeHisDaoImpl() {
        super(ScmItemStatusChangeHis.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<ScmItemStatusChangeHis> getStatusChangedItems() {
        Session session = getSession();
        Query query = session.createQuery("from ScmItemStatusChangeHis a where a.batchId is null");
        query.setMaxResults(1000); //最多一千笔,否则处理时间很长
        return query.list();
    }

    /**
     * 记录批号(检查是否被修改过)
     * @param itemStatus
     */
    public void batchLog(ScmItemStatusChangeHis itemStatus){
        //saveOrUpdate(itemStatus);

        Session session = getSession();
        session.evict(itemStatus);

        Query query = session.createQuery("update ScmItemStatusChangeHis a set a.batchId = :batchId, a.batchDate = :batchDate where a.ruid = :ruid and a.batchId is null");
        query.setParameter("ruid", itemStatus.getRuid());
        query.setParameter("batchId", itemStatus.getBatchId());
        query.setParameter("batchDate", itemStatus.getBatchDate());
        if(query.executeUpdate() == 0){
            throw new RuntimeException("可能多IC程序运行，库存状态变更被多次执行："+itemStatus.getChangeId());
        }
    }
}
