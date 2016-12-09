package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.ic.model.NcRealTimeStockItem;
import com.chinadrtv.erp.ic.model.RealTimeStockItem;
import com.chinadrtv.erp.ic.model.SalesStockItem;
import com.chinadrtv.erp.ic.model.WmsRealTimeStockItem;
import com.chinadrtv.erp.model.inventory.ItemInventoryChannel;

import java.util.List;
import java.util.Map;

/**
 * @author cuiming
 * @version 1.0
 * @since 2013-1-28 下午6:15:47
 * 
 */
public interface ItemInventoryDao {

	 List<ItemInventoryChannel> getItemNcInventoryChannel(String chanel,
                                                                List ncfree, List spellcode, String warehouses, String locationType);

	List<ItemInventoryChannel> getItemSkuInventoryChannel(String chanel,
                                                                 List productId, List plucode, List spellcode, String warehouses,
                                                                 String locationType);

	ItemInventoryChannel getDbItemInventory(String channel,
                                                   String warehouse, String locationType, Long productId);

	void saveOrUpdateItemInventory(
            ItemInventoryChannel itemInventoryChannel);

	ItemInventoryChannel getCacheItemInventory(
            String chanel,
            String warehouse,
            String locationType,
            Long productId);

    void saveCacheItemInventory(
            String chanel,
            String warehouse,
            String locationType,
            Long productId,
            ItemInventoryChannel itemInventoryChannel);

    List<ItemInventoryChannel> getAllItemInventories();
    /**
     * 获取NC级别的即时库存
     * @param params
     * @return
     */
    List<NcRealTimeStockItem> getNcRealTimeStock(Map<String, Object> params);
    /**
     * 获取NC级别的即时库存(不区分可不可买)
     * @param params
     * @return
     */
    List<NcRealTimeStockItem> getNcRealTimeAllStock(Map<String, Object> params);

    /**
     * 获取即时库存(含套装)
     * @param params
     * @return
     */
    List<RealTimeStockItem> getDbRealTimeStock(Map<String, Object> params);
    /**
     * 获取即时库存(含套装,不区分可不可售)
     * @param params
     * @return
     */
    List<RealTimeStockItem> getDbRealTimeAllStock(Map<String, Object> params);
    /**
     * 获取即时库存
     * @param params
     * @return
     */
    List<WmsRealTimeStockItem> getWmsRealTimeStock(Map<String, Object> params);

    /**
     * 获取销售库存信息
     * @param params
     * @return
     */
    List<SalesStockItem> getSalesStockItem(Map<String, Object> params);
}
