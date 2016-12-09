package com.chinadrtv.erp.sales.core.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.agent.Parameters;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.tc.core.constant.*;

import com.chinadrtv.erp.tc.core.dao.*;
import com.chinadrtv.erp.sales.core.service.InventoryAgentService;
import com.chinadrtv.erp.tc.core.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.BusinessTypeConstants;
import com.chinadrtv.erp.constant.ChannelConstants;
import com.chinadrtv.erp.constant.LocationTypeConstants;
import com.chinadrtv.erp.constant.TransactionSourceConstants;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.ic.dao.ItemInventoryDao;
import com.chinadrtv.erp.ic.dao.PlubasInfoDao;
import com.chinadrtv.erp.ic.service.ItemInventoryAllocateService;
import com.chinadrtv.erp.model.Acorderlist;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.EdiClear;
import com.chinadrtv.erp.model.ModifyOrderhist;
import com.chinadrtv.erp.model.Ordermangerlog;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.SmppSend;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.OrderHistory;
import com.chinadrtv.erp.model.inventory.AllocatedEvent;
import com.chinadrtv.erp.model.inventory.AllocatedEventItem;
import com.chinadrtv.erp.model.inventory.ItemInventoryChannel;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.model.trade.ShipmentDelivery;
import com.chinadrtv.erp.model.trade.ShipmentDetail;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.constant.model.OrderhistAssignInfo;
import com.chinadrtv.erp.tc.core.constant.model.shipment.RequestScanOutInfo;
import com.chinadrtv.erp.tc.core.constant.model.shipment.ShipmentException;
import com.chinadrtv.erp.tc.core.constant.model.shipment.ShipmentReturnCode;
import com.chinadrtv.erp.tc.core.dto.ShipmentSenderDto;
import com.chinadrtv.erp.tc.core.utils.CollectionUtil;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.ShipmentHeaderService;
import com.chinadrtv.erp.util.PojoUtils;


/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-5
 */
