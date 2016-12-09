package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.ItemInventorySnapshotDao;
import com.chinadrtv.erp.model.inventory.ItemInventorySnapshot;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * 库存快照访问
 * User: gaodejian
 * Date: 13-2-1
 * Time: 下午1:01
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ItemInventorySnapshotDaoImpl extends GenericDaoHibernateBase<ItemInventorySnapshot, Long> implements ItemInventorySnapshotDao {

    public ItemInventorySnapshotDaoImpl() {
        super(ItemInventorySnapshot.class);
    }

    public void executeSnapshot(String snapshotId, String snapshotBy) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "insert into ITEM_INVENTORY_SNAPSHOT(" +
                "  id,ref_id,channel,warehouse,location_type,product_id,product_code,on_hand_qty,frozen_qty," +
                "  allocated_qty,allocated_distribution_qty,allocated_other_qty,in_transit_purchase_qty," +
                "  in_transit_movement_qty,in_transit_other_qty,created_by,modified_by,created,modified," +
                "  snapshot_id,snapshot_date,snapshot_by" +
                ")" +
                "select " +
                "  ITEM_INVENTORY_SNAPSHOT_SEQ.NEXTVAL,id,channel,warehouse,location_type,product_id,product_code," +
                "  on_hand_qty,frozen_qty,allocated_qty,allocated_distribution_qty,allocated_other_qty," +
                "  in_transit_purchase_qty,in_transit_movement_qty,in_transit_other_qty,created_by,modified_by," +
                "  created,modified,:snapshotId,sysdate,:snapshotBy " +
                "from ITEM_INVENTORY_CHANNEL"
        );
        query.setString("snapshotId", snapshotId);
        query.setString("snapshotBy", snapshotBy);
        query.executeUpdate();
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

}
