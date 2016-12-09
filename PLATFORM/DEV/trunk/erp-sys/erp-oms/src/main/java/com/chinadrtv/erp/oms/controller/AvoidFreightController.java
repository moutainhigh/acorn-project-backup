package com.chinadrtv.erp.oms.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.AvoidFreightDto;
import com.chinadrtv.erp.oms.service.AreaGroupHeaderService;
import com.chinadrtv.erp.oms.service.AvoidFreightService;
import com.chinadrtv.erp.oms.service.CompanyService;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-26
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 * 免运费登记
 */
@Controller
@RequestMapping("freight")
public class AvoidFreightController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AreaGroupHeaderService areaGroupHeaderService;
    @Autowired
    private AvoidFreightService avoidFreightService;

    @RequestMapping(value = "avoidFreight", method = RequestMethod.GET)
    public ModelAndView main(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("freight/avoidFreight");
        List<Company> list = companyService.getAllCompanies();

        mav.addObject("companyList", list);

        return mav;
    }

    /**
     * 显示列表
     * @param request
     * @param avoidFreightDto
     * @param dataModel
     * @return
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> list(HttpServletRequest request, AvoidFreightDto avoidFreightDto,DataGridModel dataModel)
            throws IOException, JSONException, ParseException {

        Map<String,Object> result = null;
        try{
                //区别订单号和运单号
                String orderId = avoidFreightDto.getShipmentId();
                int index=orderId.indexOf("V");
                if(index>0)
                {
                    avoidFreightDto.setShipmentId(orderId.substring(0,index));
                }
                result = avoidFreightService.getAvoidFreightDtoList(avoidFreightDto, dataModel);


        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //调整运费保存
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String saveShipmentHeader(
            @RequestParam(required = true, defaultValue="") String afterFreight,
            @RequestParam(required = true, defaultValue="") Long Id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception{

        try{
            //登记
            avoidFreightService.registerAvoidFreight(Id,afterFreight);
            response.getWriter().print("{\"success\":\"true\"}");
        } catch (Exception ex) {

            response.setContentType("text/plain; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }
}
