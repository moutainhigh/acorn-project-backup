package com.chinadrtv.util;

import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class XMLUtils {

    public static boolean validateXml(InputStream xsd, InputStream  xml)
            throws SAXException, IOException {
        // 建立schema工厂
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        // 建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
//		File schemaFile = new File(xsdpath);
        // 利用schema工厂，接收验证文档文件对象生成Schema对象
        Schema schema = schemaFactory.newSchema(new StreamSource(xsd));
        // 通过Schema产生针对于此Schema的验证器，利用schenaFile进行验证
        Validator validator = schema.newValidator();
        // 得到验证的数据源
        Source source = new StreamSource(xml);

        // 开始验证，成功输出success!!!，失败输出fail
        try {
            validator.validate(source);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}

