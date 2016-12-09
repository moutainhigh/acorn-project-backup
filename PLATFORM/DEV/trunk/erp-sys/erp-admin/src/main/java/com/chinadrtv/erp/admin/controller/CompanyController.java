package com.chinadrtv.erp.admin.controller;

import com.chinadrtv.erp.admin.model.Company;
import com.chinadrtv.erp.admin.model.CompanyType;
import com.chinadrtv.erp.admin.model.MailType;
import com.chinadrtv.erp.admin.service.CompanyService;
import com.chinadrtv.erp.admin.service.CompanyTypeService;
import com.chinadrtv.erp.admin.service.MailTypeService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-13
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CompanyController extends BaseController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyTypeService companyTypeService;
    @Autowired
    private MailTypeService mailTypeService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(CompanyType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {
                    try
                    {
                       CompanyType companyType  = companyTypeService.findById(text);
                       setValue(companyType);
                    }
                    catch(Exception ex) { /* do nothing */}
                }
            }
        });

        binder.registerCustomEditor(MailType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {
                    try
                    {
                      MailType mailType  = mailTypeService.findById(text);
                      setValue(mailType);
                    }
                    catch(Exception ex)  { /* do nothing */}
                }
            }
        });
    }


    @RequestMapping("/admin/company")
    public ModelAndView index() throws Exception {
        return new ModelAndView("company/index");
    }
    /*
    @RequestMapping("/admin/freightLine")
    public ModelAndView freightLine(String companyId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("companyId", companyId);
        return new ModelAndView("company/freightLine", map);
    }
    */

    @RequestMapping(value = "/admin/company/load", method = RequestMethod.POST)
    public String load(@RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam Integer rows,
                        @RequestParam(required = false, defaultValue = "") String companyId,
                        @RequestParam(required = false, defaultValue = "") String companyName,
                        @RequestParam(required = false, defaultValue = "") String companyType,
                        @RequestParam(required = false, defaultValue = "") String mailType,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {

        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();

        List<Company> list;
        int totalRecords;

        if(!companyId.equals("")){
            list = new ArrayList<Company>();
            Company company = companyService.getCompanyById(companyId);
            if(company != null){
                //hibernate要求鉴别类BaseType的Id必须唯一，实际情况并不唯一
                if(company.getCompanyType() == null){
                    try
                    {
                        company.setCompanyType(companyTypeService.findById(company.getTypeId()));
                    }
                    catch(Exception ex) { /* do nothing */}
                }
                //hibernate要求鉴别类BaseType的Id必须唯一，实际情况并不唯一
                if(company.getMailType() == null){
                    try
                    {
                        company.setMailType(mailTypeService.findById(company.getMailTypeId()));
                    }
                    catch(Exception ex) { /* do nothing */}
                }
                list.add(company);

            }
            totalRecords = list.size();
        }
        else
        {
            list = companyService.getAllCompanies(companyName, companyType, mailType, (page -1) * rows, rows);

            for(Company company : list)
            {
                //hibernate要求鉴别类BaseType的Id必须唯一，实际情况并不唯一
                if(company.getCompanyType() == null){
                    try
                    {
                        company.setCompanyType(companyTypeService.findById(company.getTypeId()));
                    }
                    catch(Exception ex) { /* do nothing */}
                }
                //hibernate要求鉴别类BaseType的Id必须唯一，实际情况并不唯一
                if(company.getMailType() == null){
                    try
                    {
                        company.setMailType(mailTypeService.findById(company.getMailTypeId()));
                    }
                    catch(Exception ex) { /* do nothing */}
                }
            }

            totalRecords = companyService.getCompanyCount(companyName, companyType, mailType);
        }

        String jsonData = "{\"rows\":" +jsonBinder.toJson(list) + ",\"total\":" + totalRecords + " }";
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
        return null;
    }

    @RequestMapping(value = "/admin/company/add", method = RequestMethod.POST)
    public ModelAndView add(Company company, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            Company cmp = companyService.getCompanyById(company.getCompanyId());
            if(cmp != null){
                response.getWriter().print("{\"errorMsg\":\"添加失败:公司编号"+ company.getCompanyId() +"已经存在!\"}");
            }
            else
            {
                companyService.addCompany(company);
                response.getWriter().print("{\"success\":\"true\"}");
            }
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }

    @RequestMapping(value = "/admin/company/save", method = RequestMethod.POST)
    public ModelAndView save(Company company, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            companyService.saveCompany(company);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }

    @RequestMapping(value = "/admin/company/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(required = true) String companyId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            companyService.removeCompany(companyId);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }
}
