package com.chinadrtv.erp.ic.service.impl;

import com.chinadrtv.erp.constant.BusinessTypeConstants;
import com.chinadrtv.erp.constant.TransactionSourceConstants;
import com.chinadrtv.erp.ic.dao.ScmCloseShipmentDao;
import com.chinadrtv.erp.ic.service.ItemInventoryTransactionService;
import com.chinadrtv.erp.ic.service.ScmCloseShipmentService;
import com.chinadrtv.erp.model.inventory.ItemInventoryTransaction;
import com.chinadrtv.erp.model.inventory.ScmCloseShipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-20
 * Time: 上午9:28
 * To change this template use File | Settings | File Templates.
 * WMS出库取消
 */
@Service("scmCloseShipmentService")
public class ScmCloseShipmentServiceImpl implements ScmCloseShipmentService {
    private static final Logger LOG = LoggerFactory.getLogger(ScmCloseShipmentServiceImpl.class);

    @Autowired
    private ScmCloseShipmentDao scmCloseShipmentDao;
    @Autowired
    private ItemInventoryTransactionService itemInventoryTransactionService;
    /**
     * 数据同步到库存事务表，回写数据库
     */
    public void insertScmCloseShipmentService() {

        //获取数据信息
        List<ScmCloseShipment> scmCloseShipmentList = scmCloseShipmentDao.getScmCloseShipment();

        if(scmCloseShipmentList.size() > 0)
        {
            Date sysDate = new Date();
            for(int i=0;i<scmCloseShipmentList.size();i++)
            {
                ItemInventoryTransaction items = new ItemInventoryTransaction();
                items.setChannel("ACORN");  //not null
                items.setWarehouse(scmCloseShipmentList.get(i).getWarehouse());        //not null
                items.setLocationType(String.valueOf(scmCloseShipmentList.get(i).getItemsStatus()));         //not null
                //判断业务类型 调用 方法
                items.setBusinessDate(scmCloseShipmentList.get(i).getCancelDate());
                //items.setBusinessType(getBusinessType(Integer.parseInt(scmCloseShipmentList.get(i).getOrderType())));      //not null
                //转移到BusinessTypeConstants
                items.setBusinessType(BusinessTypeConstants.OrderType2BusinessTypeWMS(scmCloseShipmentList.get(i).getOrderType()));
                items.setBusinessNo(scmCloseShipmentList.get(i).getShipmentId());   //not null
                items.setProductCode(scmCloseShipmentList.get(i).getItem());
                //与出库类型一致但,出库类型为复数
                items.setEstimatedQty(scmCloseShipmentList.get(i).getTotalQty());
                items.setQty(0.0);
                items.setSourceId(TransactionSourceConstants.WMS);     //not null
                items.setSourceDesc("WMS");
                items.setCreatedBy("SYS");
                items.setModifiedBy("SYS");
                items.setCreated(sysDate);  //not null
                items.setModified(sysDate);
                items.setBatchId(scmCloseShipmentList.get(i).getBatchId());
                items.setBatchDate(scmCloseShipmentList.get(i).getBatchDate());
                items.setBatchBy("SYS");
                try{
                    //调用入库服务
                    boolean b = itemInventoryTransactionService.insertItemInventoryTransaction(items);
                    if(b)
                    {
                        // 数据同步成功，回写数据库
                        scmCloseShipmentDao.updateScmCloseShipment(scmCloseShipmentList.get(i).getRuId(),sysDate);
                    }
                }
                catch (RuntimeException ex){
                    LOG.error(ex.getMessage());
                    throw ex;
                }
                catch (Exception ex)
                {
                    LOG.error("WMS出库取消异常："+ex.getMessage());
                }

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
