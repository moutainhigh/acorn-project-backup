package com.chinadrtv.erp.oms.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.model.trade.ShipmentDetail;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.oms.dao.AvoidFreightDao;
import com.chinadrtv.erp.oms.dao.ShipmentDetailDao;
import com.chinadrtv.erp.oms.service.ShipmentDetailService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-4-26
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 * 免运费登记
 */
@Service("shipmentDetailService")
public class ShipmentDetailServiceImpl implements ShipmentDetailService {
    @Autowired
    private AvoidFreightDao avoidFreightDao;
    @Autowired
    private ShipmentDetailDao shipmentDetailDao;

    /**
     * 免运费登记表头
     * @param sh
     * @param afterFreight
     */
    public void copyShipmentHeader(ShipmentHeader sh, String afterFreight) {
        shipmentDetailDao.deleteDetailByShipmentId("R".concat(sh.getShipmentId())); //删除上次减免明细
        avoidFreightDao.deleteByShipmentId("R".concat(sh.getShipmentId()));
        ShipmentHeader shipmentHeader = new ShipmentHeader();
        shipmentHeader.setShipmentId("R"+sh.getShipmentId());
        shipmentHeader.setAccountType("3");  //发运单记账类型
        shipmentHeader.setOrderHistory(sh.getOrderHistory());
        shipmentHeader.setOrderId(sh.getOrderId());
        shipmentHeader.setRevision(sh.getRevision());
        shipmentHeader.setMailId(sh.getMailId());
        shipmentHeader.setEntityId(sh.getEntityId());
        AgentUser user = SecurityHelper.getLoginUser();
        shipmentHeader.setCrusr(user.getUserId());
        shipmentHeader.setMddt(new Date());
        shipmentHeader.setAccountStatusId("5");  //订单节点反馈状态
        shipmentHeader.setAccountStatusRemark(sh.getAccountStatusRemark());
        shipmentHeader.setCrdt(new Date());
        shipmentHeader.setSenddt(sh.getSenddt());
        shipmentHeader.setFbdt(sh.getFbdt());
        shipmentHeader.setOutdt(sh.getOutdt());
        shipmentHeader.setAccdt(sh.getAccdt());
        shipmentHeader.setLogisticsStatusId("5");   //订单物流节点状态
        shipmentHeader.setLogisticsStatusRemark(sh.getLogisticsStatusRemark());
        shipmentHeader.setRfoutdat(sh.getRfoutdat());
        shipmentHeader.setItemFlag(sh.getItemFlag());
        shipmentHeader.setOwner(sh.getOwner());
        shipmentHeader.setVolume(sh.getVolume());
        shipmentHeader.setWeight(sh.getWeight());
        shipmentHeader.setWarehouseId(sh.getWarehouseId());
        shipmentHeader.setAssociatedId(sh.getId());
        shipmentHeader.setOrderRefRevisionId(sh.getOrderRefRevisionId());
        shipmentHeader.setProdPrice(new BigDecimal(0));
        shipmentHeader.setPostFee1(sh.getPostFee1());
        shipmentHeader.setPostFee2(sh.getPostFee2());
        shipmentHeader.setClearFee(sh.getClearFee());
        shipmentHeader.setReconcilFlag(sh.getReconcilFlag());
        shipmentHeader.setReconcilUser(sh.getReconcilUser());
        shipmentHeader.setReconcilDate(sh.getReconcilDate());
        shipmentHeader.setClearPostFee(sh.getClearPostFee());
        shipmentHeader.setAccount(sh.getAccount());

        String temp = (sh.getMailPrice() != null ? String.valueOf(sh.getMailPrice()):"0.0");
        double oldMailPrice = Double.parseDouble(temp);
        double newMailPrice = Double.parseDouble(afterFreight);   //调整运费
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal mailPrice = new BigDecimal(df.format(newMailPrice - oldMailPrice));
        shipmentHeader.setMailPrice(mailPrice);

        avoidFreightDao.saveShipmentHeader(shipmentHeader);
    }

    /**
     * 免运费登记明细
     * @param sh
     * @param afterFreight
     */
    public void copyShipmentDetail(ShipmentHeader sh, String afterFreight) {
        ShipmentDetail shipmentDetail = null;
        Set<ShipmentDetail> details = sh.getShipmentDetails();      //获取明细
        if(details.size() > 0)
        {
            for(ShipmentDetail sd:details)
            {  //判断运费是否大于0
                double temp = Double.parseDouble(sd.getCarrier().toString());
                double temp2 = Double.parseDouble(afterFreight);   //调整运费
                if(temp > 0D)
                {   //保存明细
                    ShipmentHeader rsh = avoidFreightDao.searchShipmentByShipmentId("R".concat(sh.getShipmentId()));
                    shipmentDetail = new ShipmentDetail();
                    shipmentDetail.setShipmentHeader(rsh); //表头逆向单ID
                    shipmentDetail.setShipmentId("R"+sd.getShipmentId());
                    shipmentDetail.setShipmentLineNum(sd.getShipmentLineNum());
                    shipmentDetail.setItemId(sd.getItemId());
                    shipmentDetail.setItemDesc(sd.getItemDesc());
                    shipmentDetail.setTotalQty(0L);
                    shipmentDetail.setUnitPrice(new BigDecimal(0));
                    shipmentDetail.setQuantity(sd.getQuantity());
                    shipmentDetail.setFreeFlag(sd.getFreeFlag());
                    shipmentDetail.setRemark(sd.getRemark());
                    shipmentDetail.setAllocationFlag(sd.getAllocationFlag());
                    DecimalFormat df = new DecimalFormat("0.00");
                    BigDecimal newCarrier = new BigDecimal(df.format(temp2 - temp));//调整后运费
                    shipmentDetail.setCarrier(newCarrier);
                    shipmentDetailDao.saveShipmentDetail(shipmentDetail);
                }
            }
        }
    }
}
