package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.model.trade.ShipmentDetail;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.model.trade.ShipmentTotalPriceAdj;
import com.chinadrtv.erp.model.trade.ShipmentTotalPriceAdjDetail;
import com.chinadrtv.erp.oms.dao.LowerPriceInfoDao;
import com.chinadrtv.erp.oms.dao.ShipmentHeaderDao;
import com.chinadrtv.erp.oms.dao.ShipmentTotalPriceAdjDao;
import com.chinadrtv.erp.oms.dao.ShipmentTotalPriceAdjDetailDao;
import com.chinadrtv.erp.oms.dto.LowerPriceInfoDto;
import com.chinadrtv.erp.oms.dto.OrderDetailsDto;
import com.chinadrtv.erp.oms.service.LowerPriceInfoService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-18
 * Time: 下午1:11
 * To change this template use File | Settings | File Templates.
 */
@Service("lowerPriceInfoService")
public class LowerPriceInfoServiceImpl implements LowerPriceInfoService {
    @Autowired
    private LowerPriceInfoDao lowerPriceInfoDao;
    @Autowired
    private ShipmentTotalPriceAdjDao shipmentTotalPriceAdjDao;
    @Autowired
    private ShipmentTotalPriceAdjDetailDao shipmentTotalPriceAdjDetailDao;
    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;

    private DecimalFormat df = new DecimalFormat("0.00");      //格式化数字保留2位小数

    @Override
    public List<LowerPriceInfoDto> getLowerPriceInfoDto(String orderId, String mailId, int index, int size) {
        List<LowerPriceInfoDto> lowerPriceInfoDtoList = new ArrayList<LowerPriceInfoDto>();
        if("".equals(orderId) && "".equals(mailId)){
             return lowerPriceInfoDtoList;
        }
        List list = lowerPriceInfoDao.getShipmentHeader(orderId,mailId,index,size);
        Object[] obj = null;
        if(list.size() > 0){
            for(int i=0;i<list.size();i++){
                obj = (Object[])list.get(i);
                LowerPriceInfoDto lp = new LowerPriceInfoDto();
                lp.setSenddt(obj[0] != null ? obj[0].toString() : null);
                lp.setShipmentId(obj[1] != null ? obj[1].toString() : null);
                lp.setMailId(obj[2] != null ? obj[2].toString() : null);
                lp.setOrderType(obj[3] != null ? obj[3].toString() : null);

                String totalprice = obj[4] != null ? obj[4].toString() : "0.00";
                lp.setTotalPrice(df.format(Double.parseDouble(totalprice)));

                lp.setAddrdesc(obj[5] != null ? obj[5].toString() : null);
                lp.setName(obj[6] != null ? obj[6].toString() : null);
                lp.setOrderId(obj[7] != null ? obj[7].toString() : null);

                String mailprice = obj[8] != null ? obj[8].toString() : "0.00";
                lp.setMailPrice(df.format(Double.parseDouble(mailprice)));

                lp.setLowerPrice(this.getOrderLowerPrice(orderId));   //全渠道订单最低商品总金额
                String am = this.getAlertAmount(orderId);
                lp.setAlertAmount(df.format(Double.parseDouble(am)));     //订单商品折扣后金额

                lowerPriceInfoDtoList.add(lp);
            }
        }
        return lowerPriceInfoDtoList;
    }
    private String getOrderLowerPrice(String orderId){
        double tmp = 0.00;
        List list = lowerPriceInfoDao.getOrderDetails(orderId);
        Object[] obj = null;
        if(list.size() > 0){
            for(int i=0;i<list.size();i++){
                obj = (Object[])list.get(i);
                double lp = Double.parseDouble(obj[7] != null ? obj[7].toString() : "0.00");
                int snum = Integer.parseInt(obj[4] != null ? obj[4].toString() : "0");
                int upnum = Integer.parseInt(obj[2] != null ? obj[2].toString() : "0") ;
                int num = snum+upnum;
                tmp += lp*num;
            }
        }
        return String.valueOf(df.format(tmp));
    }
    private String getAlertAmount(String orderId){
         return lowerPriceInfoDao.getAmountByShipmentTotalPriceAdj(orderId);
    }

    @Override
    public Long getCountLowerPriceDto(String orderId, String mailId) {
        if("".equals(orderId) && "".equals(mailId)){
            return 0L;
        }
        return lowerPriceInfoDao.getShipmentHeaderCount(orderId,mailId);
    }

