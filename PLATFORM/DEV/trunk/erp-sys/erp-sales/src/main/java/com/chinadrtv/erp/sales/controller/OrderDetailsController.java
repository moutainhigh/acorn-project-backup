/**
 * 
 */
package com.chinadrtv.erp.sales.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.ic.model.CompanyDeliverySpan;
import com.chinadrtv.erp.ic.service.CompanyDeliverySpanService;
import com.chinadrtv.erp.marketing.core.common.AuditTaskType;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.Amortisation;
import com.chinadrtv.erp.model.Cardtype;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.Card;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.model.inventory.ProductType;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.service.AmortisationService;
import com.chinadrtv.erp.sales.core.service.OrderUrgentApplicationService;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.sales.core.util.DateUtil;
import com.chinadrtv.erp.sales.dto.CustomerFrontDto;
import com.chinadrtv.erp.sales.dto.OrderDto;
import com.chinadrtv.erp.sales.dto.OrderInfoDto;
import com.chinadrtv.erp.sales.dto.ReceiptDto;
import com.chinadrtv.erp.sales.exception.ExceptionErrorTitle;
import com.chinadrtv.erp.sales.exception.OrderDetailsAddressNotFoundException;
import com.chinadrtv.erp.sales.service.OrderInfoService;
import com.chinadrtv.erp.tc.core.constant.model.OrderhistAssignInfo;
import com.chinadrtv.erp.tc.core.service.AddressExtService;
import com.chinadrtv.erp.tc.core.service.OrderhistCompanyAssignService;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.CardDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.AreaService;
import com.chinadrtv.erp.uc.service.CardService;
import com.chinadrtv.erp.uc.service.CardtypeService;
import com.chinadrtv.erp.uc.service.CityService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.CountyAllService;
import com.chinadrtv.erp.uc.service.OldContactexService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.uc.service.ProvinceService;
import com.chinadrtv.erp.uc.service.ShoppingCartService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * @author dengqianyong
 * 
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/myorder/orderDetails")
public class OrderDetailsController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(OrderDetailsController.class);
	
	@Value("${CALL_ORDER_LOGISTICS_URL}")
	private String callOrderLogisticsUrl;
	
	@Value("${CALL_ORDER_TRACK_URL}")
	private String callOrderTrackUrl;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@Autowired
	private OrderhistService orderhistService;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
    private OldContactexService oldContactexService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AddressExtService addressExtService;
	
	@Autowired
	private PhoneService phoneService;
	
	@Autowired
	private CompanyDeliverySpanService companyDeliverySpanService;
    @Autowired
    private OrderhistCompanyAssignService orderhistCompanyAssignService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private CountyAllService countyAllService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardtypeService cardtypeService;
	
	@Autowired
	private AmortisationService amortisationService;
	
	@Autowired
	private NamesService namesService;
	
    @Autowired
    private ChangeRequestService changeRequestService;
    
    @Autowired
    private UserBpmTaskService userBpmTaskService;
    
    @Autowired
    private OrderUrgentApplicationService orderUrgentApplicationService;
    
    @Autowired
    private SysMessageService sysMessageService;

    private static Map<String, String> orderStatusMap = new HashMap<String, String>();
	private static Map<String, String> customStatusMap = new HashMap<String, String>();
	private static Map<String, String> orderTypeMap = new HashMap<String, String>();
	private static List<Map<String, String>> bankTypes;
	private static List<Cardtype> allBankCardtypes;
	private static Map<String, List<Cardtype>> mappingBankCardtypes;
	private static Map<String, String> idTypeMap = new HashMap<String, String>();
	private static Map<String, String> soldwithMap = new HashMap<String, String>();
	
	@PostConstruct
	public void init() {
		for (Names names : namesService.getAllNames("ORDERSTATUS")) {
			orderStatusMap.put(names.getId().getId(), names.getDsc());
		}
		for (Names names : namesService.getAllNames("CUSTOMIZESTATUS")) {
			customStatusMap.put(names.getId().getId(), names.getDsc());
		}
		for (Names names : namesService.getAllNames("ORDERTYPE")) {
			orderTypeMap.put(names.getId().getId(), names.getDsc());
		}
		
		bankTypes = cardtypeService.getBanktypes();
		allBankCardtypes = cardtypeService.getAllBankCards();
		mappingBankCardtypes = new HashMap<String, List<Cardtype>>(bankTypes.size());
		for (int idx = 0; idx < bankTypes.size(); idx++) {
			mappingBankCardtypes.put(String.valueOf(idx), new ArrayList<Cardtype>());
		}
		
		outter: for (Cardtype ct : allBankCardtypes) {
			for (Map<String, String> bt : bankTypes) {
				if (StringUtils.equals(ct.getBankName(), bt.get("name"))) {
					mappingBankCardtypes.get(bt.get("value")).add(ct);
					continue outter;
				}
				if (StringUtils.isEmpty(ct.getBankName())) {
					mappingBankCardtypes.get(String.valueOf(bankTypes.size() - 1)).add(ct);
					continue outter;
				}
			}
		}
		
		idTypeMap.put("001", "身份证");
		idTypeMap.put("002", "护照");
		idTypeMap.put("011", "军官证");
		
		soldwithMap.put("1", "直接销售");
		soldwithMap.put("2", "搭销");
		soldwithMap.put("3", "赠品");
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	/**
	 * 得到订单信息
	 * @param orderId
	 * @param cartId
	 * @param batchId
	 * @param activable
	 * @param from
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/get/{orderId}", method = RequestMethod.GET)
	public String viewOrder(@PathVariable("orderId") String orderId,
			@RequestParam(value = "cartId", required = false) Long cartId,
			@RequestParam(value = "batchId", required = false) String batchId,
			@RequestParam(value = "activable", required = false) boolean activable,
			@RequestParam(value = "from", required = false) Integer from,
			ModelMap model) {
		Order order = orderhistService.getOrderHistByOrderid(orderId);
		List<OrderDto.OrderDetailDto> orderdetDtos = changeOrderDetailDtoList(order.getOrderdets());
		try {
			setViewModel(order, orderdetDtos, model);
		} catch (Exception e) {
			if (e instanceof ExceptionErrorTitle) {
				model.put("errorTitle", ((ExceptionErrorTitle) e).getErrorTtile());
			} else {
				model.put("errorTitle", "历史信息错误，无法查询");
			}
			model.put("errorMsg", e.getMessage());
			return "myorder/errorOrder";
		}
		
		if (cartId != null) {
			Order cartOrder = shoppingCartService.createOrderbyShoppingCart(cartId);
			List<OrderDto.OrderDetailDto> cartdetDtos = changeOrderDetailDtoList(cartOrder.getOrderdets());
			model.put("editDetails", cartdetDtos);
			model.put("cartId", cartId);
			Map<OrderDto.OrderDetailDto, String> map = orderInfoService.compare(
					order.getOrderdets(), cartOrder.getOrderdets());
			model.put("detailsMap", map);
			model.put("isDetChanged", isOrderdetChanged(map));
		}
		
		
		model.put("batchId", batchId);
		model.put("editable", orderhistService.isOrderModifiable(orderId));
		model.put("deletable", orderhistService.isOrderDeletable(orderId));
		model.put("approvaled", StringUtils.equals(
				checkOrderInBmp(orderId).get("approve"), Boolean.TRUE.toString()));
		model.put("activable", activable);
		model.put("consultable", orderhistService.isOrderConsultable(orderId));
		
		boolean couldReApply = orderUrgentApplicationService.couldReApply(orderId);
		model.addAttribute("couldReApply", couldReApply);
		
		return "myorder/orderDetails";
	}
	
	/**
	 * 得到商品明细
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/details/{orderId}", method = RequestMethod.POST)
	public List<Map<String, String>> viewDetailsByOrderId(@PathVariable("orderId") String orderId) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Order order = orderhistService.getOrderHistByOrderid(orderId);
		Set<OrderDetail> orderdets = order.getOrderdets();
		for (OrderDetail det : orderdets) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("prodid", det.getProdid());
			map.put("prodname", det.getProdname());
			ProductType pt = orderInfoService.getProductType(det.getProdid(), det.getProducttype());
			map.put("productTypeName", pt != null ? pt.getDsc() : "");
			BigDecimal price = (det.getUpnum() != null && det.getUpnum() != 0) ? det.getUprice() : det.getSprice();
			map.put("price", price.setScale(2).toString());
			Integer num = (det.getUpnum() != null && det.getUpnum() != 0) ? det.getUpnum() : det.getSpnum();
			map.put("num", num.toString());
			map.put("jifen", det.getJifen());
			map.put("soldwith", soldwithMap.get(det.getSoldwith()));
			result.add(map);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/contact/{contactId}")
	public Contact getContact(@PathVariable("contactId") String contactId) {
		return contactService.get(contactId);
	}
	
	/**
	 * 得到当前订单的收货信息(收货人、收货地址、收货电话)
	 * @param getContactId
	 * @param addressId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/receipt")
	public List<ReceiptDto> getReceipt(
			@RequestParam(value = "contactId", required = true) String getContactId, 
			@RequestParam(value = "addressId", required = true) String addressId) {
		List<ReceiptDto> receipts = new ArrayList<ReceiptDto>();
		ReceiptDto rec = orderInfoService.getReceipt(getContactId, addressId);
		receipts.add(rec);
        return receipts;
	}
	
	/**
	 * 得到当前联系人所有的收货信息(收货人、收货地址、收货电话)
	 * @param contactId
	 * @param payType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/receipts")
	public List<ReceiptDto> getReceipts(
			@RequestParam(value = "contactId", required = true) String contactId,
			@RequestParam(value = "payType", required = false) String payType) {
		if (StringUtils.isNotEmpty(payType) && StringUtils.equals(payType, "11")) {
			List<ReceiptDto> receipts = orderInfoService.getPickUpSelfReceipts();
			String contactName = contactService.get(contactId).getName();
			List<Phone> phones = phoneService.getPhonesByContactId(contactId);
			for (ReceiptDto re : receipts) {
				re.setContactId(contactId);
				re.setContactName(contactName);
				re.setPhones(phones);
			}
			return receipts;
		} else {
			return orderInfoService.getReceiptsByContactId(contactId);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/checkwrong/{addressId}")
	public String checkWrong(@PathVariable("addressId") String addressId) {
		AddressExt ae = addressExtService.getAddressExt(addressId);
		if (ae.getProvince() == null || ae.getCity() == null || ae.getCounty() == null || ae.getArea() == null) {
			return Boolean.TRUE.toString();
		}
		if (!ae.getProvince().getProvinceid().equals(ae.getArea().getProvid())
				|| !ae.getCity().getCityid().equals(ae.getArea().getCityid())
				|| !ae.getCounty().getCountyid().equals(ae.getArea().getCountyid())) {
			return Boolean.TRUE.toString();
		}
		
		return Boolean.FALSE.toString();
	}
			
	
	/**
	 * 设置主号码
	 * @param contactId
	 * @param phoneId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/prmphn")
	public String editPrmphn(
			@RequestParam(value = "contactId", required = true) String contactId,
			@RequestParam(value = "phoneId", required = true) String phoneId) {
		phoneService.setPrimePhone(contactId, phoneId);
		return "SUCCESS";
	}
	
	/**
	 * 设置备选号码
	 * @param isUnset
	 * @param contactId
	 * @param phoneId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bckphn")
	public String editBckphn(
			@RequestParam(value = "isUnset", required = false) Boolean isUnset,
			@RequestParam(value = "contactId", required = true) String contactId,
			@RequestParam(value = "phoneId", required = true) String phoneId) {
		if (isUnset != null && isUnset) {
			phoneService.unsetBackupPhone(phoneId);
		} else {
			phoneService.setBackupPhone(contactId, phoneId);
		}
		return "SUCCESS";
	}
	
	/**
	 * 增加地址
	 * @param orderInfo
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/address/add", method = RequestMethod.POST)
	public String addAddress(@RequestBody OrderInfoDto orderInfo) throws Exception { // orderInfo 只用到 Address部分
		AddressExt addressExt = new AddressExt();
		Address address = new Address();
		setAddress(addressExt, address, orderInfo);
		addressExt.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_UNAUDITED);
		addressExtService.createAddressExt(addressExt, address);
		return address.getAddressid();
	}
	
	@ResponseBody
	@RequestMapping(value = "/address/{addressId}/delivey/{orderId}", method = RequestMethod.GET)
	public String mailPrice(@PathVariable("addressId") String addressId,
			@PathVariable("orderId") String orderId) {
		Order order = orderhistService.getOrderHistByOrderid(orderId);
		AddressExt ae = addressExtService.getAddressExt(addressId);

        Order newOrder = new Order();
        BeanUtils.copyProperties(order,newOrder);
        newOrder.setAddress(ae);
        OrderhistAssignInfo oai = orderhistCompanyAssignService.queryDefaultAssignInfo(newOrder);

		CompanyDeliverySpan delivery = companyDeliverySpanService
				.getDeliverySpan(oai.getEntityId(),
						Long.valueOf(newOrder.getOrdertype()),
						Long.valueOf(newOrder.getPaytype()),
						Long.valueOf(ae.getProvince().getProvinceid()),
						Long.valueOf(ae.getCity().getCityid()),
						Long.valueOf(ae.getCounty().getCountyid()),
						Long.valueOf(ae.getArea().getAreaid()));
		return delivery.getUserDef1();
	}
	
	/**
	 * 得到当前订单使用的信息卡信息
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/credit/{orderId}")
	public List<Map<String, Object>> getCredit(@PathVariable String orderId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(1);
		Map<String, Object> map = new HashMap<String, Object>(10);
		Order order = orderhistService.getOrderHistByOrderid(orderId);
		if (StringUtils.isEmpty(order.getCardnumber())) return result;
		
		Card card = cardService.getCard(Long.valueOf(order.getCardnumber()));
		String cardContactName = orderInfoService.getContactNameByContactId(card.getContactId());
		Cardtype cardtype = cardtypeService.getCardType(card.getType());
		
		String idType = "";
		String idTypeName = "";
		String idNum = "";
		List<Card> idCards = cardService.getIdentityCard(card.getContactId());
		if (CollectionUtils.isEmpty(idCards)) {
			idCards = cardService.getOtherIdentityCard(card.getContactId());
		}
		if (!CollectionUtils.isEmpty(idCards)) {
			idType = idCards.get(0).getType();
			idTypeName = idTypeMap.get(idType);
			idNum = idCards.get(0).getCardNumber();
		}
		
		map.put("cardId", card.getCardId());
		map.put("contactId", card.getContactId());
		map.put("contactName", cardContactName);
		map.put("cardTypeId", card.getType());
		map.put("cardType", StringUtils.isNotEmpty(cardtype.getBankName()) 
				? cardtype.getBankName() + "-" + cardtype.getCardname() 
						: cardtype.getCardname());
		map.put("cardNumber", card.getCardNumber());
		map.put("bankName", cardtype.getBankName());
		map.put("validity", card.getValidDate());
		map.put("extraCode", card.getExtraCode());
		map.put("identityType", idType);
		map.put("identityTypeName", idTypeName);
		map.put("identityNumber", idNum);
		map.put("amortisation", order.getLaststatus());
		
		List<Amortisation> amortisations = amortisationService.queryCardTypeAmortisations(card.getType());
		Collections.sort(amortisations, new AmortisationComparator());
		map.put("amortisations", amortisations);
		
		result.add(map);
		return result;
	}
	
	/**
	 * 得到用户所有信用卡列表
	 * @param contactId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/credits/{contactId}")
	public List<CardDto> getCredits(@PathVariable("contactId") String contactId) {
		List<CardDto> cards = cardService.getCards(contactId);
		if (CollectionUtils.isEmpty(cards)) return cards;
		
		List<CardDto> dtos = new ArrayList<CardDto>(cards.size());
		for (CardDto dto : cards) {
			List<Amortisation> amortisations = amortisationService.queryCardTypeAmortisations(dto.getType());
			Collections.sort(amortisations, new AmortisationComparator());
			dto.setContactName(orderInfoService.getContactNameByContactId(dto.getContactId()));
			dto.setShowextracode(cardtypeService.getCardType(dto.getType()).getShowextracode());
			dto.setAmortisations(amortisations);
			dtos.add(dto);
		}
		return dtos;
	}
	
	/**
	 * 得到信用卡可分期列表
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/credit/amor")
	public List<Amortisation> amortisations(
			@RequestParam(value = "type", required = true) String type) {
		List<Amortisation> amortisations = amortisationService
				.queryCardTypeAmortisations(type);
		Collections.sort(amortisations, new AmortisationComparator());
		return amortisations;
	}
	
	@ResponseBody
	@RequestMapping(value = "/credit/duplicate", method = RequestMethod.POST)
	public String checkRepeat(Card card) {
		return String.valueOf(cardService.isCardDuplicate(card));
	}
	
	/**
	 * 得到银行类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/banktype")
	public List<Map<String, String>> getBanktypes() {
		return bankTypes;
	}
	
	/**
	 * 得到卡类型
	 * @param bank
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cardtype")
	public List<Cardtype> getBankcards(
			@RequestParam(value = "bank", required = false) String bank) {
		if (StringUtils.isEmpty(bank)) { // 得到所有的卡类型
			return allBankCardtypes;
		}
		return mappingBankCardtypes.get(bank);
	}
	
	/**
	 * 保存信用卡
	 * @param card
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/card/save")
	public Map<String, String> saveOrUpdateCard(Card card) {
		Map<String, String> result = new HashMap<String, String>();
		
		if (StringUtils.equals("001", card.getType())) { 
			// 如果保存的身份证，需要判断库里是否已经存在身份证信息，已存在则更新，不存在新增
			List<Card> idcards = cardService.getIdentityCard(card.getContactId());
			if (!CollectionUtils.isEmpty(idcards)) {
				Card idcard = idcards.get(0);
				card.setCardId(idcard.getCardId());
			}
		} else if (!StringUtils.equals("002", card.getType())
				&& !StringUtils.equals("011", card.getType())
				&& !StringUtils.equals("014", card.getType())) {
			card.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_UNAUDITED);
		}
		
		try {
			cardService.merge(card);
		} catch (ServiceException e) {
			result.put("st", "-1");
			result.put("msg", e.getMessage());
			return result;
		}
		result.put("st", "1");
		result.put("msg", "success");
		return result;
	}

    /**
     * 检查手机号码是否重复
     * @param phone
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/check/phone", method = RequestMethod.POST)
	public String checkMobileExists(@RequestBody Phone phone) {
		return String.valueOf(phoneService.checkExistSameNameAndPhone(phone));

        /*
        //固定电话号码验证
        List<Phone> telephone = this.convertStrTotelePhone(phoneStr);
        List<Phone> contactPhoneList = phoneService.getPhonesByContactId(contactId);
        for (Phone contactPhone : contactPhoneList) {
            String phoneNum = (contactPhone.getPhn1() == null ? "" : contactPhone.getPhn1()) + contactPhone.getPhn2() + (contactPhone.getPhn3() == null ? "" : contactPhone.getPhn3());
            for (Phone phone : telephone) {
                String newPhoneNum = (phone.getPhn1() == null ? "" : phone.getPhn1()) + phone.getPhn2() + (phone.getPhn3() == null ? "" : phone.getPhn3());
                if (phoneNum.equals(newPhoneNum)) {
                    map.put("result", 1);
                }
            }
        }
         */
	}
	
	@ResponseBody
	@RequestMapping(value = "/check/contactName", method = RequestMethod.GET)
	public String checkContactNameBeingModified(
			@RequestParam(value = "contactId", required = true) String contactId) {
		Collection<UserBpmTask> results = userBpmTaskService
				.queryUnProcessedContactBaseChangeTask(contactId);
		return String.valueOf(!CollectionUtils.isEmpty(results));
	}
	
	/**
	 * 检查重复手机号码
	 * @param contactId
	 * @param phones
	 * @return 0：无需处理，1：他人号码重复，2：多人号码重复
	 */
	@ResponseBody
	@RequestMapping(value = "/check/repeatMobile", method = RequestMethod.POST)
	public Map<String, Object> checkRepeatMobile(
			@RequestParam(value = "contactId", required = true) String contactId,
			@RequestBody List<Map<String, Object>> phones) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (CollectionUtils.isEmpty(phones)) { // 没有新增手机
			result.put("result", 0);
			return result;
		}

		List<String> queryParams = new ArrayList<String>();
		for (Map<String, Object> map : phones) {
			queryParams.add((String) map.get("phn2"));
		}

		Integer count = contactService.findContactCountByPhoneList(queryParams);
		if (count == null || count == 0) { // 新增的手机号码没有查到重复
			result.put("result", 0);
			return result;
		}

		DataGridModel dataGridModel = new DataGridModel();
		dataGridModel.setRows(Integer.MAX_VALUE);
		Map<String, Object> contactMap = contactService.findContactByPhoneList(
				queryParams, dataGridModel);
		List<Contact> contacts = (List<Contact>) contactMap.get("rows");
		if (contacts.size() == 1) {
			result.put("result", 1);
			result.put("contactId", contacts.get(0).getContactid());
		} else {
			List<CustomerFrontDto> rows = new ArrayList<CustomerFrontDto>();
	        for(Contact contact : contacts){
	            CustomerFrontDto dto = new CustomerFrontDto();
	            AddressDto address = addressService.getContactMainAddress(contact.getContactid());
	            String level = contactService.findLevelByContactId(contact.getContactid());
	            dto.setAddress(address != null ? address.getAddress() : "");
	            dto.setCustomerId(contact.getContactid());
	            dto.setName(contact.getName());
	            dto.setLevel(level);
	            dto.setCrusr(contact.getCrusr());
	            dto.setCrdtStr(DateUtil.formatDate(contact.getCrdt(), DateUtil.FORMAT_DATETIME_FULL));
	            rows.add(dto);
	        }
			result.put("result", 2);
			result.put("rows", rows);
		}

		return result;
	}
	
	@RequestMapping(value = "/logistics", method = RequestMethod.POST)
	public void orderLogistics(HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) String orderId)
			throws IOException {
		String param = String.format("{\"orderId\":\"%s\"}", orderId);
		String res = null;
		try {
			res = callOrderQuery(callOrderLogisticsUrl, param);
		} catch (Exception e) {
		}
		HttpUtil.ajaxReturn(response, res != null ? res : "[]", "application/json");
	}
	
	@RequestMapping(value = "/track", method = RequestMethod.POST)
	public void orderTrack(HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) String orderId)
			throws IOException {
		String param = String.format("{\"orderId\":\"%s\"}", orderId);
		String res = null;
		try {
			res = callOrderQuery(callOrderTrackUrl, param);
		} catch (Exception e) {
		}
		HttpUtil.ajaxReturn(response, res != null ? res : "[]", "application/json");
	}
	
	/**
	 * 计算运费
	 * @param orderId
	 * @param cartId
	 * @param addressId
	 * @return
	 * @throws ErpException
	 */
	@ResponseBody
	@RequestMapping(value = "/mailprice/{orderId}", method = RequestMethod.POST)
	public String calMailPrice(@PathVariable("orderId") String orderId, 
			@RequestParam(value = "cartId", required = false) Long cartId, 
			@RequestParam(value = "addressId", required = true) String addressId,
			@RequestParam(value = "amor", required = false) Integer amor) throws ErpException {
		Order order = new Order();
		BeanUtils.copyProperties(orderhistService.getOrderHistByOrderid(orderId), order);
		if (cartId != null) {
			Order cartOrder = shoppingCartService.createOrderbyShoppingCart(cartId);
			order.setOrderdets(cartOrder.getOrderdets());
		}
		if (amor != null) {
			order.setLaststatus(amor.toString());
		}
		AddressExt addressExt = new AddressExt();
		BeanUtils.copyProperties(addressExtService.getAddressExt(addressId), addressExt);
		order.setAddress(addressExt);
		BigDecimal price = orderhistService.calcOrderMailPrice(order, SecurityHelper.getLoginUser().getDepartment());
		return price != null ? price.toString() : "0";
	}
	
	/**
	 * 检查订单是否正在审批
	 * 
	 * @param orderId
	 * @return true 有审批中的流程 
	 * 			false 没有正在的流程
	 */
	@ResponseBody
	@RequestMapping(value = "/check/{orderId}/approving", method = RequestMethod.POST)
	public Map<String, String> checkOrderInBmp(@PathVariable("orderId") String orderId) {
		Map<String, String> result = new HashMap<String, String>();
		List<UserBpmTask> tasks = userBpmTaskService.queryUnterminatedOrderChangeTask(orderId);
		String approve = Boolean.TRUE.toString();
		for (UserBpmTask task : tasks) {
			if (StringUtils.equals("0", task.getStatus())) {
				approve = Boolean.FALSE.toString();
				UserBpm userBpm = changeRequestService.queryApprovingTaskById(task.getBatchID());
				result.put("batchId", task.getBatchID());
				result.put("bpmType", AuditTaskType.fromOrdinal(Integer.parseInt(userBpm.getBusiType())));
				break;
			}
		}
		result.put("approve", approve);
		return result;
	}
	
	/**
	 * 检查当前订单是否有关联订单
	 * @param orderId
	 * @param contactChanged
	 * @param addressChanged
	 * @param phoneChanged
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check/{orderId}/related", method = RequestMethod.POST)
	public List<Map<String, ?>> checkRelated(@PathVariable("orderId") String orderId, 
			@RequestParam(value = "contactChanged", required = false) boolean contactChanged,
			@RequestParam(value = "addressChanged", required = false) boolean addressChanged,
			@RequestParam(value = "phoneChanged", required = false) boolean phoneChanged) {
		List<Order> orders = null;
		try {
			orders = orderhistService.checkCorrelativeOrder(orderId,
					addressChanged, contactChanged, phoneChanged);
		} catch (ErpException e) {
			logger.error(e.getMessage(), e);
		}
		if (CollectionUtils.isEmpty(orders)) return Collections.emptyList();
		
		List<Map<String, ?>> result = new ArrayList<Map<String, ?>>(orders.size());
		for (Order order : orders) {
			if (order.getOrderid().equals(orderId)) continue;
			
			Map<String, Object> map = new HashMap<String, Object>(9);
			map.put("orderId", order.getOrderid());
			map.put("createTime", DateUtil.formatDate(order.getCrdt(), DateUtil.FORMAT_DATETIME_FULL));
			map.put("crusr", order.getCrusr());
			map.put("grpid", order.getGrpid());
			map.put("contactName", orderInfoService.getContactNameByContactId(order.getContactid()));
			map.put("status", orderStatusMap.get(order.getStatus()));
			map.put("productPrice", order.getProdprice().setScale(2).toString());
			map.put("mailPrice", order.getMailprice().setScale(2).toString());
			map.put("totalPrice", order.getTotalprice().setScale(2).toString());
			result.add(map);
		}
		return result;
	}
	
	/**
	 * 提交修改订单
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit/submit", method = RequestMethod.POST)
	public Map<String, String> edit(@RequestBody OrderInfoDto orderInfo) {
		Map<String, String> res = new HashMap<String, String>();
		if (orderInfo == null) {
			res.put("code", "-1");
			res.put("msg", "修改订单失败");
			return res;
		}
		
		if (!orderhistService.isOrderModifiable(orderInfo.getOrderId())) {
			res.put("code", "-1");
			res.put("msg", "订单不可修改");
			return res;
		}
		
		try {
			// 验证订单信息
			orderInfoService.validateCommit(orderInfo);
		} catch (Exception e) {
			res.put("code", "-1");
			res.put("msg", e.getMessage());
			return res;
		}
		
		// 该order是数据库中order的副本，对该order进行修改时，不会被hibernate联级更新
        Order originalOrder = orderhistService.getOrderHistByOrderid(orderInfo.getOrderId());
//        if ((StringUtils.equals(originalOrder.getPaytype(), "1") || StringUtils.equals(originalOrder.getPaytype(), "11"))
//        		&& transferBlackListService.checkContactBlackList(originalOrder.getContactid())) {
//            res.put("code", "-1");
//            res.put("msg", "订购客户为黑名单客户，无法使用货到付款或上门自提的支付方式。");
//            return res;
//        }
        Order editOrder = new Order();
		BeanUtils.copyProperties(originalOrder, editOrder);
        editOrder.setMdusr(SecurityHelper.getLoginUser().getUserId());
		
		// 更新订单属性
		updateOrder(editOrder, orderInfo);
		// 更新订单商品
		updateOrderDetails(editOrder, orderInfo, originalOrder);
		// 更新地址
		Address editAddress = updateAddress(editOrder, orderInfo, originalOrder);
		
		Contact editContact = null;
		if (!originalOrder.getGetcontactid().equals(orderInfo.getContactId())) {
			editContact = contactService.get(orderInfo.getContactId());
		}
		
		// 更新信用卡信息
		Card card = null;
		if (StringUtils.equals(originalOrder.getPaytype(), "2")) {
			card = updateCard(orderInfo.getCard());
			editOrder.setPaycontactid(orderInfo.getCard().getContactId());
		}
		
		try {
			orderhistService.saveOrderModifyRequest(
					editOrder, // 修改后的订单对象
					editAddress, // 修改后的地址对象
					editContact, // 修改后的联系人对象
					changePhone(orderInfo.getContactId(), orderInfo.getPhones()), // 新增的电话
                    card, // 信用卡对象
                    null, // 身份证对象（订单中不关心身份证，暂时给null）
                    orderInfo.getRelateds(), // 关联单号
					orderInfo.getRemark());
		} catch (ErpException e) {
			logger.error(e.getMessage(), e);
			res.put("code", "-1");
			res.put("msg", e.getMessage());
			return res;
		}
		if(StringUtils.isNotBlank(orderInfo.getBatchId())) {
			//关闭流程
			changeRequestService.closeChangeRequestStatus(orderInfo.getBatchId());
			//同步批次
			changeRequestService.syncBatchStatus(orderInfo.getBatchId());
			//处理消息
			UserBpm ub = changeRequestService.queryApprovingTaskById(orderInfo.getBatchId());
			MessageType mType = MessageType.MODIFY_ORDER_REJECT;
			sysMessageService.handleMessage(mType, ub.getOrderID(), ub.getCreateDate());
		}
		
		try {
			sysMessageService.addMessage(createMessage(originalOrder.getCrusr(),originalOrder.getOrderid()));
		} catch (Exception e) {
		}
		
		res.put("code", "1");
		res.put("msg", "订单修改已提交审批");
		return res;
	}
	
	/**************************************************************************/
	/****						private method						   	   ****/
	/**
	 * @throws IOException 
	 * @throws ClientProtocolException ************************************************************************/
	
	/**
	 * 
	 * @param uri
	 * @param param
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String callOrderQuery(String uri, String param)
			throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(uri);
		StringEntity entity = new StringEntity(param);
		entity.setContentType("application/json;charset=utf-8");
		post.setEntity(entity);
		HttpResponse resp = new DefaultHttpClient().execute(post);
		if (resp.getStatusLine().getStatusCode() == 200) {
			return EntityUtils.toString(resp.getEntity(), "utf-8");
		}
		return null;
	}
	
	private SysMessage createMessage(String userid, String orderId) throws Exception {
		SysMessage msg = new SysMessage();
		msg.setSourceTypeId(String.valueOf(MessageType.MODIFY_ORDER.getIndex()));
		msg.setRecivierGroup(userService.getUserGroup(userid));
		msg.setDepartmentId(userService.getDepartment(userid));
        msg.setContent(orderId);
		msg.setCreateUser(SecurityHelper.getLoginUser().getUserId());
		msg.setCreateDate(new Date());
		return msg;
	}
	
	private List<OrderDto.OrderDetailDto> changeOrderDetailDtoList(Set<OrderDetail> orderdets) {
		List<OrderDto.OrderDetailDto> orderDetails = new ArrayList<OrderDto.OrderDetailDto>();
		for (OrderDetail orderdet : orderdets) {
			OrderDto.OrderDetailDto detDto = new OrderDto.OrderDetailDto(orderdet);
			ProductType pt = orderInfoService.getProductType(detDto.getProdid(), detDto.getProducttype());
			if (pt != null) detDto.setProductTypeName(pt.getDsc());
			orderDetails.add(detDto);
		}
		return orderDetails;
	}
	
	private void setViewModel(Order order, List<OrderDto.OrderDetailDto> orderDetails, ModelMap model) throws Exception {
		OrderDto orderDto = new OrderDto(order, orderDetails);
		if (StringUtils.isNotEmpty(orderDto.getStatus()))
			orderDto.setOrderStatusName(orderStatusMap.get(orderDto.getStatus()));
		if (StringUtils.isNotEmpty(orderDto.getCustomizestatus()))
			orderDto.setCustomizeStatusName(customStatusMap.get(orderDto.getCustomizestatus()));
		if (StringUtils.isNotEmpty(orderDto.getOrdertype()))
			orderDto.setOrderTypeName(orderTypeMap.get(orderDto.getOrdertype()));
		
		OrderDto.ContactDto contact = new OrderDto.ContactDto(contactService.get(order.getContactid()));
		String jifen = "0.0";
		try {
			jifen = contactService.findJiFenByContactId(order.getContactid()).toString();
		} catch (Exception e) {
		}
		contact.setJifen(jifen);
		contact.setLevelName(contactService.findLevelByContactId(order.getContactid()));
		AgentUser au = oldContactexService.queryBindAgentUser(order.getContactid());
		if (au != null) {
			contact.setBindingGroup(au.getUserId() + "-" + au.getName() + "-" + au.getDefGrp());
		}

		if (order.getAddress() != null) {
			AddressDto address = null;
			try {
				address = addressService.queryAddress(order.getAddress().getAddressId());
				address.setContactName(orderInfoService.getContactNameByContactId(address.getContactid()));
				CompanyDeliverySpan delivery = companyDeliverySpanService.getDeliverySpan(
						order.getEntityid(), 
						Long.valueOf(order.getOrdertype()), 
						Long.valueOf(order.getPaytype()), 
						Long.valueOf(order.getAddress().getProvince().getProvinceid()), 
						Long.valueOf(order.getAddress().getCity().getCityid()), 
						Long.valueOf(order.getAddress().getCounty().getCountyid()), 
						Long.valueOf(order.getAddress().getArea().getAreaid()));
				orderDto.setDelivery(delivery);
			} catch (Exception e) {
				throw new OrderDetailsAddressNotFoundException(e);
			}
			model.put("address", address);
		}
		
		List<Phone> phones = null;
		if (StringUtils.equals("11", order.getPaytype())) {
			phones = phoneService.getPhonesByContactId(order.getContactid());
		} else {
			phones = phoneService.getPhonesByContactId(order.getGetcontactid());
		}
				
		if (!CollectionUtils.isEmpty(phones)) {
			String[] phoneArray = new String[phones.size()];
			for (int idx = 0; idx < phones.size(); idx++) {
				Phone ph = phones.get(idx);
				phoneArray[idx] = mergePhone(ph.getPhonetypid(), ph.getPhn1(),
						ph.getPhn2(), ph.getPhn3(), false);
			}
			model.put("phoneArray", phoneArray);
		}
		model.put("order", orderDto);
		model.put("contact", contact);
        
        model.put("getContactName", contactService.get(order.getGetcontactid()).getName());
        model.put("payContactName", contactService.get(order.getPaycontactid()).getName());
        
        List<Map<String, Object>> cards = getCredit(order.getOrderid());
        if (!CollectionUtils.isEmpty(cards)) model.put("cardMap", cards.get(0));
	}
	
	private boolean isOrderdetChanged(Map<OrderDto.OrderDetailDto, String> map) {
		for (Entry<OrderDto.OrderDetailDto, String> entry : map.entrySet()) {
			if (!entry.getValue().equals(OrderInfoService.DET_NONE)) return true;
		}
		return false;
	}
	
	private void updateOrder(Order editOrder, OrderInfoDto orderInfo) {
		editOrder.setGetcontactid(orderInfo.getContactId());
		editOrder.setNote(orderInfo.getOrder().getNote());
		editOrder.setInvoicetitle(orderInfo.getOrder().getInvoiceTitle());
		editOrder.setMailprice(orderInfo.getOrder().getMailPrice());
		editOrder.setProdprice(orderInfo.getOrder().getProductTotalPrice());
		editOrder.setTotalprice(orderInfo.getOrder().getTotalPrice());
		editOrder.setReqEMS(orderInfo.getOrder().getEms());
		editOrder.setCardnumber(orderInfo.getOrder().getCardId());
		editOrder.setCardtype(orderInfo.getOrder().getCardType());
		if (orderInfo.getOrder().getAmortisation() != null ) 
			editOrder.setLaststatus(orderInfo.getOrder().getAmortisation().toString());
	}
	
	private void updateOrderDetails(Order editOrder, OrderInfoDto orderInfo, Order originalOrder) {
		if (orderInfo.getCart().getCartId() != null) {
			Order cartOrder = shoppingCartService.createOrderbyShoppingCart(
					orderInfo.getCart().getCartId());
			if (cartOrder != null) {
				Set<OrderDetail> cartOrderDetails = cartOrder.getOrderdets();
				Set<OrderDetail> editOrderDetails = orderInfoService.editOrderDetails(
						originalOrder.getOrderdets(), cartOrderDetails);
				editOrder.setOrderdets(editOrderDetails);
				shoppingCartService.deleteShoppingCartById(orderInfo.getCart().getCartId());
			} else { // 购物车订单对象不存在, 不走流程
				editOrder.setOrderdets(Collections.<OrderDetail> emptySet());
			}
		} else { // 没有返回购物车修改明细单, 不走流程
			editOrder.setOrderdets(Collections.<OrderDetail> emptySet());
		}
	}
	
	private Address updateAddress(Order editOrder, OrderInfoDto orderInfo, Order originalOrder) {
		AddressExt editAddressExt = new AddressExt();
		Address editAddress = new Address();
		
		Address address = addressService.get(orderInfo.getAddress().getAddressId());
		BeanUtils.copyProperties(address, editAddress);
		AddressExt addressExt = addressExtService.getAddressExt(orderInfo.getAddress().getAddressId());
		BeanUtils.copyProperties(addressExt, editAddressExt);
		editOrder.setAddress(editAddressExt);
		return editAddress;
	}
	
	private List<Phone> changePhone(String contactId, List<OrderInfoDto.Phone> phoneDtos) {
		List<Phone> phones = new ArrayList<Phone>(phoneDtos.size());
		for (OrderInfoDto.Phone phoneDto : phoneDtos) {
			Phone phone = new Phone();
			phone.setContactid(contactId);
			phone.setPhn1(phoneDto.getPhn1());
			phone.setPhn2(phoneDto.getPhn2());
			phone.setPhn3(phoneDto.getPhn3());
			phone.setPhonetypid(phoneDto.getPhoneType());
			phone.setPrmphn(phoneDto.getPrmphn());
			phone.setPhoneNum(
					mergePhone(phoneDto.getPhoneType(),
								phoneDto.getPhn1(), 
								phoneDto.getPhn2(), 
								phoneDto.getPhn3(), 
								false));
			phone.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_UNAUDITED);
			phones.add(phone);
		}
		return phones;
	}
	
	/*
	 * 设置地址
	 */
	private void setAddress(AddressExt editAddressExt, Address editAddress, OrderInfoDto orderInfo) throws Exception {
		// get province, city, county and area
		String provinceId = orderInfo.getAddress().getProvinceId();
		String cityId = orderInfo.getAddress().getCityId();
		String countyId = orderInfo.getAddress().getCountyId();
		String araeId = orderInfo.getAddress().getAreaId();
		
		AreaAll area = areaService.get(Integer.valueOf(araeId));
		if (area == null || !Integer.valueOf(countyId).equals(area.getCountyid())) {
			throw new Exception("Area null or cannot match county");
		}
		
		CountyAll county = countyAllService.queryByCountyId(Integer.valueOf(countyId));
		if (county == null || !Integer.valueOf(cityId).equals(county.getCityid())) {
			throw new Exception("County null or cannot match city");
		}
		
		CityAll city = cityService.get(Integer.valueOf(cityId));
		if (city == null || !provinceId.equals(city.getProvid())) {
			throw new Exception("City null or cannot match province");
		}
		
		Province province = provinceService.get(provinceId);
		if (province == null) {
			throw new Exception("Province null");
		}
		
		// set addressext
		editAddressExt.setContactId(orderInfo.getContactId());
		editAddressExt.setProvince(province);
		editAddressExt.setCity(city);
		editAddressExt.setCounty(county);
		editAddressExt.setArea(area);
		editAddressExt.setAreaName(orderInfo.getAddress().getAreaName());
		editAddressExt.setAddressDesc(orderInfo.getAddress().getAddressDesc());
		
		
		AddressDto ad = new AddressDto();
		ad.setState(orderInfo.getAddress().getProvinceId());
		ad.setCityId(Integer.valueOf(orderInfo.getAddress().getCityId()));
		ad.setCountyId(Integer.valueOf(orderInfo.getAddress().getCountyId()));
		ad.setAreaid(Integer.valueOf(orderInfo.getAddress().getAreaId()));
		ad.setAddress(orderInfo.getAddress().getAddressDesc());
		
		// set address
		editAddress.setContactid(orderInfo.getContactId());
		editAddress.setState(province.getProvinceid()); 
		editAddress.setCity(city.getCode());
		editAddress.setArea(area.getSpellid() != null ? area.getSpellid().toString() 
						: (county.getSpellid() != null ? county.getSpellid().toString() : ""));
		editAddress.setAreaid(Integer.valueOf(orderInfo.getAddress().getAreaId()));
		editAddress.setAddress(addressService.addressConcordancy(ad));
		editAddress.setZip(orderInfo.getAddress().getZipcode());
		editAddress.setAddrtypid("2");
	}

	private Card updateCard(OrderInfoDto.Card formCard) {
		Card card = new Card();
		if (formCard.getCardId() != null) {
			try {
				BeanUtils.copyProperties(
						cardService.getCard(formCard.getCardId()), card);
			} catch (Exception e) {
			}
		}
		
		BeanUtils.copyProperties(formCard, card);
		return card;
	}
	
	private String mergePhone(String phoneType, String phn1, String phn2,
			String phn3, boolean isEncrypt) {
		StringBuilder sb = new StringBuilder();
		if (isMobile(phoneType)) {
			if (isEncrypt) {
				sb.append(phn2.substring(0, 7));
				sb.append("****");
			} else {
				sb.append(phn2);
			}
		} else {
			if (StringUtils.isNotEmpty(phn1) && !"0".equals(phn1)) {
				sb.append(phn1);
				sb.append("-");
			}
			if (isEncrypt) {
				sb.append(phn2.substring(0, 4));
				sb.append("****");
                if (StringUtils.isNotEmpty(phn3)) {
                    sb.append("-");
                    sb.append(phn3);
                }
			} else {
				sb.append(phn2);
				if (StringUtils.isNotEmpty(phn3)) {
					sb.append("-");
					sb.append(phn3);
				}
			}
		}
		return sb.toString();
	}
	
	private boolean isMobile(String type) {
		return "4".equals(type);
	}
	
	private class AmortisationComparator implements Comparator<Amortisation> {
		@Override
		public int compare(Amortisation o1, Amortisation o2) {
			return o1.getAmortisation() - o2.getAmortisation();
		}
	}

}