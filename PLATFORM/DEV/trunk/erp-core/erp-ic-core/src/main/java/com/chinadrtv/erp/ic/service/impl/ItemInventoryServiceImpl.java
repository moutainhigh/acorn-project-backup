package com.chinadrtv.erp.ic.service.impl;

import com.chinadrtv.erp.constant.ChannelConstants;
import com.chinadrtv.erp.constant.LocationTypeConstants;
import com.chinadrtv.erp.ic.dao.ItemInventoryDao;
import com.chinadrtv.erp.ic.dao.PlubasInfoDao;
import com.chinadrtv.erp.ic.dao.WarehouseDao;
import com.chinadrtv.erp.ic.model.NcRealTimeStockItem;
import com.chinadrtv.erp.ic.model.RealTimeStockItem;
import com.chinadrtv.erp.ic.model.WmsRealTimeStockItem;
import com.chinadrtv.erp.ic.service.ItemInventoryService;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.model.inventory.ItemInventoryChannel;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cuiming
 * @version 1.0
 * @since 2013-1-28 下午6:19:57
 */
@Service("itemInventoryService")
public class ItemInventoryServiceImpl implements ItemInventoryService {

    private static final Logger LOG = LoggerFactory.getLogger(ItemInventoryServiceImpl.class);

	@Autowired
	private ItemInventoryDao itemInventoryDao;

    @Autowired
    private PlubasInfoDao plubasInfoDao;

    @Autowired
    private WarehouseDao warehouseDao;

	/*
	 * (非 Javadoc) <p>Title: getItemNcInventoryChannel</p> <p>Description: </p>
	 * 
	 * @param chanel
	 * 
	 * @param ncfree
	 * 
	 * @param spellcode
	 * 
	 * @param warehouses
	 * 
	 * @param locationType
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.ic.service.ItemInventoryService#getItemNcInventoryChannel
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	public List<ItemInventoryChannel> getItemNcInventoryChannel(String chanel,
			String nccode, String spellcode, String warehouses,
			String locationType) {
		// TODO Auto-generated method stub

		List nccodelist = new ArrayList();
		List spellcodelist = new ArrayList();
		try {
			nccodelist.add(nccode);
			spellcodelist.add(spellcode);
		} catch (Exception e) {
            LOG.error(e.getMessage());
		}
		List<ItemInventoryChannel> result = itemInventoryDao
				.getItemNcInventoryChannel(chanel, nccodelist, spellcodelist,
						warehouses, locationType);
		List<ItemInventoryChannel> cachKeyList = new ArrayList();
		if (result != null && !result.isEmpty()) {
			for (int i = 0; i < result.size(); i++) {
				ItemInventoryChannel itemInventoryChannel = (ItemInventoryChannel) result
						.get(i);
				cachKeyList.add(itemInventoryDao.getCacheItemInventory(
						itemInventoryChannel.getChannel(),
						itemInventoryChannel.getWarehouse(),
						itemInventoryChannel.getLocationType(),
						itemInventoryChannel.getProductId()));
			}
		}
		return cachKeyList;
	}

	/*
	 * (非 Javadoc) <p>Title: getItemNcInventoryChannelList</p> <p>Description:
	 * </p>
	 * 
	 * @param chanel
	 * 
	 * @param ncfree
	 * 
	 * @param spellcode
	 * 
	 * @param warehouses
	 * 
	 * @param locationType
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.ic.service.ItemInventoryService#
	 * getItemNcInventoryChannelList(java.lang.String, java.util.List,
	 * java.util.List, java.lang.String, java.lang.String)
	 */
	public List<ItemInventoryChannel> getItemNcInventoryChannelList(
			String chanel, List ncfree, List spellcode, String warehouses,
			String locationType) {
		// TODO Auto-generated method stub
		List<ItemInventoryChannel> result = itemInventoryDao
				.getItemNcInventoryChannel(chanel, ncfree, spellcode,
						warehouses, locationType);
		List<ItemInventoryChannel> cachKeyList = new ArrayList();
		if (result != null && !result.isEmpty()) {
			for (int i = 0; i < result.size(); i++) {
				ItemInventoryChannel itemInventoryChannel = (ItemInventoryChannel) result
						.get(i);
				cachKeyList.add(itemInventoryDao.getCacheItemInventory(
						itemInventoryChannel.getChannel(),
						itemInventoryChannel.getWarehouse(),
						itemInventoryChannel.getLocationType(),
						itemInventoryChannel.getProductId()));
			}
		}
		return cachKeyList;
	}

