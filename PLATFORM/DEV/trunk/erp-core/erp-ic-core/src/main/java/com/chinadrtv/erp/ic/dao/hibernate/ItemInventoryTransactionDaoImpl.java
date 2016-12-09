package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.ItemInventoryTransactionDao;
import com.chinadrtv.erp.model.inventory.ItemInventoryTransaction;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cuiming
 * @version 1.0
 * @since 2013-1-28 下午3:31:38
 * 
 */
@Repository
public class ItemInventoryTransactionDaoImpl extends
		GenericDaoHibernateBase<ItemInventoryTransaction, Long> implements
		ItemInventoryTransactionDao {

	public ItemInventoryTransactionDaoImpl() {
		super(ItemInventoryTransaction.class);
	}

	public List<ItemInventoryTransaction> getUnhandledBatchTransactions() {
		Session session = getSession();
		Query query = session
				.createQuery("from ItemInventoryTransaction a where a.batchId is null order by a.id asc");
        query.setMaxResults(2000);
        return query.list();
	}

	public void saveOrUpdateItemTransaction(ItemInventoryTransaction transaction) {
		saveOrUpdate(transaction);
	}

    public void executeItemTransactions(Long startId, Long endId, String batchId, String batchUser) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "merge into ITEM_INVENTORY_CHANNEL a \n" +
                "using (\n" +
                "  select \n" +
                "     channel,warehouse,location_type,product_id,product_code,\n" +
                "     sum(nvl(on_hand_qty, 0)) as on_hand_qty,\n" +
                "     sum(nvl(frozen_qty,0)) as frozen_qty,\n" +
                "     sum(nvl(allocated_qty, 0)) as allocated_qty,\n" +
                "     sum(nvl(allocated_distribution_qty, 0)) as allocated_distribution_qty,\n" +
                "     sum(nvl(allocated_other_qty, 0)) as allocated_other_qty,\n" +
                "     sum(nvl(in_transit_purchase_qty, 0)) as in_transit_purchase_qty,\n" +
                "     sum(nvl(in_transit_movement_qty, 0)) as in_transit_movement_qty,\n" +
                "     sum(nvl(in_transit_other_qty, 0)) as in_transit_other_qty\n" +
                "  from ITEM_INVENTORY_TRANSACTION where BATCH_ID is null and ID >= :startId and ID <= :endId\n" +
                "  group by channel,warehouse,location_type,product_id,product_code\n" +
                ") b\n" +
                "on (\n" +
                "   a.channel=b.channel and\n" +
                "   a.warehouse=b.warehouse and\n" +
                "   a.location_type=b.location_type and\n" +
                "   a.product_id=b.product_id and\n" +
                "   a.product_code=b.product_code\n" +
                ")\n" +
                "when matched then \n" +
                "  update set \n" +
                "    a.on_hand_qty = nvl(a.on_hand_qty, 0) + b.on_hand_qty,\n" +
                "    a.frozen_qty = nvl(a.frozen_qty, 0) + b.frozen_qty,\n" +
                "    a.allocated_qty = nvl(a.allocated_qty, 0) + b.allocated_qty,\n" +
                "    a.allocated_distribution_qty = nvl(a.allocated_distribution_qty, 0) + b.allocated_distribution_qty,\n" +
                "    a.allocated_other_qty = nvl(a.allocated_other_qty, 0) + b.allocated_other_qty,\n" +
                "    a.in_transit_purchase_qty = nvl(a.in_transit_purchase_qty, 0) + b.in_transit_purchase_qty,\n" +
                "    a.in_transit_movement_qty = nvl(a.in_transit_movement_qty, 0) + b.in_transit_movement_qty,\n" +
                "    a.in_transit_other_qty = nvl(a.in_transit_other_qty, 0) + b.in_transit_other_qty,\n" +
                "    a.modified_by = :batchUser,a.modified = sysdate \n" +
                "when not matched then \n" +
                "  insert (\n" +
                "     a.id, a.channel,a.warehouse,a.location_type,a.product_id,a.product_code,a.on_hand_qty,\n" +
                "     a.frozen_qty,a.allocated_qty,a.allocated_distribution_qty,a.allocated_other_qty,\n" +
                "     a.in_transit_purchase_qty,a.in_transit_movement_qty,a.in_transit_other_qty,\n" +
                "     a.modified_by,a.created_by,a.modified,a.created\n" +
                "  ) \n" +
                "  values (\n" +
                "     ITEM_INVENTORY_CHANNEL_SEQ.NEXTVAL,\n" +
                "     b.channel,b.warehouse,b.location_type,b.product_id,b.product_code,b.on_hand_qty,\n" +
                "     b.frozen_qty,b.allocated_qty,b.allocated_distribution_qty,b.allocated_other_qty,\n" +
                "     b.in_transit_purchase_qty,b.in_transit_movement_qty,b.in_transit_other_qty,\n" +
                "     :batchUser,:batchUser,sysdate,sysdate\n" +
                "  )"
        );
        query.setLong("startId", startId);
        query.setLong("endId", endId);
        query.setString("batchUser", batchUser);
        query.executeUpdate();

        query = session.createSQLQuery(
                "update ITEM_INVENTORY_TRANSACTION " +
                "set batch_id=:batchId, batch_by=:batchUser, batch_date=sysdate, modified=sysdate, modified_by=:batchUser " +
                "where batch_id is null and id >= :startId and id <= :endId");
        query.setLong("startId", startId);
        query.setLong("endId", endId);
        query.setString("batchId", batchId);
        query.setString("batchUser", batchUser);
        query.executeUpdate();
    }

	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}

}
