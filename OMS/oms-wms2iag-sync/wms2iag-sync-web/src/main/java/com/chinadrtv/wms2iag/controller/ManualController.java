package com.chinadrtv.wms2iag.controller;

import com.chinadrtv.wms2iag.model.ImportInfo;
import com.chinadrtv.wms2iag.model.ResultInfo;
import com.chinadrtv.wms2iag.sync.biz.WmsSyncBizHandler;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping({ "/wms" })
public class ManualController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ManualController.class);

    public ManualController()
    {
        logger.info("ManualController is created!");
    }

    @Autowired
    private WmsSyncBizHandler wmsSyncBizHandler;

    @RequestMapping(value = "/sync", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResultInfo input(@RequestBody ImportInfo importInfo)
    {
        logger.info("begin manual wms sync");
        ResultInfo resultInfo=new ResultInfo();
        try
        {
            List<Date> timeList = this.parseDateFromInput(importInfo);
            boolean succ=false;
            for(int i=0;i<3;i++)
            {
                if(!wmsSyncBizHandler.wmsSync(DateUtils.addMinutes(timeList.get(0),-5)))
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
            logger.info("end manual wms sync");
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
            list.add(DateUtils.parseDate(importInfo.getStartDate(), dateFormats));
        }

        return list;
    }
}
