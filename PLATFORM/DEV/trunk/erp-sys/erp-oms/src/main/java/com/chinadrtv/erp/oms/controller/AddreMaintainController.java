package com.chinadrtv.erp.oms.controller;


import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.AddressDto;
import com.chinadrtv.erp.oms.service.AreaGroupHeaderService;
import com.chinadrtv.erp.oms.service.CompanyService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-14
 * Time: 下午1:44
 * To change this template use File | Settings | File Templates.
 * 承运商地址组维护
 */
@Controller
@RequestMapping("addreMaintain")
public class AddreMaintainController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AreaGroupHeaderService areaGroupHeaderService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView main(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("addreMaintain/index");
        List<Company> list = companyService.getAllCompanies();
        List<OrderChannel> channelList = areaGroupHeaderService.getAllChannel();
        mav.addObject("companyList", list);
        mav.addObject("channelList",channelList);
        return mav;
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> list(HttpServletRequest request, AddressDto addressDto,DataGridModel dataModel)
            throws IOException, JSONException, ParseException {

        Map<String,Object> result = null;
        try{
            result = areaGroupHeaderService.getAddressDtoList(addressDto,dataModel);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 启用
     * @param id
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "on", method = RequestMethod.POST)
    public ModelAndView on(
            @RequestParam(required = true) Long id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            areaGroupHeaderService.updateIsActive(id,"Y");
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }
    /**
     * 停用
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "off", method = RequestMethod.POST)
    public ModelAndView off(
            @RequestParam(required = true) Long id,
            HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            areaGroupHeaderService.updateIsActive(id,"N");
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    /**
     * 新增数据保存
     * @param apName
     * @param apDesc
     * @param cid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String logisticsAlternate(
            @RequestParam(required = true, defaultValue="") String apName,
            @RequestParam(required = true, defaultValue="") String apDesc,
            @RequestParam(required = true, defaultValue="") Long cid,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        try
        {
            if(!apName.equals("") && !apDesc.equals("") && !cid.equals(""))
            {
                areaGroupHeaderService.saveAreGroupHeader(apName,apDesc,cid);
                response.getWriter().print("{\"success\":\"true\"}");
            }

        } catch (Exception ex) {

            response.setContentType("text/plain; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }

    /**
     * 明细导入
     * @param request
     * @param response
     * @param mFile
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */

    @RequestMapping(value = "upload")
    @ResponseBody
    public Map<String,String> upload(HttpServletRequest request,HttpServletResponse response,
                                     @RequestParam("fileToUpload") CommonsMultipartFile mFile)
            throws IOException, JSONException, ParseException {

        Map<String,String> map = new HashMap<String, String>();

        HSSFWorkbook book = null;
        if (!mFile.isEmpty()) {
            try {
                //解析上传Excel文件
                book =  areaGroupHeaderService.upload(mFile.getInputStream());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(book!=null){

            map.put("success","数据导入成功!");

        }else if(book==null){

            map.put("error","数据导入失败,请核实上传的Excel文件!");
        }
        return map;
    }

    /**
     * 显示地址组明细
     * @param request
     * @param areId
     * @param dataModel
     * @return
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    @RequestMapping(value = "areaGroupDetailList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> areaGroupDetailList(HttpServletRequest request, Long areId,DataGridModel dataModel)
            throws IOException, JSONException, ParseException {

        Map<String,Object> result = null;
        try{
            System.out.println("测试获取值："+areId);
            result = areaGroupHeaderService.getAreaGroupDetailDtoList(areId,dataModel);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 地址明细导出Excel
     * @param request
     * @param response
     * @param areId
     * @param dataModel
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    @RequestMapping(value = "download", method = RequestMethod.POST)
    public void download(HttpServletRequest request,HttpServletResponse response,
                         @RequestParam(required = true) Long areId,DataGridModel dataModel)
            throws IOException, JSONException, ParseException {

        HSSFWorkbook book = null;
        try
        {
            book = areaGroupHeaderService.getAreaGroupDetailDtoForDownload(areId);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        if(book!=null){
            response.setContentType ( "application/ms-excel" ) ;
            response.setHeader ( "Content-Disposition" ,
                    "attachment;filename="+new String("导出Excel.xls".getBytes(),"utf-8")) ;

            book.write(response.getOutputStream());
        }

    }
}
