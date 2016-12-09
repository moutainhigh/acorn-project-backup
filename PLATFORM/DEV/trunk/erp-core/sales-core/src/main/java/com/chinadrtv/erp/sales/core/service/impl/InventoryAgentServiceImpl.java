package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.constant.BusinessTypeConstants;
import com.chinadrtv.erp.constant.ChannelConstants;
import com.chinadrtv.erp.constant.LocationTypeConstants;
import com.chinadrtv.erp.constant.TransactionSourceConstants;
import com.chinadrtv.erp.ic.model.RealTimeStockItem;
import com.chinadrtv.erp.ic.service.ItemInventoryAllocateService;
import com.chinadrtv.erp.ic.service.ItemInventoryService;
import com.chinadrtv.erp.ic.service.PlubasInfoService;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.Parameters;
import com.chinadrtv.erp.model.inventory.AllocatedEvent;
import com.chinadrtv.erp.model.inventory.AllocatedEventItem;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.model.inventory.ProductSuiteType;
import com.chinadrtv.erp.model.trade.ShipmentDetail;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.core.dao.ParametersDao;
import com.chinadrtv.erp.tc.core.dao.PluSplitDao;
import com.chinadrtv.erp.tc.core.model.InventoryAssignInfo;
import com.chinadrtv.erp.tc.core.service.OrderSkuSplitService;
import com.chinadrtv.erp.tc.core.utils.CollectionUtil;
import com.chinadrtv.erp.sales.core.service.InventoryAgentService;
import com.chinadrtv.erp.sales.core.service.ShipmentHeaderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-9-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class InventoryAgentServiceImpl implements InventoryAgentService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(InventoryAgentServiceImpl.class);

    /* 允许负库存的状态 */
    private static final String[] ALLOW_NEGATIVE_STOCK_STATUS = {"1", "3", "9"};

    @Autowired
    private ItemInventoryAllocateService itemInventoryAllocateService;
    /*@Autowired
    private ParametersDao parametersDao;
    private String icContolFlag="UseICStockCalc";*/
    @Autowired
    private OrderhistDao orderhistDao;
    @Autowired
    private OrderSkuSplitService orderSkuSplitService;
    @Autowired
    private PluSplitDao pluSplitDao;
    @Autowired
    private ItemInventoryService itemInventoryService;
    @Autowired
    private PlubasInfoService plubasInfoService;

    private List<String> serviceOrderTypeList;

    private List<String> notPayTypeList;

    @Value("${serviceOrderTypes}")
     public void initServiceOrderTypes(String serviceOrderTypes)
    {
        serviceOrderTypeList=new ArrayList<String>();
        if(StringUtils.isNotEmpty(serviceOrderTypes))
        {
            String[] tokens = serviceOrderTypes.split(",");
            for(String str:tokens)
            {
                if(!serviceOrderTypeList.contains(str))
                {
                    serviceOrderTypeList.add(str);
                }
            }
        }
    }

    @Value("${notPayTypes}")
    public void initNotPayTypes(String notPayTypes)
    {
        notPayTypeList=new ArrayList<String>();
        if(StringUtils.isNotEmpty(notPayTypes))
        {
            String[] tokens = notPayTypes.split(",");
            for(String str:tokens)
            {
                if(!notPayTypeList.contains(str))
                {
                    notPayTypeList.add(str);
                }
            }
        }
    }

    private boolean isIcControl() {
        /*Parameters parameters = parametersDao.getSystemParm(icContolFlag);
        if(parameters==null)
            return false;
        if("Y".equals(parameters.getParam()))
            return true;
        return false;*/
        return true;
    }

    public boolean isProdIcControl(String flag)
    {
        if(Arrays.asList(ALLOW_NEGATIVE_STOCK_STATUS).contains(flag))
            return false;
        return true;
    }

    public void reserveInventory(Order order,ShipmentHeader shipmentHeader,String warehouseId,String usrId) {
        if(serviceOrderTypeList.contains(order.getOrdertype())||notPayTypeList.contains(order.getPaytype()))
        {
            return;
        }

        boolean isICControl=this.isIcControl();
        if(isICControl==true)
        {
            logger.info("begin inventory check orderid:"+order.getOrderid());
            checkInventoryQuantity(order, shipmentHeader);
            logger.info("end inventory check orderid:"+order.getOrderid());
        }
        if(shipmentHeader!=null)
            this.unassignInventory(order,usrId,shipmentHeader);
        try
        {
            this.assignInventory(order,warehouseId,usrId);
        }catch (RuntimeException exp)
        {
            logger.error("reserve inventory error:",exp);
            if(isICControl==true)
            {
                throw exp;
            }
        }
    }

    private void checkInventoryQuantity(Order order, ShipmentHeader shipmentHeader) {
        //判断库存量(只需要判断需要库存控制的产品就行了)
        List<InventoryAssignInfo> inventoryAssignInfoList=this.getAssignInfoFromOrder(order);
        if(shipmentHeader!=null)
        {
            List<InventoryAssignInfo> reserveList=this.getAssignInfoFromShipment(shipmentHeader);
            //合并(优先处理需要库存控制的产品)
            for(InventoryAssignInfo reserveItem:reserveList)
            {
                int index=inventoryAssignInfoList.indexOf(reserveItem);
                if(index>=0)
                {
                    InventoryAssignInfo matchItem=inventoryAssignInfoList.get(index);
                    matchItem.setQuantity(matchItem.getQuantity()-reserveItem.getQuantity());
                }
            }
        }
        for(InventoryAssignInfo item:inventoryAssignInfoList)
        {
            if(item.getQuantity()>0)
            {
                List<RealTimeStockItem> stockItems = itemInventoryService.getDbRealTimeAllStock(item.getScmId());
                if (stockItems == null || stockItems.isEmpty()) {
                    logger.error("inventory check error - no stock orderid:"+order.getOrderid()+" itemid:"+item.getScmId());
                    throw new RuntimeException("该商品暂时没有库存,商品编号为:" + item.getScmId());
                }
                if (stockItems.get(0).getAvailableQty().compareTo(Double.valueOf(String.valueOf(item.getQuantity())))< 0 || stockItems.get(0).getAvailableQty().compareTo(1d)<0 ) {
                    logger.error("inventory check error - less stock orderid:"+order.getOrderid()+" itemid:"+item.getScmId());
                    logger.error("inventory quantity:"+stockItems.get(0).getAvailableQty());
                    logger.error("reserve quantity:"+item.getQuantity());
                    throw new RuntimeException("该商品库存不足,商品编号为:" + item.getScmId());
                }
            }
        }
    }

    public void unreserveInventory(Order order, ShipmentHeader shipmentHeader, String usrId) {
        if(serviceOrderTypeList.contains(order.getOrdertype())||notPayTypeList.contains(order.getPaytype()))
        {
            return;
        }
        if (null != shipmentHeader) {
            //取消分配库存
            /*if(order.getRevision()!=null&&shipmentHeader.getOrderRefRevisionId()!=null)
            {
                if(order.getRevision().intValue()!=shipmentHeader.getOrderRefRevisionId().intValue())
                {
                    String message="订单与运单版本不一致"+order.getOrderid();
                    logger.error(message);
                    throw new RuntimeException(message);
                }
            }*/
            unassignInventory(order, usrId, shipmentHeader);

        }
    }

    private void unassignInventory(Order order, String usrId, ShipmentHeader shipmentHeader) {
        String message = "";
        try {
            Set<ShipmentDetail> sdSet = shipmentHeader.getShipmentDetails();
            AllocatedEvent event = new AllocatedEvent();
            event.setChannel(ChannelConstants.Default);
            event.setBusinessType(BusinessTypeConstants.ORDER_ALLOCATED_RETAIL_CROSS_COMPANY);
            event.setSourceId(TransactionSourceConstants.OMS);
            event.setWarehouse(shipmentHeader.getWarehouseId());
            event.setUser(usrId);
            event.setBusinessNo(shipmentHeader.getShipmentId());
            event.setBusinessDate(new Date());

            for (ShipmentDetail sd : sdSet) {
                AllocatedEventItem item = new AllocatedEventItem();
                item.setLocationType(LocationTypeConstants.INVENTORY_QUALITY_PRODUCT);

                //查询RUID
                PlubasInfo pinfo = pluSplitDao.getIagentProduct(sd.getItemId());
                item.setProductId(pinfo.getRuid());
                item.setProductCode(sd.getItemId());
                item.setQuantity(new Double(sd.getTotalQty()));
                event.getEventItems().add(item);
            }

            itemInventoryAllocateService.unassign(event);
            if (!event.isSuccess()) {
                message = CollectionUtil.printStringList(event.getErrors());
            }

        } catch (Exception e) {
            logger.error("调用库存服务失败，" + message, e);
            //e.printStackTrace();
        }
    }

    private void assignInventory(Order orderhist, String warehouseId, String mdusr) {
        String message = "";

        try {
            Set<OrderDetail> orderdetSet = orderhist.getOrderdets();

            AllocatedEvent event = new AllocatedEvent();
            event.setChannel(ChannelConstants.Default);
            event.setBusinessType(BusinessTypeConstants.ORDER_ALLOCATED_RETAIL_CROSS_COMPANY);
            event.setSourceId(TransactionSourceConstants.OMS);
            event.setWarehouse(warehouseId);
            event.setUser(mdusr);
            event.setBusinessNo(this.generateBusinessNo(orderhist));
            event.setBusinessDate(new Date());

            for (Iterator<OrderDetail> iter = orderdetSet.iterator(); iter.hasNext(); ) {
                OrderDetail orderdet = iter.next();

                Map<String, Object> splitResult = orderhistDao.queryProdNonSuiteInfo(orderdet);
                String isSuite = splitResult.get("ISSUITEPLU").toString();

                boolean isAllNegativeStock=true;
                //如果产品是非套装产品
                if (isSuite.equals("0")) {
                    AllocatedEventItem item = new AllocatedEventItem();
                    item.setLocationType(LocationTypeConstants.INVENTORY_QUALITY_PRODUCT);
                    item.setProductId(new Long(splitResult.get("RUID").toString()));
                    item.setProductCode(splitResult.get("PLUCODE").toString());
                    item.setQuantity(new Double(orderdet.getSpnum() + orderdet.getUpnum()));
                    item.setAllowNegative(isAllNegativeStock);

                    event.getEventItems().add(item);
                } else {
                    //如果产品是套装产品
                    List<Map<String, Object>> suiteItemList = orderSkuSplitService.orderSkuSplit(orderdet);
                    for (Map<String, Object> itemMap : suiteItemList) {
                        //数量
                        Integer num = Integer.parseInt(itemMap.get("qty").toString());
                        AllocatedEventItem item = new AllocatedEventItem();
                        item.setLocationType(LocationTypeConstants.INVENTORY_QUALITY_PRODUCT);
                        item.setProductId(new Long(itemMap.get("ruid").toString()));
                        item.setProductCode(itemMap.get("plucode").toString());
                        item.setQuantity(new Double(num));
                        item.setAllowNegative(isAllNegativeStock);

                        event.getEventItems().add(item);
                    }
                }
            }

            logger.warn("begin assign bussiness id:"+event.getBusinessNo());
            logger.warn("assign items size:"+event.getEventItems().size());
            itemInventoryAllocateService.assign(event);
            logger.warn("end assign bussiness id:"+event.getBusinessNo());
            if (!event.isSuccess()) {
                logger.error("error assign bussiness id:"+event.getBusinessNo());
                message = CollectionUtil.printStringList(event.getErrors());
                throw new RuntimeException(message);
            }
            else
            {
                logger.warn("succ assign bussiness id:"+event.getBusinessNo());
            }
        } catch (Exception e) {
            logger.error("分配库存失败, " + message, e);
            logger.error("error assign bussiness unknown error");
            //e.printStackTrace();
            throw new RuntimeException("分配库存失败:"+e.getMessage());
        }
    }

    private String generateBusinessNo(Order order) {
        String businessNo = order.getOrderid() + String.format("V%03d", order.getRevision());
        return businessNo;
    }


    private List<InventoryAssignInfo> getAssignInfoFromOrder(Order order)
    {
        List<InventoryAssignInfo> inventoryAssignInfoList=new ArrayList<InventoryAssignInfo>();
        for(OrderDetail orderDetail:order.getOrderdets())
        {
            int quantity=0;
            quantity+=(orderDetail.getUpnum()!=null?orderDetail.getUpnum().intValue():0);
            quantity+=(orderDetail.getSpnum()!=null?orderDetail.getSpnum().intValue():0);
            List<InventoryAssignInfo> itemList=getAssignInfoFromProd(orderDetail.getProdid(),orderDetail.getProducttype(),quantity);
            for(InventoryAssignInfo item:itemList)
            {
                int index=inventoryAssignInfoList.indexOf(item);
                if(index>=0)
                {
                    InventoryAssignInfo matchItem= inventoryAssignInfoList.get(index);
                    matchItem.setQuantity(matchItem.getQuantity()+item.getQuantity());
                }
                if(!inventoryAssignInfoList.contains(item))
                {
                    inventoryAssignInfoList.add(item);
                }
            }
        }
        return inventoryAssignInfoList;
    }

    private List<InventoryAssignInfo> getAssignInfoFromProd(String prodId,String prodType,int quantity)
    {
        List<InventoryAssignInfo> inventoryAssignInfoList=new ArrayList<InventoryAssignInfo>();
        String typeName=null;
        if(StringUtils.isNotEmpty(prodType))
        {
            if(!"none".equalsIgnoreCase(prodType))
            {
                typeName=pluSplitDao.searchProductType(prodType);
            }
        }
        List<PlubasInfo> plubasInfoList = plubasInfoService.getPlubasInfos(prodId,null,null,typeName);
        PlubasInfo plubasInfo=null;
        if(plubasInfoList!=null)
        {
            for(PlubasInfo item:plubasInfoList)
            {
                if(StringUtils.isNotEmpty(item.getNcfreename()))
                {
                    if(item.getNcfreename().equals(typeName))
                    {
                        plubasInfo=item;
                        break;
                    }
                }
                else
                {
                    if(StringUtils.isEmpty(typeName))
                    {
                        plubasInfo=item;
                        break;
                    }
                }
            }
        }

        if(plubasInfo==null)
        {
            throw new RuntimeException("该商品暂时没有库存,商品编号为:" + prodId);
        }

        boolean ctlFlag=this.isProdIcControl(plubasInfo.getStatus());
        if(ctlFlag==false)
            return inventoryAssignInfoList;

        if ("0".equals(plubasInfo.getIssuiteplu()))
        {
            InventoryAssignInfo inventoryAssignInfo=new InventoryAssignInfo();
            inventoryAssignInfo.setQuantity(quantity);
            inventoryAssignInfo.setIcControl(ctlFlag);
            inventoryAssignInfo.setScmId(plubasInfo.getPlucode());

            inventoryAssignInfoList.add(inventoryAssignInfo);
        }
        else
        {
            //套装
            List<ProductSuiteType> productSuiteTypeList = pluSplitDao.searchProductSuiteTypeByProdSuiteScmId(plubasInfo.getPlucode());
            for (ProductSuiteType productSuiteType : productSuiteTypeList) {
                //单品不受控判断   //不受控商品
                //PlubasInfo suitePlubasInfo = plubasInfoService.getPlubasInfo(productSuiteType.getProductSuiteTypeId().getProdScmId());

                InventoryAssignInfo inventoryAssignInfo=new InventoryAssignInfo();
                inventoryAssignInfo.setQuantity(quantity*productSuiteType.getProdNum());
                inventoryAssignInfo.setIcControl(ctlFlag);
                inventoryAssignInfo.setScmId(productSuiteType.getProdScmId());
                int index=inventoryAssignInfoList.indexOf(inventoryAssignInfo);
                if(index>=0)
                {
                    InventoryAssignInfo matchItem=inventoryAssignInfoList.get(index);
                    matchItem.setQuantity(matchItem.getQuantity()+inventoryAssignInfo.getQuantity());
                }
                else
                {
                    inventoryAssignInfoList.add(inventoryAssignInfo);
                }
            }
        }
        return inventoryAssignInfoList;
    }

    private List<InventoryAssignInfo> getAssignInfoFromShipment(ShipmentHeader shipmentHeader)
    {
        List<InventoryAssignInfo> inventoryAssignInfoList=new ArrayList<InventoryAssignInfo>();
        for(ShipmentDetail shipmentDetail:shipmentHeader.getShipmentDetails())
        {
            InventoryAssignInfo inventoryAssignInfo=new InventoryAssignInfo();
            inventoryAssignInfo.setQuantity(shipmentDetail.getTotalQty().intValue());
            inventoryAssignInfo.setIcControl(true);
            inventoryAssignInfo.setScmId(shipmentDetail.getItemId());

            int index=inventoryAssignInfoList.indexOf(inventoryAssignInfo);
            if(index>=0)
            {
                InventoryAssignInfo matchItem=inventoryAssignInfoList.get(index);
                matchItem.setQuantity(matchItem.getQuantity()+inventoryAssignInfo.getQuantity());
            }
            else
            {
                inventoryAssignInfoList.add(inventoryAssignInfo);
            }
        }

        return inventoryAssignInfoList;
    }
}
