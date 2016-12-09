package com.chinadrtv.amazon.service.impl;

import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.amazon.model.AmazonOrderConfig;
import com.chinadrtv.amazon.service.ImportTransformService;
import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.payload.JavaResult;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class ImportTransformServiceImpl implements ImportTransformService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ImportTransformServiceImpl.class);

    public ImportTransformServiceImpl()
    {
    }

    private Smooks smooks = null;

    @PostConstruct
    public void init() throws Exception
    {
        try
        {
            smooks = new Smooks("smooks/smooks-config.xml");
        }catch (Exception exp)
        {
            logger.error("smooks init error:", exp);
            throw exp;
        }
    }

    @Override
    public List<PreTradeDto> transform(AmazonOrderConfig amazonOrderConfig, String jsonval) {

        if(smooks==null)
        {
            logger.error("smooks is null");
            return null;
        }
        try {
            jsonval = "{" + jsonval.substring(91, jsonval.length() - 1).toString();

            ExecutionContext executionContext = smooks.createExecutionContext();
            JavaResult result = new JavaResult();
            Source source = new StreamSource(new StringReader(jsonval));
            smooks.filterSource(executionContext, source, result);
            List<PreTradeDto> listpretrade = (List<PreTradeDto>)result.getBean("orderlist");
            if(listpretrade==null)
            {
                logger.error("transform result is null-data:"+jsonval);
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < listpretrade.size(); i++) {
                Date createDate = sdf.parse(listpretrade.get(i).getInvoiceComment().replace('T', ' ').toString().replace('Z', ' ').toString());
                listpretrade.get(i).setCrdt(createDate);
            }
            return listpretrade;
        } catch (Exception e) {
            logger.error("smook transform error:",e);
        } finally {
            //smooks.close();
        }
        return null;
    }

    /**
     * 取特定日期的零点零分零秒
     */
    private Date getDateStart(String strDate) {
        SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null ;
        try {
            date = shortDateFormat.parse(strDate) ;
        } catch (ParseException e) {
        }
        return date ;
    }

    public List<PreTradeDto> transformXMLToPOJO(Smooks smooks, String str) {
        List<PreTradeDto> tradelist = null;
        Source source = new StreamSource(new StringReader(str));

        JavaResult javaResult = new JavaResult();

        smooks.filterSource(source, javaResult);

        tradelist = (List<PreTradeDto>) javaResult.getBean("tradeList");
        return tradelist;
    }
}
