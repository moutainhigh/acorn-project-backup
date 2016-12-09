/**
 * 
 */
package com.chinadrtv.erp.oms.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

 

/**
 * 解析ＸＭＬ
 * @author haoleitao
 * @date 2013-1-5 上午10:24:47
 *
 */
public class ParseXmlUtil {
	private static final Logger log = LoggerFactory.getLogger(ParseXmlUtil.class);
	
	public static String getNodeValue(String str,String tagName) {
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		  DocumentBuilder builder;
		   try   {
		   builder  =  factory.newDocumentBuilder();
		   InputStream in = new  ByteArrayInputStream(str.getBytes());
		   log.info("doc"+in);
		   Document doc  =  builder.parse(in); 
		   log.info("doc"+doc.getTextContent());
		  }   catch  (ParserConfigurationException e)  {
		    //  TODO Auto-generated catch block
		   e.printStackTrace();
		  }   catch  (SAXException e)  {
		    //  TODO Auto-generated catch block
		   e.printStackTrace();
		  }   catch  (IOException e)  {
		    //  TODO Auto-generated catch block
		   e.printStackTrace();
		  }  
		   
		   return "";
	}

	
	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException{
		String str ="<?xml version=\"1.0\" encoding=\"utf-8\"?><ops_trades_response><ops_trade_id>209501112447545</ops_trade_id><agent_trade_id>2012121700001000300023617965</agent_trade_id><result>false</result><message_code>002</message_code><message>订单重复, 该订单已经被导入过</message></ops_trades_response>";
		log.info("str:"+ParseXmlUtil.getNodeValue(str,"message_code"));
		
	}

}
