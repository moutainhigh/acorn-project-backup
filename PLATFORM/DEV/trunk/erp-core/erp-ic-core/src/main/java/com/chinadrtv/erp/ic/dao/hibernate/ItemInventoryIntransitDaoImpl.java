package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.ItemInventoryIntransitDao;
import com.chinadrtv.erp.ic.model.NcIntransitItem;
import com.chinadrtv.erp.model.inventory.ItemInventoryIntransit;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-2
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ItemInventoryIntransitDaoImpl
        extends GenericDaoHibernateBase<ItemInventoryIntransit, Long>
        implements ItemInventoryIntransitDao {

    public ItemInventoryIntransitDaoImpl() {
        super(ItemInventoryIntransit.class);
    }
    /*
	 * @see com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase#
	 * setSessionFactory(org.hibernate.SessionFactory)
	 */
    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<NcIntransitItem> GetNcIntransitItems(String nccode, String ncfreename) {
        Session session = getSession();
        Query q = session.createSQLQuery(
                "select a.warehouse,c.warehouse_name as warehouseName, b.nccode,b.ncfree,b.ncfreeName,a.arrive_date as arriveDate,sum(a.transit_qty) as transitQty " +
                "from acoapp_oms.item_inventory_intransit a " +
                "left join iagent.plubasinfo b on a.product_id = b.ruid " +
                "left join acoapp_oms.warehouse c on a.warehouse = c.warehouse_code " +
                "where b.nccode=:nccode and (:ncfreename is null or nvl(b.ncfreename,'0')=nvl(:ncfreename,'0')) " +
                "group by a.warehouse,b.nccode,b.ncfree,b.ncfreename,a.arrive_date,c.warehouse_name")
                .addScalar("warehouse", StringType.INSTANCE)
                .addScalar("warehouseName", StringType.INSTANCE)
                .addScalar("nccode", StringType.INSTANCE)
                .addScalar("ncfree", StringType.INSTANCE)
                .addScalar("ncfreeName", StringType.INSTANCE)
                .addScalar("arriveDate", DateType.INSTANCE)
                .addScalar("transitQty", DoubleType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(NcIntransitItem.class));
        q.setParameter("nccode", nccode);
        q.setParameter("ncfreename", ncfreename);
        return q.list();
    }
}
