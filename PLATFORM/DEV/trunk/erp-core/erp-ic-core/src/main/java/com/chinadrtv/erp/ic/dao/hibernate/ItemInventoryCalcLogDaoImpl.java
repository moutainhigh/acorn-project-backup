package com.chinadrtv.erp.ic.dao.hibernate;


import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.ItemInventoryCalcLogDao;
import com.chinadrtv.erp.model.inventory.ItemInventoryCalcLog;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gaodejian
 * @version 1.0
 * @since 2013-1-28 下午3:31:38
 * 
 */
@Repository
public class ItemInventoryCalcLogDaoImpl extends
		GenericDaoHibernateBase<ItemInventoryCalcLog, Long> implements
		ItemInventoryCalcLogDao {

	public ItemInventoryCalcLogDaoImpl() {
		super(ItemInventoryCalcLog.class);
	}

    /**
     * 创建未处理日记（根据库存事物）
     * @param batchId
     * @param batchUser
     * @return
     */
    public ItemInventoryCalcLog createUnhandledCalcLog(String batchId, String batchUser){
        Session session = getSession();
        SQLQuery query = session.createSQLQuery(
                "select /*+ FIRST_ROWS */ " +
                "       0 as ID," +
                "       nvl(min(ID),0) as START_REVESION, " +
                "       nvl(max(ID),0) as END_REVESION, " +
                "       count(ID) as ROWS_AFFECTED," +
                "       :batchId as BATCH_ID, " +
                "       :batchUser as CREATED_BY," +
                "       :batchUser as MODIFIED_BY," +
                "       '1' as STATUS," +
                "       '' as STATUS_REMARK," +
                "       sysdate as STATUS_DATE," +
                "       sysdate as CREATED," +
                "       sysdate as MODIFIED " +
                "from (select /*+index(a IDX_ITEM_INV_TRAN_CREATED) */ id from ITEM_INVENTORY_TRANSACTION a where BATCH_ID is null and CREATED > sysdate - 3 order by ID asc)" +
                "where rownum <= 5000 ")
                .addEntity(ItemInventoryCalcLog.class);
        query.setString("batchId", batchId);
        query.setString("batchUser", batchUser);
        return (ItemInventoryCalcLog)query.uniqueResult();
    }

	public void saveLog(ItemInventoryCalcLog log) {
        if(log.getId() != null && log.getId() > 0){
            getHibernateTemplate().saveOrUpdate(log);
        } else {
            log.setId(null);
            getHibernateTemplate().evict(log);
            getHibernateTemplate().saveOrUpdate(log);
        }
	}

	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}
}
