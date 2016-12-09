package com.chinadrtv.acorn.service.impl;

import com.chinadrtv.acorn.bean.FeedbackInfo;
import com.chinadrtv.acorn.service.XmlCheckService;
import com.chinadrtv.util.XMLUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class XmlCheckServiceImpl implements XmlCheckService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(XmlCheckServiceImpl.class);

    @Value("${xml.schema}")
    private String xmlSchemaPath;

    private Schema schema;

    @PostConstruct
    private void init()
    {
        try
        {
            ClassLoader classLoader = XmlCheckServiceImpl.class.getClassLoader();
            InputStream schemaStream = classLoader.getResourceAsStream(xmlSchemaPath);
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            schema = schemaFactory.newSchema(new StreamSource(schemaStream));
        }catch (Exception exp)
        {
            schema=null;
            logger.error("init schema error:",exp);
        }
    }

    @Override
    public boolean checkXml(FeedbackInfo feedbackInfo) {
        if(schema==null)
        {
            logger.error("schema is null");
            return false;
        }
        try{
            Validator validator = schema.newValidator();
            InputStream xmlStream = new ByteArrayInputStream(feedbackInfo.getXml().getBytes("utf-8"));
            Source source = new StreamSource(xmlStream);
            validator.validate(source);
            return true;
        }catch (Exception exp)
        {
            logger.error("validate error:",exp);
            logger.error("invalidate data:"+feedbackInfo.getXml());
            return false;
        }
    }

    /*public boolean checkXml2(FeedbackInfo feedbackInfo)
    {
        boolean result = false;
        try {
            ClassLoader classLoader = XmlCheckServiceImpl.class.getClassLoader();
            InputStream schemaStream = classLoader.getResourceAsStream(xmlSchemaPath);
            InputStream xmlStream = new ByteArrayInputStream(feedbackInfo.getXml().getBytes("utf-8"));

            result = XMLUtils.validateXml(schemaStream, xmlStream);
        } catch (Exception e) {
            result = false;
            logger.error("xml schema errorr:",e);
            e.printStackTrace();
        }

        return result;
    } */
}