    @Override
    public List<OrderDetailsDto> getOrderDetailsDto(String orderId) {
        List list = lowerPriceInfoDao.getOrderDetails(orderId);
        Object[] obj = null;
        List<OrderDetailsDto> orderDetailsDtoList = new ArrayList<OrderDetailsDto>();
        if(list.size() > 0){
            for(int i=0;i<list.size();i++){
                obj = (Object[])list.get(i);
                OrderDetailsDto od = new OrderDetailsDto();
                String uprice = obj[1] != null ? obj[1].toString() : "0.00";
                String upnum = obj[2] != null ? obj[2].toString() : "0";
                String sprice = obj[3] != null ? obj[3].toString() : "0.00";
                String snum = obj[4] != null ? obj[4].toString() : "0";
                //商品均价
                double uprice1 = Double.parseDouble(uprice);
                int upnum1 = Integer.parseInt(upnum);
                double sprice1 = Double.parseDouble(sprice);
                int snum1 = Integer.parseInt(snum);
                double averagePrice = (uprice1*upnum1 + sprice1*snum1)/(upnum1+snum1);
                od.setOrderId(obj[0] != null ? obj[0].toString() : null);
                od.setuPrice(String.valueOf(uprice1*upnum1 + sprice1*snum1));
                od.setUpNum(String.valueOf(upnum1+snum1));
                od.setsPrice(String.valueOf(averagePrice));
                od.setProdid(obj[5] != null ? obj[5].toString() : null);
                od.setProdName(obj[6] != null ? obj[6].toString() : null);

                DecimalFormat df = new DecimalFormat("0.00");      //格式化数字保留2位小数
                String lprice = obj[7] != null ? obj[7].toString() : "0.00";
                od.setlPrice(df.format(Double.parseDouble(lprice)));
                orderDetailsDtoList.add(od);
            }
        }
        return orderDetailsDtoList;
    }

    @Override
    public void saveLowerPrice(String orderId, String editPrice) {
        ShipmentHeader shipmentHeader = shipmentHeaderDao.getShipmentFromOrderId(orderId);
        double alertMoney = Double.parseDouble(editPrice);     //客服调整金额
        if(shipmentHeader != null){
            //表头信息
            ShipmentTotalPriceAdj adj = new ShipmentTotalPriceAdj();
            adj.setShipmentId(shipmentHeader.getShipmentId());
            adj.setOrderId(shipmentHeader.getOrderId());
            adj.setRevision(shipmentHeader.getRevision());
            adj.setMailId(shipmentHeader.getMailId());
            adj.setEntityId(shipmentHeader.getEntityId());
            adj.setAgentCreateDate(new Date());
            adj.setStatus(1L);
            BigDecimal amount = new BigDecimal(alertMoney);
            adj.setAmount(amount);  //合计金额
            adj.setRemark("客服价格调整");
            AgentUser user = SecurityHelper.getLoginUser();
            adj.setAgentUser(user.getUserId());       //客服用户名
            shipmentTotalPriceAdjDao.saveShipmentTotalPriceAdj(adj);
            //明细信息
            ShipmentTotalPriceAdjDetail adjDetail = null;
            List<ShipmentDetail> detailList = new ArrayList<ShipmentDetail>(shipmentHeader.getShipmentDetails());
            if(detailList.size() > 0){
                double tmpMoney = 0.00;    //累加金额
                for (int i=0;i<detailList.size();i++){
                    double totalprice = 0.00;
                    if(detailList.size() == 1){
                        totalprice = alertMoney;
                    }
                    if(i == detailList.size() - 1){
                        totalprice = alertMoney - tmpMoney;
                    }
                    ShipmentDetail sd = detailList.get(i);
                    adjDetail = new ShipmentTotalPriceAdjDetail();
                    adjDetail.setShipmentRefundId(shipmentHeader.getId());  //SHIPMENT_HEADER外键
                    adjDetail.setShipmentId(sd.getShipmentId());   //出库单号
                    adjDetail.setShipmentLineNum(sd.getId());        //出库单行号
                    adjDetail.setItemId(sd.getItemId());                  //商品编码
                    adjDetail.setItemDesc(sd.getItemDesc());    //商品描述
                    adjDetail.setTotalQty(sd.getTotalQty());   //订购数量
                    if(detailList.size() > 1 && i!= detailList.size() - 1){
                        //算单品金额百分比
                        double price = sd.getUnitPrice().doubleValue();
                        long qty = sd.getTotalQty();
                        double orderMoney = shipmentHeader.getProdPrice().doubleValue();
                        double zongjia = price*qty;
                        double percen = zongjia/orderMoney;   //百分比
                        double f = percen * alertMoney*qty;
                        BigDecimal number = new BigDecimal(f);
                        totalprice = number.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                        tmpMoney += totalprice;
                    }
                    adjDetail.setTotalPrice(new BigDecimal(totalprice)); //商品零售总价格
                    adjDetail.setFreeFlag(sd.getFreeFlag());   //是否赠品
                    adjDetail.setRemark(sd.getRemark());  //产品备注
                    //adjDetail.setAgentQty(sd.getTotalQty());    //客服确认数量
                    //adjDetail.setAgentAmount(sd.getCarrier());  //客服确认应少收金额
                    //adjDetail.setWarehouseQty();
                    //adjDetail.setAccountQty();
                    //adjDetail.setAccountAmount();
                    //adjDetail.setUnitName();    //数量单位
                    //保存明细
                    shipmentTotalPriceAdjDetailDao.saveShipmentTotalPriceAdjDetail(adjDetail);

                }
            }
        }



    }

}
