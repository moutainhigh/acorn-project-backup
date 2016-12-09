package com.chinadrtv.erp.admin.controller;

import com.chinadrtv.erp.admin.model.Company;
import com.chinadrtv.erp.admin.model.FreightLine;
import com.chinadrtv.erp.admin.model.FreightQuota;
import com.chinadrtv.erp.admin.service.CompanyService;
import com.chinadrtv.erp.admin.service.FreightLineService;
import com.chinadrtv.erp.admin.service.FreightQuotaService;
import com.chinadrtv.erp.util.JsonBinder;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-13
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class FreightQuotaController extends BaseController {

    @Autowired
    private FreightQuotaService freightQuotaService;
    @Autowired
    private FreightLineService freightLineService;
    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/admin/freightQuota/load", method = RequestMethod.POST)
    public String load(
            @RequestParam(required = true, defaultValue = "") String companyId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供DataGrid绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<FreightQuota> companyList = freightQuotaService.getFreightQuotas(companyId);
        int totalRecords = companyList.size();
        String jsonData = "{\"rows\":" +jsonBinder.toJson(companyList) + ",\"total\":" + totalRecords + " }";

        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
        return null;
    }

    @RequestMapping(value = "/admin/freightQuota/save", method = RequestMethod.POST)
    public ModelAndView save(FreightQuota freightQuota, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            freightQuotaService.saveFreightQuota(freightQuota);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex)
        {
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/admin/freightQuota/update", method = RequestMethod.POST)
    public ModelAndView update(FreightQuota freightQuota, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            freightQuotaService.saveFreightQuota(freightQuota);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex)
        {
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/admin/freightQuota/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(required = true) Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        List<FreightLine> list = freightLineService.getLegFreightLines(id);
        if(list.size() > 0){
            response.getWriter().print("{\"errorMsg\":\"当前额度已经在费用配置中使用,不能删除！\"}");
        }
        else
        {
            freightQuotaService.removeFreightQuota(id);
        }
        return null;
    }

    @RequestMapping(value = "/admin/freightQuota/lookup", method = RequestMethod.POST)
    public String lookup(
            @RequestParam(required = true, defaultValue = "") String companyId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<FreightQuota> companyList = freightQuotaService.getFreightQuotas(companyId);
        response.setContentType("text/json;charset=UTF-8");
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
                    catch(Exception ex) { /* do nothing */}
                }
            }
        });
    }
}
