package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.model.cntrpbank.InvoicereSubmit;
import com.chinadrtv.erp.sales.core.util.ExceptionMsgUtil;
import com.chinadrtv.erp.sales.dto.MyOrderFrontAuditDto;
import com.chinadrtv.erp.sales.service.SourceConfigure;
import com.chinadrtv.erp.service.core.dto.InvoicereDto;
import com.chinadrtv.erp.service.core.service.InvoicereSubmitService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping("/invoice")
public class InvoicereController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(InvoicereController.class);

    @Autowired
    protected InvoicereSubmitService invoicereSubmitService;

    @Autowired
    private SourceConfigure sourceConfigure;

    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryInvoice(DataGridModel dataGridModel, InvoicereDto invoicereDto)
    {
        try
        {
            Map<String, Object> pageMap = new HashMap<String, Object>();

            Integer beginRow = dataGridModel.getStartRow();

            invoicereDto.setStartPos(beginRow.intValue());
            invoicereDto.setPageSize(dataGridModel.getRows());

            boolean bQueryDateValid=true;
            if(invoicereDto.getBeginDate()==null&&invoicereDto.getEndDate()==null)
            {
                bQueryDateValid=false;
            }
            else if (invoicereDto.getBeginDate()!=null&&invoicereDto.getEndDate()==null)
            {
                Date dateEnd=DateUtils.addDays(invoicereDto.getBeginDate(),this.sourceConfigure.getInvoiceQueryDays());
                if(dateEnd.compareTo(new Date())<0)
                {
                    bQueryDateValid=false;
                }
            }
            else if(invoicereDto.getBeginDate()==null&&invoicereDto.getEndDate()!=null)
            {
                 bQueryDateValid=false;
            }
            else
            {
                Date dateEnd=DateUtils.addDays(invoicereDto.getBeginDate(),this.sourceConfigure.getInvoiceQueryDays());
                if(dateEnd.compareTo(invoicereDto.getEndDate())<0)
                {
                    bQueryDateValid=false;
                }
            }

            if(bQueryDateValid==false)
            {
                pageMap.put("total", 0);
                pageMap.put("rows", new ArrayList<MyOrderFrontAuditDto>());
                pageMap.put("err", "查询日期必须在"+this.sourceConfigure.getInvoiceQueryDays()+"天内");
                return pageMap;
            }

            //调整日期内的时间
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormatFull=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                if(invoicereDto.getBeginDate()!=null)
                {
                    String str=simpleDateFormat.format(invoicereDto.getBeginDate());
                    invoicereDto.setBeginDate(simpleDateFormatFull.parse(str+" 00:00:00"));
                }
                if(invoicereDto.getEndDate()!=null)
                {
                    String str=simpleDateFormat.format(invoicereDto.getEndDate());
                    invoicereDto.setEndDate(simpleDateFormatFull.parse(str+" 23:59:59"));
                }
            }catch (Exception exp)
            {
                logger.error("invoice query date error:", exp);
            }
            List<InvoicereSubmit> invoicereSubmitList=invoicereSubmitService.queryInvoicereSubmit(invoicereDto);

            if(invoicereDto.getCountRows()!=null&&invoicereDto.getCountRows().intValue()>=0)
            {
                pageMap.put("total", invoicereDto.getCountRows());
            }
            else
            {
                pageMap.put("total", invoicereSubmitService.queryInvoicereSubmitTotalCount(invoicereDto));
            }

            pageMap.put("rows", invoicereSubmitList);
            return pageMap;

        }catch (Exception exp)
        {
            logger.error("query invoice submit error:",exp);
            Map<String, Object> pageMap = new HashMap<String, Object>();
            pageMap.put("total", 0);
            pageMap.put("rows", new ArrayList<MyOrderFrontAuditDto>());
            pageMap.put("err", ExceptionMsgUtil.getMessage(exp));
            return pageMap;
        }
    }

    @RequestMapping(value = "/export")
    public void exportInvoices(InvoicereDto invoicereDto, HttpServletResponse response)
    {
        try
        {
            //调整日期内的时间
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormatFull=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                if(invoicereDto.getBeginDate()!=null)
                {
                    String str=simpleDateFormat.format(invoicereDto.getBeginDate());
                    invoicereDto.setBeginDate(simpleDateFormatFull.parse(str+" 00:00:00"));
                }
                if(invoicereDto.getEndDate()!=null)
                {
                    String str=simpleDateFormat.format(invoicereDto.getEndDate());
                    invoicereDto.setEndDate(simpleDateFormatFull.parse(str+" 23:59:59"));
                }
            }catch (Exception exp)
            {
                logger.error("export invoice query date error:", exp);
            }

            HSSFWorkbook wb = invoicereSubmitService.exportInvoices(invoicereDto);
            if (wb != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String fileName = "Invoice-" +sdf.format(new Date()) + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                OutputStream output = response.getOutputStream();
                wb.write(output);
                output.flush();
                output.close();
            }
        }catch (Exception exp)
        {
             logger.error("export invoicere error:", exp);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addInvoice(InvoicereSubmit invoicereSubmit)
    {
        Map<String, Object> map=new Hashtable<String,Object>();

        try
        {
            invoicereSubmit.setApplicant(SecurityHelper.getLoginUser().getUserId().toString());
            invoicereSubmitService.addNewInvoice(invoicereSubmit);
            map.put("succ","1");
            map.put("msg","发票申请成功");
        }catch (ErpException erpExp)
        {
            map.put("succ","0");
            map.put("msg",erpExp.getMessage());
            logger.error("add invoice apply error:"+erpExp.getMessage(), erpExp);
        }
        catch (Exception exp)
        {
            map.put("succ","0");
            map.put("msg","发票申请失败");
            logger.error("add invoice apply unknown error:",exp);
        }

        return map;
    }

    @RequestMapping(value = "/init")
    @ResponseBody
    public InvoicereSubmit getInitInvoiceFromOrder(@RequestParam(required = true)String orderId)
    {
        return invoicereSubmitService.fetchInvoiceInfoFromOrder(orderId);
    }
}
