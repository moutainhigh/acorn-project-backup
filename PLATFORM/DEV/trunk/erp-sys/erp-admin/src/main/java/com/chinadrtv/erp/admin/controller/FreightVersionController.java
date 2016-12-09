package com.chinadrtv.erp.admin.controller;

import com.chinadrtv.erp.admin.model.Company;
import com.chinadrtv.erp.admin.model.FreightLine;
import com.chinadrtv.erp.admin.model.FreightVersion;
import com.chinadrtv.erp.admin.service.CompanyService;
import com.chinadrtv.erp.admin.service.FreightLineService;
import com.chinadrtv.erp.admin.service.FreightVersionService;
import com.chinadrtv.erp.util.JsonBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-13
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class FreightVersionController extends BaseController {

    private static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
    @Autowired
    private FreightVersionService freightVersionService;
    @Autowired
    private FreightLineService freightLineService;
    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/admin/freightVersion/load", method = RequestMethod.POST)
    public String load(
            @RequestParam(required = false, defaultValue = "") String companyId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供DataGrid绑定数据
        */
        String jsonData;
        if(!companyId.equals("")){
            List<FreightVersion> versions = freightVersionService.getFreightVersions(companyId);
            int totalRecords = versions.size();
            jsonBinder.setDateFormat("yyyy-MM-dd");
            jsonData = "{\"rows\":" +jsonBinder.toJson(versions) + ",\"total\":" + totalRecords + " }";
        } else {
            jsonData = "{\"rows\":[],\"total\":0}";
        }
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
        return null;
    }

    @RequestMapping(value = "/admin/freightVersion/save", method = RequestMethod.POST)
    public ModelAndView save(FreightVersion freightVersion, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            freightVersionService.saveFreightVersion(freightVersion);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex)
        {
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/admin/freightVersion/update", method = RequestMethod.POST)
    public ModelAndView update(FreightVersion freightVersion, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            freightVersionService.saveFreightVersion(freightVersion);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex)
        {
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/admin/freightVersion/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(required = true) Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            List<FreightLine> list = freightLineService.getLegFreightLines(id);
            if(list.size() > 0){
                response.getWriter().print("{\"errorMsg\":\"当前版本已经在费用配置中使用,不能删除！\"}");
            }
            else
            {
                freightVersionService.removeFreightVersion(id);
            }
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/admin/freightVersion/lookup", method = RequestMethod.POST)
    public String lookup(
            @RequestParam(required = true, defaultValue = "") String companyId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        response.setContentType("text/json;charset=UTF-8");
        List<FreightVersion> companyList = freightVersionService.getFreightVersions(companyId);
        jsonBinder.setDateFormat("yyyy-MM-dd");
        response.getWriter().print(jsonBinder.toJson(companyList));
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(Company.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {
                    try
                    {
                        setValue(companyService.getCompanyById(text));
                    }
                    catch(Exception ex) { }
                }
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