	/*
	 * (非 Javadoc) <p>Title: getItemSkuInventoryChannel</p> <p>Description: </p>
	 * 
	 * @param chanel
	 * 
	 * @param productId
	 * 
	 * @param plucode
	 * 
	 * @param spellcode
	 * 
	 * @param warehouses
	 * 
	 * @param locationType
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.ic.service.ItemInventoryService#getItemSkuInventoryChannel
	 * (java.lang.String, java.lang.Long, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public List<ItemInventoryChannel> getItemSkuInventoryChannel(String chanel,
			Long productId, String plucode, String spellcode,
			String warehouses, String locationType) {
		// TODO Auto-generated method stub
		List spellcodelist = new ArrayList();
		List productIdlist = new ArrayList();
		List plucodelist = new ArrayList();
		try {
			spellcodelist.add(spellcode);
			productIdlist.add(productId);
			plucodelist.add(plucode);
		} catch (Exception e) {
            LOG.error(e.getMessage());
		}
		List<ItemInventoryChannel> result = itemInventoryDao
				.getItemSkuInventoryChannel(chanel, productIdlist, plucodelist,
						spellcodelist, warehouses, locationType);
		List<ItemInventoryChannel> cachKeyList = new ArrayList();
		if (result != null && !result.isEmpty()) {
			for (int i = 0; i < result.size(); i++) {
				ItemInventoryChannel itemInventoryChannel = (ItemInventoryChannel) result
						.get(i);
				cachKeyList.add(itemInventoryDao.getCacheItemInventory(
						itemInventoryChannel.getChannel(),
						itemInventoryChannel.getWarehouse(),
						itemInventoryChannel.getLocationType(),
						itemInventoryChannel.getProductId()));
			}
		}
		return cachKeyList;
	}

	/*
	 * (非 Javadoc) <p>Title: getItemSkuInventoryChannelList</p> <p>Description:
	 * </p>
	 * 
	 * @param chanel
	 * 
	 * @param productId
	 * 
	 * @param plucode
	 * 
	 * @param spellcode
	 * 
	 * @param warehouses
	 * 
	 * @param locationType
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.ic.service.ItemInventoryService#
	 * getItemSkuInventoryChannelList(java.lang.String, java.util.List,
	 * java.util.List, java.util.List, java.lang.String, java.lang.String)
	 */
	public List<ItemInventoryChannel> getItemSkuInventoryChannelList(
			String chanel, List productId, List plucode, List spellcode,
			String warehouses, String locationType) {

		List<ItemInventoryChannel> result = itemInventoryDao
				.getItemSkuInventoryChannel(chanel, productId, plucode,
						spellcode, warehouses, locationType);
		List<ItemInventoryChannel> cachKeyList = new ArrayList();
		if (result != null && !result.isEmpty()) {
			for (int i = 0; i < result.size(); i++) {
				ItemInventoryChannel itemInventoryChannel = (ItemInventoryChannel) result
						.get(i);
				cachKeyList.add(itemInventoryDao.getCacheItemInventory(
						itemInventoryChannel.getChannel(),
						itemInventoryChannel.getWarehouse(),
						itemInventoryChannel.getLocationType(),
						itemInventoryChannel.getProductId()));
			}
		}
		// TODO Auto-generated method stub
		return cachKeyList;
	}

