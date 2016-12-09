package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.model.CompanyPayment;
import com.chinadrtv.erp.model.CompanyPaymentSettle;
import com.chinadrtv.erp.model.trade.ShipmentSettlement;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.CompanyPaymentDto;
import com.chinadrtv.erp.oms.dto.ShipmentSettlementDto;
import com.chinadrtv.erp.oms.service.CompanyPaymentService;
import com.chinadrtv.erp.oms.service.CompanyPaymentSettleService;
import com.chinadrtv.erp.oms.service.CompanyService;
import com.chinadrtv.erp.oms.service.ShipmentSettlementService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.JsonBinder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 流水单拍平
 * User: gaodejian
 * Date: 13-4-9
 * Time: 下午1:00
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CompanyPaymentSettleController extends BaseController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyPaymentService companyPaymentService;

    @Autowired
    private CompanyPaymentSettleService companyPaymentSettleService;

    @Autowired
    private ShipmentSettlementService  shipmentSettlementService;

    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("/company/settle")
    public ModelAndView settle() throws Exception {
        ModelAndView modelAndView = new ModelAndView("/company/settle");
        modelAndView.addObject("paymentUrl", "/company/settle/loadPayments");
        modelAndView.addObject("settlementUrl", "/company/settle/loadSettlements");
        return modelAndView;
    }

    @RequestMapping(value = "/company/settle/confirm", method = RequestMethod.POST)
    public void settleConfirm(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            String[] ids1 = request.getParameterValues("ids1[]");
            String[] ids2 = request.getParameterValues("ids2[]");
            if(ids1.length > 0 && ids2.length > 0){
                AgentUser agentUser = SecurityHelper.getLoginUser();


                Long[] paymentIds = new Long[ids1.length];
                Long[] settlementIds = new Long[ids2.length];
                for(int i = 0; i < ids1.length; i++){
                    paymentIds[i] = Long.parseLong(ids1[i]);
                }
                for(int i = 0; i < ids2.length; i++){
                    settlementIds[i] = Long.parseLong(ids2[i]);
                }

                List<CompanyPayment> payments = companyPaymentService.getPayments(paymentIds);
                List<ShipmentSettlement> settlements = shipmentSettlementService.getSettlements(settlementIds);
                List<CompanyPaymentSettle> settles = new ArrayList<CompanyPaymentSettle>();

                for(CompanyPayment payment : payments)
                {
                    if(payment.getAmount() == null){
                        payment.setAmount(new BigDecimal(0));
                    }

                    if(payment.getSettledAmount() == null){
                        payment.setSettledAmount(new BigDecimal(0));
                    }

                    //未核销付款水单
                    if(!"2".equals(payment.getIsSettled()))
                    {
                        BigDecimal settledAmount = payment.getSettledAmount();
                        //查找尽量大额水单冲掉结算单
                        for(ShipmentSettlement settlement : settlements){
                            //未核销之结算单
                            if("0".equals(settlement.getIsSettled())){
                                if(payment.getAmount()
                                          .subtract(payment.getSettledAmount())
                                          .compareTo(new BigDecimal(settlement.getArAmount())) >= 0)
                                {
                                    settlement.setIsSettled("1");
                                    String userId =  SecurityHelper.getLoginUser().getUserId();//操作用户id,从session中获取
                                    settlement.setSettledUser(userId);  //结算人员
                                    settlement.setSettledDate(new Date());//结算日期

                                    settledAmount = settledAmount.add(new BigDecimal(settlement.getArAmount()));
                                    payment.setSettledAmount(settledAmount);

                                    if(payment.getAmount().compareTo(payment.getSettledAmount()) > 0){
                                       payment.setIsSettled("1");  //部分核销
                                    } else {
                                       payment.setIsSettled("2");  //全部核销
                                    }

                                    //添加一个中间关联类
                                    CompanyPaymentSettle settle = new CompanyPaymentSettle();
                                    settle.setPayment(payment);
                                    settle.setSettlement(settlement);
                                    settle.setAmount(settlement.getArAmount());
                                    if(agentUser != null){
                                        settle.setCreateDate(new Date());
                                        settle.setCreateUser(agentUser.getUserId());
                                        settle.setUpdateDate(new Date());
                                        settle.setUpdateUser(agentUser.getUserId());
                                    }
                                    settles.add(settle);
                                }
                            }
                        }

                        if(agentUser != null){
                            payment.setUpdateDate(new Date());
                            payment.setUpdateUser(agentUser.getUserId());
                        }
                        companyPaymentService.saveOrUpdate(payment);
                    }
                }

                for(ShipmentSettlement settlement : settlements)
                {
                    //未核销结算单
                    if("0".equals(settlement.getIsSettled()))
                    {
                        BigDecimal remaining = new BigDecimal(settlement.getArAmount());

                        for(CompanyPayment payment : payments)
                        {
                            //未核销或部分核销水单
                            if(!"2".equals(payment.getIsSettled()))
                            {
                                BigDecimal settledAmount = payment.getSettledAmount();
                                if(settledAmount == null){
                                    settledAmount = new BigDecimal(0);
                                }

                                BigDecimal balance = payment.getAmount().subtract(settledAmount);
                                if(remaining.compareTo(balance) >= 0){
                                    remaining = remaining.subtract(balance);

                                    //增加水单结算量
                                    settledAmount = settledAmount.add(balance);
                                    payment.setSettledAmount(settledAmount);
                                    payment.setIsSettled("2"); //完全
                                    if(agentUser != null){
                                        payment.setUpdateDate(new Date());
                                        payment.setUpdateUser(agentUser.getUserId());
                                    }

                                    //添加一个中间关联类
                                    CompanyPaymentSettle settle = new CompanyPaymentSettle();
                                    settle.setPayment(payment);
                                    settle.setSettlement(settlement);
                                    settle.setAmount(balance.doubleValue());
                                    if(agentUser != null){
                                        settle.setCreateDate(new Date());
                                        settle.setCreateUser(agentUser.getUserId());
                                        settle.setUpdateDate(new Date());
                                        settle.setUpdateUser(agentUser.getUserId());
                                    }
                                    settles.add(settle);
                                }

                                if(remaining.compareTo(new BigDecimal(0)) == 0){
                                    settlement.setIsSettled("1");
                                    break;
                                }
                            }
                        }
                        if(agentUser != null){
                            settlement.setUpdateDate(new Date());
                            settlement.setUpdateUser(agentUser.getUserId());
                        }
                        shipmentSettlementService.saveOrUpdate(settlement);
                    }
                }

                for(CompanyPaymentSettle settle : settles){
                    companyPaymentSettleService.saveOrUpdate(settle);
                }
            }
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":\"水单拍平失败("+ ex.getMessage() +")\"}");
        }
    }

    @RequestMapping(value = "/company/settle/cancel", method = RequestMethod.POST)
    public void settleCancel(
            @RequestParam(required = true) Integer[] ids2,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {

            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
    }

    @RequestMapping(value = "/company/settle/loadPayments", method = RequestMethod.POST)
    public void loadPayments(
               @RequestParam(required = false, defaultValue = "1") int page,
               @RequestParam Integer rows,
               @RequestParam(required = false) Integer companyId,
               @RequestParam(required = false) String paymentCode,
               @RequestParam(required = false) Date paymentStart,
               @RequestParam(required = false) Date paymentEnd,
               @RequestParam(required = false) Boolean isSettled,
               HttpServletRequest request,
               HttpServletResponse response) throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();

        if(companyId != null && companyId > 0){
            params.put("companyId", companyId);
        }

        if(paymentCode != null && !paymentCode.equals("")){
            params.put("paymentCode", paymentCode);
        }

        if(paymentStart != null){
            params.put("paymentStart", paymentStart);
        }

        if(paymentEnd != null){
            long time = paymentEnd.getTime();
            time+= (23*60*60+59*60+59)*1000;
            paymentEnd.setTime(time);
            params.put("paymentEnd", paymentEnd);
        }

        if(isSettled !=null && isSettled){
            //0:未核销 1:核销中 2全部核销
            params.put("isSettled", "2");
        }

        Long totalRecords = companyPaymentService.getPaymentCount(params);
        List<CompanyPayment> list = companyPaymentService.getPayments(params, (page - 1) * rows, rows);

        BigDecimal amount = new BigDecimal(0);
        BigDecimal settledAmount = new BigDecimal(0);
        for(CompanyPayment payment:list){
            if(payment.getAmount() != null){
                amount = amount.add(payment.getAmount());
            }
            if(payment.getSettledAmount() != null){
                settledAmount = settledAmount.add(payment.getSettledAmount());
            }
        }

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setExclusionStrategies(
                        new ExcludeUnionResource(
                                new String[]{"parent", "orderChannel", "orderPayTypes"}))
                .create();



        String jsonData = "{\"rows\":" +gson.toJson(list) + ",\"total\":" + totalRecords + ",\"footer\":[{\"paymentCode\":\"合计\",\"amount\":"+ amount +",\"settledAmount\":"+ settledAmount +"}]}";
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
    }

    @RequestMapping(value = "/company/settle/loadSettlements", method = RequestMethod.POST)
    public void loadSettlements(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) String shipmentId,
            @RequestParam(required = false) Date ftStart,
            @RequestParam(required = false) Date ftEnd,
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) Date rfStart,
            @RequestParam(required = false) Date rfEnd,
            @RequestParam(required = false) Boolean isSettled,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();

        if(companyId != null && companyId > 0){
            params.put("companyId", companyId);
        }

        if(shipmentId != null && !shipmentId.equals("")){
            params.put("shipmentId", shipmentId);
        }

        if(ftStart != null){
            params.put("ftStart", ftStart);
        }

        if(ftEnd != null){
            long time = ftEnd.getTime();
            time+= (23*60*60+59*60+59)*1000;
            ftEnd.setTime(time);
            params.put("ftEnd", ftEnd);
        }

        if(orderId != null && !orderId.equals("")){
            params.put("orderId", orderId);
        }

        if(rfStart != null){
            params.put("rfStart", rfStart);
        }

        if(rfEnd != null){
            long time = rfEnd.getTime();
            time+= (23*60*60+59*60+59)*1000;
            rfEnd.setTime(time);
            params.put("rfEnd", rfEnd);
        }

        if(isSettled !=null && isSettled){
            //0:未核销 1:已核销
            params.put("isSettled", "1");
        }

        Long totalRecords = shipmentSettlementService.getSettlementCount(params);
       // List<ShipmentSettlement> list = shipmentSettlementService.getSettlements(params, (page - 1) * rows, rows);
        List<ShipmentSettlementDto> list = shipmentSettlementService.getShipmentSettlementDtoInfo(params, (page - 1) * rows, rows);
        Double amount = 0.0;
        for(ShipmentSettlementDto settlement:list){
            if(settlement.getArAmount() != null){
                amount += settlement.getArAmount();
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");      //格式化数字保留2位小数

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setExclusionStrategies(
                        new ExcludeUnionResource(
                                new String[]{"orderHistory", "orderhist", "orderdets", "shipmentDetails", "parent", "orderChannel", "orderPayTypes"}))
                .create();


        String jsonData = "{\"rows\":" +gson.toJson(list) + ",\"total\":" + totalRecords + ",\"footer\":[{\"createDate\":\"合计\",\"arAmount\":"+ df.format(amount) +"}]}";
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
    }
}
