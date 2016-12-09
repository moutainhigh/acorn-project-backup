package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.model.agent.Card;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.pay.core.model.CredentialsType;
import com.chinadrtv.erp.pay.core.model.OnlinePayment;
import com.chinadrtv.erp.pay.core.model.PayResult;
import com.chinadrtv.erp.pay.core.service.OnlinePayService;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.model.CreditcardOnlineAuthorization;
import com.chinadrtv.erp.sales.core.model.CreditcardOnlineAuthorizationResponse;
import com.chinadrtv.erp.sales.core.service.AmortisationService;
import com.chinadrtv.erp.sales.core.service.CreditcardOnlineAuthorizationService;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.sales.dto.ContactAddressPhoneDto;
import com.chinadrtv.erp.sales.dto.CreditCardDto;
import com.chinadrtv.erp.sales.dto.CreditCardOwnerDto;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.service.*;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.hibernate.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单结算
 * User: gaodejian
 * Date: 13-6-5
 * Time: 下午1:59
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CheckoutController extends BaseController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CheckoutController.class);
    @Autowired
    private CardService cardService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CardtypeService cardtypeService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private OrderhistService orderhistService;

    @Autowired
    private AmortisationService amortisationService;

    @Autowired
    private CreditcardOnlineAuthorizationService creditcardOnlineAuthorizationService;

    @Autowired
    private OnlinePayService onlinePayService;

    @Autowired
    private UserService userService;

    @Autowired
    private SysMessageService sysMessageService;

    @RequestMapping(value = "/checkout/cc/{orderId}", method = RequestMethod.GET)
    public ModelAndView creditCardCheckout(@PathVariable String orderId, HttpServletRequest request) {
        try {
            ModelAndView modelAndView = new ModelAndView("cart/cc");
            modelAndView.addObject("orderId", orderId);
            List<Cardtype> cardtypes = cardtypeService.queryUsefulCardtypes();

            if (cardtypes != null) {
                //001=身份证 002=护照 011=军官证 014=台胞证
                for (Object ct : cardtypes.toArray()) {
                    Cardtype cardtype = (Cardtype) ct;
                    if (cardtype != null && (
                            "014".equalsIgnoreCase(cardtype.getCardtypeid()))) {
                        cardtypes.remove(cardtype);
                    }
                }
                modelAndView.addObject("cardTypes", cardtypes);
                modelAndView.addObject("cardBanks", getUsefulBanks(cardtypes));
            }

            Order order = orderhistService.getOrderHistByOrderid(orderId);
            if (order != null) {
                modelAndView.addObject("totalPrice", order.getTotalprice());
                Contact contact = contactService.get(order.getContactid());
                if (contact != null) {
                    AddressExt addressExt = order.getAddress();
                    if (addressExt != null) {
                        ContactAddressPhoneDto dto = getContactAddressPhone(order.getContactid(), contact.getName(), addressExt.getAddressId());
                        if (dto != null) {
                            modelAndView.addObject("contactId", dto.getContactid());
                            modelAndView.addObject("contactName", dto.getContactName());
                            modelAndView.addObject("shippingAddresses", new ContactAddressPhoneDto[]{dto});
                        }
                    }
                }
            }
            return modelAndView;
        } catch (Exception ex) {
            return handleExceptionArray(ex);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkout/cc/default", method = RequestMethod.POST)
    public List<CreditCardDto> getDefaultCards(@RequestParam(required = false) String contactId,
                                               @RequestParam(required = false, defaultValue = "0") Double amount) throws Exception {
        List<CreditCardDto> defaults = new ArrayList<CreditCardDto>();
        if(StringUtils.isNotBlank(contactId)){
            try {
                Contact contact = contactService.get(contactId);
                if (contact != null) {
                    //001=身份证 002=护照 011=军官证 014=台胞证
                    List<CreditCardDto> cards = new ArrayList<CreditCardDto>();
                    List<Card> creditCards = cardService.getCardsByContactIdExcludeCardTypes(contact.getContactid(), "001", "002", "011", "014");

                    List<Card> identityCards = cardService.getCardsByContactIdIncludeCardTypes(contact.getContactid(), "001", "002", "011");  //, "014"

                    if (identityCards != null && identityCards.size() > 0) {
                        CreditCardDto idCardDto = null;
                        for (Card card : identityCards) {
                            Cardtype cardtype = cardtypeService.getCardType(card.getType());
                            if (cardtype != null) {
                                cards.add(new CreditCardDto(contact, card, cardtype, new ArrayList<Amortisation>()));
                                //默认带出审核通过卡
                                if (idCardDto == null && (card.getState() == null || card.getState() == 0 || card.getState() == 2)) {
                                    idCardDto = cards.get(cards.size() - 1);
                                }
                            }
                        }
                        if (idCardDto != null) {
                            defaults.add(idCardDto);
                        }
                    }

                    if (creditCards != null && creditCards.size() > 0) {
                        CreditCardDto ccCardDto = null;
                        for (Card card : creditCards) {
                            Cardtype cardtype = cardtypeService.getCardType(card.getType());
                            if (cardtype != null) {
                                List<Amortisation> amortisations = amortisationService.queryCardTypeAmortisations(cardtype.getCardtypeid());
                                if (amortisations != null) {
                                    cards.add(new CreditCardDto(contact, card, cardtype, amortisations));
                                }
                                //默认带出审核通过卡
                                if (ccCardDto == null && (card.getState() == null || card.getState() == 0 || card.getState() == 2)) {
                                    ccCardDto = cards.get(cards.size() - 1);
                                }
                            }
                        }
                        if (ccCardDto != null) {
                            defaults.add(ccCardDto);
                        }
                    }
                }
            } catch (Exception ex) {
               /* do nothing */
            }
        }
        return defaults;
    }

    @ResponseBody
    @RequestMapping(value = "/checkout/cc/all", method = RequestMethod.POST)
    public List<CreditCardDto> getAllCards(
            @RequestParam(required = false) String contactId
    ) {
        List<CreditCardDto> cards = new ArrayList<CreditCardDto>();
        if(StringUtils.isNotBlank(contactId)){
            try {
                Contact contact = contactService.get(contactId);
                if (contact != null) {
                    //001=身份证 002=护照 011=军官证 014=台胞证
                    List<Card> creditCards = cardService.getCardsByContactIdExcludeCardTypes(contact.getContactid());
                    if (creditCards != null && creditCards.size() > 0) {
                        for (Card card : creditCards) {
                            Cardtype cardtype = cardtypeService.getCardType(card.getType());
                            if (cardtype != null) {
                                List<Amortisation> amortisations = amortisationService.queryCardTypeAmortisations(cardtype.getCardtypeid());
                                if (amortisations != null) {
                                    cards.add(new CreditCardDto(contact, card, cardtype, amortisations));
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
               /* do nothing */
            }
        }
        return cards;
    }

    @ResponseBody
    @RequestMapping(value = "/checkout/cc/save", method = RequestMethod.POST)
    public String creditCardsave(
            @RequestParam(required = false) String contactId,
            @RequestParam(required = false) String cardNumber,
            @RequestParam(required = false) String cardType,
            @RequestParam(required = false) String validDate,
            @RequestParam(required = false) String extraCode,
            @RequestParam(required = false) Long cardId
    ) {
        if (cardId != null && cardId > 0) {
            try {
                Card card = cardService.getCard(cardId);
                if (card != null) {
                    if (cardNumber.contains("*")) {
                        String oldCardNumber = encryptCardNumber(card.getCardNumber());
                        if(oldCardNumber.equalsIgnoreCase(cardNumber)){
                            cardNumber = card.getCardNumber();
                        } else{
                            return "卡信息更新失败：请录入完整的卡信息";
                        }
                    }

                    if (extraCode.contains("*")) {
                        String oldExtraCode = encryptExtraCode(card.getExtraCode());
                        if(oldExtraCode.equalsIgnoreCase(extraCode)){
                            extraCode = card.getExtraCode();
                        } else{
                            return "卡信息更新失败：请录入完整的Cvv2信息";
                        }
                    }


                    String[] idcardtypes = {"001", "002", "011", "014"};
                    //身份卡不需要审批,其他需要审批
                    //if(false && card.getState() != null && (card.getState() == 0)){  //0:待审 - 任何情况都插入
                    if (Arrays.asList(idcardtypes).contains(cardType)) {
                        if (cardService.updateCard(cardId, cardNumber, cardType, validDate, extraCode)) {
                            return "卡信息更新成功!";
                        } else {
                            return "卡信息更新失败!";
                        }
                    } else {
                        //区分INBOUND与OUTBOUND
                        Integer state = 0; //需要审批
                        AgentUser agentUser = SecurityHelper.getLoginUser();
                        String groupType = userService.getGroupType(agentUser.getUserId());
                        if ("IN".equalsIgnoreCase(groupType)) {
                            //信用卡判断是否正式客户
                            try {
                                if (contactService.get(contactId) == null) {
                                    state = null;
                                }
                            } catch (Exception ex) {
                                state = null;
                            }
                            card = cardService.addCard(contactId, cardNumber, cardType, validDate, extraCode, state);
                            if (card != null) {
                                return "卡信息添加成功!";
                            } else {
                                return "卡信息添加失败!";
                            }
                        } else if ("OUT".equalsIgnoreCase(groupType)) {
                            card = cardService.addCard(contactId, cardNumber, cardType, validDate, extraCode, state);
                            if (card != null) {
                                return "卡信息添加成功!";
                            } else {
                                return "卡信息添加失败!";
                            }
                        } else {
                            return "卡信息添加失败(未知用户类型)!";
                        }

                    }
                } else {
                    return String.format("卡信息更新失败：信用卡%的不存在", cardId);
                }
            } catch (Exception ex) {
                return String.format("卡信息更新失败：%s", ex.getMessage());
            }
        } else {
            try {
                String[] idcardtypes = {"001", "002", "011", "014"};
                //身份卡不需要审批,其他需要审批
                if (Arrays.asList(idcardtypes).contains(cardType)) {
                    Card card = cardService.addCard(contactId, cardNumber, cardType, validDate, extraCode, null);
                    if (card != null) {
                        return "卡信息添加成功!";
                    } else {
                        return "卡信息添加失败!";
                    }
                } else {
                    Integer state = 0;
                    AgentUser agentUser = SecurityHelper.getLoginUser();
                    String groupType = userService.getGroupType(agentUser.getUserId());
                    if ("IN".equalsIgnoreCase(groupType)) {
                        try {
                            //卡判断是否正式客户
                            if (contactService.get(contactId) == null) {
                                state = null;
                            }
                        } catch (Exception ex) {
                            state = null;
                        }
                        Card card = cardService.addCard(contactId, cardNumber, cardType, validDate, extraCode, state);
                        if (card != null) {
                            return "卡信息添加成功!";
                        } else {
                            return "卡信息添加失败!";
                        }
                    } else if ("OUT".equalsIgnoreCase(groupType)) {
                        Card card = cardService.addCard(contactId, cardNumber, cardType, validDate, extraCode, state);
                        if (card != null) {
                            return "卡信息添加成功!";
                        } else {
                            return "卡信息添加失败!";
                        }
                    } else {
                        return "卡信息添加失败(未知用户类型)!";
                    }
                }
            }
            catch (NonUniqueResultException ex){
                return String.format("卡信息添加失败：卡信息重复！");
            }
            catch (Exception ex) {
                return String.format("卡信息添加失败：%s", ex.getMessage());
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkout/cc/cancel", method = RequestMethod.POST)
    public Boolean creditCardCancel(@RequestParam(required = true) String orderId) {
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if (agentUser != null) {
            try {
                orderhistService.saveOrderCancelRequest(orderId, "信用卡支付取消", agentUser.getUserId());

                Order order=orderhistService.getOrderHistByOrderid(orderId);
                //消息通知主管
                SysMessage sysMessage=new SysMessage();
                sysMessage.setContent(orderId);
                sysMessage.setCreateDate(new Date());
                sysMessage.setCreateUser(agentUser.getUserId());
                sysMessage.setRecivierGroup(userService.getUserGroup(order.getCrusr()));
                sysMessage.setDepartmentId(userService.getDepartment(order.getCrusr()));
                sysMessage.setSourceTypeId(String.valueOf(MessageType.CANCEL_ORDER.getIndex()));
                sysMessageService.addMessage(sysMessage);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    @ResponseBody
    @RequestMapping(value = "/checkout/cc/pay", method = RequestMethod.POST)
    public String creditCardPay(
            @RequestParam(required = true) String orderId,
            @RequestParam(required = true) Long ccCardId,
            @RequestParam(required = false) Long idCardId,
            @RequestParam(required = false) String extraCode,
            @RequestParam(required = false) String lastStatus,
            @RequestParam(required = false) String mobile
    ) {
        try {
            AgentUser agentUser = SecurityHelper.getLoginUser();
            if (agentUser == null) {
                return String.format("订单支付异常");
            }

            Order order = orderhistService.getOrderHistByOrderid(orderId);
            if (order == null) {
                return String.format("订单%s不存在！", orderId);
            }
            Card ccCard = cardService.getCard(ccCardId);
            Cardtype cardtype = cardtypeService.getCardType(ccCard.getType());

            if (cardtype == null) {
                return String.format("证件类型不存在！");
            }

            if(StringUtils.isBlank(extraCode)){
                extraCode = ccCard.getExtraCode();
            }

            order.setPaycontactid(ccCard.getContactId());
            order.setCardnumber(String.valueOf(ccCard.getCardId()));
            order.setCardtype(cardtype.getCardname());
            order.setLaststatus(lastStatus);
            order.setMdusr(agentUser.getUserId());
            order.setMddt(new Date());

            if (canOnlinePay(cardtype.getBankName())) {
                //在线受权
                Date expiryDate;
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                    expiryDate = format.parse(ccCard.getValidDate());
                } catch (ParseException ex) {
                    expiryDate = new Date();
                }

                OnlinePayment req = new OnlinePayment();
                req.setAmount(order.getTotalprice());
                req.setCardNo(ccCard.getCardNumber());
                req.setExpiryDate(expiryDate);
                req.setStageNum(Integer.parseInt(lastStatus));
                req.setOrderId(orderId);

                if ("招行".equalsIgnoreCase(cardtype.getBankName())) {
                    req.setPaytype("Merchants");
                } else if ("工行".equalsIgnoreCase(cardtype.getBankName())) {
                    req.setMobile(mobile);
                    req.setExtraCode(extraCode);
                    req.setPaytype("icbc");
                    //001=身份证 002=护照 011=军官证 014=台胞证
                    Card idCard = cardService.getCard(idCardId);
                    if(idCard != null){
                        req.setCredentialsNo(idCard.getCardNumber());
                        //只支持身份证/护照
                        if("001".equalsIgnoreCase(idCard.getType()))  {
                            req.setCredentialsType(CredentialsType.IDENTITYCARD);
                        } else if("002".equalsIgnoreCase(idCard.getType()))  {
                            req.setCredentialsType(CredentialsType.PASSORT);
                        }
                    }
                }

                PayResult rsp = onlinePayService.pay(req);
                if (rsp.isSucc()) {
                    order.setCardrightnum(rsp.getAuthID());
                    logger.info(
                            "-----------REQ-------------\n" +
                            "cardNo:" + req.getCardNo() + "\n" +
                            "expiryDate:" + req.getExpiryDate() + "\n" +
                            "stageNum:" + req.getStageNum() + "\n" +
                            "amount:" + req.getAmount() + "\n" +
                            "-----------RSP-------------\n" +
                            "transTime:" + rsp.getTransTime() + "\n" +
                            "authID:" + rsp.getAuthID() + "\n" +
                            "orderNum:" + rsp.getOrderNum() + "\n" +
                            "hpFee:" + rsp.getHpFee() + "\n" +
                            "hpAmount:" + rsp.getHpAmount() + "\n" +
                            "totalAmount:" + rsp.getTotalAmount() + "\n" +
                            "refNum:" + rsp.getRefNum() + "\n" +
                            "--------------------------\n"
                    );
                    order.setConfirm("-1");
                    order.setCardrightnum(rsp.getAuthID());
                    orderhistService.updateOrderhist(order);

                    return String.format("订单%s索权成功！", orderId);
                } else {
                    orderhistService.updateOrderhist(order);
                    String result = String.format("%s在线索权失败：%s[%s]", cardtype.getBankName(),rsp.getErrorMsg(), rsp.getErrorCode());
                    logger.error("订单号:" + orderId + "-" + result);
                    return result;
                }
            } else {
                orderhistService.updateOrderhist(order);
            }

            return String.format("订单%s支付成功！", orderId);


        } catch (Exception ex) {
            String result = String.format("订单%s支付异常：%s", orderId, ex.getMessage());
            logger.error(result,ex);
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkout/cc/canOnlinePay", method = RequestMethod.POST)
   public Boolean  canOnlinePay( @RequestParam(required = true)  String bankName){
       if ("招行".equalsIgnoreCase(bankName) || "工行".equalsIgnoreCase(bankName)) {
           return true;
       } else {
           return false;
       }
   }


    @ResponseBody
    @RequestMapping(value = "/checkout/cc/change", method = RequestMethod.POST)
    public CreditCardOwnerDto creditCardChange(
            @RequestParam(required = true) String contactId,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            Contact contact = contactService.get(contactId);
            if (contact != null) {
                return new CreditCardOwnerDto(contactId, contact.getName(), getAllCards(contactId));
            }
        } catch (Exception ex) {
           /* do nothing */
        }

        return new CreditCardOwnerDto(contactId, contactId, new ArrayList<CreditCardDto>());
    }

    @Deprecated
    @ResponseBody
    @RequestMapping(value = "/checkout/cc/auth", method = RequestMethod.POST)
    public Boolean creditCardAuth(
            @RequestParam(required = true) String orderId,
            @RequestParam(required = true) Long ccCardId,
            @RequestParam(required = false) Long idCardId,
            @RequestParam(required = false) String extraCode,
            @RequestParam(required = false) String laststatus) {

        try {
            Order order = orderhistService.getOrderHistByOrderid(orderId);
            if (order != null) {
                Card card = cardService.getCard(ccCardId);
                if (card != null) {
                    order.setCallid(String.valueOf(card.getCardId()));
                    order.setCardnumber(card.getCardNumber());
                    order.setCardtype(card.getType());
                    order.setLaststatus(laststatus);

                    Date expiryDate;
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm");
                        expiryDate = format.parse(card.getValidDate());
                    } catch (ParseException ex) {
                        expiryDate = new Date();
                    }

                    CreditcardOnlineAuthorization creditcardOnlineAuthorization = new CreditcardOnlineAuthorization();
                    creditcardOnlineAuthorization.setAmount(order.getTotalprice());
                    creditcardOnlineAuthorization.setCardNo(order.getCardnumber());
                    creditcardOnlineAuthorization.setExpiryDate(expiryDate);
                    creditcardOnlineAuthorization.setStageNum(Integer.parseInt(laststatus));

                    CreditcardOnlineAuthorizationResponse rsp = creditcardOnlineAuthorizationService.hirePurchase(creditcardOnlineAuthorization);
                    if (rsp != null) {
                        order.setCardrightnum(rsp.getAuthID());
                    }
                    orderhistService.updateOrderhist(order);
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    private ContactAddressPhoneDto getContactAddressPhone(String contractId, String contractName, String addressId) throws Exception {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setRows(999);
        AddressDto addressDto = addressService.queryAddress(addressId);
        Map<String, Object> phoneMap = phoneService.findByContactId(contractId, dataGridModel);
        List<Phone> phoneList = (List<Phone>) phoneMap.get("rows");
        ContactAddressPhoneDto contactAddressPhoneDto = new ContactAddressPhoneDto(addressDto, phoneList);
        contactAddressPhoneDto.setContactName(contractName);
        return contactAddressPhoneDto;
    }

    private List<String> getUsefulBanks(List<Cardtype> cardtypes) {
        List<String> banks = new ArrayList<String>();
        if (cardtypes != null) {
            for (Cardtype type : cardtypes) {
                if (StringUtils.isNotBlank(type.getBankName())) {
                    if (!banks.contains(type.getBankName())) {
                        banks.add(type.getBankName());
                    }
                }
            }
        }
        return banks;
    }

    public String encryptCardNumber( String cardNumber){
        if(cardNumber.length() > 8){
            String prefix = cardNumber.substring(0, 4);
            String suffix = cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
            cardNumber = StringUtils.rightPad(prefix, cardNumber.length() - 8, "*") +suffix;
        } else if(cardNumber.length() > 4) {
            String prefix = cardNumber.substring(0, 4);
            cardNumber = StringUtils.rightPad(prefix, cardNumber.length() - 4, "*");
        }
        return cardNumber;
    }

    public String encryptExtraCode( String extraCode){
        if(extraCode != null && extraCode.length() > 1){
            String prefix = extraCode.substring(0, 1);
            extraCode = StringUtils.rightPad(prefix, extraCode.length(), "*");
        }
        return extraCode;
    }
}
