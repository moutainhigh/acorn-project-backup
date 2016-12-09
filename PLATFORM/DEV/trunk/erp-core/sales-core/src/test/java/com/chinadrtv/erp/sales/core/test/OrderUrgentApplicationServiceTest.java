/*
 * @(#)OrderUrgentApplicationServiceTest.java 1.0 2013-7-15上午10:03:16
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.test;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.model.cntrpbank.OrderUrgentApplication;
import com.chinadrtv.erp.sales.core.service.OrderUrgentApplicationService;
import com.chinadrtv.erp.test.SpringTest;
import com.chinadrtv.erp.uc.dto.UrgentOrderDto;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-7-15 上午10:03:16 
 * 
 */
public class OrderUrgentApplicationServiceTest extends SpringTest {

	@Autowired
	private OrderUrgentApplicationService orderUrgentApplicationService;
	 
	@SuppressWarnings("unchecked")
	//@Test
	public void queryPageList(){
		UrgentOrderDto dto = new UrgentOrderDto();
		DataGridModel dataGrid = new DataGridModel();
		dataGrid.setPage(1);
		dataGrid.setStartRow(1);
		Map<String, Object> pageMap = orderUrgentApplicationService.queryPageList(dto, dataGrid);
		
		Integer total = (Integer) pageMap.get("total");
		List<UrgentOrderDto> pageList = (List<UrgentOrderDto>) pageMap.get("rows");
		
		Assert.assertTrue(total >= 0);
		Assert.assertTrue(pageList.size() >= 0);
	}
	
	//@Test
	public void queryFinishedUrgentOrder() throws Exception{
		List<OrderUrgentApplication> list = orderUrgentApplicationService.queryFinishedUrgentOrder("27430", null, null);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() >= 0);
	}

    @Test
    public void test()  throws Exception
    {
        /*HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://192.168.3.170:8094/order/logistics/query");
                                         http://192.168.3.170:8094/order/logistics/query
        StringEntity se = new StringEntity("{\"orderId\":\"922034214\"}","UTF-8");
                                           "{\"orderId\", \"922034214\"}","UTF-8"
        se.setContentType("application/json");
        request.setEntity(se);

        HttpResponse response =client.execute(request);

        int code=response.getStatusLine().getStatusCode();
        byte[] datas=new byte[100];
        int len=response.getEntity().getContent().read(datas);
        String str=new String(datas,0,len,"UTF-8");
*/
        HttpPost post = new HttpPost("http://192.168.3.170:8094/order/logistics/query");
        StringEntity entity = new StringEntity("{\"orderId\":\"922034214\"}", "UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
        HttpClient client = new DefaultHttpClient();
        HttpResponse resp = client.execute(post);

        if (resp.getStatusLine().getStatusCode() == 200) {
            /* 读返回数据 */
            String res = EntityUtils.toString(resp.getEntity());
            System.out.println(res);
        }


        //String str=HttpUtil.postUrl("http://192.168.3.170:8094/order/logistics/query","{\"orderId\":\"922034214\"}","UTF-8");
        //System.out.println(str);
    }
}
