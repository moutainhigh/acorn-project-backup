package com.chinadrtv.erp.distribution.controller;

import com.chinadrtv.erp.model.marketing.CallbackLog;
import com.chinadrtv.erp.model.marketing.CallbackLogSpecification;
import com.chinadrtv.erp.uc.service.CallbackLogService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CallbackLogController extends BaseController {

	@Autowired
	private CallbackLogService callbackLogService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {
                    try
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        setValue(sdf.parse(text));
                    }
                    catch(Exception ex) { /* do nothing */}
                }
            }
        });
    }

//    @RequestMapping(value = "/callbackLog/index", method= RequestMethod.GET)
//    public ModelAndView index(HttpServletRequest request) throws Exception {
//        ModelAndView modelAndView = new ModelAndView("/callbackLog/index");
//        return modelAndView;
//    }

	@ResponseBody
	@RequestMapping(
            value = "/callback/logs",
            method= RequestMethod.POST)
	public Map<String, Object> findCallbackLogs(
            CallbackLogSpecification specification,
            Integer page, Integer rows){
        Map<String, Object> map = new HashMap<String, Object>();

        Long totalNum = callbackLogService.findCallbackLogCount(specification);
        if(totalNum > 0)
        {
            List<CallbackLog> list = callbackLogService.findCallbackLogs(specification, (page - 1) * rows, rows);
            map.put("total", totalNum);
            map.put("rows", list);
        }
        else
        {
            map.put("total", 0);
            map.put("rows", new ArrayList<CallbackLog>());
        }
        return map;
	}

    @RequestMapping(value = "/callback/logs/export")
    public void export(CallbackLogSpecification specification, HttpServletResponse response) {
        try
        {
            HSSFWorkbook wb = callbackLogService.exportCallbackLogs(specification);
            if (wb != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String fileName = "Log" +sdf.format(new Date()) + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                OutputStream output = response.getOutputStream();
                wb.write(output);
                output.flush();
                output.close();
            }
        }
        catch (IOException ex){
            /* do nothing */
        }
    }
}
