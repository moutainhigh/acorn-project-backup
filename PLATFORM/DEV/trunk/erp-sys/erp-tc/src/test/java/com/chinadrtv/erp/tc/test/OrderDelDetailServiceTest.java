package com.chinadrtv.erp.tc.test;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.service.OrderDelDetailService;

/**
 * 删除订单明细
 * @author taoyawei
 *
 */
public class OrderDelDetailServiceTest extends TCTest{
	
	 @Autowired
	 private OrderDelDetailService orderDelDetailService;
	 @Test
	 public void  getCouponRevisebyProc()
	    {
		 //删除:{"orderdetid":"2240565","mdusr":"sa"}
		 Map<String, Object> map=new HashMap<String,Object>();
		 map.put("orderdetid","2240565");
		 map.put("mdusr","sa");
		 int detid=0;
	        String str="Y";
	        try {
	          OrderDetail orderdet1=orderDelDetailService.queryOrderdetbydetid(map.get("orderdetid").toString());
	          if(orderdet1.getOrderid()!=null){
	          String orderid=orderdet1.getOrderid();
	          orderDelDetailService.insertTcHistory(orderid);//生成快照
	          detid= orderDelDetailService.delOrderDetail(orderdet1);//删除明细
	          List<OrderDetail> orderdetList=orderDelDetailService.queryOrderdetbyorid(orderid);
	          orderDelDetailService.totalOrderHistprice(orderdetList,orderid);//重算金额及运费
	          str=orderDelDetailService.conticketProc(map.get("mdusr").toString(), orderid);
	          }
	          if(detid!=0)
	           System.out.print("删除订单明细成功"+str);
	          System.out.print("删除订单明细失败"+str);
	          
	        } catch (Exception e) {
	         System.out.print("删除订单明细失败");
	        }
	    }

}