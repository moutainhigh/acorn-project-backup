package com.chinadrtv.util;

import javax.management.modelmbean.XMLParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.util.exception.XmlParserException;
import com.thoughtworks.xstream.XStream;

public class XmlUtil {

    private static Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    /**
     * xml 转化为java bean
     * 
     * @param xml 
     * @return  java bean
     * @throws XMLParseException 
     */

    @SuppressWarnings("unchecked")
    public static <T> T toObject(String xml) throws XmlParserException {
        if (xml == null) {
            logger.warn("xml is null!");
            throw new XmlParserException("xml content is null");
        }
        XStream xstream = new XStream();
        T object;
        try {
            object = (T) xstream.fromXML(xml);
        } catch (RuntimeException e) {
            logger.error("xml:" + xml, e);
            throw new XmlParserException("xml:" + xml, e);
        }
        return object;
    }

    /**
     * java bean 转化为xml
     * 
     * @param object  bean
     * @param beanClasses   需要转化成xml的bean对象的class
     * @return  string of xml
     * @throws XMLParseException 
     */

    public static String toXml(Object object) throws XmlParserException {
        if (object == null) {
            logger.warn("object is null!");
            throw new XmlParserException("object is null");
        }
        XStream xstream = new XStream();
        String xml;
        try {
            xml = xstream.toXML(object);
        } catch (RuntimeException e) {
            logger.error("object:" + object.getClass(), e);
            throw new XmlParserException("object:" + object.getClass(),e);
        }
        return xml;
    }

    /**
     * 对于xstream的annotation的说明
     * 将下面的annotation配置在java bean的属性或者类名上
     * 可以参考本项目的test目录中的ConfigCenters.java
     * @XStreamAlias("xx")
     * 设置xml中element的别名
     * @XStreamAsAttribute 
     * 将该字段设为根标签的attribute
     * @XStreamImplicit(itemFieldName="XX")
     * 将List的根节点去掉和改变列表名字
     * @XStreamOmitField  
     * 忽略该属性
     * @param object   java bean
     * @param beanClasses 
     * @return  xml of bean
     * @throws XMLParseException 
     */

    public static String toXmlByAnnotation(Object object) throws XmlParserException {
        if (object == null) {
            logger.warn("object is null!");
            throw new XmlParserException("object is null");
        }
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        return xstream.toXML(object);
    }

    /**
     * 
     * @param xml toXmlByAnnotation产生的xml对应
     * @param beanClasses
     * @return
     * @throws XMLParseException 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T toObjectByAnnotation(String xml, Class beanClass) throws XmlParserException {
        if (beanClass == null) {
            logger.warn("beanClasses is null!");
            return null;
        }
        if (xml == null) {
            logger.warn("xml is null!");
            return null;
        }
        XStream xstream = new XStream();
        xstream.processAnnotations(beanClass);
        T object;
        try {
            object = (T) xstream.fromXML(xml);
        } catch (RuntimeException e) {
            logger.error("xml:" + xml, e);
            throw new XmlParserException("xml:" + xml,e);
        }
        return object;
    }

}
