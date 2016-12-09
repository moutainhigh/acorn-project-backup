package com.chinadrtv.order.download.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.trade.*;
import com.chinadrtv.erp.tc.core.constant.AccountStatusConstants;
import com.chinadrtv.erp.tc.core.constant.LogisticsStatusConstants;
import com.chinadrtv.erp.tc.core.dao.ShipmentHeaderDao;
import com.chinadrtv.order.download.dal.iagent.dao.OrderDownloadDao;
import com.chinadrtv.order.download.dal.iagent.dao.OrderhistDao;
import com.chinadrtv.order.download.dal.wms.dao.WmsCancelShipmentDao;
import com.chinadrtv.order.download.dal.wms.dao.WmsShipmentDetail1Dao;
import com.chinadrtv.order.download.dal.wms.dao.WmsShipmentDetail2Dao;
import com.chinadrtv.order.download.dal.wms.dao.WmsShipmentHeaderDao;
import com.chinadrtv.order.download.service.OrderDownWmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;


@Service
public class OrderDownWmsServiceImpl extends GenericServiceImpl<ShipmentDownControl, Long>
        implements OrderDownWmsService {

    @Autowired
    private OrderDownloadDao orderDownControlDao;

    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;

    @Autowired
    private WmsShipmentHeaderDao wmsShipmentHeaderDao;

    @Autowired
    private WmsShipmentDetail1Dao wmsShipmentDetail1Dao;

    @Autowired
    private WmsShipmentDetail2Dao wmsShipmentDetail2Dao;

    @Autowired
    @Qualifier("orderhistDaoEs37")
    private OrderhistDao orderhistDao;

    @Autowired
    private WmsCancelShipmentDao wmsCancelShipmentDao;

    @Override
    protected GenericDao<ShipmentDownControl, Long> getGenericDao() {
        return orderDownControlDao;
    }

    public String getSubString(String str, int subStrLen) {
        if (str == null || subStrLen <= 0) {
            return "";
        }
        byte[] strBytes = str.getBytes(); // 字节码数组

        if (subStrLen > strBytes.length) {
            subStrLen = strBytes.length;
        }
        String subStr = new String(strBytes, 0, subStrLen);
        // 最后一个如果只取到汉字低字节（它也占一个字符长度），那么就放弃最后一个字符
        if (subStr.charAt(subStr.length() - 1) != str.charAt(subStr.length() - 1)) {
            subStr = subStr.substring(0, subStr.length() - 1);
        }
        return subStr;
    }

    /**
     * 正常订单下传
     */
    public void shipmentDownToWms(ShipmentDownControl shipment) {
        String prodSpellName = null;
        BigDecimal totalAmt = null ;
        //判断订单是否有效（订单状态：分拣。 物流状态：普通）
        if (shipment.getLogisticsStatus().equals(LogisticsStatusConstants.LOGISTICS_NORMAL)
                && shipment.getOrderStatus().equals(AccountStatusConstants.ACCOUNT_TRANS)) {
            //任务号
            Integer taskNo = Integer.valueOf(shipment.getTaskNo());

            //获取 shipmentheader(erp tc core)
            ShipmentHeader newSh = shipmentHeaderDao.getByShipmentId(shipment.getShipmentId());

            //插入 WmsShipmentHeader
            WmsShipmentHeader wmsSh = shipmentHeaderDao.queryByShipmentId(newSh.getShipmentId());
            if (wmsSh == null){
                throw new RuntimeException("未找到可下传订单，请检查送货公司是否为空") ;
            }
            else{
                wmsSh.setTasksno(taskNo);
                wmsShipmentHeaderDao.save(wmsSh);

                //插入 WmsShipmentDetail1
                for (Iterator<ShipmentDetail> iter = newSh.getShipmentDetails().iterator(); iter.hasNext(); ) {
                    ShipmentDetail sd = iter.next();

                    WmsShipmentDetail1 wmsSd1 = new WmsShipmentDetail1();
                    wmsSd1.setShipmentId(shipment.getOrderid());
                    wmsSd1.setShipmentLineNum(sd.getShipmentLineNum());
                    wmsSd1.setItem(sd.getItemId());
                    wmsSd1.setItemDesc(sd.getItemDesc());
                    wmsSd1.setTotalQty(sd.getTotalQty());
                    wmsSd1.setUnitPrice(sd.getUnitPrice());
                    wmsSd1.setStatus("1");
                    wmsSd1.setQuantityum("GE");
                    wmsSd1.setFreeFlag(sd.getFreeFlag());
                    wmsSd1.setMemo(sd.getRemark());
                    wmsSd1.setTasksno(taskNo);
                    wmsSd1.setCarriage(null == sd.getCarrier() ? 0 : sd.getCarrier().intValue());
                    wmsSd1.setRevision(shipment.getRevison());
                    //wmsSd1.setBatchId(batchId);
                    //wmsSd1.setBatchDate(batchDate);

                    wmsShipmentDetail1Dao.save(wmsSd1);
                }

                //插入 WmsShipmentDetail2
                Order orderhist = orderhistDao.getOrderHistByOrderid(shipment.getOrderid());
                Set<OrderDetail> orderdet = orderhist.getOrderdets();
                for (Iterator<OrderDetail> iter = orderdet.iterator(); iter.hasNext(); ) {
                    OrderDetail od = iter.next();

                    WmsShipmentDetail2 wmsSd2 = new WmsShipmentDetail2();
                    wmsSd2.setShipmentId(od.getOrderid());
                    wmsSd2.setRevision(shipment.getRevison());
                    wmsSd2.setShipmentLineNum(new Long(od.getOrderid()));

                    String prodType = orderhistDao.getProdType(od);
                    String prodScmId = orderhistDao.getProdScmCode(od.getProdid(), prodType);
                    wmsSd2.setItem(prodScmId);

                    wmsSd2.setKits(od.getProdscode());    //组合商品名称
                    //总的销售数量
                    wmsSd2.setTotalQty(new Long(od.getSpnum() + od.getUpnum()));
                    //计算销售单价（总金额/数量)
                    if ((new Long(0)).equals(wmsSd2.getTotalQty())){
                        wmsSd2.setUnitPrice(od.getUprice());
                    }
                    else{
                        totalAmt = new BigDecimal("0") ;
                        if (od.getSprice()!=null && od.getSpnum()!=null)
                            totalAmt = od.getSprice().multiply(BigDecimal.valueOf(od.getSpnum())) ;
                        if (od.getUprice()!=null && od.getUpnum()!=null)
                            totalAmt = totalAmt.add(od.getUprice().multiply(BigDecimal.valueOf(od.getUpnum()))) ;
                        wmsSd2.setUnitPrice(totalAmt.divide(BigDecimal.valueOf(wmsSd2.getTotalQty()), 6, BigDecimal.ROUND_HALF_UP));
                    }
                    wmsSd2.setTasksno(taskNo);
                    wmsSd2.setCarriage(null == od.getFreight() ? 0 : od.getFreight().intValue());

                    String issuite = orderhistDao.getProdSuite(od.getProdid(), prodType);

                    wmsSd2.setIsAssembly(null == issuite ? "0" : issuite);

                    prodSpellName = orderhistDao.getProdSpellName(prodScmId);
                    prodSpellName = getSubString(prodSpellName, 20);
                    wmsSd2.setQuantityum(prodSpellName);

                    wmsShipmentDetail2Dao.save(wmsSd2);
                }

                orderDownControlDao.save(shipment);
            }
        }
    }

    /**
     * 取消订单下传
     */
    public void cancelShipmentDownToWms(ShipmentDownControl shipment) {

        //任务号
        Integer taskNo = Integer.valueOf(shipment.getTaskNo());

        if (shipment.getLogisticsStatus().equals(LogisticsStatusConstants.LOGISTICS_CANCEL)) {
            if (shipment.getOrderStatus().equals(AccountStatusConstants.ACCOUNT_CANCEL)
                    || shipment.getOrderStatus().equals(AccountStatusConstants.ACCOUNT_WMSCANCEL)) {
                //获取 shipmentheader
                ShipmentHeader newSh = shipmentHeaderDao.getByShipmentId(shipment.getShipmentId());

                WmsCancelShipment wmsCh = new WmsCancelShipment();
                wmsCh.setShipmentId(newSh.getOrderId());
                wmsCh.setWarehouse(newSh.getWarehouseId());
                //送货公司
                wmsCh.setCarrier(newSh.getEntityId());
                wmsCh.setCancelDate(newSh.getCrdt());
                wmsCh.setUpdat(new Date());

                wmsCh.setTaskno(taskNo);

                String version = shipment.getRevison();
                wmsCh.setRevision(version);

                wmsCancelShipmentDao.save(wmsCh);

                orderDownControlDao.save(shipment);
            }
        }
    }
}
