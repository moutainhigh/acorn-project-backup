package com.chinadrtv.taobao.controller;

import com.chinadrtv.taobao.biz.OrderBizHandler;
import com.chinadrtv.taobao.biz.SourceConfigure;
import com.chinadrtv.taobao.model.ImportInfo;
import com.chinadrtv.taobao.model.ResultInfo;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.service.OrderImportService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping({ "/taobao" })
public class ManualImportController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ManualImportController.class);

    @Autowired
    private OrderBizHandler orderBizHandler;

    @Autowired
    @Qualifier("sourceConfigure")
    private SourceConfigure sourceConfigure;


    @RequestMapping(value = "/import", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResultInfo input(@RequestBody ImportInfo importInfo)
    {
        logger.info("begin manual import");
        ResultInfo resultInfo=new ResultInfo();
        try
        {
            List<Date> timeList = this.parseDateFromInput(importInfo);
            List<TaobaoOrderConfig> configList=new ArrayList<TaobaoOrderConfig>();
            if(!StringUtils.isEmpty(importInfo.getSourceId()))
            {
                int sourceId=Integer.parseInt(importInfo.getSourceId());
                for(TaobaoOrderConfig taobaoOrderConfig:sourceConfigure.getTaobaoOrderConfigList())
                {
                    if(sourceId==taobaoOrderConfig.getSourceId().intValue())
                    {
                        configList.add(taobaoOrderConfig);
                    }
                }
            }
            else
            {
                configList.addAll(sourceConfigure.getTaobaoOrderConfigList());
            }
            if(configList!=null)
            {
                boolean succ=false;
                for(int i=0;i<3;i++)
                {
                    if(!orderBizHandler.input(configList,timeList.get(0),timeList.get(1)))
                    {
                        Thread.sleep(10000);
                    }
                    else
                    {
                        succ=true;
                        break;
                    }
                }
                if(succ==false)
                {
                    resultInfo.setSucc(false);
                    resultInfo.setErrorMsg("正在进行定时导入，请稍后重试。");
                }
            }
            return resultInfo;
        }
        catch (Exception exp)
        {
            logger.error("import error:", exp);
            resultInfo.setSucc(false);
            resultInfo.setErrorMsg(exp.getMessage());
            return resultInfo;
        }
        finally {
            logger.info("end manual import");
        }
    }

    @RequestMapping(value = "/feedback", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResultInfo feedback(@RequestBody ImportInfo importInfo)
    {
        logger.info("begin manual feedback");
        ResultInfo resultInfo=new ResultInfo();
        try
        {
            List<TaobaoOrderConfig> configList=new ArrayList<TaobaoOrderConfig>();
            if(!StringUtils.isEmpty(importInfo.getSourceId()))
            {
                int sourceId=Integer.parseInt(importInfo.getSourceId());
                for(TaobaoOrderConfig taobaoOrderConfig:sourceConfigure.getTaobaoOrderConfigList())
                {
                    if(sourceId==taobaoOrderConfig.getSourceId().intValue())
                    {
                        configList.add(taobaoOrderConfig);
                    }
                }
            }
            else
            {
                configList.addAll(sourceConfigure.getTaobaoOrderConfigList());
            }
            if(configList!=null)
            {
                boolean succ=false;
                for(int i=0;i<3;i++)
                {
                    if(!orderBizHandler.feedback(configList))
                    {
                        Thread.sleep(10000);
                    }
                    else
                    {
                        succ=true;
                        break;
                    }
                }
                if(succ==false)
                {
                    resultInfo.setSucc(false);
                    resultInfo.setErrorMsg("正在进行定时反馈，请稍后重试。");
                }
            }
            return resultInfo;
        }
        catch (Exception exp)
        {
            logger.error("feedback error:", exp);
            resultInfo.setSucc(false);
            resultInfo.setErrorMsg(exp.getMessage());
            return resultInfo;
        }
        finally {
            logger.info("end manual feedback");
        }
    }

    private String[] dateFormats=new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"};

    private List<Date> parseDateFromInput(ImportInfo importInfo) throws Exception
    {
        List<Date> list=new ArrayList<Date>();
        if(StringUtils.isEmpty(importInfo.getStartDate()))
        {
            list.add(new Date());
        }
        else
        {
            list.add(DateUtils.parseDate(importInfo.getStartDate(), dateFormats));
        }

        if(StringUtils.isEmpty(importInfo.getEndDate()))
        {
            list.add(DateUtils.addDays(list.get(0), 1));
        }
        else
        {
            list.add(DateUtils.parseDate(importInfo.getEndDate(), dateFormats));
        }

        return list;
    }
}
