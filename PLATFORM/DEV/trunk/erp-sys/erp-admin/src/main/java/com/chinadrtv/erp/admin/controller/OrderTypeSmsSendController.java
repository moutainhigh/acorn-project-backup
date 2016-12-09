package com.chinadrtv.erp.admin.controller;


import com.chinadrtv.erp.admin.model.*;
import com.chinadrtv.erp.admin.service.*;
import com.chinadrtv.erp.util.JsonBinder;
import org.json.JSONException;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 12-11-13
 * Time: 上午10:13
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class OrderTypeSmsSendController extends BaseController {

    @Autowired
    private OrderTypeSmsService orderTypeSmsService;
    @Autowired
    private OrderTypeService orderTypeService;
    @Autowired
    private PayTypeService payTypeService;
    @Autowired
    private UserService userService;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //设置时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

        binder.registerCustomEditor(OrderType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {
                    OrderType orderType  = orderTypeService.findById(text);
                    setValue(orderType);
                }
            }
        });

        binder.registerCustomEditor(PayType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {
                    PayType payType  = payTypeService.findById(text);
                    setValue(payType);
                }
            }
        });
    }
    private static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
    @RequestMapping("/admin/OrderTypeSmsSendLogs")
    public String auditLogs() throws Exception {
        return "OrderHistSms/OrderHistList";
    }
    @RequestMapping(value = "/admin/queryOrderTypeSmsLog", method = RequestMethod.POST)
    public String queryPromotionsJSON(@RequestParam(required = false, defaultValue = "0") int page,
                                      @RequestParam Integer rows,
                                      @RequestParam(required = false, defaultValue = "") String v_smstype,
                                      @RequestParam(required = false, defaultValue = "") String v_ordertype,
                                      @RequestParam(required = false, defaultValue = "") String v_setProName,
                                      @RequestParam(required = false, defaultValue = "") String v_smsName,
                                      @RequestParam(required = false, defaultValue = "") String usid,
                                      @RequestParam(required = false, defaultValue = "") String v_paytype,
                                      @RequestParam(required = false, defaultValue = "") String v_beginDate,
                                      @RequestParam(required = false, defaultValue = "") String v_endDate,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        String dateFormatStr =  "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        jsonBinder.setDateFormat(dateFormatStr);
        dateFormat.setLenient(false);
        String sBeginDate = v_beginDate;
        Date beginDate = null;
        if (sBeginDate != null &&  !sBeginDate.isEmpty()){
            beginDate = dateFormat.parse(sBeginDate);
        }
        String sEndDate =v_endDate;
        Date endDate =  null;
        if (sEndDate != null &&  !sEndDate.isEmpty()){
            endDate = dateFormat.parse(sEndDate);
        }
        /*
        TODO:提供GridView绑定数据
        */
         List<OrderTypeSmsSend> orderHistList=orderTypeSmsService.searchPaginatedOrderTypeSmsSendByAppDate(v_smstype,v_ordertype,v_setProName,v_smsName,usid,v_paytype,beginDate,endDate,page-1,rows);

        int totalRecords = orderTypeSmsService.getOrderTypeSmsSendCountByAppDate(v_smstype,v_ordertype,v_setProName,v_smsName,usid,v_paytype,beginDate,endDate);
        response.setContentType("text/json; charset=UTF-8");
        String jsonData = "{\"rows\":" +jsonBinder.toJson(orderHistList) + ",\"total\":" + totalRecords + " }";
        response.getWriter().print(jsonData);
        response.setHeader("Cache-Control", "no-cache");
        return null;
    }
    /*保存新添加的短信配置信息*/
    @RequestMapping(value = "/admin/OrderTypeSmsLogCount", method = RequestMethod.POST)
    public ModelAndView smssendCount( @RequestParam(required = false, defaultValue = "") String v_ordertype,
                                      @RequestParam(required = false, defaultValue = "") String v_paytype,
                            HttpServletRequest request,
                            HttpServletResponse response)  throws Exception{
        String dateFormatStr =  "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        jsonBinder.setDateFormat(dateFormatStr);
        dateFormat.setLenient(false);
        String sBeginDate = "";
        Date beginDate = null;
        if (sBeginDate != null &&  !sBeginDate.isEmpty()){
            beginDate = dateFormat.parse(sBeginDate);
        }
        String sEndDate ="";
        Date endDate =  null;
        if (sEndDate != null &&  !sEndDate.isEmpty()){
            endDate = dateFormat.parse(sEndDate);
        }
        int totalRecords = orderTypeSmsService.getOrderTypeSmsSendCountByAppDate("",v_ordertype,"","","",v_paytype,beginDate,endDate);

        response.setContentType("text/json; charset=UTF-8");
        String jsonData = "{\"total\":\"" + totalRecords + "\"}";
        response.getWriter().print(jsonData);
        response.setHeader("Cache-Control", "no-cache");

        return null ;
    }
    /*保存新添加的短信配置信息*/
    @RequestMapping(value = "/admin/SaveOrderTypeSmsLog", method = RequestMethod.POST)
    public ModelAndView saveNewProductRequest(
                                      @RequestParam(required = false, defaultValue = "") String v_smstype,
                                      @RequestParam(required = false, defaultValue = "") String v_smsName,
                                      @RequestParam(required = false, defaultValue = "") String v_paytype,
                                      @RequestParam(required = false, defaultValue = "") String v_ordertype,
                                      @RequestParam(required = false, defaultValue = "") String v_setProId,
                                      @RequestParam(required = false, defaultValue = "") String v_sendDelyTime,
                                      @RequestParam(required = false, defaultValue = "") String v_smscontent
                                      ) throws Exception
    {
        OrderTypeSmsSend orderTypeSmsSend=new OrderTypeSmsSend();
        orderTypeSmsSend.setId(0);
        orderTypeSmsSend.setSmsType(v_smstype);
        orderTypeSmsSend.setSmsName(v_smsName);
        orderTypeSmsSend.setPaytype(payTypeService.findById(v_paytype));
        orderTypeSmsSend.setUser(userService.findById(v_setProId));
        orderTypeSmsSend.setSendDelyTime(Double.parseDouble(v_sendDelyTime));
        orderTypeSmsSend.setSmscontent(v_smscontent);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        orderTypeSmsSend.setSetDate(StrToDate(df.format(new Date())));
            orderTypeSmsSend.setOrderType(orderTypeService.findById(v_ordertype));
        orderTypeSmsService.addOrderTypeSmsSend(orderTypeSmsSend);
        return null;
    }


    @RequestMapping(value = "/admin/UpdateOrderTypeSmsLog", method = RequestMethod.POST)
    /*修改短信配置信息*/
    public ModelAndView updateNewProductRequest(
                                                  @RequestParam(required = false, defaultValue = "") String id,
                                                 @RequestParam(required = false, defaultValue = "") String v_smstype,
                                                 @RequestParam(required = false, defaultValue = "") String v_smsName,
                                                 @RequestParam(required = false, defaultValue = "") String v_paytype,
                                                 @RequestParam(required = false, defaultValue = "") String v_ordertype,
                                                 @RequestParam(required = false, defaultValue = "") String v_setProId,
                                                 @RequestParam(required = false, defaultValue = "") String v_sendDelyTime,
                                                 @RequestParam(required = false, defaultValue = "") String v_smscontent
    ) throws Exception {
        OrderTypeSmsSend orderTypeSmsSend=new OrderTypeSmsSend();
        orderTypeSmsSend.setId(Integer.parseInt(id));
        orderTypeSmsSend.setSmsType(v_smstype);
        orderTypeSmsSend.setSmsName(v_smsName);
        orderTypeSmsSend.setPaytype(payTypeService.findById(v_paytype));
        orderTypeSmsSend.setUser(userService.findById(v_setProId));
        orderTypeSmsSend.setSendDelyTime(Double.parseDouble(v_sendDelyTime));
        orderTypeSmsSend.setSmscontent(v_smscontent);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        orderTypeSmsSend.setSetDate(StrToDate(df.format(new Date())));
        orderTypeSmsSend.setOrderType(orderTypeService.findById(v_ordertype));
        orderTypeSmsService.saveOrderTypeSmsSend(orderTypeSmsSend);
        return null;
    }
    @RequestMapping(value = "/admin/DelOrderTypeSmsLog", method = RequestMethod.POST)
    public ModelAndView delNewProducstRequest(@RequestParam(required = true) Integer id,
                                                HttpServletRequest reest) throws JSONException {

        orderTypeSmsService.delOrderTypeSmsLog(id);
        return null;
    }
    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return date;
    }
}
