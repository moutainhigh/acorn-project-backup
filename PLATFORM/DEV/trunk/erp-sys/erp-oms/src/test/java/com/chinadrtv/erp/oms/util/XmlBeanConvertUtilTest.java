package com.chinadrtv.erp.oms.util;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.dom4j.Element;
import org.dom4j.Node;
import org.junit.Test;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Bean 转 XML Test
 * @author haoleitao
 * @date 2012-12-28 下午4:39:01
 *
 */
public class XmlBeanConvertUtilTest {
	 private static final Log log = LogFactory.getLog(XmlBeanConvertUtil.class);
	
	 private static  final String domain_package = "com.chinadrtv.erp.model";
	 private static  final String domain_package_dot = "com.chinadrtv.erp.model.";
	 

	 
	 /*
	  * 用于 genenateXml()方法 从对象中得到值，以生成xml
	  */
	 public static String getFieldValue(Object objValue, String name) {
		 //重命名属性名
		 name = Tt_(name);
	  StringBuffer xml = new StringBuffer();
	  if (objValue instanceof java.util.Set) {
		  if(name.equals("pre_trade_details")) name="skus";
	   xml.append("<" + name + ">");
	   Set list = (Set) objValue;
	   if (list == null || list.size() == 0) { // 如果是集合就递归
	   } else {
	    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	     Object object = (Object) iterator.next();
	     xml.append(generateXML_Detail(object));
	    }
	   }
	   xml.append("</" + name + ">");
	  } 
	  
	  else if(objValue!=null&&objValue.getClass().getPackage().getName().equals(domain_package)){
	   xml.append(generateXML(objValue));
	  }if(objValue==null){
	   xml.append("<" + name + ">");
	   xml.append("</" + name + ">");
	  }
	  else if (objValue instanceof java.util.Date) { // 处理时间类型
	   xml.append("<" + name + ">");
	   java.util.Date valueDate = (java.util.Date) objValue;
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	   xml.append(formatter.format(valueDate));
	   xml.append("</" + name + ">");
	  } else if (objValue instanceof String) {
	   xml.append("<" + name + ">");
	   xml.append(objValue);
	   xml.append("</" + name + ">");
	  } else if (objValue instanceof Integer) {
	   xml.append("<" + name + ">");
	   xml.append(String.valueOf(objValue));
	   xml.append("</" + name + ">");
	  }else if (objValue instanceof Double) {
		   xml.append("<" + name + ">");
		   xml.append(String.valueOf(objValue));
		   xml.append("</" + name + ">");
	  }else if (objValue instanceof Long) {
		   xml.append("<" + name + ">");
		   xml.append(String.valueOf(objValue));
		   xml.append("</" + name + ">");
		  }
	  