    /**
     * 获取实时库存
     * @param nccode
     * @param pluname
     * @param spellcode
     * @param ncfreename
     * @return
     */
    public List<RealTimeStockItem> getDbRealTimeStock(String nccode, String pluname, String spellcode, String ncfreename) {
        Map<String, Object> params = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(nccode)){
            params.put("nccode", nccode);
        }
        if(StringUtils.isNotBlank(pluname)){
            params.put("pluname", pluname);
        }
        if(StringUtils.isNotBlank(spellcode)){
            params.put("spellcode", spellcode);
        }
        if(StringUtils.isNotBlank(ncfreename)){
            params.put("ncfreename", ncfreename);
        }

        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            params.put("grpid", agentUser.getWorkGrp());
        }

        return itemInventoryDao.getDbRealTimeStock(params);
    }

    public List<RealTimeStockItem> getDbRealTimeStock(String plucode) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(plucode)){
            params.put("plucode", plucode);
        }
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            params.put("grpid", agentUser.getWorkGrp());
        }
        return itemInventoryDao.getDbRealTimeStock(params);
    }

    public List<RealTimeStockItem> getDbRealTimeAllStock(String plucode) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(plucode)){
            params.put("plucode", plucode);
        }
        //不受商品不可卖, 渠道商品不可卖限制
        /*
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            params.put("grpid", agentUser.getWorkGrp());
        }
        */
        return itemInventoryDao.getDbRealTimeAllStock(params);
    }

    public List<RealTimeStockItem> getDbRealTimeStock(String nccode, String ncfree, String ncfreename) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(nccode)){
            params.put("nccode", nccode);
        }

        if(StringUtils.isNotBlank(ncfree)){
            params.put("ncfree", ncfree);
        }

        if(StringUtils.isNotBlank(ncfreename)){
            params.put("ncfreename", ncfreename);
        }

        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            params.put("grpid", agentUser.getWorkGrp());
        }
        return itemInventoryDao.getDbRealTimeStock(params);
    }

    public List<RealTimeStockItem> getDbRealTimeAllStock(String nccode, String ncfree, String ncfreename) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(nccode)){
            params.put("nccode", nccode);
        }

        if(StringUtils.isNotBlank(ncfree)){
            params.put("ncfree", ncfree);
        }

        if(StringUtils.isNotBlank(ncfreename)){
            params.put("ncfreename", ncfreename);
        }
        //不受商品不可卖, 渠道商品不可卖限制
        /*
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            params.put("grpid", agentUser.getWorkGrp());
        }
        */
        return itemInventoryDao.getDbRealTimeAllStock(params);
    }

    public List<RealTimeStockItem> getDbRealTimeStockEx(String nccode, String ncfreename) {
        if(StringUtils.isBlank(nccode)){
            return new ArrayList<RealTimeStockItem>();
        }
        Map<String, Object> params = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(nccode)){
            params.put("nccode", nccode);
        }
        if(StringUtils.isNotBlank(ncfreename)){
            params.put("ncfreename", ncfreename);
        }
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            params.put("grpid", agentUser.getWorkGrp());
        }
        return itemInventoryDao.getDbRealTimeStock(params);
    }

    public List<NcRealTimeStockItem> getNcRealTimeStock(String nccode) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(nccode)){
            params.put("nccode", nccode);
        }
        /*
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            params.put("grpid", agentUser.getWorkGrp());
        }
        */
        return itemInventoryDao.getNcRealTimeStock(params);
    }

    public List<NcRealTimeStockItem> getNcRealTimeAllStock(String nccode) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(nccode)){
            params.put("nccode", nccode);
        }
        /*
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            params.put("grpid", agentUser.getWorkGrp());
        }
        */
        return itemInventoryDao.getNcRealTimeAllStock(params);
    }


    public List<NcRealTimeStockItem> getNcRealTimeStock(String nccode, String ncfree, String ncfreename) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(nccode)){
            params.put("nccode", nccode);
        }
        if(StringUtils.isNotBlank(ncfree)){
            params.put("ncfree", ncfree);
        }

        if(StringUtils.isNotBlank(ncfreename)){
            params.put("ncfreename", ncfreename);
        }
        /*
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            params.put("grpid", agentUser.getWorkGrp());
        }
        */
        return itemInventoryDao.getNcRealTimeStock(params);
    }

    public List<NcRealTimeStockItem> getNcRealTimeAllStock(String nccode, String ncfree, String ncfreename) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(nccode)){
            params.put("nccode", nccode);
        }
        if(StringUtils.isNotBlank(ncfree)){
            params.put("ncfree", ncfree);
        }

        if(StringUtils.isNotBlank(ncfreename)){
            params.put("ncfreename", ncfreename);
        }
        /*
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            params.put("grpid", agentUser.getWorkGrp());
        }
        */
        return itemInventoryDao.getNcRealTimeAllStock(params);
    }

    public List<WmsRealTimeStockItem> getWmsRealTimeStock(String nccode, String ncfree, String ncfreename){
        Map<String, Object> params = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(nccode)){
            params.put("nccode", nccode);
        }

        if(StringUtils.isNotBlank(ncfree)){
            params.put("ncfree", ncfree);
        }

        if(StringUtils.isNotBlank(ncfreename)){
            params.put("ncfreename", ncfreename);
        }
        /*
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            params.put("grpid", agentUser.getWorkGrp());
        }
        */
        return itemInventoryDao.getWmsRealTimeStock(params);
    }

    public List<WmsRealTimeStockItem> getWmsRealTimeStock(String nccode, String ncfreename){
        Map<String, Object> params = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(nccode)){
            params.put("nccode", nccode);
        }

        if(StringUtils.isNotBlank(ncfreename)){
            params.put("ncfreename", ncfreename);
        }
        /*
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            params.put("grpid", agentUser.getWorkGrp());
        }
        */
        return itemInventoryDao.getWmsRealTimeStock(params);
    }

    public List<RealTimeStockItem> getCacheRealTimeStock(String nccode, String pluname, String spellcode, String ncfreename) {
        List<PlubasInfo> plubasInfos = plubasInfoDao.getPlubasInfos(nccode, pluname, spellcode, ncfreename);
        List<Warehouse> warehouses = warehouseDao.getAllWarehouses();
        List<RealTimeStockItem> stockItems = new ArrayList<RealTimeStockItem>();

        for(PlubasInfo plubasInfo: plubasInfos){
            RealTimeStockItem stockItem = new RealTimeStockItem();
            stockItem.setProductId(plubasInfo.getRuid());
            stockItem.setProductCode(plubasInfo.getPlucode());
            stockItem.setProductName(plubasInfo.getPluname());
            stockItem.setNcCode(plubasInfo.getNccode());
            stockItem.setSpellCode(plubasInfo.getSpellcode());
            stockItem.setListPrice(plubasInfo.getSlprc());
            stockItem.setMaxPrice(plubasInfo.getHslprc());
            stockItem.setMinPrice(plubasInfo.getLslprc());
            stockItem.setOnHandQty(0.0);
            stockItem.setAvailableQty(0.0);
            for(Warehouse warehouse: warehouses){
                ItemInventoryChannel item = itemInventoryDao.getCacheItemInventory(
                        ChannelConstants.Default,
                        String.valueOf(warehouse.getWarehouseId()),
                        LocationTypeConstants.INVENTORY_QUALITY_PRODUCT,
                        plubasInfo.getRuid());
                if(item != null){
                    if(item.getOnHandQty() == null) item.setOnHandQty(0.0);
                    if(item.getAllocatedQty() == null) item.setAllocatedQty(0.0);
                    if(item.getAllocatedDistributionQty() == null) item.setAllocatedDistributionQty(0.0);
                    if(item.getAllocatedOtherQty() == null) item.setAllocatedOtherQty(0.0);

                    stockItem.setOnHandQty(stockItem.getOnHandQty() + item.getOnHandQty());
                    stockItem.setAvailableQty(stockItem.getAvailableQty() + item.getOnHandQty() - item.getAllocatedQty() - item.getAllocatedDistributionQty() - item.getAllocatedOtherQty());
                }
            }
            stockItems.add(stockItem);
        }

        return stockItems;
    }

}
