package com.chinadrtv.erp.ic.service.impl;

import com.chinadrtv.erp.ic.dao.ItemInventoryDao;
import com.chinadrtv.erp.ic.dao.PlubasInfoDao;
import com.chinadrtv.erp.ic.dao.WarehouseDao;
import com.chinadrtv.erp.ic.service.ItemInventoryAllocateService;
import com.chinadrtv.erp.ic.service.ItemInventoryTransactionService;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.model.inventory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 库存预分配服务
 * User: gaodejian
 * Date: 13-2-4
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
@Service("itemInventoryAllocateService")
public class ItemInventoryAllocateServiceImpl implements ItemInventoryAllocateService {

    @Autowired
    private ItemInventoryTransactionService itemInventoryTransactionService;

    @Autowired
    private ItemInventoryDao itemInventoryDao;

    @Autowired
    private PlubasInfoDao plubasInfoDao;

    @Autowired
    private WarehouseDao  warehouseDao;

    /**
     * 预分配
     * @param event
     */
    public void assign(AllocatedEvent event){

        List<ItemInventoryTransaction> lists = new ArrayList<ItemInventoryTransaction>();

        for(AllocatedEventItem item : event.getEventItems()) {

            ItemInventoryTransaction trans = new ItemInventoryTransaction();
            trans.setChannel(event.getChannel());
            trans.setWarehouse(event.getWarehouse());
            trans.setSourceId(event.getSourceId());
            trans.setSourceDesc("OMS");
            trans.setBusinessNo(event.getBusinessNo());
            trans.setBusinessType(event.getBusinessType());
            if(event.getBusinessDate() != null){
                trans.setBusinessDate(event.getBusinessDate());
            } else {
                trans.setBusinessDate(new Date());
            }
            trans.setCreatedBy(event.getUser());
            trans.setCreated(new Date());
            trans.setModifiedBy(event.getUser());
            trans.setModified(new Date());
            trans.setProductId(item.getProductId());
            trans.setProductCode(item.getProductCode());
            trans.setLocationType(item.getLocationType());
            trans.setEstimatedQty(item.getQuantity());
            trans.setQty(0.0);

            if(check(event, item, trans)){
                lists.add(trans);
            }
        }
        //只有所有记录全部满足条件才进行库存事物新增
        //临时注释掉,不管成功失败都保存进去
        if(event.isSuccess()){
            for(ItemInventoryTransaction trans : lists){
                itemInventoryTransactionService.insertItemInventoryTransaction(trans);
            }
        }
    }

    /**
     * 预分配取消
     * @param event
     */
    public void unassign(AllocatedEvent event){

        List<ItemInventoryTransaction> lists = new ArrayList<ItemInventoryTransaction>();

        for(AllocatedEventItem item : event.getEventItems()) {

            ItemInventoryTransaction trans = new ItemInventoryTransaction();
            trans.setChannel(event.getChannel());
            trans.setWarehouse(event.getWarehouse());
            trans.setBusinessType(event.getBusinessType());
            trans.setSourceId(event.getSourceId());
            trans.setSourceDesc("OMS");
            trans.setBusinessNo(event.getBusinessNo());
            if(event.getBusinessDate() != null){
                trans.setBusinessDate(event.getBusinessDate());
            } else {
                trans.setBusinessDate(new Date());
            }
            trans.setCreatedBy(event.getUser());
            trans.setCreated(new Date());
            trans.setModifiedBy(event.getUser());
            trans.setModified(new Date());
            trans.setProductId(item.getProductId());
            trans.setProductCode(item.getProductCode());
            trans.setLocationType(item.getLocationType());
            trans.setEstimatedQty(item.getQuantity() * -1); //取消直接使用负数
            trans.setQty(0.0);

            if(check(event, item, trans)){
                lists.add(trans);
            }
        }
        //只有所有记录全部满足条件才进行库存事物新增
        if(event.isSuccess()){
            for(ItemInventoryTransaction trans : lists){
                itemInventoryTransactionService.insertItemInventoryTransaction(trans);
            }
        }
    }

    /**
     * 检查库存有效性
     * @param event
     * @param trans
     * @return
     */
    private Boolean check(AllocatedEvent event, AllocatedEventItem eventItem, ItemInventoryTransaction trans){

        //String[] allowNegatives = { "1", "3", "9" };
        /* TO DO 临时全部允许负库存销售 */
        //String[] allowNegatives = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        PlubasInfo info = plubasInfoDao.getPlubasInfo(trans.getProductCode());
        if(info != null) {
            if(eventItem.isAllowNegative()){
                return true;
            } else {
                //不允许负库存销售(缓存)
                ItemInventoryChannel item = itemInventoryDao.getCacheItemInventory(
                        trans.getChannel(),
                        trans.getWarehouse(),
                        trans.getLocationType(),
                        trans.getProductId());
                if(item != null){
                    //预分配数量不能超过在手量与已分配量之差
                    Double allocated = item.getAllocatedQty() + item.getAllocatedDistributionQty() + item.getAllocatedOtherQty() +
                                       (item.getFrozenQty() != null ? item.getFrozenQty() : 0D);

                    if(trans.getEstimatedQty() > 0 && trans.getEstimatedQty() > item.getOnHandQty() - allocated) {
                        event.getErrors().add(trans.getProductCode() + "不允许负库存销售("+GetWarehouseName(trans.getWarehouse())+")");
                        return false;
                    }
                    return true;
                }
                event.getErrors().add(trans.getProductCode() + "商品库存不存在("+GetWarehouseName(trans.getWarehouse())+")");
                return false;
            }
        } else {
            event.getErrors().add(trans.getProductCode() + "商品不存在");
            return false;
        }
    }

    private String GetWarehouseName(String warehouseId){
        try
        {
            Warehouse wh = warehouseDao.get(Long.parseLong(warehouseId));
            return wh.getWarehouseName();
        }
        catch (Exception ex){
            return warehouseId;
        }
    }
}
