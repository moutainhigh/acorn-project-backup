package com.chinadrtv.order.download.test;

import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.trade.ShipmentDownControl;
import com.chinadrtv.erp.model.trade.WmsShipmentDetail2;
import com.chinadrtv.order.download.service.OrderFilterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guoguo
 * Date: 13-7-26
 * Time: 上午5:06
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/applicationContext*.xml")
@TransactionConfiguration//( defaultRollback = false)
@Transactional
public class DownloadWmsTest {

    @Autowired
    private OrderFilterService orderFilterService;

    //@Test
    public void testSubStr() {
        String prodSpellName = "百年蔷薇厨prodSpellName.length()";
        if (prodSpellName !=null && prodSpellName.length() > 20)
        prodSpellName = prodSpellName.substring(0, 20);
        System.out.println(prodSpellName);
    }

    //@Test
    public void testUnitPrice(){
        BigDecimal totalAmt = null ;
        WmsShipmentDetail2 wmsSd2 = new WmsShipmentDetail2() ;
        OrderDetail od = new OrderDetail() ;
        od.setUpnum(1);
        od.setUprice(BigDecimal.valueOf(3));
        od.setSpnum(1);
        od.setSprice(BigDecimal.valueOf(2.12));
        totalAmt = BigDecimal.valueOf(0) ;
        wmsSd2.setTotalQty(new Long(new Long(od.getSpnum() + od.getUpnum())));
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
        System.out.println(wmsSd2.getUnitPrice());
    }

    @Test
    public void testRead()
    {
        List<ShipmentDownControl> shipmentDownControlList = orderFilterService.getShipmentDown();
        if(shipmentDownControlList!=null)
        {
            System.out.println(shipmentDownControlList.size());
        }
    }
}