	  return xml.toString();
	 }

	 /**
	  * 生成xml
	  */
	 public static String generateXML(Object object) {
	  Class cls_class = object.getClass();
	  String cls_str = cls_class.toString();
	  String objname = cls_str.substring(cls_str.lastIndexOf(".") + 1,
	    cls_str.length());// 得到类名称，包含前缀
	  StringBuffer xml = new StringBuffer();
	  //重命名
	  
	  objname = "ops_trade";
	  xml.append("<" + objname + ">");
	  Field[] fields = cls_class.getDeclaredFields(); // 得到成员属性名称，不包含前缀
	  String name = "";
	  try {
	   for (int i = 0; i < fields.length; i++) {
	    name = fields[i].getName();
	    if (name.equals("preTradeLot") || name.equals("preTrade")) { // 如果是序列号就去掉
	     continue;
	    }
	    Object objValue = PropertyUtils.getProperty(object, name); // 得到属性值
	    xml.append(getFieldValue(objValue, name));
	   }

	  } catch (Exception e) {
	   log.error("在"+object+"中没有找到属性名为"+name+"的方法...", e);
	  }
	  xml.append("</" + objname + ">");
	  return xml.toString();
	 }
	 
	 
	 /**
	  * 生成xml
	  */
	 public static String generateXML_Detail(Object object) {
	  Class cls_class = object.getClass();
	  String cls_str = cls_class.toString();
	  String objname = cls_str.substring(cls_str.lastIndexOf(".") + 1,
	    cls_str.length());// 得到类名称，包含前缀
	  StringBuffer xml = new StringBuffer();
	  //重命名
	  
	  objname = "sku";
	  xml.append("<" + objname + ">");
	  Field[] fields = cls_class.getDeclaredFields(); // 得到成员属性名称，不包含前缀
	  String name = "";
	  try {
	   for (int i = 0; i < fields.length; i++) {
	    name = fields[i].getName();
	    if (name.equals("preTradeLot") || name.equals("preTrade")) { // 如果是序列号就去掉
	     continue;
	    }
	    
	    Object objValue = PropertyUtils.getProperty(object, name); // 得到属性值
	    if(name.equals("qty")){
	    	name ="number";
	    	 xml.append(getFieldValue(objValue, name));
	    }else if(name.equals("upPrice")){
	    	 name ="price";
	    	 xml.append(getFieldValue(objValue, name));
	    }else if(name.equals("outSkuId")){
	    	 name ="sku_code";
	    	 //如果outskuId为null
	    	 if(objValue == null){
	    		 objValue = PropertyUtils.getProperty(object, "skuId");
	    	 }
	    	 xml.append(getFieldValue(objValue, name));
	    }else{
	    	continue;
	    }
	    
	   
	   }

	  } catch (Exception e) {
	   log.error("在"+object+"中没有找到属性名为"+name+"的方法...", e);
	  }
	  xml.append("</" + objname + ">");
	  return xml.toString();
	 }

	 /*
	  * 生成bean
	  */
	 public static Object generateBean(Element strXML) {
	  String name = "";
	  String type = "";
	  String classname = "";
	  Object object = null;
	  try {
	   classname = domain_package_dot + strXML.getName();
	   object = Class.forName(classname).newInstance();
	   List<Element> e_array = strXML.elements();
	   for (Iterator iterator = e_array.iterator(); iterator.hasNext();) {
	    {
	     Element element = (Element) iterator.next();
	     name = element.getName();
	     log.debug("查询到方法节点名"+name);
	     type = PropertyUtils.getPropertyType(object, name.toLowerCase())
	       .getName();
	     object = newObjectByType(object, type, name, element);
	    }
	   }
	  } catch (Exception e) {
	   log.error(classname+"不存在或者在"+classname+"中没有找到属性名:"+name.toLowerCase(), e);
	  }
	  return object;
	 }

	 /**
	  * 根据参数给类赋值
	  * @param object 
	  * @param type
	  * @param field_name
	  * @param element
	  * @return
	  */
	 private static Object newObjectByType(Object object, String type,
	   String field_name, Element element) {
	  try {
	   if (type.endsWith("java.lang.String")) {      //处理String类型

	    PropertyUtils
	      .setProperty(object, field_name, element.getText());
	   } else if (type.endsWith("java.lang.Integer")) {   //处理integer
	    System.out.println(type.endsWith("java.lang.Integer"));
	    PropertyUtils.setProperty(object, field_name, Integer
	      .parseInt(element.getText()));
	   } else if (type.endsWith("java.util.Set")) { 
	    Set set = new HashSet();//处理set
	    List<Element> e_list = element.elements();
	    for (Iterator iterator = e_list.iterator(); iterator.hasNext();) {
	     Element object2 = (Element) iterator.next();
	     generateBean(object2);
	     set.add(generateBean(object2));
	     PropertyUtils.setProperty(object, field_name, set);
	    }
	   }else if(type.startsWith(domain_package)){
	    if(getBeanById(element)!=null){
	    Class cls = Class.forName(domain_package_dot+element.getName());
	    PropertyUtils.setProperty(object, field_name.toLowerCase(), cls.cast(getBeanById(element)));
	    }
	   }
	  } catch (Exception e) {
	   log.error(domain_package_dot+element.getName()+"类不存在", e);
	  }
	  return object;
	 }

	 private static Object getBeanById(Element e) throws Exception {
	  Object object = null;
	  Node node = e.selectSingleNode("//id");
	  log.debug("没有找到属性"+e.getName()+"的id");
	  if(node!=null){
	   Integer id = Integer.parseInt(node.getText());
	  // object =  shiftService.getObjectById(Class.forName(domain_package_dot+e.getName()),id);
	  }
	  return object;
	 }

	 /**
	  * 生成最终xml，加上头
	  * @param args
	  */
	 public static String beanToXml(Object object){
	  String xml = "<?xml version='1.0' encoding='UTF-8'?>"
	   + generateXML(object);
	  log.debug("生成最终xml："+xml);
	  return xml;
	 }
	 
	 
	 public static String Tt_(String name){
		 StringBuffer result = new StringBuffer("");
		 name.length();
		 for(int i=0;i<name.length();i++){
			char m = name.charAt(i);
			if('A' <= m && m <= 'Z' ){
				result.append("_");
				result.append(Character.toLowerCase(m));
			}else{
				result.append(m);
			}
		 }
			
		 return result.toString() ;
	 }
	 
	@Test
	 public void test(){
		 String name = "aaaBBccDD";
		 System.out.println(XmlBeanConvertUtil.Tt_(name));
		 assertTrue(XmlBeanConvertUtil.Tt_(name).equals("aaa_b_bcc_d_d"));
	 }

		 
		 

}
