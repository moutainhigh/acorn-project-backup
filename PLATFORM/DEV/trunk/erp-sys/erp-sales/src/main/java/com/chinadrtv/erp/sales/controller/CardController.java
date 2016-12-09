package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.Cardtype;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.agent.Card;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.pay.core.model.CredentialsType;
import com.chinadrtv.erp.pay.core.model.OnlinePayment;
import com.chinadrtv.erp.pay.core.model.PayResult;
import com.chinadrtv.erp.pay.core.model.PayType;
import com.chinadrtv.erp.pay.core.service.OnlinePayService;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.service.SourceConfigure;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.service.CardService;
import com.chinadrtv.erp.uc.service.CardtypeService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-6-25
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping("/cardpay")
public class CardController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CardController.class);

    @Autowired
    private UserBpmTaskService userBpmTaskService;

    @Autowired
    private PhoneService phoneService;
    @Autowired
    private OrderhistService orderhistService;
    @Autowired
    private CardService cardService;
    @Autowired
    private CardtypeService cardtypeService;
    @Autowired
    private OnlinePayService onlinePayService;

    @ResponseBody
    @RequestMapping(value = "/grant")
    public Map<String, String> grant(
            @RequestParam String orderId,
            @RequestParam String phone,
            @RequestParam String cvv,
            @RequestParam String credentialsNo) {
        Map<String, String> map = new HashMap<String, String>();
        AgentUser agentUser = SecurityHelper.getLoginUser();
        Order order = orderhistService.getOrderHistByOrderid(orderId);
        if (StringUtils.isEmpty(order.getCardnumber())) {
            map.put("st", "error");
            map.put("msg", "订单中没有关联信用卡");
            return map; // error
        }

        if (StringUtils.equals(order.getConfirm(), "-1")) {
            map.put("st", "error");
            map.put("msg", "已索权成功的订单不能再次索权");
            return map; // error
        }

        Card card = cardService.getCard(Long.valueOf(order.getCardnumber()));
        Cardtype cardtype = cardtypeService.getCardType(card.getType());
        /*if(!"1".equals(orderId))
        {
            map.put("st", "succ");
            map.put("msg", "索权成功");
            return map;
        }*/
        boolean bCanPay=false;
        List<PayType> payTypeList=onlinePayService.getAllPayTypes();
        if(cardtype!=null)
        {
            if(payTypeList!=null)
            {
                for(PayType payType:payTypeList)
                {
                    if(StringUtils.equals(cardtype.getBankName(),payType.getPayTypeName()))
                    {
                        bCanPay=true;
                        break;
                    }
                }
            }
        }
        if(bCanPay){
            try {
                OnlinePayment request = new OnlinePayment();
                if(payTypeList!=null)
                {
                    for(PayType payType:payTypeList)
                    {
                        if(payType.getPayTypeName().equals(cardtype.getBankName()))
                        {
                            request.setPaytype(payType.getPayType());
                            break;
                        }
                    }
                }

                if(StringUtils.isNotBlank(credentialsNo))
                {
                    String[] values=credentialsNo.split(":");
                    request.setCredentialsNo(values[1]);
                    if("0".equals(values[0]))
                        request.setCredentialsType(CredentialsType.IDENTITYCARD);
                    else
                        request.setCredentialsType(CredentialsType.PASSORT);
                }

                request.setAmount(order.getTotalprice());
                request.setCardNo(card.getCardNumber());
                request.setExpiryDate(DateUtil.string2Date(card.getValidDate(), "yyyy-MM"));
                request.setStageNum(StringUtils.isEmpty(order.getLaststatus()) ? 1
                        : Integer.parseInt(order.getLaststatus()));
                //request.setPaytype(sourceConfigure.getTestPayType());
                request.setOrderId(orderId);
                if(StringUtils.isNotBlank(cvv))
                    request.setExtraCode(cvv);
                if(StringUtils.isNotBlank(phone))
                    request.setMobile(phone);

                PayResult response = onlinePayService
                        .pay(request);
                if(response.isSucc()){
                    logger.info(
                            "--------------------------\n"+
                                    "transTime:"+response.getTransTime()+"\n"+
                                    "authID:"+response.getAuthID()+"\n"+
                                    "orderNum:"+response.getOrderNum()+"\n"+
                                    "hpFee:"+response.getHpFee()+"\n"+
                                    "hpAmount:"+response.getHpAmount()+"\n"+
                                    "totalAmount:"+response.getTotalAmount()+"\n"+
                                    "refNum:"+response.getRefNum()+"\n"+
                                    "--------------------------\n"
                    );

                    order.setPaycontactid(card.getContactId());
                    order.setCardrightnum(response.getAuthID());
                    order.setMdusr(agentUser.getUserId());
                    order.setMddt(new Date());
                    order.setConfirm("-1");

                    orderhistService.updateOrderhist(order);
                    map.put("st", "succ");
                    map.put("msg", "索权成功");
                    return map; // error
                } else {
                    map.put("st", "error");
                    String strError=String.format("索权失败：%s[%s]",
                            response.getErrorMsg(), response.getErrorCode());
                    map.put("msg", strError);
                    logger.error("grant error order id:"+order.getOrderid()+" "+strError);
                    logger.error("grant error order id:"+order.getOrderid()+"- card id:"+order.getCardnumber());
                    return map; // error
                }
            } catch (Exception e) {
                map.put("st", "error");
                map.put("msg", e.getMessage());
                logger.error("grant exception order id:"+e.getMessage(), e);
                logger.error("grant error order id:"+order.getOrderid()+"- card id:"+order.getCardnumber());
                return map; // error
            }
        }
        map.put("st", "error");
        map.put("msg", "此卡不支持在线索权");
        return map; // error
    }

    @ResponseBody
    @RequestMapping(value = "/grantCheck/{orderId}")
    public Map<String,String> grantCheck(@PathVariable("orderId") String orderId)
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            List<UserBpmTask> userBpmTaskList = userBpmTaskService.queryUnterminatedOrderChangeTask(orderId);
            if(userBpmTaskList!=null&&userBpmTaskList.size()>0)
            {
                map.put("result","1");
            }
            else
            {
                //如果是工行，那么需要检查手机是否必须
                Order order=orderhistService.getOrderHistByOrderid(orderId);
                Card card=cardService.getCard(Long.parseLong(order.getCardnumber()));
                map.put("cvv",card.getExtraCode());
                Integer stage=StringUtils.isNotBlank(order.getLaststatus())? Integer.parseInt(order.getLaststatus()):1;
                map.put("result","0");
                map.put("stage",stage.toString());
            }
        }catch (Exception exp)
        {
            map.put("result","2");
            map.put("msg",exp.getMessage());
            logger.error("check order grant error:", exp);
        }
        return map;
    }

    private Phone getCardDefaultPhone(String contactId)
    {
        List<Phone> phoneList=phoneService.getPhonesByContactId(contactId);
        if(phoneList!=null&&phoneList.size()>0)
        {
            for(Phone phone:phoneList)
            {
                if("4".equals(phone.getPhonetypid())&&isAuditValid(phone.getState()))
                {
                    if("Y".equals(phone.getPrmphn()))
                        return phone;
                }
            }

            for(Phone phone:phoneList)
            {
                if("4".equals(phone.getPhonetypid())&&isAuditValid(phone.getState()))
                {
                    return phone;
                }
            }
        }
        return null;
    }

    private boolean isAuditValid(Integer flag)
    {
        if(flag==null||(flag!=null&&
                (flag.intValue()== CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED
                ||flag.intValue()==4)))
        {
            return true;
        }

        return false;
    }

    private List<Card> getContactCredentialsCard(String contactId)
    {
        List<Card> retList=new ArrayList<Card>();
        List<Card> cardList=cardService.getAllIdentityCardByContact(contactId);
        if(cardList!=null&&cardList.size()>0)
        {
            for(Card card:cardList)
            {
                if("001".equals(card.getType())||"002".equals(card.getType()))
                    retList.add(card);
            }
        }

        return retList;
    }

    private class SortByPhonePriority implements Comparator{
        @Override
        public int compare(Object o1, Object o2) {
            Phone phone1=(Phone)o1;
            Phone phone2=(Phone)o2;
            int priority1=this.getPriotiry(phone1);
            int priority2=this.getPriotiry(phone2);
            return priority1-priority2;
        }

        public int getPriotiry(Phone phone)
        {
            if("Y".equalsIgnoreCase(phone.getPrmphn()))
                return 0;
            else if("B".equalsIgnoreCase(phone.getPrmphn()))
                return 1;
            else
                return 100;
        }
    }

    @RequestMapping(value="/getCardpayPhones")
    @ResponseBody
    public List<Phone> getCardpayPhones(String orderId)
    {
        List<Phone> phoneList=new ArrayList<Phone>();
        if(StringUtils.isBlank(orderId))
            return phoneList;
        try{
            Order order = orderhistService.getOrderHistByOrderid(orderId);
            List<Phone> pList = phoneService.getPhonesByContactId(order.getPaycontactid());
            //先主后备、最后没有设置的
            if(pList!=null)
            {
                Collections.sort(pList, new SortByPhonePriority());
                for(Phone phone:pList)
                {
                    if("4".equals(phone.getPhonetypid())&&this.isAuditValid(phone.getState()))
                    {
                        phoneList.add(phone);
                    }
                }
            }
        }catch (Exception exp)
        {
            logger.error("fetch card pay phones error:", exp);
        }
        return phoneList;
    }

    @RequestMapping(value="/getCredentials")
    @ResponseBody
    public List<Map<String,String>> getCredentials(
            @RequestParam(required = false, defaultValue = "") String orderId){
        List<Map<String,String>> list=new ArrayList<Map<String, String>>();
        if(StringUtils.isBlank(orderId))
        {
             return list;
        }

        try {
            Order order=orderhistService.getOrderHistByOrderid(orderId);
            List<Card> cardList=this.getContactCredentialsCard(order.getPaycontactid());
            if(cardList!=null)
            {
                for(Card card:cardList)
                {
                    Map<String,String> map=new HashMap<String, String>();
                    if("001".equals(card.getType()))
                    {
                        map.put("id","0:"+card.getCardNumber());
                        map.put("value","身份证："+card.getCardNumber());
                    }
                    else if("002".equals(card.getType()))
                    {
                        map.put("id","1:"+card.getCardNumber());
                        map.put("value","护照："+card.getCardNumber());
                    }
                    list.add(map);
                }
            }
        } catch (Exception e) {
            logger.error("fetch credentials error:",e);
        }
        return list;
    }
}
