package com.chinadrtv.erp.ic.service.impl;

import com.chinadrtv.erp.constant.BusinessTypeConstants;
import com.chinadrtv.erp.constant.TransactionSourceConstants;
import com.chinadrtv.erp.ic.dao.ShipmentHeaderHisDao;
import com.chinadrtv.erp.ic.dao.WmsShipmentHeaderHisDao;
import com.chinadrtv.erp.ic.model.ScmOutNotice;
import com.chinadrtv.erp.ic.service.ItemInventoryTransactionService;
import com.chinadrtv.erp.ic.service.ShipmentHeaderHisService;
import com.chinadrtv.erp.model.inventory.ItemInventoryTransaction;
import com.chinadrtv.erp.model.inventory.WmsShipmentDetailHis;
import com.chinadrtv.erp.model.inventory.WmsShipmentHeaderHis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: liukuan
 * Date: 13-2-4
 * Time: 下午6:38
 * To change this template use File | Settings | File Templates.
 * SCM出库通知单
 */
@Service("shipmentHeaderHisServiceImpl")
public class ShipmentHeaderHisServiceImpl implements ShipmentHeaderHisService {
    private static final Logger LOG = LoggerFactory.getLogger(ShipmentHeaderHisServiceImpl.class);

    @Autowired
    private WmsShipmentHeaderHisDao wmsShipmentHeaderHisDao;

    @Deprecated
    @Autowired
    private ShipmentHeaderHisDao shipmentHeaderHisDao;

    @Autowired
    private ItemInventoryTransactionService itemInventoryTransactionService;

    public void processShipments(String batchUser){

        Date now = new Date();
        Long batchId = now.getTime();

        List<WmsShipmentHeaderHis> shipments = wmsShipmentHeaderHisDao.getUnhandledShipments();
        for(WmsShipmentHeaderHis shipment : shipments)
        {
            shipment.setBatchId(batchId.toString());
            shipment.setBatchDate(now);

            for(WmsShipmentDetailHis detail: shipment.getDetails())
            {
                detail.setBatchId(batchId.toString());
                detail.setBatchDate(now);

                ItemInventoryTransaction items = new ItemInventoryTransaction();
                items.setChannel("ACORN");    //not null
                items.setWarehouse(shipment.getWarehouse());     //not null
                items.setLocationType(detail.getStatus());     //not null
                //判断业务类型 调用 方法
                //items.setBusinessType(getBusinessType(shipment.getOrderTypper()));   //not null
                //转移到BusinessTypeConstants
                items.setBusinessType(BusinessTypeConstants.OrderType2BusinessTypeAPP(String.valueOf(shipment.getOrderTypper())));
                items.setBusinessChildType("");
                items.setBusinessNo(shipment.getShipmentId());        //not null
                items.setBusinessDate(shipment.getOrderDateTime());
                items.setProductCode(detail.getItem());
                items.setLotNo("");
                items.setEstimatedQty(Double.valueOf(detail.getTotalQty()));
                items.setQty(0.0);
                items.setReferenceNo("");
                items.setSourceId(TransactionSourceConstants.SCM);            //not null
                items.setSourceDesc("SCM");
                items.setCreatedBy(batchUser);
                items.setModifiedBy(batchUser);
                items.setCreated(now);          //not null
                items.setModified(now);
                //数据同步库存事务表
                itemInventoryTransactionService.insertItemInventoryTransaction(items);
            }

            wmsShipmentHeaderHisDao.batchLog(shipment);
        }
    }

    /**
     * 创建执行Scm出库
     */
    @Deprecated
    public void createShipmentHeaderHis() {
        //获取Scm出库信息
        List<ScmOutNotice> list = shipmentHeaderHisDao.getScmOutNotice();
        if(list.size() > 0)
        {
            List<Long> ids = new ArrayList<Long>();
            Date date = new Date();
            for(int i=0;i<list.size();i++)
            {
                ItemInventoryTransaction items = new ItemInventoryTransaction();
                items.setChannel("ACORN");    //not null
                items.setWarehouse(list.get(i).getWarehouse());     //not null
                items.setLocationType(list.get(i).getStatus());     //not null
                //判断业务类型 调用 方法
                //items.setBusinessType(getBusinessType(list.get(i).getOrderTyper()));   //not null
                //转移到BusinessTypeConstants
                items.setBusinessType(BusinessTypeConstants.OrderType2BusinessTypeAPP(String.valueOf(list.get(i).getOrderTyper())));
                items.setBusinessChildType("");
                items.setBusinessNo(list.get(i).getShipmentId());        //not null
                items.setBusinessDate(list.get(i).getOrderDateTime());
                items.setProductCode(list.get(i).getItem());
                items.setLotNo("");
                items.setEstimatedQty(list.get(i).getTotalQty());
                items.setQty(0.0);
                items.setReferenceNo("");
                items.setSourceId(TransactionSourceConstants.SCM);            //not null
                items.setSourceDesc("SCM");
                items.setCreatedBy("SYS");
                items.setModifiedBy("SYS");
                items.setCreated(date);          //not null
                items.setModified(date);
                items.setBatchId("");
                items.setBatchDate(date);
                items.setBatchBy("SYS");
                //数据同步库存事务表
                itemInventoryTransactionService.insertItemInventoryTransaction(items);

                if(!ids.contains(list.get(i).getRuid())){
                    ids.add(list.get(i).getRuid());
                }
            }
            //批号被修改多次，被误报多服务器操作
            try
            {
                for(Long id : ids) {
                    shipmentHeaderHisDao.updateShipmentHeaderHis(id, date);
                }
            }catch (RuntimeException ex) {
                LOG.error(ex.getMessage());
                //用于多服务器运行回滚事物,勿删
                throw ex;
            }
            catch (Exception ex)
            {
                LOG.error("SCM出库通知单异常："+ex.getMessage());
            }
        }
    }
    /**
     * 判断业务类型
     */
    /*
    private String getBusinessType(int temp)
    {
        String businessType = "";
        if(temp == 1)
        {
            businessType = "201";
        }
        else if(temp == 2)
        {
            businessType = "202";
        }
        else if(temp == 3)
        {
            businessType = "203";
        }
        else if(temp == 4)
        {
            businessType = "204";
        }
        else if(temp == 5)
        {
            businessType = "205";
        }
        else if(temp == 6)
        {
            businessType = "206";
        }
        else if(temp == 7)
        {
            businessType = "207";
        }
        else if(temp == 8)
        {
            businessType = "208";
        }
        else if(temp == 9)
        {
            businessType = "209";
        }
        else if(temp == 10)
        {
            businessType = "210";
        }
        else if(temp == 11)
        {
            businessType = "211";
        }
        else if(temp == 12)
        {
            businessType = "212";
        }
        else if(temp == 13)
        {
            businessType = "213";
        }
        else if(temp == 14)
        {
            businessType = "214";
        }
        else if(temp == 15)
        {
            businessType = "215";
        }
        else if(temp == 16)
        {
            businessType = "216";
        }
        else if(temp == 17)
        {
            businessType = "217";
        }
        else if(temp == 18)
        {
            businessType = "218";
        }
        return businessType;
    }
    */
}