@Service
public class ShipmentHeaderServiceImpl extends GenericServiceImpl<ShipmentHeader, Long> implements ShipmentHeaderService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShipmentHeaderServiceImpl.class);

    private List<String> serviceOrderTypeList;

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

    /*特别判断的送货公司：上海EMS*/
    public static final String COMPANY_EMS = "36";

    @Autowired
    private OrderSkuSplitService orderSkuSplitService;
    @Autowired
    private PhoneDao phoneDao;
    @Autowired
    private OrderhistCompanyAssignService orderhistCompanyAssignService;
    @Autowired
    private AcorderlistDao acorderlistDao;
    @Autowired
    private ModifyOrderhistDao modifyOrderhistDao;
    @Autowired
    private SmppSendDao smppSendDao;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private OrdermangerlogDao ordermangerlogDao;
    @Autowired
    private NamesDao namesDao;
    @Autowired
    private OrderhistService orderhistService;
    @Autowired
    private InventoryAgentService  inventoryAgentService;
    @Autowired
    private OrderHistoryService orderHistoryService;
    @Autowired
    private OrderExtDao orderExtDao;
    @Autowired
    private ShipmentDeliveryDao shipmentDeliveryDao;
    @Autowired
    private ItemInventoryDao itemInventoryDao;
    @Autowired
    private PlubasInfoDao plubasInfoDao;
    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;
    @Autowired
    private EdiClearDao ediClearDao;
    @Autowired
    private PvService pvService;

    @Autowired
    private ShipmentChangeListener shipmentChangeListener;

    @Override
    protected GenericDao<ShipmentHeader, Long> getGenericDao() {
        return shipmentHeaderDao;
    }

    /*
     * 同步取消运单
    * <p>Title: 同步取消运单</p>
    * <p>Description:</p>
    * @param shipmentHeader
    * @return int
    * @throws Exception
    * @see com.chinadrtv.erp.shipment.service.ShipmentHeaderService#cancelWayBill(com.chinadrtv.erp.model.agent.ShipmentHeader)
    */
    public int cancelWayBill(Map<String, Object> params) throws Exception {
        /**
         * 订单取消时，同步取消运单。
         1、更新旧运单状态为取消状态。 调用取消库存服务
         2、判断是否存在旧运单版本，存在则更新运单状态为取消。
         3、立即下传WMS，无窗口期。
         4、记录状态变更历史。
		         输入：订单号、版本
		         输出：更新或者失败，失败，返回错误详细信息。
         */
        if (null == params.get("orderid")) {
            throw new BizException("订单号[orderid]不能为空");
        }
        if (null == params.get("mdusr") || "".equals(params.get("mdusr").toString())) {
            throw new BizException("修改人信息不能为空");
        }
        String orderId = params.get("orderid").toString();
        String modifyUser = params.get("mdusr").toString();

        Order orderhist = orderhistService.getOrderHistByOrderid(orderId);
        if (null == orderhist) {
            throw new BizException("订单[" + orderId + "]不存在");
        }

        //取消运单
        ShipmentHeader sh =this.cancelShipmentHeader(orderhist, modifyUser);
        inventoryAgentService.unreserveInventory(orderhist,sh, modifyUser);

        //Step3 下传WMS
        //wmsShipmentService.cancelInsertWms(orderhist, oldSh);
        ShipmentDelivery shipmentDelivery = new ShipmentDelivery();
        shipmentDelivery.setOrderId(orderId);
        shipmentDelivery.setCrtDate(new Date());
        shipmentDelivery.setIsCancel("1");
        shipmentDelivery.setShipmentId(sh.getShipmentId());
        shipmentDelivery.setCrtUsr(modifyUser);
        shipmentDeliveryDao.saveOrUpdate(shipmentDelivery);

        logger.debug("同步取消运单 finish");

        return 0;
    }

    /*
     * 分配库存
    * <p>Title: assignInventory</p>
    * <p>Description: </p>
    * @param orderhist
    * @return boolean
    * @see com.chinadrtv.erp.tc.service.OrderhistService#assignInventory(com.chinadrtv.erp.model.agent.Order)
    *//*
    public boolean assignInventory(Order orderhist, String mdusr) {
        if(serviceOrderTypeList.contains(orderhist.getOrdertype()))
        {
            return true;
        }
        boolean isSuccess = false;
        String message = "";

        try {
            Set<OrderDetail> orderdetSet = orderhist.getOrderdets();
            OrderhistAssignInfo ohai = orderhistCompanyAssignService.queryDefaultAssignInfo(orderhist);

            AllocatedEvent event = new AllocatedEvent();
            event.setChannel(ChannelConstants.Default);
            event.setBusinessType(BusinessTypeConstants.ORDER_ALLOCATED_RETAIL_CROSS_COMPANY);
            event.setSourceId(TransactionSourceConstants.OMS);
            event.setWarehouse(ohai.getWarehouseId().toString());
            event.setUser(mdusr);
            event.setBusinessNo(this.generateBusinessNo(orderhist));
            event.setBusinessDate(new Date());

            for (Iterator<OrderDetail> iter = orderdetSet.iterator(); iter.hasNext(); ) {
                OrderDetail orderdet = iter.next();

                Map<String, Object> splitResult = orderhistService.queryProdNonSuiteInfo(orderdet);
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
            isSuccess = event.isSuccess();
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
            e.printStackTrace();
            throw new RuntimeException("分配库存失败:"+e.getMessage());
        }

        return isSuccess;
    }*/

    /*
     * 分配库存
    * <p>Title: assignInventory</p>
    * <p>Description: </p>
    * @param orderhist
    * @param warehouseId
    * @param mdusr
    * @return boolean
    * @see com.chinadrtv.erp.tc.service.OrderhistService#assignInventory(com.chinadrtv.erp.model.agent.Order, java.lang.String, java.lang.String)
    */
    /*public boolean assignInventory(Order orderhist, String warehouseId, String mdusr) {
        if(serviceOrderTypeList.contains(orderhist.getOrdertype()))
        {
            return true;
        }
        boolean isSuccess = false;
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

                Map<String, Object> splitResult = orderhistService.queryProdNonSuiteInfo(orderdet);
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
            isSuccess = event.isSuccess();
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
            e.printStackTrace();
            throw new RuntimeException("分配库存失败:"+e.getMessage());
        }

        return isSuccess;
    }*/

    /*
     * 取消分配库存
    * <p>Title: unasignInventory</p>
    * <p>Description: </p>
    * @param orderhist
    * @return boolean
    * @see com.chinadrtv.erp.tc.service.OrderhistService#unasignInventory(com.chinadrtv.erp.model.agent.Order)
    */
    /*public boolean unasignInventory(Order orderhist, String mdusr) {
        if(serviceOrderTypeList.contains(orderhist.getOrdertype()))
        {
            return true;
        }
        boolean isSuccess = false;
        String message = "";

        try {
            Set<OrderDetail> orderdetSet = orderhist.getOrderdets();
            ShipmentHeader sh = this.getShipmentHeaderFromOrderId(orderhist.getOrderid());
            //兼容原有系统
            if (null == sh) {
                return true;
            }

            AllocatedEvent event = new AllocatedEvent();
            event.setChannel(ChannelConstants.Default);
            event.setBusinessType(BusinessTypeConstants.ORDER_ALLOCATED_RETAIL_CROSS_COMPANY);
            event.setSourceId(TransactionSourceConstants.OMS);
            event.setWarehouse(sh.getWarehouseId());
            event.setUser(mdusr);
            event.setBusinessNo(sh.getShipmentId());
            event.setBusinessDate(new Date());

            for (Iterator<OrderDetail> iter = orderdetSet.iterator(); iter.hasNext(); ) {
                OrderDetail orderdet = iter.next();

                Map<String, Object> splitResult = orderhistService.queryProdNonSuiteInfo(orderdet);
                String isSuite = splitResult.get("ISSUITEPLU").toString();

                //如果产品是非套装产品
                if (isSuite.equals("0")) {
                    AllocatedEventItem item = new AllocatedEventItem();
                    item.setLocationType(LocationTypeConstants.INVENTORY_QUALITY_PRODUCT);
                    item.setProductId(new Long(splitResult.get("RUID").toString()));
                    item.setProductCode(splitResult.get("PLUCODE").toString());
                    item.setQuantity(new Double(orderdet.getSpnum() + orderdet.getUpnum()));
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

                        event.getEventItems().add(item);
                    }
                }
            }

            itemInventoryAllocateService.unassign(event);

            isSuccess = event.isSuccess();
            if (!event.isSuccess()) {
                message = CollectionUtil.printStringList(event.getErrors());
            }
        } catch (Exception e) {
            logger.error("取消分配库存失败，" + message, e);
            e.printStackTrace();
        }

        return isSuccess;
    }*/

    /**
     * 取消分配库存
     * @param oldSh
     * @param modifyUser
     * @return void
     */
    /*public boolean unassignInventory(ShipmentHeader oldSh, String modifyUser) {
        boolean success = false;
        if (null != oldSh) {
            //取消分配库存
            String message = "";
            try {
                Set<ShipmentDetail> sdSet = oldSh.getShipmentDetails();
                AllocatedEvent event = new AllocatedEvent();
                event.setChannel(ChannelConstants.Default);
                event.setBusinessType(BusinessTypeConstants.ORDER_ALLOCATED_RETAIL_CROSS_COMPANY);
                event.setSourceId(TransactionSourceConstants.OMS);
                event.setWarehouse(oldSh.getWarehouseId());
                event.setUser(modifyUser);
                event.setBusinessNo(oldSh.getShipmentId());
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

                success = event.isSuccess();
            } catch (Exception e) {
                logger.error("调用库存服务失败，" + message, e);
                e.printStackTrace();
            }
        }

        return success;
    }
*/
    /**
     * 取消旧的运单
     * <p>Title: cancelShipmentHeader</p>
     * <p>Description: </p>
     * @param oh
     * @param modifyUser
     * @see com.chinadrtv.erp.sales.core.service.ShipmentHeaderService#cancelShipmentHeader(com.chinadrtv.erp.model.agent.Order, java.lang.String)
     */
    public ShipmentHeader cancelShipmentHeader(Order oh, String modifyUser) {
        ShipmentHeader shipmentHeader=null;
        ShipmentHeader oldSh = this.getShipmentHeaderFromOrderId(oh.getOrderid());
        if (null != oldSh) {
            shipmentHeader=new ShipmentHeader();
            BeanUtils.copyProperties(oldSh, shipmentHeader);
            oldSh.setLogisticsStatusId(LogisticsStatusConstants.LOGISTICS_CANCEL);
            oldSh.setAccountStatusId(AccountStatusConstants.ACCOUNT_CANCEL);
            oldSh.setMddt(new Date());
            oldSh.setMdusr(modifyUser);
            this.updateShipmentHeader(oldSh);
        }
        return shipmentHeader;
    }

    /**
     * 产生新运单
     * @param orderhist
     * @param isCancel  是否是取消订单产生运单
     * @return int
     * @throws Exception
     */
    public ShipmentHeader generateShipmentHeader(Order orderhist, boolean isCancel) throws Exception {
        /**
         * 订单产生时，生成运单。
         1、订单调用简单承运商规则，分配送货公司，仓库
         2、生成新运单版本号，保存运单表头，状态均为初始状态。
         3、拆分商品为12位商品编码，插入运单表体。
         4、判断是否存在旧运单版本，存在则更新运单状态为取消。
		         输入：订单信息
		         输出：更新或者失败，失败，返回错误详细信息。
         */
        String mdusr = orderhist.getMdusr();
        if (null == mdusr || "".equals(mdusr)) {
            mdusr = orderhist.getCrusr();
        }

        //Step1
        OrderhistAssignInfo oai = orderhistCompanyAssignService.queryDefaultAssignInfo(orderhist);

        //Step2
        //生成新运单
        ShipmentHeader newSh = fillShipmentHeader(orderhist, isCancel, mdusr);
        newSh.setWarehouseId(oai.getWarehouseId().toString());
        newSh.setEntityId(oai.getEntityId());

        return newSh;
    }

    /**
     * 运单重发生成新的运单
     * <p>需要根本库存</p>
     *
     * @param orderhist
     * @return ShipmentHeader
     * @throws Exception
     */
    private ShipmentHeader resendGenerateShipmentHeader(Order orderhist, String entityId, String warehouseId) throws Exception {
        /**
         * 订单产生时，生成运单。
         1、订单调用简单承运商规则，分配送货公司，仓库
         2、生成新运单版本号，保存运单表头，状态均为初始状态。
         3、拆分商品为12位商品编码，插入运单表体。
         4、判断是否存在旧运单版本，存在则更新运单状态为取消。
		         输入：订单信息
		         输出：更新或者失败，失败，返回错误详细信息。
         */
        String mdusr = orderhist.getMdusr();
        if (null == mdusr || "".equals(mdusr)) {
            mdusr = orderhist.getCrusr();
        }

        //Step2
        //生成新运单
        ShipmentHeader newSh = this.fillShipmentHeader(orderhist, false, mdusr);
        newSh.setMailId(orderhist.getMailid());
        newSh.setEntityId(orderhist.getEntityid());
        newSh.setWarehouseId(warehouseId);

        newSh.setAccountStatusId("2");
        newSh.setLogisticsStatusId("0");
        inventoryAgentService.reserveInventory(orderhist,null, warehouseId,mdusr);

        return newSh;
    }

    /*
     * 根据送货公司和仓库地址产生运单
    * <p>Title: generateShipmentHeader</p>
    * <p>Description: </p>
    * @param orderhist
    * @param entityId
    * @param warehouseId
    * @return ShipmentHeader
    * @throws Exception
    * @see com.chinadrtv.erp.tc.service.ShipmentHeaderService#generateShipmentHeader(com.chinadrtv.erp.model.agent.Order, java.lang.String, java.lang.String)
     */
    public ShipmentHeader generateShipmentHeader(Order orderhist, String entityId, String warehouseId) throws Exception {
        /**
         * 订单产生时，生成运单。
         1、订单调用简单承运商规则，分配送货公司，仓库
         2、生成新运单版本号，保存运单表头，状态均为初始状态。
         3、拆分商品为12位商品编码，插入运单表体。
         4、判断是否存在旧运单版本，存在则更新运单状态为取消。
		         输入：订单信息
		         输出：更新或者失败，失败，返回错误详细信息。
         */
        String mdusr = orderhist.getMdusr();
        if (null == mdusr || "".equals(mdusr)) {
            mdusr = orderhist.getCrusr();
        }

        //Step2
        //生成新运单
        ShipmentHeader newSh = fillShipmentHeader(orderhist, false, mdusr);
        newSh.setWarehouseId(warehouseId);
        newSh.setEntityId(entityId);

        return newSh;
    }


    /*
     * 运单重发
    * @Description:
    * @param orderhist
    * @param entityId
    * @param warehouseId
    * @param isInStock		是否是库内改单重发运单
    * @throws Exception
    * @return int
     */
    public ShipmentHeader generateShipmentHeader(Order orderhist, String entityId, String warehouseId, boolean isInStock) throws Exception {
        /**
         * 订单产生时，生成运单。
         1、订单调用简单承运商规则，分配送货公司，仓库
         2、生成新运单版本号，保存运单表头，状态均为初始状态。
         3、拆分商品为12位商品编码，插入运单表体。
         4、判断是否存在旧运单版本，存在则更新运单状态为取消。
		         输入：订单信息
		         输出：更新或者失败，失败，返回错误详细信息。
         */
        String mdusr = orderhist.getMdusr();
        if (null == mdusr || "".equals(mdusr)) {
            mdusr = orderhist.getCrusr();
        }

        //Step2
        //生成新运单
        ShipmentHeader newSh = fillShipmentHeader(orderhist, false, mdusr);
        if (isInStock) {
            newSh.setAccountStatusId(orderhist.getStatus());
            String customerStatus = null == orderhist.getCustomizestatus() ? "0" : orderhist.getCustomizestatus();
            customerStatus = "".equals(customerStatus) ? "0" : customerStatus;
            newSh.setLogisticsStatusId(customerStatus);
        }
        newSh.setWarehouseId(warehouseId);
        newSh.setEntityId(entityId);

        return newSh;
    }

    /**
     * 生成运单
     *
     * @param orderhist
     * @param isCancel  是否为取消订单生成运单
     * @param mdusr
     * @return ShipmentHeader
     * @throws Exception
     * @Description:
     */
    private ShipmentHeader fillShipmentHeader(Order orderhist, boolean isCancel, String mdusr) throws Exception {

        ShipmentHeader newSh = new ShipmentHeader();
        newSh.setOrderId(orderhist.getOrderid());
        newSh.setOrderRefRevisionId(new Long(orderhist.getRevision()));
        newSh.setMailId(orderhist.getMailid());
        newSh.setEntityId(orderhist.getEntityid());

        newSh.setCrdt(new Date());
        newSh.setCrusr(mdusr);
        newSh.setSenddt(orderhist.getSenddt());
        newSh.setFbdt(orderhist.getFbdt());
        newSh.setOutdt(orderhist.getOutdt());
        newSh.setAccdt(orderhist.getAccdt());
        if (isCancel) {
            newSh.setLogisticsStatusId(LogisticsStatusConstants.LOGISTICS_CANCEL);
            newSh.setAccountStatusId(AccountStatusConstants.ACCOUNT_CANCEL);
        } else {
            newSh.setLogisticsStatusId(LogisticsStatusConstants.LOGISTICS_NORMAL);
            newSh.setAccountStatusId(AccountStatusConstants.ACCOUNT_NEW);
        }
        //newSh.setRfoutdat(rfoutdat);
        //newSh.setItemFlag(itemFlag);
        newSh.setOwner(orderhist.getContactid());
        //newSh.setVolume(volume);
        //newSh.setWeight(weight);
        //newSh.setWarehouseId(warehouseId);
        newSh.setProdPrice(orderhist.getProdprice());
        newSh.setMailPrice(orderhist.getMailprice());
        newSh.setTotalPrice(orderhist.getTotalprice());
        newSh.setAccountType("1");

        //Step3
        Set<OrderDetail> orderdetSet = orderhist.getOrderdets();

        Set<ShipmentDetail> sdSet = new HashSet<ShipmentDetail>();
        for (OrderDetail od : orderdetSet) {
            List<Map<String, Object>> sdList = orderSkuSplitService.orderSkuSplit(od);
            for (Iterator<Map<String, Object>> iter = sdList.iterator(); iter.hasNext(); ) {
                Map<String, Object> resultMap = iter.next();
                ShipmentDetail sd = new ShipmentDetail();
                sd.setShipmentHeader(newSh);
                sd.setItemId(resultMap.get("plucode").toString());
                sd.setItemDesc(resultMap.get("pluname").toString());
                sd.setTotalQty(new Long(resultMap.get("qty").toString()));
                sd.setUnitPrice(new BigDecimal(resultMap.get("unitprice").toString()));

                sd.setShipmentLineNum(new Long(null == resultMap.get("linenum") ? "0" : resultMap.get("linenum").toString()));
                sd.setFreeFlag(Integer.parseInt(null == resultMap.get("freeflag") ? "0" : resultMap.get("freeflag").toString()));
                //sd.setAllocationFlag(allocationFlag);
                //sd.setRemark(remark);
                sd.setCarrier(new BigDecimal(null == resultMap.get("postfee") ? "0" : resultMap.get("postfee").toString()));
                sdSet.add(sd);
            }

        }
        newSh.setShipmentDetails(sdSet);

        return newSh;
    }


    /**
    * <p>Title: 重发包查询</p>
    * <p>Description: </p>
    * @param orderid
    * @return shipmentSenderDto
    */
    public ShipmentSenderDto queryWaybill(String orderid) throws Exception {

        Order orderhist = orderhistService.getOrderHistByOrderid(orderid);
        if (null == orderhist) {
            throw new BizException("订单不存在");
        }
        ShipmentHeader sh = this.getShipmentHeaderFromOrderId(orderid);
        ShipmentSenderDto ssDto = null;
        if (null == sh) {
            ssDto = new ShipmentSenderDto();
            ssDto.setOrderId(orderid);
        } else {
            ssDto = (ShipmentSenderDto) PojoUtils.convertPojo2Dto(sh, ShipmentSenderDto.class);
        }

        ssDto.setOrderType(orderhist.getOrdertype());
        String orderTypeName = namesDao.getValueByTidAndId(TcConstants.ORDERTYPE, orderhist.getOrdertype());
        ssDto.setOrderTypeName(orderTypeName);
        ssDto.setWorkerId(orderhist.getCrusr());
        //AgentUser agentUser = userDao.get(orderhist.getCrusr());
        //ssDto.setWorkerName(agentUser.getName());

        //客户
        AddressExt address = orderhist.getAddress();
        if (null == address || null == address.getContactId() || "".equals(address.getContactId().trim())) {
            throw new BizException("客户信息不存在");
        }
        Contact contact = contactDao.get(address.getContactId());
        if (null == contact) {
            throw new BizException("客户信息不存在");
        }
        ssDto.setContactId(address.getAddressId());
        ssDto.setContactName(contact.getName());

        //送货公司
        String companyId = orderhist.getEntityid();
        if (null == companyId || "".equals(companyId.trim())) {
            throw new BizException("送货公司不存在");
        }
        Company company = companyDao.get(companyId);
        if (null == company) {
            throw new BizException("送货公司不存在");
        }
        ssDto.setCompanyId(companyId);
        ssDto.setCompanyName(company.getName());

        //订购方式
        if (null != orderhist.getMailtype()) {
            ssDto.setMailType(orderhist.getMailtype());
            String mailTypeName = namesDao.getValueByTidAndId(TcConstants.MAIL_TYPE_NAMES, orderhist.getMailtype());
            ssDto.setMailTypeName(mailTypeName);
        }

        //订单状态
        if (null != orderhist.getStatus()) {
            ssDto.setOrderStatus(orderhist.getStatus());
            String orderStatusName = namesDao.getValueByTidAndId(TcConstants.ORDER_STATUS, orderhist.getStatus());
            ssDto.setOrderStatusName(orderStatusName);
        }

        //出库状态 CustomizeStatus
        if (null != orderhist.getCustomizestatus()) {
            ssDto.setCustomerStatus(orderhist.getCustomizestatus());
            String customerStatusName = namesDao.getValueByTidAndId(TcConstants.CUSTOMER_STATUS, ssDto.getCustomerStatus());
            ssDto.setCustomerStatusName(customerStatusName);
        }

        //付款方式
        if (null != orderhist.getPaytype()) {
            ssDto.setPayType(orderhist.getPaytype());
            String payTypeName = namesDao.getValueByTidAndId(TcConstants.PAY_TYPE, orderhist.getPaytype());
            ssDto.setPayTypeName(payTypeName);
        }

        //订购时间
        ssDto.setOrderDate(orderhist.getCrdt());
        //出库时间CONFIRMEXPDT
        ssDto.setOutDate(orderhist.getConfirmexpdt());
        //交寄时间
        ssDto.setSendDate(orderhist.getSenddt());

        return ssDto;
    }

    /*
     * 重发运单
    * <p>Title: 重发运单</p>
    * <p>Description: </p>
    * @param shipmentSenderDto
    * @throws Exception
    * @see com.chinadrtv.erp.tc.service.ShipmentHeaderService#resendWaybill(com.chinadrtv.erp.tc.dto.ShipmentSenderDto)
    */
    public int resendWaybill(ShipmentSenderDto shipmentSenderDto)
            throws Exception {
        /**
         *   1、订单是否出库，如果未出库，则失败
	         2、生成新版本运单，界面上确认送货公司
	         3、同时扣减库存
	         4、打重发下传标记
		         展示原有发运单
		         查询条件 orderid, mailid
		
		         如果有两张，选一张，然后重选 送货公司， 执行重发操作，生成新发运单
         */
        Long id = shipmentSenderDto.getId();
        String orderId = shipmentSenderDto.getOrderId();
        //新的送货公司
        String companyId = shipmentSenderDto.getNewCompanyId();
        Company company = companyDao.getCompany(companyId);
        if (null == company) {
            throw new BizException("承运商不存在");
        }
        if (null == company.getWarehouseId()) {
            throw new BizException("承运商对应仓库不存在");
        }
        String mdusr = shipmentSenderDto.getMdusr();

        Order orderhist = orderhistService.getOrderHistByOrderid(orderId);
        String customizeStatus = orderhist.getCustomizestatus();

        String status = null == orderhist.getStatus() ? "" : orderhist.getStatus();
        boolean couldResend = false;
        if (status.equals(AccountStatusConstants.ACCOUNT_TRANS) || status.equals(AccountStatusConstants.ACCOUNT_REJECTION)) {
            couldResend = true;
        }

        if (!couldResend) {
            throw new BizException("只有状态为[分拣、拒收]的订单可重发");
        }

        //Step1
        //如果已出库
        boolean isOut = orderhistService.judgeOrderStatus(customizeStatus);
        if (!isOut) {
            throw new BizException("订单未出库");
        }

        /*
         * update iagent.orderhist a set a.mailid = '', a.customizestatus = '0', a.status = 2, a.confirmexpdt = null, a.result = 1 
        where a.orderid = p_docno;
         */
        
        orderhist.setMailtype(company.getMailtype());
        orderhist.setMailid(null);
        orderhist.setCustomizestatus("0");
        orderhist.setStatus("2");
        orderhist.setMddt(new Date());
        orderhist.setMdusr(mdusr);
        orderhist.setIsassign("2");
        orderhist.setConfirmexpdt(null);
        orderhist.setResult("1");
        Integer revision = orderhist.getRevision();
        orderhist.setRevision(revision + 1);
        orderhistService.saveOrUpdate(orderhist);

        OrderHistory orderHistory = orderhistService.insertTcHistory(orderhist);

        orderExtDao.insertOrUpdateByOrderhist(orderhist, company.getWarehouseId().toString());

        String shipmentId = null;
        //如果现有程序则生成发运单，锁库
        if (null != id) {
            //取消老运单，不回收库存
            ShipmentHeader oldSh = this.getShipmentHeaderFromOrderId(orderhist.getOrderid());
            if (null != oldSh) {
                oldSh.setLogisticsStatusId(LogisticsStatusConstants.LOGISTICS_CANCEL);
                oldSh.setAccountStatusId(AccountStatusConstants.ACCOUNT_CANCEL);
                oldSh.setMddt(new Date());
                oldSh.setMdusr(mdusr);
                this.updateShipmentHeader(oldSh);
            }

            //再生成
            ShipmentHeader sh = this.resendGenerateShipmentHeader(orderhist, company.getCompanyid(), company.getWarehouseId().toString());
            sh.setOrderHistory(orderHistory);
            sh = this.addOrUpdateShipmentHeader(sh);
            shipmentId = sh.getShipmentId(); 
        }

        //Step4
        ShipmentDelivery shipmentDelivery = new ShipmentDelivery();
        shipmentDelivery.setOrderId(orderId);
        shipmentDelivery.setCrtDate(new Date());
        shipmentDelivery.setIsCancel("0");
        shipmentDelivery.setCrtUsr(mdusr);
        shipmentDelivery.setShipmentId(shipmentId);
        shipmentDeliveryDao.saveOrUpdate(shipmentDelivery);
        
        logger.info("订单["+orderId+"]重发包生成新运单["+shipmentId+"] ");

        return 0;
    }


    /**
     * SR3.6_1.5 更新运单的配送公司
     *
     * @param params
     * @return	Integer
     * @throws Exception
     */
    public int updateShipmentEntity(Map<String, Object> params) throws Exception {
        /**
         * 提供单笔订单明细的更新功能。

		         送货公司更新如果在WMS中实现，这种模式只能直接修改发运单，不生成新的发运单（这块和之前确认的更换送货公司在OMS实现有差异）
		         此外仓库（warehouse）你看一下是否有必要建立。
		         目前这个在wms实现送货公司更新在今后上线“承运商管理”后需要重构，在OMS系统中实现。
		
		   wms中实现发运单更新送货公司逻辑如下：
		
		         接口导入参数：
		         运单号，新送货公司，新出库仓库id
		         输出参数：
		         1、true 或false
		         2、true_msg或False_msg的信息。
		
		         处理逻辑：
		         1、 如果新的出库仓库和原订单的出库仓库不一致，需要判断此订单的商品在“新出库仓库”是否有货（初期在不上线IC的情况下可忽略此判断）
		         2、 如果反馈新仓库无库存，着返回“目标的仓库无库存无法满足此转单”，反馈false，不给转单。
		         3、 如果有库存，完成转单，并替换发运单上新承运商和新出库仓库。
		
		         成功后需要更新的表，
		         Order，entityid，mailtype，
		
		         orderext，WAREHOUSEUID_EXT，WAREHOUSENAMEEXT
		
		         shipment_header，entityid，warehouse_id
		
		         1、库内换送货公司，直接替换发运单表头记录，并记录流水；
		         2、出库后同库换送货公司，直接替换发运单表头的记录，生成记录流水，
		         再生成一负一正的结算记录；
		         3、出库后跨库换送货公司， 待商定。
		         4、记录状态变更历史。
		
		         替换更换送货公司原有接口，ESB服务已存在，需重构。
         */
        if (null == params.get("orderId")) {
            throw new BizException("订单编号[orderId] 不能为空");
        }
        if (null == params.get("newentityId")) {
            throw new BizException("送货公司[newentityId] 不能为空");
        }
        if (null == params.get("newwarehouseId")) {
            throw new BizException("仓库ID[newwarehouseId] 不能为空");
        }
        if (null == params.get("mdusr")) {
            throw new BizException("更新人[mdusr] 不能为空");
        }
        if (null == params.get("shipmentId")) {
            throw new BizException("发运单号[shipmentId] 不能为空");
        }
        String orderId = params.get("orderId").toString();
        String entityId = params.get("newentityId").toString();
        String warehouseId = params.get("newwarehouseId").toString();
        String mdusr = params.get("mdusr").toString();
        String shipmentId = params.get("shipmentId").toString();

        Order orderhist = orderhistService.getOrderHistByOrderid(orderId);
        if (null == orderhist) {
            throw new BizException("订单[" + orderId + "]不存在");
        }

        ShipmentHeader oldSh = this.getByShipmentId(shipmentId);

        //新程序有发运单
        if (null != oldSh) {
            // 如果运单已经出库
            //(修改原运单，不生成新运单)：修改：送货公司，仓库，面单号
            if (oldSh.getLogisticsStatusId().equals(ShipmentLogisticsStatusConstants.LOGISTICS_OUT)) {

                this.updateOrderByParam(orderhist, entityId, mdusr);
                OrderHistory orderHistory = orderhistService.insertTcHistory(orderhist);

                oldSh.setMddt(new Date());
                oldSh.setMdusr(mdusr);
                oldSh.setEntityId(entityId);
                ShipmentHeader sh = this.updateShipmentHeader(oldSh);
                sh.setOrderHistory(orderHistory);

                //下传WMS
                ShipmentDelivery shipmentDelivery = new ShipmentDelivery();
                shipmentDelivery.setOrderId(orderId);
                shipmentDelivery.setCrtDate(new Date());
                shipmentDelivery.setIsCancel("0");
                shipmentDelivery.setShipmentId(sh.getShipmentId());
                shipmentDelivery.setCrtUsr(mdusr);
                shipmentDeliveryDao.saveOrUpdate(shipmentDelivery);

                //如果运单未出库
                //(修改原运单物流状态为取消，生成新运单)：修改：送货公司，仓库
            } else if (oldSh.getLogisticsStatusId().equals(ShipmentLogisticsStatusConstants.LOGISTICS_NORMAL)) {
                Company company = companyDao.get(entityId);
                orderhist.setMailtype(company.getMailtype());
                Integer revision = orderhist.getRevision() + 1;
                orderhist.setRevision(revision);
                orderhist.setMdusr(mdusr);
                orderhist.setMddt(new Date());
                orderhist.setEntityid(entityId);
                orderhist.setIsassign("2");
                orderhistService.saveOrUpdate(orderhist);

                //新仓库锁库
                try {
                    inventoryAgentService.reserveInventory(orderhist,oldSh,warehouseId,mdusr);
                } catch (Exception e) {
                    logger.error(e.getMessage(),e); //e.printStackTrace();
                }

                OrderHistory orderHistory = orderhistService.insertTcHistory(orderhist);

                //orderExtDao.insertOrUpdateByOrderhist(orderhist, company.getWarehouseId().toString());

                //还原旧仓库库存、取消旧运单
                this.cancelShipmentHeader(orderhist, mdusr);
                //生成新运单
                ShipmentHeader sh = this.generateShipmentHeader(orderhist, entityId, warehouseId, true);
                sh.setOrderHistory(orderHistory);
                this.addOrUpdateShipmentHeader(sh);
            } else {
                throw new BizException("无效的运单状态");
            }
        //无运单的老订单
        } else {
            this.updateOrderByParam(orderhist, entityId, mdusr);
            orderhistService.insertTcHistory(orderhist);
        }

        return 0;
    }

    private void updateOrderByParam(Order orderhist, String entityId, String mdusr) {
        Company company = companyDao.get(entityId);
        //更新订单
        orderhist.setMailtype(company.getMailtype());
        orderhist.setEntityid(entityId);
        orderhist.setMddt(new Date());
        orderhist.setMdusr(mdusr);
        orderhist.setIsassign("2");
        orderhistService.saveOrUpdate(orderhist);
        
        //orderExtDao.insertOrUpdateByOrderhist(orderhist, company.getWarehouseId().toString());
    }

    /**
     * 判断是否缺货
     *
     * @param orderhist
     * @return boolean    true 缺货， false 有余货
     * @throws
     * @Description:
     */
    @SuppressWarnings("unused")
	@Deprecated
    private boolean judgeStock(Order orderhist, String warehouseId) throws Exception {

        Set<OrderDetail> odSet = orderhist.getOrderdets();
        if (null == odSet || 0 == odSet.size()) {
            throw new BizException("无订单明细");
        }
        for (OrderDetail od : odSet) {
            List<Map<String, Object>> splitList = orderSkuSplitService.orderSkuSplit(od);
            for (Map<String, Object> splitMap : splitList) {
                String plucode = splitMap.get("plucode").toString();
                Long ruid = new Long(splitMap.get("ruid").toString());
                Long qty = new Long(splitMap.get("qty").toString());

                ItemInventoryChannel iic = itemInventoryDao
                        .getCacheItemInventory(ChannelConstants.Default, warehouseId, "1", ruid);

                //库存量大于订单量
                if ((null == iic ? 0 : iic.getOnHandQty()) > qty) {
                    return false;
                    //如果库存量小于订单量
                } else {
                    //允许负库存的商品
                    PlubasInfo info = plubasInfoDao.getPlubasInfo(plucode);
                    if (null != info) {
                       return inventoryAgentService.isProdIcControl(info.getStatus());
                    }
                }
            }
        }

        return true;
    }

    /*
     * 根据shipmentId 获取 ShipmentHeader
    * <p>Title: 根据shipmentId 获取 ShipmentHeader</p>
    * <p>Description: </p>
    * @param shipmentId
    * @return
    * @see com.chinadrtv.erp.shipment.service.ShipmentService#getByShipmentId(java.lang.Long)
    */
    public ShipmentHeader getByShipmentId(String shipmentId) {
        return shipmentHeaderDao.getByShipmentId(shipmentId);
    }


    /*
     * 产生运单
    * <p>Title: generateWaybill</p>
    * <p>Description: </p>
    * @param orderhist
    * @param b
    * @see com.chinadrtv.erp.tc.service.ShipmentHeaderService#generateWaybill(com.chinadrtv.erp.model.agent.Order, boolean)
    */
    public void generateWaybill(Order orderhist, boolean isCancel) throws Exception {
        OrderHistory orderHistory = orderHistoryService.insertTcHistory(orderhist);
        ShipmentHeader sh = this.generateShipmentHeader(orderhist, isCancel);
        sh.setOrderHistory(orderHistory);
        this.addOrUpdateShipmentHeader(sh);
    }

    /**
     * <p>Title: 生成库存业务单号</p>
     * <p>Description: </p>
     *
     * @param order
     * @return String
     */
    public String generateBusinessNo(Order order) {
        String businessNo = order.getOrderid() + String.format("V%03d", order.getRevision());
        return businessNo;
    }

    @Autowired(required=false)
    @Qualifier("smsMsgSuffix")
    private String smsMessageSuffix;

    /**
     * 运单出库扫描
     *
     * @param requestScanOutInfo
     * @throws Exception
     */
    public void scanOutShipment(RequestScanOutInfo requestScanOutInfo) throws Exception {
        //1、检查运单客户资料信息、订单信息是否完整。
        //2、判断运单版本是否为最新版本，判断是否有修改请求（地址计数器）。
        //3、更新订单、运单状态为出库扫描完成状态，面单号为上传面单号。
        //4、发送出库扫描短信（异步）。
        //5、记录状态变更历史。

        logger.info("orderId:" + requestScanOutInfo.getOrderId() + " mailId:" + requestScanOutInfo.getMailId() + " version:" + requestScanOutInfo.getRevision() + " entity:" + requestScanOutInfo.getCarrier());

        //兼容老数据
        if (requestScanOutInfo.getRevision() != null && requestScanOutInfo.getRevision().intValue() < 0) {
            requestScanOutInfo.setRevision(null);
        }
        if (requestScanOutInfo.getMailId() == null || "".equals(requestScanOutInfo.getMailId())) {
            throw new ShipmentException(new ShipmentReturnCode(ShipmentCode.FIELD_INVALID, "订单号：" + requestScanOutInfo.getOrderId() + " 没有提供邮件号"));
        }
        requestScanOutInfo.setMailId(requestScanOutInfo.getMailId().toUpperCase());

        //老数据
        ShipmentHeader shipmentHeader = null;
        if (requestScanOutInfo.getRevision() != null) {
            shipmentHeader = this.getShipmentHeaderFromOrderId(requestScanOutInfo.getOrderId());
            if (shipmentHeader == null) {
                //现在都有运单
                throw new ShipmentException(new ShipmentReturnCode(ShipmentCode.SHIPMENT_NOT_FOUND, "订单号：" + requestScanOutInfo.getOrderId() + " 没有对应的运单"));
            } else if (shipmentHeader.getShipmentDetails() == null || shipmentHeader.getShipmentDetails().size() == 0) {
                throw new ShipmentException(new ShipmentReturnCode(ShipmentCode.NOT_DETAILS, "订单号：" + requestScanOutInfo.getOrderId() + " 对应运单没有明细"));
            }
        }

        Order orderhist = orderhistService.getOrderHistByOrderid(requestScanOutInfo.getOrderId());
        if (orderhist == null) {
            throw new ShipmentException(new ShipmentReturnCode(ShipmentCode.ORDER_NOT_FOUND, "订单号：" + requestScanOutInfo.getOrderId() + " 未找到"));
        } else {
            if (shipmentHeader != null) {
                Long ll2 = shipmentHeader.getOrderRefRevisionId();
                Integer refVersion = -1;
                if (ll2 != null) {
                    refVersion = ll2.intValue();
                }
                if (!refVersion.equals(orderhist.getRevision())) {
                    throw new ShipmentException(new ShipmentReturnCode(ShipmentCode.ORDER_HAVE_MODIFY, "订单号：" + requestScanOutInfo.getOrderId() + " 已发生更改"));
                }
                //new code start
                if(ll2 != null)
                {
                    if(!ll2.equals(requestScanOutInfo.getRevision()))
                    {
                        throw new ShipmentException(new ShipmentReturnCode(ShipmentCode.ORDER_HAVE_MODIFY, "订单号：" + requestScanOutInfo.getOrderId() + " 已发生更改"));
                    }
                }
                //new code end
            }
        }

        ShipmentReturnCode shipmentReturnCode = this.checkOrderOnScanOut(orderhist);
        boolean bRetryScan=false;
        //抛出runtime异常
        if (shipmentReturnCode != null && shipmentReturnCode.getCode() != null && !ShipmentCode.SUCC.equals(shipmentReturnCode.getCode())) {
            if(ShipmentCode.ORDER_IS_OUT.equals(shipmentReturnCode.getCode()))
            {
                bRetryScan=this.isCanOrderRescan(orderhist,requestScanOutInfo.getMailId().toUpperCase());
            }
            if(bRetryScan==false)
                throw new ShipmentException(shipmentReturnCode);
        }

        //兼容老版本订单检查
        //兼容老订单判断 --新老订单都需要判断
        //if(shipmentHeader==null)
        {
            shipmentReturnCode=checkOriginalOrderhist(orderhist,requestScanOutInfo);
            if(shipmentReturnCode!=null&&shipmentReturnCode.getCode()!=null&&!ShipmentCode.SUCC.equals(shipmentReturnCode.getCode()))
            {
                throw new ShipmentException(shipmentReturnCode);
            }
        }

        if(bRetryScan==false)
        {
            if (!"2".equals(orderhist.getPaytype()) && !isNotSendOrderType(orderhist.getOrdertype())) {
                boolean bHavePhone = false;
                List<Phone> phoneList = null;
                if (orderhist.getGetcontactid() != null) {
                    phoneList = phoneDao.findList("from Phone where contactid=:contactId", new ParameterString("contactId", orderhist.getGetcontactid()));
                    //phoneList = phoneDao.findPhoneListFromContactAndType(orderhist.getGetcontactid(),4);
                    if (phoneList != null && phoneList.size() > 0) {
                        bHavePhone = true;
                    }
                }
                // 如果没有联系人或者电话，那么报错
                if (bHavePhone == false) {
                    throw new ShipmentException(new ShipmentReturnCode(ShipmentCode.ORDER_NOT_CONTACT_INFO, "订单：" + requestScanOutInfo.getOrderId() + " 没有收货人或者电话信息"));
                }
                String phoneNumber = getGetContactPhoneFromOrder(orderhist, phoneList);
                if (phoneNumber != null) {
                    String message;
                    if ("98".equals(orderhist.getOrdertype())) {
                        message = "尊敬的奥雅会员：您订购的商品已出库，邮件号为 " + requestScanOutInfo.getMailId() + "。如您满意请给5星好评，如有问题请及时联系旺旺客服。";
                    } else {
                    if("162".equals(orderhist.getOrdertype()))
                    {
                        message="您好，您的订单"+ orderhist.getOrderid() +"已在发货途中，请耐心等待，并保持电话的畅通，如有查询或售后问题，请致电我司客服400-669-8888。";
                    }
                    else
                    {
                        final String[] orderTypeTos = new String[]{"100", "103", "104", "106", "107", "108", "109", "110", "121", "122", "123", "127"};
                        boolean bFind = false;
                        for (String orderTypeTo : orderTypeTos) {
                            if (orderTypeTo.equals(orderhist.getOrdertype())) {
                                bFind = true;
                                break;
                            }
                        }
                        if (bFind == true) {
                            message = getSmpMessage(orderhist.getOrdertype(), orderhist.getEntityid(), requestScanOutInfo.getMailId());
                        } else {
                            message = "尊敬的顾客:您订购的商品已发出，订单号为<" + requestScanOutInfo.getOrderId() + "> ，如有疑问请拨打4008886226。";
                        }
                    }
                }
                    SmppSend smppSend = new SmppSend();
                    smppSend.setTo(phoneNumber);
                    smppSend.setMessage(message + (smsMessageSuffix!=null?smsMessageSuffix:""));
                    smppSend.setFlag(0);
                    smppSend.setOrderid(orderhist.getOrderid());
                    smppSend.setOperDt(new Date());
                    smppSendDao.save(smppSend);
                }
            }

            orderhist.setMailid(requestScanOutInfo.getMailId().toUpperCase());
            orderhist.setConfirmexpdt(new Date());
            orderhist.setCustomizestatus(ShipmentLogisticsStatusConstants.LOGISTICS_OUT);
            //orderhist.setMddt(new Date());
            //orderhist.setMdusr(requestScanOutInfo.getUser());

            orderhistService.saveOrUpdate(orderhist);

            if (shipmentHeader != null) {
                shipmentHeader.setLogisticsStatusId(ShipmentLogisticsStatusConstants.LOGISTICS_OUT);
                shipmentHeader.setMailId(requestScanOutInfo.getMailId());
                shipmentHeader.setMddt(new Date());
                shipmentHeader.setMdusr(requestScanOutInfo.getUser());

                this.updateShipmentHeader(shipmentHeader);
            }
        }

        Ordermangerlog ordermangerlog = new Ordermangerlog();
        ordermangerlog.setOpttyp(2);
        ordermangerlog.setOptdat(new Date());
        ordermangerlog.setOptpsn(requestScanOutInfo.getUser());
        ordermangerlog.setOrderid(requestScanOutInfo.getOrderId());
        ordermangerlog.setMail(requestScanOutInfo.getMailId());
        ordermangerlogDao.save(ordermangerlog);
        //insert into ordermangerlog(ruid,opttyp,optdat,optpsn,orderid,mail)
        //values(sq_ordermangerlog.nextval,2,sysdate,P_Psn,v_orderhistrow.orderid,nvl(v_orderhistrow.mailid,'0'));
    }

    private String getSmpMessage(String orderType, String sComapny, String mailId) {
        String sContext = "尊敬的客户，您在";
        if ("100".equals(orderType)) {
            sContext += "【天猫益尔健旗舰店】";
        } else if ("103".equals(orderType) || "127".equals(orderType)) {
            sContext += "【天猫橡果国际旗舰店】";
        } else if ("104".equals(orderType)) {
            sContext += "【天猫好记星旗舰店】";
        } else if ("106".equals(orderType)) {
            sContext += "【亚马逊橡果旗舰店】";
        } else if ("107".equals(orderType)) {
            sContext += "【QQ商城益尔健旗舰店】";
        } else if ("108".equals(orderType)) {
            sContext += "【QQ商城好记星旗舰店】";
        } else if ("109".equals(orderType)) {
            sContext += "【当当网橡果旗舰店】";
        } else if ("110".equals(orderType)) {
            sContext += "【1号店益尔健旗舰店】";
        } else if ("123".equals(orderType)) {
            sContext += "【卓越亚马逊橡果旗舰店】";
        }

        String sComp = null;
        if ("107".equals(sComapny)) {
            //茶马古道（快递）
            sComp = "申通";
        } else if ("6".equals(sComapny)) {
            //上海21CN商城
            sComp = "申通";
        } else if ("77".equals(sComapny)) {
            //淘宝网
            sComp = "EMS";
        } else
            sComp = "";

        if ("121".equals(orderType)) {
            sContext = "尊敬的会员：您订购的宝贝已搭乘" + sComp + "快递，飞向您的城市，邮件号" + mailId + "，有问题请联系4006601500【奥雅化妆品旗舰店】";
        } else if ("122".equals(orderType)) {
            sContext = "尊敬的会员：您订购的宝贝已搭乘" + sComp + "快递，飞向您的城市，邮件号" + mailId + "，有问题请联系4006601500【橡果国际品牌直营店】";
        } else {
            sContext += "订购的商品已由";
            sContext += sComp;
            sContext += "快递发出，邮件号为";
            sContext += mailId;
            sContext += "，如有问题请联系店铺客服";
        }
        return sContext;
    }


    @Autowired
    private UserBpmTaskService userBpmTaskService;

    /**
     * 订单扫描出库前检查
     *
     * @param orderhist
     * @return
     */
    private ShipmentReturnCode checkOrderOnScanOut(Order orderhist) {
        ShipmentReturnCode shipmentReturnCode = null;
        if ("4".equals(orderhist.getMailtype())
                && ("80".equals(orderhist.getOrdertype()) || "81".equals(orderhist.getOrdertype()))) {
            shipmentReturnCode = new ShipmentReturnCode();
            shipmentReturnCode.setCode(ShipmentCode.ORDER_SHOULD_NOT_SCANOUT);
            shipmentReturnCode.setDesc("订单号：" + orderhist.getOrderid() + " 不符合扫描出库");
            //TODO:
            //insert into acoapp_cntrpbank.ordercancelapplication(tuid, orderid, applicationreason, returnreason, status, apppsn, appdate)
            //values(acoapp_cntrpbank.sq_ordercancelapplication.nextval,P_OrderNo,'订单被取消或完成', '后台取消',2, 'wmsintf', sysdate);
            return shipmentReturnCode;
        }

        List<Acorderlist> acorderlistList = acorderlistDao.findAcorderListFromOrderId(orderhist.getOrderid());
        if (acorderlistList != null && acorderlistList.size() > 0) {
            shipmentReturnCode = new ShipmentReturnCode();
            shipmentReturnCode.setCode(ShipmentCode.ORDER_EXIT_ACORDER);
            shipmentReturnCode.setDesc("订单号：" + orderhist.getOrderid() + " 没有及时取消加定");
            return shipmentReturnCode;
        }

        List<ModifyOrderhist> modifyOrderhistList = modifyOrderhistDao.findModifyFromOrderId(orderhist.getOrderid());
        if (modifyOrderhistList != null && modifyOrderhistList.size() > 0) {
            shipmentReturnCode = new ShipmentReturnCode();
            shipmentReturnCode.setCode(ShipmentCode.ORDER_EXIT_MODIFY);
            shipmentReturnCode.setDesc("订单号：" + orderhist.getOrderid() + " 存在未处理的修改请求");
            return shipmentReturnCode;
        }

        if ("1".equals(orderhist.getCustomizestatus())
                || ("0".equals(orderhist.getStatus()) || "1".equals(orderhist.getStatus()) || "7".equals(orderhist.getStatus()) || "8".equals(orderhist.getStatus()))) {
            shipmentReturnCode = new ShipmentReturnCode();
            shipmentReturnCode.setCode(ShipmentCode.ORDER_HAVE_CANCELED);
            shipmentReturnCode.setDesc("订单号：" + orderhist.getOrderid() + " 已取消");
            return shipmentReturnCode;
        }

        int customerStatusFlag = 0;
        if (StringUtils.isNotEmpty(orderhist.getCustomizestatus())) {
            try {
                customerStatusFlag = Integer.parseInt(orderhist.getCustomizestatus());
            } catch (Exception exp) {

            }
        }

        if ((orderhist.getCustomizestatus() != null && !"".equals(orderhist.getCustomizestatus()) && customerStatusFlag >= 2)
                || "5".equals(orderhist.getStatus())) {
            shipmentReturnCode = new ShipmentReturnCode();
            shipmentReturnCode.setCode(ShipmentCode.ORDER_IS_OUT);
            shipmentReturnCode.setDesc("订单号：" + orderhist.getOrderid() + " 已出库，不可重复");
            return shipmentReturnCode;
        }

        if ((orderhist.getCustomizestatus() != null && !"".equals(orderhist.getCustomizestatus()) && customerStatusFlag >= 2)
                || "6".equals(orderhist.getStatus())) {
            shipmentReturnCode = new ShipmentReturnCode();
            shipmentReturnCode.setCode(ShipmentCode.ORDER_HAVE_REJECTED);
            shipmentReturnCode.setDesc("订单号：" + orderhist.getOrderid() + " 订单拒收，不能出库");
            return shipmentReturnCode;
        }

        //新版本的检查修改请求
        List<UserBpmTask> userBpmTaskList = userBpmTaskService.queryUnterminatedOrderChangeTask(orderhist.getOrderid());
        if(userBpmTaskList!=null&&userBpmTaskList.size()>0)
        {
            shipmentReturnCode = new ShipmentReturnCode();
            shipmentReturnCode.setCode(ShipmentCode.ORDER_EXIT_MODIFY);
            shipmentReturnCode.setDesc("订单号：" + orderhist.getOrderid() + " 存在未处理的修改请求");
            return shipmentReturnCode;
        }

        return shipmentReturnCode;
    }

    private boolean isCanOrderRescan(Order orderhist, String mailId)
    {
        int customerStatusFlag = 0;
        if (StringUtils.isNotEmpty(orderhist.getCustomizestatus())) {
            try {
                customerStatusFlag = Integer.parseInt(orderhist.getCustomizestatus());
            } catch (Exception exp) {

            }
        }
        if(AccountStatusConstants.ACCOUNT_TRANS.equals(orderhist.getStatus())&&customerStatusFlag==2)
        {
            if(StringUtils.isNotBlank(mailId)&&mailId.equals(orderhist.getMailid()))
                return true;
        }

        return false;
    }

    private String getGetContactPhoneFromOrder(Order orderhist, List<Phone> phoneList) {
        if (orderhist.getGetcontactid() != null) {
            if (phoneList == null)
                phoneList = phoneDao.findPhoneListFromContactAndType(orderhist.getGetcontactid(), 4);
            else {
                List<Phone> phoneList1 = new ArrayList<Phone>();
                for (Phone phone : phoneList) {
                    if ("4".equals(phone.getPhonetypid())) {
                        phoneList1.add(phone);
                    }
                }

                phoneList = phoneList1;
            }

            if (phoneList != null) {
                //首先找主要电话
                for (Phone phone : phoneList) {
                    if ("Y".equals(phone.getPrmphn())) {
                        String phoneNumber = getValidPhoneNumber(phone.getPhn2());
                        if (phoneNumber != null)
                            return phoneNumber;
                    }
                }

                //找有电话的
                for (Phone phone : phoneList) {
                    String phoneNumber = getValidPhoneNumber(phone.getPhn2());
                    if (phoneNumber != null)
                        return phoneNumber;
                }
            }
        }

        return null;
    }

    private String getValidPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            if (phoneNumber.length() == 12 && phoneNumber.startsWith("0")) {
                return phoneNumber.substring(1);
            } else if (phoneNumber.length() == 11 && phoneNumber.startsWith("1")) {
                return phoneNumber;
            }
        }
        return null;
    }

    private boolean isNotSendOrderType(String orderType) {
        /*if (notSendSmpOrderTypeList != null) {
            for (String notOrderType : this.notSendSmpOrderTypeList) {
                if (notOrderType.equals(orderType))
                    return true;
            }
        } */
        final String[] notSendOrderTypes = new String[]{"14", "15", "32", "33", "35", "36", "37", "38", "49", "50", "51", "52", "53", "56", "57",
                "63", "64", "65", "66", "69", "70", "72", "74", "75", "76", "77", "79", "80", "83", "86",
                "88", "89", "90", "91", "92", "93", "95", "96", "97", "62",
                "1", "45", "46", "47", "48", "2", "39", "40", "41", "29", "42", "43", "44", "67", "87", "68",
                "115", "124","239","245","246"};
        for (String item : notSendOrderTypes) {
            if (item.equals(orderType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查老订单数据
     * - 目前检查订单的送货公司、mailtype、商品价格、订单价格
     * @param orderhist
     * @param requestScanOutInfo
     * @return
     */
    private ShipmentReturnCode checkOriginalOrderhist(Order orderhist, RequestScanOutInfo requestScanOutInfo)
    {
        ShipmentReturnCode shipmentReturnCode=null;
        if(StringUtils.isNotEmpty(requestScanOutInfo.getMailType()))
        {
            if(!requestScanOutInfo.getMailType().equals(orderhist.getMailtype()))
            {
                logger.error("mailtype error:"+orderhist.getMailtype()+"-"+requestScanOutInfo.getMailType());
                shipmentReturnCode=new ShipmentReturnCode(ShipmentCode.ORDER_OLD_ENTITY_NOT_MATCH, "此包裹的订购方式与iagent的不一致，请检查iagent相关订单");
                return shipmentReturnCode;
            }
        }
        if(StringUtils.isNotEmpty(requestScanOutInfo.getTotalPrice()))
        {
            BigDecimal totalPrice=new BigDecimal(requestScanOutInfo.getTotalPrice());
            if(totalPrice.compareTo(orderhist.getTotalprice())!=0)
            {
                logger.error("totalprice error:"+orderhist.getTotalprice()+"-"+totalPrice);
                shipmentReturnCode=new ShipmentReturnCode(ShipmentCode.ORDER_OLD_PRICE_NOT_MATCH, "此包裹的金额与iagent的不一致，请检查iagent相关订单");
                return shipmentReturnCode;
            }
        }
        if(StringUtils.isNotEmpty(requestScanOutInfo.getProdPrice()))
        {
            BigDecimal prodPrice=new BigDecimal(requestScanOutInfo.getProdPrice());
            if(prodPrice.compareTo(orderhist.getProdprice())!=0)
            {
                logger.error("prodprice error:"+orderhist.getProdprice()+"-"+prodPrice);
                shipmentReturnCode=new ShipmentReturnCode(ShipmentCode.ORDER_OLD_PRICE_NOT_MATCH, "此包裹的金额与iagent的不一致，请检查iagent相关订单");
                return shipmentReturnCode;
            }
        }

        if(StringUtils.isNotEmpty(requestScanOutInfo.getCarrier()))
        {
            String companyName="";
            if(orderhist.getEntityid()!=null)
            {
                Company company=companyDao.getCompany(orderhist.getEntityid());
                if(company!=null)
                {
                    companyName=company.getName();
                }
            }
            if(StringUtils.isNotEmpty(companyName))
            {
                companyName=companyName.trim();
            }
            if(!requestScanOutInfo.getCarrier().equals(companyName))
            {
                logger.error("carrier error:"+companyName+"-"+requestScanOutInfo.getCarrier());
                shipmentReturnCode=new ShipmentReturnCode(ShipmentCode.ORDER_OLD_ENTITY_NOT_MATCH, "此包裹的送货公司信息与iagent的不一致，请检查iagent相关订单");
                return shipmentReturnCode;
            }
        }
        return null;
    }
    

    public ShipmentHeader updateShipmentHeader(ShipmentHeader shipmentHeader) {
        logger.debug("begin update ShipmentHeader");

        //logger.debug("end update ShipmentHeader");

        return this.shipmentHeaderDao.updateShipmentHeader(shipmentHeader);
    }

    public ShipmentHeader addShipmentHeader(ShipmentHeader shipmentHeader) throws Exception {
        logger.debug("begin add ShipmentHeader");

        if (shipmentHeader.getShipmentDetails() == null || shipmentHeader.getShipmentDetails().size() == 0) {
            throw new Exception("提供发运单明细");
        }
        if (shipmentHeader.getShipmentId() == null || "".equals(shipmentHeader.getShipmentId())) {
            //添加运单号 订单号+订单版本号(3位)
            // 首先获取订单号
            if (shipmentHeader.getOrderId() == null || "".equals(shipmentHeader.getOrderId())
                    || shipmentHeader.getOrderRefRevisionId() == null) {
                throw new Exception("未提供订单信息");
            }
            shipmentHeader.setShipmentId(shipmentHeader.getOrderId() + String.format("V%03d", shipmentHeader.getOrderRefRevisionId()));
            for (ShipmentDetail shipmentDetail : shipmentHeader.getShipmentDetails()) {
                shipmentDetail.setShipmentId(shipmentHeader.getShipmentId());
            }
        }
        logger.debug("end add ShipmentHeader");
        return this.shipmentHeaderDao.addShipmentHeader(shipmentHeader);

    }

    public ShipmentHeader getShipmentHeader(Long shipmentHeaderId) {
        logger.debug("get ShipmentHeader");

        return this.shipmentHeaderDao.get(shipmentHeaderId);
    }

    public ShipmentHeader getShipmentHeaderFromOrderId(String orderId) {
        List<ShipmentHeader> shipmentHeaderList = this.shipmentHeaderDao.findList("from ShipmentHeader where accountType='1' and orderId=:orderId", new ParameterString("orderId", orderId));
        if (shipmentHeaderList != null && shipmentHeaderList.size() > 0) {
            //找到没有取消的
            for (ShipmentHeader shipmentHeader : shipmentHeaderList) {
                if (!shipmentHeader.getLogisticsStatusId().equals(ShipmentLogisticsStatusConstants.LOGISTICS_CANCEL)) {
                    return shipmentHeader;
                }
            }
        }

        logger.debug("get ShipmentHeader is null");

        return null;
    }

    /* (非 Javadoc)
    * <p>Title: addOrUpdateShipmentHeader</p>
    * <p>Description: </p>
    * @param shipmentheader
    * @return
    * @throws Exception
    * @see com.chinadrtv.erp.shipment.service.ShipmentService#addOrUpdateShipmentHeader(com.chinadrtv.erp.model.trade.ShipmentHeader)
    */
    public ShipmentHeader addOrUpdateShipmentHeader(
            ShipmentHeader shipmentHeader) throws Exception {
        if (null == shipmentHeader.getId()) {
            return this.addShipmentHeader(shipmentHeader);
        } else {
            return this.updateShipmentHeader(shipmentHeader);
        }
    }

    public boolean settleAccountsShipment(Map<String, Object> params) throws Exception {
        logger.debug("begin settleAccountsShipment");

        boolean result = false;

        if (null == params.get("clearid")) {
            throw new BizException("导入号[clearid]不能为空");
        }

        if (null == params.get("companyid") || "".equals(params.get("companyid").toString())) {
            throw new BizException("送货公司[companyid]不能为空");
        }

        String clearId = params.get("clearid").toString();
        String companyId = params.get("companyid").toString();
        String userId = params.get("userId").toString();

        //获取EDICLEAR
        EdiClear ediClear = ediClearDao.getEdiClear(clearId);

        if (ediClear != null) {
            //获取运单号
            Order orderhist = orderhistService.getOrderHistByOrderid(ediClear.getOrderid());

            //判断订单是否处于完成和分拣状态
            if ((!orderhist.getStatus().equals(AccountStatusConstants.ACCOUNT_TRANS)) && (!orderhist.getStatus().equals(AccountStatusConstants.ACCOUNT_FINI))){
                this.updateEdiClear(ediClear, userId, "订单状态不对。");
                throw new BizException("导入号" + clearId + "订单状态不对");
            }

            ShipmentHeader shipment = shipmentHeaderDao.getByMailId(ediClear.getMailid(), ediClear.getOrderid());

            if (shipment != null) {
                //判断送货公司是否一致
                if (!shipment.getEntityId().equals(companyId)) {
                    this.updateEdiClear(ediClear, userId, "运单实际送货公司与选择的送货公司不一致。");
                    throw new BizException("导入号" + clearId + "不存在对应的结账送货公司不一致");
                }

                //判断是否已经结算(ReconcilFlag 1 为结算， null 或 0 为未结算)
                if ((shipment.getReconcilFlag() == null) || (shipment.getReconcilFlag().equals(0))) {

                    //配送状态：1 完成 2 失败
                    if (ediClear.getStatus().equals("1")) {
                        orderhist.setStatus(AccountStatusConstants.ACCOUNT_FINI);

                        if (!shipment.getEntityId().equals(COMPANY_EMS)) {
                            //应毛震宇要求，在没有更新order表中postfee、clearfee等信息的情况下，强制打掉
                            orderhist.setResult("2");
                        }

                        shipment.setAccountStatusId(AccountStatusConstants.ACCOUNT_FINI);
                        shipment.setFbdt(new Date());
                        shipment.setClearFee(new BigDecimal(ediClear.getNowmoney()));
                        shipment.setClearPostFee(new BigDecimal(ediClear.getPostFee()));

                        if (!shipment.getEntityId().equals(COMPANY_EMS)) {
                            Map<String, Object> pvParams = new HashMap<String, Object>();
                            pvParams.put("sorderid", ediClear.getOrderid());
                            pvParams.put("scrusr", userId);
                            pvService.getJifenproc(pvParams);
                        }

                        for (OrderDetail od : orderhist.getOrderdets()) {
                            od.setState(AccountStatusConstants.ACCOUNT_FINI);
                        }

                        //保存订单快照
                        orderhistService.insertTcHistory(orderhist);
                        //保存订单
                        orderhistService.saveOrUpdate(orderhist);
                        //保存运单
                        this.addOrUpdateShipmentHeader(shipment);

                        this.updateEdiClear(ediClear, userId, "更新成功。");
                        result = true;
                    } else {
                        this.updateEdiClear(ediClear, userId, "非完成状态不能导入。");
                        throw new BizException("订单" + orderhist.getOrderid() + "非完成状态不能导入。");
                    }

                } else {
                    this.updateEdiClear(ediClear, userId, "已被结算。");
                    throw new BizException("订单" + orderhist.getOrderid() + "已被结算。");
                }
            } else {
                this.updateEdiClear(ediClear, userId, "不存在对应的运单");
                throw new BizException("导入号" + clearId + "不存在对应的运单");
            }

            logger.debug("end settleAccountsShipment");

            return result;

        } else {
            throw new BizException("导入号" + clearId + "不存在");
        }
    }

    private void updateEdiClear(EdiClear ediClear, String userId, String remark) {
        ediClear.setImpusr(userId);
        ediClear.setRemark(remark);
        ediClearDao.save(ediClear);
    }
}
