package com.chinadrtv.erp.ic.service.impl;

import com.chinadrtv.erp.ic.dao.ItemInventoryDao;
import com.chinadrtv.erp.ic.dao.ItemInventorySnapshotDao;
import com.chinadrtv.erp.ic.service.ItemInventorySnapshotService;
import com.chinadrtv.erp.model.inventory.ItemInventoryChannel;
import com.chinadrtv.erp.model.inventory.ItemInventorySnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 库存快照
 * User: gaodejian
 * Date: 13-2-4
 * Time: 下午3:00
 * To change this template use File | Settings | File Templates.
 */
@Service("itemInventorySnapshotService")
public class ItemInventorySnapshotServiceImpl implements
        ItemInventorySnapshotService {

    private static final Logger LOG = LoggerFactory.getLogger(ItemInventorySnapshotServiceImpl.class);

    @Autowired
    private ItemInventoryDao itemInventoryDao;
    @Autowired
    private ItemInventorySnapshotDao itemInventorySnapshotDao;

    public void captureEx(String batchUser){
        String snapshotId =  String.valueOf(new Date().getTime());
        itemInventorySnapshotDao.executeSnapshot(snapshotId, batchUser);
    }

    /**
     * 程序代码进行复制快照
     * @param batchUser
     */
    @Deprecated
    public void capture(String batchUser){

        List<ItemInventoryChannel> items = itemInventoryDao.getAllItemInventories();
        if(items.size() > 0){
            try
            {
                Date now = new Date();
                Long snapshotId = now.getTime();
                for(ItemInventoryChannel item : items){
                    copy(snapshotId, batchUser, now, item);
                }
            }
            catch (Exception ex){
                LOG.error(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    /**
     * 快照复制
     * @param snapshotId
     * @param snapshotUser
     * @param now
     * @param item
     */
    private void copy(Long snapshotId, String snapshotUser, Date now, ItemInventoryChannel item){

        ItemInventorySnapshot snapshot = new ItemInventorySnapshot();

        snapshot.setRefId(item.getId());
        snapshot.setChannel(item.getChannel());
        snapshot.setWarehouse(item.getWarehouse());
        snapshot.setLocationType(item.getLocationType());
        snapshot.setProductId(item.getProductId());
        snapshot.setProductCode(item.getProductCode());
        snapshot.setOnHandQty(item.getOnHandQty());
        snapshot.setFrozenQty(item.getFrozenQty());
        snapshot.setAllocatedQty(item.getAllocatedQty());
        snapshot.setAllocatedDistributionQty(item.getAllocatedDistributionQty());
        snapshot.setAllocatedOtherQty(item.getAllocatedOtherQty());
        snapshot.setInTransitPurchaseQty(item.getInTransitPurchaseQty());
        snapshot.setInTransitMovementQty(item.getInTransitMovementQty());
        snapshot.setInTransitOtherQty(item.getInTransitOtherQty());
        snapshot.setModified(now);
        snapshot.setModifiedBy(snapshotUser);
        snapshot.setCreated(now);
        snapshot.setCreatedBy(snapshotUser);
        snapshot.setSnapshotId(snapshotId);
        snapshot.setSnapshotDate(now);
        snapshot.setSnapshotBy(snapshotUser);
        itemInventorySnapshotDao.save(snapshot);
    }
}
