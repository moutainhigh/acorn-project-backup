/**
 * 
 */
package com.chinadrtv.erp.oms.util;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * 解析xml 测试
 * @author haoleitao
 * @date 2013-1-5 上午11:19:39
 *
 */
public class SaxParseXmlUtilTest {
    @Test
    public void readXml(){  
    	String str ="<ops_trades_response><ops_trade_id>184855543783449</ops_trade_id><agent_trade_id>2012122800001000630025610083</agent_trade_id><result>false</result><message_code>007</message_code><message>订单总价和商品明细价格合计不相等</message></ops_trades_response>";
    	String r = SaxParseXmlUtil._readXml(str, "ops_trade_id").get(0).get("ops_trade_id");
    	assertTrue(r.equals("184855543783449"));
    }  
}
