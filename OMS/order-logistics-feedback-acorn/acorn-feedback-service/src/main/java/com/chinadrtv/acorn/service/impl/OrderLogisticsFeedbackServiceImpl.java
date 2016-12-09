package com.chinadrtv.acorn.service.impl;

import com.chinadrtv.acorn.bean.CheckResult;
import com.chinadrtv.acorn.bean.FeedbackInfo;
import com.chinadrtv.acorn.bean.UserConfig;
import com.chinadrtv.acorn.service.OrderLogisticsFeedbackService;
import com.chinadrtv.acorn.service.XmlCheckService;
import com.chinadrtv.acorn.service.XmlLogService;
import com.chinadrtv.model.iagent.MailStatusHis;
import com.chinadrtv.util.MD5Util;
import com.chinadrtv.util.XMLUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.geronimo.mail.util.Hex;
import org.milyn.Smooks;
import org.milyn.payload.JavaResult;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderLogisticsFeedbackServiceImpl implements OrderLogisticsFeedbackService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OrderLogisticsFeedbackServiceImpl.class);

    public OrderLogisticsFeedbackServiceImpl()
    {
        logger.info("OrderLogisticsFeedbackServiceImpl is created");
    }
    @Value("${first.exction_rule}")
    private String FIRST_EXCEPTION_RULE;
    @Value("${second.exction_rule}")
    private String SECOND_EXCEPTION_RULE;
    @Value("${third.exction_rule}")
    private String THIRD_EXCEPTION_RULE;
    @Value("${fourth.exction_rule}")
    private String FOURTH_EXCEPTION_RULE;
    @Value("${user.exction_notexist}")
    private String USER_EXCEPTION_NOTEXIST;
    @Value("${send.success}")
    private String SEND_SUCCESS;

    @Value("${xml.schema}")
    private String xmlSchemaPath;

    @Value("${data_encode}")
    private String serviceEncode;

    private Smooks smooks;

    @PostConstruct
    public void init()  throws Exception
    {
        try{
            smooks = new Smooks("smooks/smooks-acorn-config.xml");
        }catch (Exception exp)
        {
            logger.error("init smooks error:",exp);
            throw exp;
        }
    }

    @Override
    public CheckResult checkFeedbackData(FeedbackInfo feedbackInfo) {
        CheckResult checkResult=new CheckResult(true,SEND_SUCCESS);
        if(!this.checkRuleOne(feedbackInfo))
        {
            checkResult.setSucc(false);
            checkResult.setErrorMsg(FIRST_EXCEPTION_RULE);
            return checkResult;
        }

        String strPwd=this.checkUser(feedbackInfo.getUserId());
        if(StringUtils.isEmpty(strPwd))
        {
            checkResult.setSucc(false);
            checkResult.setErrorMsg(USER_EXCEPTION_NOTEXIST);
            return checkResult;
        }
        if(!checkData(feedbackInfo,strPwd))
        {
            checkResult.setSucc(false);
            checkResult.setErrorMsg(SECOND_EXCEPTION_RULE);
            return checkResult;
        }
        if(!checkXmlData(feedbackInfo))
        {
            checkResult.setSucc(false);
            checkResult.setErrorMsg(THIRD_EXCEPTION_RULE);
            return checkResult;
        }

        //logger.info("org hex data:"+feedbackInfo.getXml());
        //logger.info("hex str data:"+new String(Hex.encode(MD5Util.decodeBytesHex(feedbackInfo.getXml()))));
        byte[] datas=MD5Util.decodeBytesHex(feedbackInfo.getXml());
        //logger.info("org hex data:"+new String(Hex.encode(datas)));
        try{
            if(StringUtils.isEmpty(serviceEncode))
            {
                feedbackInfo.setXml(new String(datas,"UTF-8"));
            }
            else
            {
                feedbackInfo.setXml(new String(datas,serviceEncode));
            }
        }catch (Exception exp)
        {
            logger.error("data convert error:",exp);
            logger.error("org hex data:"+new String(Hex.encode(datas)));
            checkResult.setSucc(false);
            checkResult.setErrorMsg(FOURTH_EXCEPTION_RULE);
            return checkResult;
        }
        if(!checkXmlSchema(feedbackInfo))
        {
            checkResult.setSucc(false);
            checkResult.setErrorMsg(FOURTH_EXCEPTION_RULE);
            return checkResult;
        }
        return checkResult;
    }

    @Autowired
    private XmlLogService xmlLogService;

    @Override
    public List<MailStatusHis> parseFeedbackData(FeedbackInfo feedbackInfo) {
        //Smooks smooks = null;
        xmlLogService.logXml(feedbackInfo);

        List<MailStatusHis> updateInfoList = null;
        try {
            Source source = new StreamSource(new StringReader(feedbackInfo.getXml()));
            //Source source = new StreamSource(new StringReader(feedbackInfo.getXml()));//new String(feedbackInfo.getXml().getBytes(),"gb2312")));
            JavaResult javaResult = new JavaResult();
            smooks.filterSource(source, javaResult);

            updateInfoList = (List<MailStatusHis>) javaResult.getBean("updateInfoList");
        } catch (Exception e) {
            logger.error("transform error:",e);
            logger.error("transform error data:"+feedbackInfo.getXml());
            e.printStackTrace();
        } finally {
            /*if (smooks != null) {
                smooks.close();
            }*/
        }
        return updateInfoList;
    }

    private boolean checkRuleOne(FeedbackInfo feedbackInfo) {
        if(feedbackInfo.getCheckData()==null||feedbackInfo.getUserId()==null||feedbackInfo.getXml()==null)
            return false;
        else
            return true;
    }

    /*public Map<String, String> getUserConfigList() {
        return userConfigList;
    }

    @Autowired
    @Qualifier("userConfig")
    public void setUserConfigList(Map<String, String> userConfigList) {
        this.userConfigList = userConfigList;
    }

    private Map<String,String> userConfigList;*/
    private String checkUser(String userId) {
        if (UserConfig.userConfigInfo.containsKey(userId)) {
            return UserConfig.userConfigInfo.get(userId);
        } else {
            return null;
        }
    }

    private boolean checkData(FeedbackInfo feedbackInfo , String pwd) {
        Boolean Return = false;

        if  (!StringUtils.isEmpty(feedbackInfo.getCheckData()))
        {
            String EncStr = MD5Util.getMD5Hex(feedbackInfo.getUserId()+feedbackInfo.getXml()+pwd).toUpperCase();

            if (feedbackInfo.getCheckData().toUpperCase().equals(EncStr)){
                Return = true;
            }
        }
        return Return;
    }

    private static Pattern xmlPattern=Pattern.compile("[0-9a-fA-F]+");

    public boolean checkXmlData(FeedbackInfo feedbackInfo) {
        //Pattern p = Pattern.compile("[0-9a-fA-F]+");
        Matcher matcher = xmlPattern.matcher(feedbackInfo.getXml());
        return matcher.matches();
    }

    @Autowired
    private XmlCheckService xmlCheckService;

    private boolean checkXmlSchema(FeedbackInfo feedbackInfo) {
        return xmlCheckService.checkXml(feedbackInfo);
        /*boolean result = false;
        //String s_xmlpath="com/spf/web/ext/hotspot/hotspotxml/hotspot.xml";
        //File schema = new File(xmlSchemaPath);
        try {
            ClassLoader classLoader = OrderLogisticsFeedbackServiceImpl.class.getClassLoader();
            InputStream schemaStream = classLoader.getResourceAsStream(xmlSchemaPath);
            InputStream xmlStream = new ByteArrayInputStream(feedbackInfo.getXml().getBytes("utf-8"));

            result = XMLUtils.validateXml(schemaStream, xmlStream);
        } catch (Exception e) {
            result = false;
            logger.error("xml schema errorr:",e);
            e.printStackTrace();
        }

        return result;*/
    }
}
