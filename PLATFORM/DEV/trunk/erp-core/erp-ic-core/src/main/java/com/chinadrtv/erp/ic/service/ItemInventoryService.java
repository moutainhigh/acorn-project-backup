package com.chinadrtv.erp.ic.service;

import com.chinadrtv.erp.ic.model.NcRealTimeStockItem;
import com.chinadrtv.erp.ic.model.RealTimeStockItem;
import com.chinadrtv.erp.ic.model.WmsRealTimeStockItem;
import com.chinadrtv.erp.model.inventory.ItemInventoryChannel;

import java.util.List;
import java.util.Map;

/**
 * @author cuiming
 * @version 1.0
 * @since 2013-1-28 下午6:19:35
 * 
 */
public interface ItemInventoryService {
	List<ItemInventoryChannel> getItemNcInventoryChannel(String chanel,
                                                                String ncfree, String spellcode, String warehouses,
                                                                String locationType);

	List<ItemInventoryChannel> getItemNcInventoryChannelList(
            String chanel, List ncfree, List spellcode, String warehouses,
            String locationType);

	List<ItemInventoryChannel> getItemSkuInventoryChannel(String chanel,
                                                                 Long productId, String plucode, String spellcode,
                                                                 String warehouses, String locationType);

	List<ItemInventoryChannel> getItemSkuInventoryChannelList(
            String chanel, List productId, List plucode, List spellcode,
            String warehouses, String locationType);
    /**
     * 获取商品实时库存
     * @param plucode     商品编码
     * @return
     */
    List<RealTimeStockItem> getDbRealTimeStock(String plucode);
    /**
     * 获取商品实时库存(不区分可不可买)
     * @param plucode     商品编码
     * @return
     */
    List<RealTimeStockItem> getDbRealTimeAllStock(String plucode);
    /**
     * 获取商品实时库存
     * @param ncfree    规格编码(自由项)
     * @param ncfreename  规格编码(自由项名称) 套装通过名称关联（套装必填）
     * @return
     */
    List<RealTimeStockItem> getDbRealTimeStock(String nccode, String ncfree, String ncfreename);
    /**
     * 查询商品即时库存
     * @param nccode
     * @param ncfreename  规格编码(自由项名称)
     * @return
     */
    List<RealTimeStockItem> getDbRealTimeStockEx(String nccode, String ncfreename);

    /**
     * 分仓库即时库存
     * @param nccode
     * @return
     */
    List<WmsRealTimeStockItem> getWmsRealTimeStock(String nccode, String ncfree, String ncfreename);

    /**
     * 分仓库即时库存
     * @param nccode
     * @return
     */
    List<WmsRealTimeStockItem> getWmsRealTimeStock(String nccode, String ncfreename);

    /**
     * 获取NC商品库存
     * @param nccode
     * @param pluname
     * @param spellcode
     * @param ncfreename
     * @return
     */
    List<RealTimeStockItem> getDbRealTimeStock(String nccode, String pluname, String spellcode, String ncfreename);

    /**
     * 获取NC商品库存
     * @param nccode
     * @return
     */
    List<NcRealTimeStockItem> getNcRealTimeStock(String nccode);

    /**
     * 获取NC商品库存(带规格)
     * @param nccode
     * @param ncfree
     * @param ncfreename
     * @return
     */
    List<NcRealTimeStockItem> getNcRealTimeStock(String nccode, String ncfree, String ncfreename);

    /**
     * 获取NC级别的即时库存(不区分可不可买)
     * @param nccode
     * @return
     */
    List<NcRealTimeStockItem> getNcRealTimeAllStock(String nccode);
    /**
     * 获取NC商品库存(带规格,不区分可不可买)
     * @param nccode
     * @param ncfree
     * @param ncfreename
     * @return
     */
    List<NcRealTimeStockItem> getNcRealTimeAllStock(String nccode, String ncfree, String ncfreename);

    /**
     * 获取缓蹲中即时库存
     * @param nccode
     * @param pluname
     * @param spellcode
     * @param ncfreename
     * @return
     */
    List<RealTimeStockItem> getCacheRealTimeStock(String nccode, String pluname, String spellcode, String ncfreename);

}
