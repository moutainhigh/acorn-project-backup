package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.service.Cusnote;
import com.chinadrtv.erp.model.service.CusnoteId;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.uc.dto.CusnoteDto;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.CusnoteService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "notice")
public class NoticeController extends BaseController {
    private static final Logger logger = LoggerFactory
            .getLogger(NoticeController.class);

    @Autowired
    private OrderhistService orderhistService;

    @Autowired
    private NamesService namesService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private CusnoteService cusnoteService;

    @RequestMapping(value = "/queryInfo/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryInfo(@PathVariable String orderId) {
        Map result = new HashMap();
        Order order = null;
        String contactId = "";
        Contact contact = null;
        try {
            order = orderhistService.getOrderHistByOrderid(orderId);
            contactId = order.getContactid();
            contact = contactService.get(contactId);
        } catch (Exception e) {
            logger.error("客服通知时，查找订单有误。订单号：" + orderId + ", 对应客户号：" + contactId);
            result.put("error", "订单找不到或者订单对应客户找不到。");
            return result;
        }
        result.put("orderCrdt", order.getCrdt());
        result.put("contactName", contact.getName());
        return result;
    }

    @RequestMapping(value = "/queryNoticeHist/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryNoticeHist(@PathVariable String orderId) {
        return cusnoteService.getCusnoteByOrderid(orderId);
    }

    @RequestMapping(value = "/queryNoticeHistForTask", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryNoticeHistForTask(CusnoteDto cusnoteDto, DataGridModel dataGrid) {
        return cusnoteService.queryCusnote(cusnoteDto, dataGrid);
    }

    @RequestMapping(value = "/queryNoticeType", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryNoticeType() {
        List<Names> namesList = namesService.getAllNames("CUSNOTE", "通知分类");
        Map result = new HashMap();
        result.put("names", namesList);
        return result;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public void save(@RequestParam(required = true) String orderid,
                     @RequestParam(required = false, defaultValue = "") String caseid,
                     @RequestParam(required = true) String noteclass,
                     @RequestParam(required = true) String noteremark) {
        Cusnote cusnote = new Cusnote();
        cusnote.setOrderid(orderid);
        cusnote.setCaseid(caseid);
        cusnote.setNoteclass(noteclass);
        cusnote.setNoteremark(noteremark);
        cusnote.setFeatstr(cusnoteService.getCusnoteCountByOrderid(cusnote.getOrderid()) + 1);
        cusnote.setGendate(new Date());
        cusnote.setGenseat(SecurityHelper.getLoginUser().getUserId());
        cusnote.setIsreplay("0");
        cusnote.setValid(0);
        cusnote.setNotecate("1");
        cusnoteService.save(cusnote);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public void update(@RequestParam(required = true) String orderid,
                       @RequestParam(required = true) String featstr,
                       @RequestParam(required = true) String renote) {
        CusnoteId cusnoteId = new CusnoteId();
        cusnoteId.setFeatstr(Integer.valueOf(featstr));
        cusnoteId.setOrderid(orderid);
        Cusnote result = cusnoteService.queryCusnoteById(cusnoteId);
        result.setRedate(new Date());
        result.setReseat(SecurityHelper.getLoginUser().getUserId());
        result.setIsreplay("-1");
        result.setRenote(renote);
        cusnoteService.saveOrUpdate(result);
    }

}
