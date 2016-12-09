/**
 * 
 */
package com.chinadrtv.erp.marketing.core.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author haoleitao
 * @date 2013-1-5 上午11:19:39
 *
 */
public class SaxParseXmlUtil extends DefaultHandler{
    
	private HashMap<String, String> map = null;  
    private List<HashMap<String, String>> list = null;  
    /** 
     * 正在解析的元素的标签 
     */  
    private String currentTag = null;  
    /** 
     * 正在解析的元素的值 
     */  
    private String currentValue = null;  
    private String nodeName = null;  
      
    public List<HashMap<String, String>> getList(){  
        return list;  
    }  
  
    public SaxParseXmlUtil(String nodeName) {  
        this.nodeName = nodeName;  
    }  
  
    @Override  
    public void startDocument() throws SAXException {  
        // TODO 当读到一个开始标签的时候，会触发这个方法  
        list = new ArrayList<HashMap<String,String>>();  
    }  
  
    @Override  
    public void startElement(String uri, String localName, String name,  
            Attributes attributes) throws SAXException {  
        // TODO 当遇到文档的开头的时候，调用这个方法  
        if(name.equals(nodeName)){  
            map = new HashMap<String, String>();  
        }  
        if(attributes != null && map != null){  
            for(int i = 0; i < attributes.getLength();i++){  
                map.put(attributes.getQName(i), attributes.getValue(i));  
            }  
        }  
        currentTag = name;  
    }  
      
    @Override  
    public void characters(char[] ch, int start, int length)  
            throws SAXException {  
        // TODO 这个方法用来处理在XML文件中读到的内容  
        if(currentTag != null && map != null){  
            currentValue = new String(ch, start, length);  
            if(currentValue != null && !currentValue.trim().equals("") && !currentValue.trim().equals("\n")){  
                map.put(currentTag, currentValue);  
            }  
        }  
        currentTag=null;  
        currentValue=null;  
    }  
  
    @Override  
    public void endElement(String uri, String localName, String name)  
            throws SAXException {  
        // TODO 在遇到结束标签的时候，调用这个方法  
        if(name.equals(nodeName)){  
            list.add(map);  
            map = null;  
        }  
        super.endElement(uri, localName, name);  
    }  
    
    
    public static List<HashMap<String, String>> _readXml(String str, String nodeName){  
        try {  
          	StringReader stringReader  =  new StringReader(str);

        	InputSource  inputSource  =  new  InputSource(stringReader);
        	
            SAXParserFactory sf = SAXParserFactory.newInstance();
            SAXParser sp = sf.newSAXParser();
            SaxParseXmlUtil util=new SaxParseXmlUtil(nodeName);
            sp.parse(inputSource,util);
            stringReader.close();
            return util.getList();  
        } catch (ParserConfigurationException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (SAXException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
    public static void main(String args[]){
    	//String str ="<row><person><name>王小明</name><college>信息学院</college> <telephone>6258113</telephone><notes>男,1955年生,博士，95年调入海南大学</notes></person></row>";
    	String str ="<ops_trades_response><ops_trade_id>184855543783449</ops_trade_id><agent_trade_id>2012122800001000630025610083</agent_trade_id><result>false</result><message_code>007</message_code><message>订单总价和商品明细价格合计不相等</message></ops_trades_response>";
    	String r =SaxParseXmlUtil._readXml(str, "ops_trade_id").get(0).get("ops_trade_id");
        System.out.println("result:"+r);
    }
    
    
}
