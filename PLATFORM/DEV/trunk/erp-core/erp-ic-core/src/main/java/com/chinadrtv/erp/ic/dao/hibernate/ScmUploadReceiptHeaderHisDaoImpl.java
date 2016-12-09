package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.ScmUploadReceiptHeaderHisDao;
import com.chinadrtv.erp.model.inventory.ScmUploadReceiptHeaderHis;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收货入库单
 * User: gaodejian
 * Date: 13-2-1
 * Time: 下午1:01
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ScmUploadReceiptHeaderHisDaoImpl extends GenericDaoHibernateBase<ScmUploadReceiptHeaderHis, Long> implements ScmUploadReceiptHeaderHisDao {

    public ScmUploadReceiptHeaderHisDaoImpl() {
        super(ScmUploadReceiptHeaderHis.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<ScmUploadReceiptHeaderHis> getUnhandledReceipts() {
        Session session = getSession();
        Query query = session.createQuery("from ScmUploadReceiptHeaderHis a where a.batchId is null");
        query.setMaxResults(200); //最多一千笔,否则处理时间很长
        return query.list();
    }

    /**
     * 记录批号(检查是否被修改过)
     * @param receipt
     */
    public void batchLog(ScmUploadReceiptHeaderHis receipt){
        //saveOrUpdate(receipt);
        //处理多服务器同时运行IC程序

        Session session = getSession();
        session.evict(receipt);

        Query query = session.createQuery("update ScmUploadReceiptHeaderHis a set a.batchId = :batchId, a.batchDate = :batchDate where a.ruid = :ruid and a.batchId is null");
        query.setParameter("ruid", receipt.getRuid());
        query.setParameter("batchId", receipt.getBatchId());
        query.setParameter("batchDate", receipt.getBatchDate());
        if(query.executeUpdate() == 0){
            throw new RuntimeException("可能多IC程序运行，收货通知单被多次执行："+receipt.getReceiptId());
        }
    }
}
