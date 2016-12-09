package com.chinadrtv.erp.oms.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dao.AvoidFreightDao;
import com.chinadrtv.erp.oms.dao.ShipmentDetailDao;
import com.chinadrtv.erp.oms.dto.AvoidFreightDto;
import com.chinadrtv.erp.oms.service.AvoidFreightService;
import com.chinadrtv.erp.oms.service.ShipmentDetailService;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-27
 * Time: 下午1:03
 * To change this template use File | Settings | File Templates.
 * 免运费登记
 */
@Service("avoidFreightService")
public class AvoidFreightServiceImpl implements AvoidFreightService {
    @Autowired
    private AvoidFreightDao avoidFreightDao;
    @Autowired
    private ShipmentDetailDao shipmentDetailDao;
    @Autowired
    private ShipmentDetailService shipmentDetailService;

    /**
     * 分页显示数据
     * @param avoidFreightDto
     * @param dataModel
     * @return
     */
    public Map<String, Object> getAvoidFreightDtoList(AvoidFreightDto avoidFreightDto, DataGridModel dataModel) {

        Map<String,Object> result = new HashMap<String, Object>();
        List objList = avoidFreightDao.getFreightList(avoidFreightDto,dataModel);

        List<AvoidFreightDto> freightList = new ArrayList<AvoidFreightDto>();
        Object[] obj = null;
        AvoidFreightDto afd = null;
        for(int i=0;i<objList.size();i++)
        {
            obj = (Object[])objList.get(i);
            afd = new AvoidFreightDto();
            DecimalFormat df = new DecimalFormat("0.00");      //格式化数字保留2位小数
            List objCarrier = shipmentDetailDao.getCarrier("R".concat(obj[2].toString()));  //获取减免后的运费
            afd.setId(Long.valueOf(((BigDecimal)obj[0]).toString()));
            afd.setSenddt(obj[1] != null ? obj[1].toString() : null);
            afd.setShipmentId(obj[2] != null ? obj[2].toString() : null);
            afd.setMailId(obj[3] != null ? obj[3].toString() : null);
            afd.setOrderType(obj[4] != null ? obj[4].toString() : null);
            String mailPrice = obj[5] != null ? obj[5].toString() : "0";
            afd.setMailPrice(df.format(Double.parseDouble(mailPrice)));
            String address = obj[6] != null ? obj[6].toString() : null;

            if(address.length() > 12) {
                afd.setAddrdesc(address.substring(0,12));    //截取地址前12位
            }else{
                afd.setAddrdesc(obj[6] != null ? obj[6].toString() : null);
            }
            if(objCarrier.size() == 0)
            {
                afd.setCutFreight(df.format(Double.parseDouble(mailPrice)));
            } else {
                String f = objCarrier.get(0).toString();//差额
                double mailprice = Double.parseDouble(obj[5] != null ? obj[5].toString() : "0");
                double newferight = Double.parseDouble(f);
                double afterFreight = mailprice + newferight;
                afd.setCutFreight(df.format(afterFreight));
            }
            String prodPrice = obj[7] != null ? obj[7].toString() : "0";
            double totalPrice = Double.parseDouble(prodPrice)+Double.parseDouble(mailPrice);
            afd.setTotalPrice(String.valueOf(df.format(totalPrice)));
            String name = obj[8] != null ? obj[8].toString() : null;  //客户名称显示第一位
            if(name.length() > 1){
                afd.setName(name.substring(0,1).concat("**"));
            }
            freightList.add(afd);
        }
        int count = avoidFreightDao.queryCount(avoidFreightDto);
        result.put("total",count);
        result.put("rows",freightList);

        return result;
    }

    /**
     * 免运费编辑登记
     * @param id
     * @param afterFreight
     */
    public void registerAvoidFreight(Long id, String afterFreight) throws BizException{
        ShipmentHeader sh = avoidFreightDao.searchShipmentById(id);
        if( sh==null ){
        	throw new BizException("没有找到此订单，无法操作！");
        }
        
        if( "1".equals(sh.getReconcilFlag()) ){
        	throw new BizException("此订单已经结算，无法进行免运费登记！");
        }
        
        shipmentDetailService.copyShipmentHeader(sh,afterFreight);
        shipmentDetailService.copyShipmentDetail(sh,afterFreight);
        
    }

}
