package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.ScmUploadShipmentHeaderHisDao;
import com.chinadrtv.erp.model.inventory.ScmUploadShipmentHeaderHis;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liukuan
 * @version 1.0
 * @since 2013-2-6 上午9:46:24
 * 
 */
@Repository("scmUploadShipmentHeaderHisDao")
public class ScmUploadShipmentHeaderHisDaoImpl extends
        GenericDaoHibernateBase<ScmUploadShipmentHeaderHis, Long> implements
		ScmUploadShipmentHeaderHisDao {

	public ScmUploadShipmentHeaderHisDaoImpl() {
		super(ScmUploadShipmentHeaderHis.class);
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
    public List<ScmUploadShipmentHeaderHis> getUnhandledShipments() {

        Session session = getSession();
        Query query = session.createQuery("from ScmUploadShipmentHeaderHis a where a.batchId is null");
        query.setMaxResults(1000); //最多一千笔,否则处理时间很长
        return query.list();
    }

    /**
     * 记录批号(检查是否被修改过)
     * @param shipment
     */
    public void batchLog(ScmUploadShipmentHeaderHis shipment){
        //saveOrUpdate(shipment);

        Session session = getSession();
        session.evict(shipment);

        Query query = session.createQuery("update ScmUploadShipmentHeaderHis a set a.batchId = :batchId, a.batchDate = :batchDate where a.ruid = :ruid and a.batchId is null");
        query.setParameter("ruid", shipment.getRuid());
        query.setParameter("batchId", shipment.getBatchId());
        query.setParameter("batchDate", shipment.getBatchDate());
        if(query.executeUpdate() == 0){
            throw new RuntimeException("可能多IC程序运行，出库单被多次执行："+shipment.getShipmentId());
        }
    }
}
