package com.chinadrtv.erp.ic.service.impl;

import com.chinadrtv.erp.constant.BusinessTypeConstants;
import com.chinadrtv.erp.constant.ChannelConstants;
import com.chinadrtv.erp.constant.LocationTypeConstants;
import com.chinadrtv.erp.constant.TransactionSourceConstants;
import com.chinadrtv.erp.ic.dao.ScmUploadShipmentHeaderHisDao;
import com.chinadrtv.erp.ic.service.ItemInventoryTransactionService;
import com.chinadrtv.erp.ic.service.WmsOutOrderService;
import com.chinadrtv.erp.model.inventory.ItemInventoryTransaction;
import com.chinadrtv.erp.model.inventory.ScmUploadShipmentDetailHis;
import com.chinadrtv.erp.model.inventory.ScmUploadShipmentHeaderHis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-25
 * Time: 下午5:21
 * To change this template use File | Settings | File Templates.
 * Wms出库单服务
 */
@Service("wmsOutOrderService")
public class WmsOutOrderServiceImpl implements WmsOutOrderService {
    private static final Logger LOG = LoggerFactory.getLogger(WmsOutOrderServiceImpl.class);
    @Autowired
    private ScmUploadShipmentHeaderHisDao scmUploadShipmentHeaderHisDao;

    @Autowired
    private ItemInventoryTransactionService itemInventoryTransactionService;



    /**
     * 不需要关联原单计算预估数量
     * @param tran
     */
    private void calculateEstimatedQty(ItemInventoryTransaction tran) {
        tran.setEstimatedQty(tran.getQty());
        /*
       if (wmsShipmentHeaderHisDao != null) {
        tran.setEstimatedQty(wmsShipmentHeaderHisDao.getTotalQty(
                tran.getWarehouse(),
                tran.getBusinessNo(),
                BusinessTypeConstants.BusinessType2ReceiptTypeSCM(tran.getBusinessType()),
                tran.getProductCode()
        ));
        }
        */
    }

    /**
     * SCM出库
     * @param batchUser
     */
    public void shipmentsWMS(String batchUser)
    {
        if(scmUploadShipmentHeaderHisDao != null)
        {
            List<ScmUploadShipmentHeaderHis> shipments = scmUploadShipmentHeaderHisDao.getUnhandledShipments();
            if(shipments != null)
            {
                Date now = new Date();
                Long batchId = now.getTime();
                // 合并相同产品WMS出货单生成一条收货事务记录
                List<ItemInventoryTransaction> trans = new ArrayList<ItemInventoryTransaction>();
                for(ScmUploadShipmentHeaderHis shipment : shipments)
                {
                     try
                     {

                         shipment.setBatchId(batchId.toString());   //数据回写
                         shipment.setBatchDate(now);
                         for(ScmUploadShipmentDetailHis detail : shipment.getDetails())
                         {
                             detail.setBatchId(batchId.toString());
                             detail.setBatchDate(now);

                             ItemInventoryTransaction tran = new ItemInventoryTransaction();
                             tran.setChannel(ChannelConstants.Default);
                             tran.setWarehouse(shipment.getWarehouse());
                             tran.setSourceId(TransactionSourceConstants.WMS);   //出货单
                             tran.setSourceDesc("WMS");
                             tran.setBusinessNo(shipment.getShipmentId());
                             tran.setBusinessDate(shipment.getShippingDate());

                             //LocationTypeConstants.INVENTORY_QUALITY_PRODUCT
                             //出库单可能出残次品(调拨/特卖等) 2013-04-17 gaodejian
                             tran.setLocationType(LocationTypeConstants.status2LocationType(detail.getItemStatus()));
                             tran.setCreated(now);
                             tran.setModified(now);
                             tran.setCreatedBy(batchUser);
                             tran.setModifiedBy(batchUser);
                             tran.setProductId(0l);
                             tran.setProductCode(detail.getItem());
                             tran.setEstimatedQty(0.0); //暂不处理
                             if(detail.getTotalQty() != null)
                             {
                                 tran.setQty(Double.valueOf(detail.getTotalQty()));
                             }else {
                                 tran.setQty(0.0);
                             }
                             //tran.setBusinessType(getBusinessType(shipment.getOrderType()));   //业务类型
                             tran.setBusinessType(BusinessTypeConstants.OrderType2BusinessTypeWMS(String.valueOf(shipment.getOrderType())));
                             trans.add(tran);
                         }
                         //数据回写
                         scmUploadShipmentHeaderHisDao.batchLog(shipment);
                     }
                     catch (RuntimeException ex){
                         LOG.error(ex.getMessage());
                         throw ex;
                     }
                     catch (Exception ex)
                     {
                         LOG.error("WMS出库单异常：" + ex.getMessage());
                     }
                }
                // 保存Trans记录
                for (ItemInventoryTransaction tran : trans) {
                    if (tran.getBusinessType() != null) {
                        try
                        {
                            calculateEstimatedQty(tran);
                            itemInventoryTransactionService.insertItemInventoryTransaction(tran);

                        }
                        catch (Exception ex){
                            LOG.error("WMS出库单异常："+ex.getMessage());
                        }
                    }
                }
            }
        }
    }
    /**
     * 判断业务类型
     */
    /*
    private String getBusinessType(Long temp)
    {
        String businessType = "";
        if(temp == 1)
        {
            businessType = "251";
        }
        else if(temp == 2)
        {
            businessType = "252";
        }
        else if(temp == 3)
        {
            businessType = "253";
        }
        else if(temp == 4)
        {
            businessType = "254";
        }
        else if(temp == 5)
        {
            businessType = "255";
        }
        else if(temp == 6)
        {
            businessType = "256";
        }
        else if(temp == 7)
        {
            businessType = "257";
        }
        else if(temp == 8)
        {
            businessType = "258";
        }
        else if(temp == 9)
        {
            businessType = "259";
        }
        else if(temp == 10)
        {
            businessType = "260";
        }
        else if(temp == 11)
        {
            businessType = "261";
        }
        else if(temp == 12)
        {
            businessType = "262";
        }
        else if(temp == 13)
        {
            businessType = "263";
        }
        else if(temp == 14)
        {
            businessType = "264";
        }
        else if(temp == 15)
        {
            businessType = "265";
        }
        else if(temp == 16)
        {
            businessType = "266";
        }
        else if(temp == 17)
        {
            businessType = "267";
        }
        else if(temp == 18)
        {
            businessType = "268";
        }
        return businessType;
    }
    */
}
