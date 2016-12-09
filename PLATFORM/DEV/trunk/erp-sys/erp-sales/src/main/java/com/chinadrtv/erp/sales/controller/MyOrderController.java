package com.chinadrtv.erp.sales.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.AuditTaskType;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.Cardtype;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.NamesId;
import com.chinadrtv.erp.model.OrderdetChange;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.Card;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Grp;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.model.inventory.ProductType;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.pay.core.model.OnlinePayment;
import com.chinadrtv.erp.pay.core.model.PayResult;
import com.chinadrtv.erp.pay.core.service.OnlinePayService;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.constant.OrderRight;
import com.chinadrtv.erp.sales.core.model.CreditcardOnlineAuthorizationReturn;
import com.chinadrtv.erp.sales.core.model.OrderExtDto;
import com.chinadrtv.erp.sales.core.service.OrderUrgentApplicationService;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.OrderhistTrackTaskService;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.sales.core.util.ExceptionMsgUtil;
import com.chinadrtv.erp.sales.dto.ContactOrderDetailFrontDto;
import com.chinadrtv.erp.sales.dto.ContactOrderFrontDto;
import com.chinadrtv.erp.sales.dto.CreditReturnTestDto;
import com.chinadrtv.erp.sales.dto.CreditTestDto;
import com.chinadrtv.erp.sales.dto.MyOrderFrontAuditDto;
import com.chinadrtv.erp.sales.dto.MyOrderFrontDto;
import com.chinadrtv.erp.sales.dto.MyOrderQueryAuditDto;
import com.chinadrtv.erp.sales.dto.MyOrderQueryDto;
import com.chinadrtv.erp.sales.dto.OrderNoteDto;
import com.chinadrtv.erp.sales.dto.OrderdetDto;
import com.chinadrtv.erp.sales.dto.OrderhistExtDto;
import com.chinadrtv.erp.sales.service.OrderComparasionDetailService;
import com.chinadrtv.erp.sales.service.OrderInfoService;
import com.chinadrtv.erp.sales.service.SourceConfigure;
import com.chinadrtv.erp.sales.util.MyOrderAuditQueryDtoUtil;
import com.chinadrtv.erp.sales.util.MyOrderQueryDtoUtil;
import com.chinadrtv.erp.sales.util.ObjectNullCheckUtils;
import com.chinadrtv.erp.tc.core.constant.AccountStatusConstants;
import com.chinadrtv.erp.tc.core.dto.OrderAuditExtDto;
import com.chinadrtv.erp.tc.core.dto.OrderQueryDto;
import com.chinadrtv.erp.tc.core.utils.OrderException;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.CardService;
import com.chinadrtv.erp.uc.service.CardtypeService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.GrpService;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping("/myorder/myorder")
public class MyOrderController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MyOrderController.class);

    private static final String orderStatusObsolete="10";
    private static final String orderStatusObsoleteDsc="作废";

    @Autowired
    private OrderhistService orderhistService;
    
    @Autowired
    private CardService cardService;
    
    @Autowired
    private CardtypeService cardtypeService;

    private MyOrderQueryDtoUtil myOrderQueryDtoUtil;
    private MyOrderAuditQueryDtoUtil myOrderAuditQueryDtoUtil;

    @Autowired
    private ChangeRequestService changeRequestService;

    @Autowired
    private SourceConfigure sourceConfigure;

    @Autowired
    private OrderUrgentApplicationService orderUrgentApplicationService;
    
    @Autowired
    private OrderhistTrackTaskService orderhistTrackTaskService;
    
    private String NCODPaytypes;

    @PostConstruct
    private void initNCODPaytypelist()
    {
        NCODPaytypes=sourceConfigure.getNCODPaytypes();
        notCODPaytypeList=new ArrayList<String>();
        if(StringUtils.isNotEmpty(NCODPaytypes))
        {
            String[] strs=NCODPaytypes.split(",");
            for(String token:strs)
            {
               notCODPaytypeList.add(token);
            }
        }
    }

    private Date dtBegin;
    public MyOrderController()
    {
        myOrderQueryDtoUtil=new MyOrderQueryDtoUtil();
        myOrderQueryDtoUtil.init();

        myOrderAuditQueryDtoUtil=new MyOrderAuditQueryDtoUtil();
        myOrderAuditQueryDtoUtil.init();

        try{
            SimpleDateFormat simpleDateFormatTemp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dtBegin=simpleDateFormatTemp.parse("1990-01-01 00:00:01");
        }catch (Exception exp)
        {
            logger.error(exp.getMessage(),exp);//exp.printStackTrace();
        }
    }

    private String currentUsrId="27427";

    private String getCurrentUsrId()
    {
        String usrId=currentUsrId;
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser!=null)
        {
            usrId=agentUser.getUserId();
        }
        return usrId;
    }

    private List<String> notCODPaytypeList;

    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }

    public List<Map<String, String>> LookupNames(String tid)
             {
        List<Names> orderTypeList=null;
        if("AUDITSTATUS2".equals(tid))
        {
            orderTypeList=new ArrayList<Names>();
            List<String> list=AuditTaskStatus.toList();
            for(int index=0;index<list.size();index++)
            {
                Names names=new Names();
                NamesId namesId=new NamesId();
                namesId.setId(String.valueOf(index));
                namesId.setTid("");
                names.setId(namesId);
                names.setDsc(list.get(index));
                names.setTdsc(list.get(index));
                names.setValid("-1");

                orderTypeList.add(names);
            }
        }
        else
        {
            if(com.alibaba.druid.util.StringUtils.isEmpty(tid))
                return null;
            orderTypeList=namesService.getAllNames(tid);
        }

        if("ORDERSTATUS".equalsIgnoreCase(tid))
        {
            if(orderTypeList!=null)
            {
                Names orderStatusObsoleteItem=new Names();
                orderStatusObsoleteItem.setId(new NamesId(tid,orderStatusObsolete));
                orderStatusObsoleteItem.setDsc(orderStatusObsoleteDsc);
                orderTypeList.add(orderStatusObsoleteItem);
            }
        }
        List<Map<String, String>> mapList=new ArrayList<Map<String, String>>();
        for(Names orderType:orderTypeList)
        {
        	if("PAYTYPE".equals(tid)) {
                Set<String> allowedPayType = new HashSet<String>();
                allowedPayType.add("1");
                allowedPayType.add("2");
                allowedPayType.add("11");
                allowedPayType.add("12");
                allowedPayType.add("13");
        		if(allowedPayType.contains(orderType.getId().getId())) {
		            Map<String, String> map = new HashMap<String, String>();
		            map.put("id", orderType.getId().getId());
		            if("1".equals(orderType.getId().getId())) {
		            	map.put("dsc", "货到付款");
		            } else if("12".equals(orderType.getId().getId())){
		            	map.put("dsc", "邮局汇款");
		            } else {
		            	map.put("dsc", orderType.getDsc());
		            }
		            mapList.add(map);
        		}
        	} else {
                if("AUDITSTATUS".equals(tid))
                {
                    if(String.valueOf(CustomerConstant.CUSTOMER_AUDIT_STATUS_UNAUDITED).equals(orderType.getId().getId()))
                    {
                        continue;
                    }
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", orderType.getId().getId());
                map.put("dsc", orderType.getDsc());
                mapList.add(map);
        	}
        }

        return mapList;
    }

    @RequestMapping(value = "")
    public ModelAndView myorder()  {
        ModelAndView modelAndView = new ModelAndView("myorder/myorder");
        modelAndView.addObject("url", "/myorder/myorder/query");
        modelAndView.addObject("saveUrl", "#");
        modelAndView.addObject("updateUrl", "#");
        modelAndView.addObject("destroyUrl", "#");
        modelAndView.addObject("postUrl", "#");

        modelAndView.addObject("audit_statuses",JSONSerializer.toJSON(LookupNames("AUDITSTATUS2")));
        modelAndView.addObject("order_paytypes",JSONSerializer.toJSON(LookupNames("PAYTYPE")));
        modelAndView.addObject("order_statuses",JSONSerializer.toJSON(LookupNames("ORDERSTATUS")));
        modelAndView.addObject("problem_statuses",JSONSerializer.toJSON(LookupNames("PROBLEMSTATUS")));
        modelAndView.addObject("order_audit_statuses",JSONSerializer.toJSON(LookupNames("AUDITSTATUS")));
        modelAndView.addObject("order_pay_resultStatuses",JSONSerializer.toJSON(LookupNames("ORDERPAYRESULT")));
        modelAndView.addObject("NCODPaytypes",NCODPaytypes);
        modelAndView.addObject("startDate",this.getStartDate());
        modelAndView.addObject("endDate",this.getCurrentDate());
        modelAndView.addObject("currentUsrId",this.getCurrentUsrId());

        AgentUser agentUser = SecurityHelper.getLoginUser();
        boolean isGroupManager=false;
        boolean isDepartmentManager=false;
        boolean isQualityInspector=false;
        if(agentUser!=null)
        {
            isDepartmentManager = agentUser.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER) | agentUser.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
            isGroupManager = agentUser.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER) | agentUser.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);
            isQualityInspector=agentUser.hasRole(SecurityConstants.ROLE_QUALITY_INSPECTOR);
        }
        boolean isManager=false;
        if(isDepartmentManager==true||isGroupManager==true)
            isManager=true;
        modelAndView.addObject("isManager",isManager);

        //isQualityInspector=true;//
        modelAndView.addObject("isQualityInspector",isQualityInspector);
        
        //主管与经理角色
        boolean isSupervisor = false;
        if(agentUser==null)
            logger.error("login user is null");
        isSupervisor |= agentUser.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER);
		isSupervisor |= agentUser.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);
		isSupervisor |= agentUser.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER);
		isSupervisor |= agentUser.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
		modelAndView.addObject("isSupervisor", isSupervisor);

        return modelAndView;
    }

    @Autowired
    private ContactService contactService;
    @Autowired
    private AddressService addressService;
    @RequestMapping(value = "/get/{orderId}", method = RequestMethod.GET)
    public String viewOrder(@PathVariable String orderId, ModelMap model)
            throws Exception {
        Order order = orderhistService.getOrderHistByOrderid(orderId);
        OrderhistExtDto orderhistExtDto=new OrderhistExtDto();
        BeanUtils.copyProperties(order,orderhistExtDto);
        orderhistExtDto.setOrdertypeName(order.getOrdertype());
        List<Names> namesList=namesService.getAllNames("ORDERTYPE");
        if(namesList!=null)
        {
            for(Names names:namesList)
            {
                if(orderhistExtDto.getOrdertype().equals(names.getId()))
                {
                    orderhistExtDto.setOrdertypeName(names.getDsc());
                    break;
                }
            }
        }

        Contact contact = contactService.get(order.getContactid());
        AddressDto address = addressService.queryAddress(order.getAddress().getAddressId());
        Double jifen = contactService.findJiFenByContactId(order.getContactid());
        contact.setJifen(jifen.toString());
        model.put("order", orderhistExtDto);
        model.put("contact", contact);
        model.put("level", contactService.findLevelByContactId(order.getContactid()));
        model.put("address", address);
        model.put("jifen", jifen);

        return "myorder/vieworder";
    }

    private Date getFullDateTime(Date dt)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        if(dt!=null)
        {
            String str=simpleDateFormat.format(dt);
            try
            {
                Date dt1=simpleDateFormat.parse(str);
                if(dt.compareTo(dt1)==0)
                {
                    SimpleDateFormat simpleDateFormatFull=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return simpleDateFormatFull.parse(str+" 23:59:59");
                }
                else
                {
                    return dt;
                }
            }catch (Exception exp)
            {
                return dt;
            }
        }
        else
        {
            return null;
        }
    }

    private void formatQueryDate(OrderQueryDto orderQueryDto)
    {
        if(orderQueryDto==null)
            return;
        if(orderQueryDto.getEndCrdt()!=null)
        {
            orderQueryDto.setEndCrdt(this.getFullDateTime(orderQueryDto.getEndCrdt()));
        }

        if(orderQueryDto.getEndOutdt()!=null)
        {
            orderQueryDto.setEndOutdt(this.getFullDateTime(orderQueryDto.getEndOutdt()));
        }
    }

    private List<OrderExtDto> queryOrders(DataGridModel dataGridModel,MyOrderQueryDto myOrderQueryDto) throws Exception
    {
        this.formatQueryDate(myOrderQueryDto);
        MyOrderQueryDto myOrderQueryDto1=new MyOrderQueryDto();
        myOrderQueryDtoUtil.CopyNotNullValue(myOrderQueryDto, myOrderQueryDto1);

        BeanUtils.copyProperties(myOrderQueryDto1,myOrderQueryDto);
        //myOrderQueryDto=myOrderQueryDto1;
        Integer totalRows=myOrderQueryDto.getCountRows();
        myOrderQueryDto.setCountRows(null);
        List<String> ignoreFieldList=new ArrayList<String>();
        ignoreFieldList.add("STARTPOS");
        ignoreFieldList.add("PAGESIZE");
        ignoreFieldList.add("SORTCRDATE");
        if(ObjectNullCheckUtils.isFieldsNull(myOrderQueryDto,ignoreFieldList))
        {
            return null;
        }
        myOrderQueryDto.setCountRows(totalRows);

        Integer beginRow = dataGridModel.getStartRow();

        myOrderQueryDto.setStartPos(beginRow.intValue());
        myOrderQueryDto.setPageSize(dataGridModel.getRows());
        //形成地址
        if(StringUtils.isNotEmpty(myOrderQueryDto.getProvinceId()))
        {
            if(myOrderQueryDto.getAddressExt()==null)
            {
                myOrderQueryDto.setAddressExt(new AddressExt());
            }
            Province province=new Province();
            province.setProvinceid(myOrderQueryDto.getProvinceId());
            myOrderQueryDto.getAddressExt().setProvince(province);
        }
        if(StringUtils.isNotEmpty(myOrderQueryDto.getCityId()))
        {
            if(myOrderQueryDto.getAddressExt()==null)
            {
                myOrderQueryDto.setAddressExt(new AddressExt());
            }
            CityAll cityAll=new CityAll();
            cityAll.setCityid(Integer.parseInt(myOrderQueryDto.getCityId()));
            myOrderQueryDto.getAddressExt().setCity(cityAll);
        }
        if(StringUtils.isNotEmpty(myOrderQueryDto.getCountyId()))
        {
            if(myOrderQueryDto.getAddressExt()==null)
            {
                myOrderQueryDto.setAddressExt(new AddressExt());
            }
            CountyAll countyAll=new CountyAll();
            countyAll.setCountyid(Integer.parseInt(myOrderQueryDto.getCountyId()));
            myOrderQueryDto.getAddressExt().setCounty(countyAll);
        }
        if(StringUtils.isNotEmpty(myOrderQueryDto.getAreaId()))
        {
            if(myOrderQueryDto.getAddressExt()==null)
            {
                myOrderQueryDto.setAddressExt(new AddressExt());
            }
            AreaAll areaAll=new AreaAll();
            areaAll.setAreaid(Integer.parseInt(myOrderQueryDto.getAreaId()));
            myOrderQueryDto.getAddressExt().setArea(areaAll);
        }
        return orderhistService.queryOrder(myOrderQueryDto);
    }

    private List<MyOrderFrontDto> convertFromOrderExtDto(List<OrderExtDto> orderExtDtoList)
    {
        List<MyOrderFrontDto> myOrderFrontDtoList=new ArrayList<MyOrderFrontDto>();
        for(OrderExtDto orderExtDto:orderExtDtoList)
        {
            MyOrderFrontDto myOrderFrontDto=new MyOrderFrontDto();
            BeanUtils.copyProperties(orderExtDto.getOrder(),myOrderFrontDto);
            myOrderFrontDto.setOrderdets(null);
            myOrderFrontDto.setAddress(null);

            if(orderExtDto.getOrderhistTrackTask()!=null)
            {
                myOrderFrontDto.setAssignUser(orderExtDto.getOrderhistTrackTask().getAssignUser());
                myOrderFrontDto.setAssignTime(orderExtDto.getOrderhistTrackTask().getAssignTime());
                myOrderFrontDto.setTrackTime(orderExtDto.getOrderhistTrackTask().getTrackTime());
            }
            /*if (StringUtils.equals(myOrderFrontDto.getPaytype(), "2")
            		&& !StringUtils.isEmpty(myOrderFrontDto.getCardnumber())) {
            	// 使用信用卡支付, 查找付款银行
                Cardtype cardtype = cardtypeService.getCardType(cardService.getCard(
                        Long.valueOf(myOrderFrontDto.getCardnumber()))
                        .getType());
				myOrderFrontDto.setBankName(cardtype.getBankName());
            } */
            boolean couldReApply = orderUrgentApplicationService.couldReApply(myOrderFrontDto.getOrderid());
            myOrderFrontDto.setCouldReApply(couldReApply);
            myOrderFrontDtoList.add(myOrderFrontDto);
        }

        setCardTypes(myOrderFrontDtoList);
        setContactNames(myOrderFrontDtoList);

        return myOrderFrontDtoList;
    }

    private void setCardTypes(List<MyOrderFrontDto> myOrderFrontDtoList)
    {
        if(myOrderFrontDtoList==null||myOrderFrontDtoList.size()==0)
            return;
         List<Long> cardIdList=new ArrayList<Long>();
        for(MyOrderFrontDto myOrderFrontDto:myOrderFrontDtoList)
        {
            if("2".equals(myOrderFrontDto.getPaytype()))
            {
                if(StringUtils.isNotEmpty(myOrderFrontDto.getCardnumber()))
                {
                    Long cardId=Long.parseLong(myOrderFrontDto.getCardnumber());
                    if(!cardIdList.contains(cardId))
                    {
                        cardIdList.add(cardId);
                    }
                }
            }
        }

        Map<String,String> map=new HashMap<String, String>();
        if(cardIdList.size()>0)
        {
            List<Card> cardList=cardService.getCards(cardIdList);
            if(cardList!=null&&cardList.size()>0)
            {
                List<String> cardtypeIdList=new ArrayList<String>();
                for(Card card:cardList)
                {
                    if(StringUtils.isNotEmpty(card.getType()))
                    {
                        if(!cardtypeIdList.contains(card.getType()))
                        {
                            cardtypeIdList.add(card.getType());
                        }
                    }
                }
                if(cardtypeIdList.size()>0)
                {
                    List<Cardtype> cardtypeList=cardtypeService.getCardtypes(cardtypeIdList);
                    if(cardtypeList!=null)
                    {
                        for(Card card:cardList)
                        {
                           for(Cardtype cardtype:cardtypeList)
                           {
                               if(cardtype.getCardtypeid().equals(card.getType()))
                               {
                                   map.put(card.getCardId().toString(),cardtype.getBankName());
                                   break;
                               }
                           }
                        }
                    }
                }
            }
        }

        for(MyOrderFrontDto myOrderFrontDto:myOrderFrontDtoList)
        {
                if(StringUtils.isNotEmpty(myOrderFrontDto.getCardnumber()))
                {
                    if(map.containsKey(myOrderFrontDto.getCardnumber()))
                    {
                        myOrderFrontDto.setBankName(map.get(myOrderFrontDto.getCardnumber()));
                    }
                }
        }
    }

    @RequestMapping(value = "/checkGrpMember", method = RequestMethod.POST)
    @ResponseBody
    public boolean isGrpMember(String usrId)
    {
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser!=null)
        {
            if(agentUser.getUserId().equals(usrId))
                return true;
            try{
                String grpId=userService.getUserGroup(usrId);
                if(agentUser.getWorkGrp().equals(grpId))
                    return true;
                //检查部门信息
                boolean isDepartmentManager = agentUser.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER) | agentUser.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
                boolean isGroupManager = agentUser.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER) | agentUser.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);
                if(isDepartmentManager||isGroupManager)
                {
                    String departId=userService.getDepartment(usrId);
                    if(agentUser.getDepartment().equals(departId))
                        return true;
                }
            }catch (Exception exp){
               return false;
            }
        }
        return false;
    }

    @RequestMapping(value = "/isValidUser", method = RequestMethod.POST)
    @ResponseBody
    public boolean isValidUser(String usrId)
    {
        try
        {
            String strDn=userService.getUserDN(usrId);
            if(StringUtils.isBlank(strDn))
                return false;
        }catch (Exception exp)
        {
            logger.error("find user error:"+usrId, exp);
            return false;
        }

        return true;
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryMyOrders(DataGridModel dataGridModel,MyOrderQueryDto myOrderQueryDto)
    {
        try
        {
            Map<String, Object> pageMap = new HashMap<String, Object>();

            myOrderQueryDto.setSortCrDate(-1);
            //如果选择“作废”订单，那么设置为有效状态位标志
            if(orderStatusObsolete.equals(myOrderQueryDto.getStatus()))
            {
                myOrderQueryDto.setStatus(null);
                myOrderQueryDto.setCheckResult(CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
            }
            else if(AccountStatusConstants.ACCOUNT_CANCEL.equals(myOrderQueryDto.getStatus()))
            {
                //过滤掉作废订单
                myOrderQueryDto.setCheckResult(null);
                List<Integer> resultList=new ArrayList<Integer>();
                resultList.add(null);
                resultList.add(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
                resultList.add(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                myOrderQueryDto.setCheckResultList(resultList);
            }
            myOrderQueryDto.setTrackUsr(myOrderQueryDto.getCrUsr());
            List<OrderExtDto> orderExtDtoList=queryOrders(dataGridModel,myOrderQueryDto);
            if(orderExtDtoList==null)
            {
                pageMap.put("total",0);
                pageMap.put("rows",new ArrayList<MyOrderFrontDto>());
                return pageMap;
            }

            List<MyOrderFrontDto> myOrderFrontDtoList=this.convertFromOrderExtDto(orderExtDtoList);

            checkOrderRight(myOrderFrontDtoList,orderExtDtoList,this.getCurrentUsrId());
            //System.out.println("my order total:"+myOrderQueryDto.getCountRows());
            if(myOrderQueryDto.getCountRows()!=null&&myOrderQueryDto.getCountRows().intValue()>=0)
            {
                pageMap.put("total",myOrderQueryDto.getCountRows().intValue());
            }
            else
            {
                pageMap.put("total",orderhistService.queryOrderTotalCount(myOrderQueryDto));
            }
            filterObsoleteOrders(myOrderFrontDtoList);
            pageMap.put("rows", myOrderFrontDtoList);
            return pageMap;
        }catch (Exception exp)
        {
           //获取统一处理
            logger.error("queryMyOrders error:", exp);

            Map<String, Object> pageMap = new HashMap<String, Object>();
            pageMap.put("total", 0);
            pageMap.put("rows", new ArrayList<MyOrderFrontDto>());
            pageMap.put("err", ExceptionMsgUtil.getMessage(exp));
            return pageMap;
        }
    }

    private void filterObsoleteOrders(List<MyOrderFrontDto> myOrderFrontDtoList)
    {
        if(myOrderFrontDtoList!=null)
        {
            for(MyOrderFrontDto myOrderFrontDto:myOrderFrontDtoList)
            {
                if(myOrderFrontDto.getCheckResult()!=null&&myOrderFrontDto.getCheckResult().intValue()==CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED)
                {
                    myOrderFrontDto.setStatus(orderStatusObsolete);
                }
            }
        }
    }


    private void setContactNames(List myOrderFrontDtoList)
    {
        if(myOrderFrontDtoList==null||myOrderFrontDtoList.size()==0)
            return;
        List<String> contactIdList=new ArrayList<String>();
        for(Object obj:myOrderFrontDtoList)
        {
            MyOrderFrontDto myOrderFrontDto=(MyOrderFrontDto)obj;
            if(!contactIdList.contains(myOrderFrontDto.getContactid()))
            {
                contactIdList.add(myOrderFrontDto.getContactid());
            }
        }
        if(contactIdList.size()>0)
        {
            List<Contact> contactList= contactService.getContactFromIds(contactIdList);
            for(Object obj:myOrderFrontDtoList)
            {
                MyOrderFrontDto myOrderFrontDto=(MyOrderFrontDto)obj;
                for(Contact contact:contactList)
                {
                    if(contact.getContactid().equals(myOrderFrontDto.getContactid()))
                    {
                        myOrderFrontDto.setContactname(contact.getName());
                        break;
                    }
                }
            }
        }
    }

    private void setAuditContactNames(List<MyOrderFrontAuditDto> myOrderFrontDtoList)
    {
        if(myOrderFrontDtoList==null||myOrderFrontDtoList.size()==0)
            return;
        List<String> contactIdList=new ArrayList<String>();
        for(MyOrderFrontDto myOrderFrontDto:myOrderFrontDtoList)
        {
            if(!contactIdList.contains(myOrderFrontDto.getContactid()))
            {
                contactIdList.add(myOrderFrontDto.getContactid());
            }
        }
        if(contactIdList.size()>0)
        {
            List<Contact> contactList= contactService.getContactFromIds(contactIdList);
            for(MyOrderFrontDto myOrderFrontDto:myOrderFrontDtoList)
            {
                for(Contact contact:contactList)
                {
                    if(contact.getContactid().equals(myOrderFrontDto.getContactid()))
                    {
                        myOrderFrontDto.setContactname(contact.getName());
                        break;
                    }
                }
            }
        }
    }

    private void checkOrderRight(List myOrderFrontDtoList,List<OrderExtDto> orderExtDtoList,String usrId)
    {
        if(myOrderFrontDtoList==null||myOrderFrontDtoList.size()==0)
            return;

        List<Order> orderList =new ArrayList<Order>();
        for(OrderExtDto orderExtDto:orderExtDtoList)
        {
            orderList.add(orderExtDto.getOrder());
        }

        Map<Order,List<String>> mapRight= orderhistService.checkOrderRights(orderList, usrId);

        for(Object obj:myOrderFrontDtoList)
        {
            if(obj instanceof MyOrderFrontDto )
            {
                MyOrderFrontDto myOrderFrontDto =(MyOrderFrontDto)obj;
                boolean bFind=false;
                for(Map.Entry<Order,List<String>> entry : mapRight.entrySet())
                {
                    if(myOrderFrontDto.getOrderid().equals(entry.getKey().getOrderid()))
                    {
                        myOrderFrontDto.setRight(getRightTokens(entry.getValue()));
                        bFind=true;
                        break;
                    }
                }

                if(bFind==false)
                {
                    myOrderFrontDto.setRight("|VIEW");
                }
            }
        }
    }

    private String getRightTokens(List<String> list)
    {
        StringBuilder stringBuilder=new StringBuilder();
        for(String str:list)
        {
            if(OrderRight.ALL.equals(str))
            {
                stringBuilder.append("|ALL");
            }
            else if(OrderRight.Delete.equals(str))
            {
                stringBuilder.append("|DELETE");
            }
            else if(OrderRight.Modify.equals(str))
            {
                stringBuilder.append("|MODIFY");
            }
            else if(OrderRight.View.equals(str))
            {
                stringBuilder.append("|VIEW");
            }
            else if(OrderRight.Consult.equals(str))
            {
                stringBuilder.append("|CONSULT");
            }
        }
        return stringBuilder.toString();
    }


    @RequestMapping(value = "/query_audit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryMyOrdersAudit(DataGridModel dataGridModel,MyOrderQueryAuditDto myOrderQueryAuditDto)
    {
        try
        {
            this.formatQueryDate(myOrderQueryAuditDto);
            Map<String, Object> pageMap = new HashMap<String, Object>();
            MyOrderQueryAuditDto orderAuditQueryDto1=new MyOrderQueryAuditDto();
            myOrderAuditQueryDtoUtil.CopyNotNullValue(myOrderQueryAuditDto,orderAuditQueryDto1);
            myOrderQueryAuditDto=orderAuditQueryDto1;
            Integer totalRows=myOrderQueryAuditDto.getCountRows();

            List<String> ignoreFieldList=new ArrayList<String>();
            ignoreFieldList.add("STARTPOS");
            ignoreFieldList.add("PAGESIZE");
            ignoreFieldList.add("SORTCRDATE");
            if(ObjectNullCheckUtils.isFieldsNull(myOrderQueryAuditDto,ignoreFieldList))
            {
                pageMap.put("total",0);
                pageMap.put("rows",new ArrayList<MyOrderFrontAuditDto>());
                return pageMap;
            }
            myOrderQueryAuditDto.setSortCrDate(-1);
            myOrderQueryAuditDto.setCountRows(totalRows);

            Integer beginRow = dataGridModel.getStartRow();

            myOrderQueryAuditDto.setStartPos(beginRow.intValue());
            myOrderQueryAuditDto.setPageSize(dataGridModel.getRows());

            List<MyOrderFrontAuditDto> myOrderFrontAuditDtoList=new ArrayList<MyOrderFrontAuditDto>();
            //查询审核的订单
            List<OrderAuditExtDto> orderAuditExtDtoList=orderhistService.queryAuditOrder(myOrderQueryAuditDto);
            if(orderAuditExtDtoList!=null)
            {
                for(OrderAuditExtDto orderAuditExtDto:orderAuditExtDtoList)
                {
                    MyOrderFrontAuditDto myOrderFrontAuditDto=new MyOrderFrontAuditDto();
                    myOrderFrontAuditDto.setAuditstatus(orderAuditExtDto.getUserBpm().getStatus());
                    myOrderFrontAuditDto.setBatchid(orderAuditExtDto.getUserBpm().getId().toString());
                    myOrderFrontAuditDto.setTotalprice(orderAuditExtDto.getOrder().getTotalprice());
                    myOrderFrontAuditDto.setCrusr(orderAuditExtDto.getOrder().getCrusr());
                    myOrderFrontAuditDto.setCrdt(orderAuditExtDto.getOrder().getCrdt());
                    //审核生成时间
                    myOrderFrontAuditDto.setAuditdt(orderAuditExtDto.getUserBpm().getCreateDate());
                    myOrderFrontAuditDto.setContactid(orderAuditExtDto.getOrder().getContactid());
                    myOrderFrontAuditDto.setOrderid(orderAuditExtDto.getOrder().getOrderid());
                    myOrderFrontAuditDto.setStatus(orderAuditExtDto.getOrder().getStatus());
                    myOrderFrontAuditDto.setPaytype(orderAuditExtDto.getOrder().getPaytype());
                    myOrderFrontAuditDto.setRight("");


                    if(StringUtils.isNotEmpty(orderAuditExtDto.getUserBpm().getBusiType()))
                    {
                        int num = Integer.parseInt(orderAuditExtDto.getUserBpm().getBusiType());
                        myOrderFrontAuditDto.setAudittype(AuditTaskType.fromOrdinal(num) );
                    }
                    myOrderFrontAuditDtoList.add(myOrderFrontAuditDto);
                }
            }
            logger.debug("my audit total:"+myOrderQueryAuditDto.getCountRows());
            if(myOrderQueryAuditDto.getCountRows()!=null&&myOrderQueryAuditDto.getCountRows().intValue()>=0)
            {
                pageMap.put("total",myOrderQueryAuditDto.getCountRows());
            }
            else
            {
                pageMap.put("total",orderhistService.queryOrderAuditTotalCount(myOrderQueryAuditDto));
            }
            this.setAuditContactNames(myOrderFrontAuditDtoList);
            this.setAuditRight(myOrderFrontAuditDtoList);
            pageMap.put("rows",myOrderFrontAuditDtoList);
            return pageMap;
        }
        catch (Exception exp)
        {
            logger.error("queryMyOrdersAudit error:",exp);
            Map<String, Object> pageMap = new HashMap<String, Object>();
            pageMap.put("total", 0);
            pageMap.put("rows", new ArrayList<MyOrderFrontAuditDto>());
            pageMap.put("err", ExceptionMsgUtil.getMessage(exp));
            return pageMap;
        }
    }

    private void setAuditRight(List<MyOrderFrontAuditDto> myOrderFrontAuditDtoList)
    {
        List<String> batchIdList=new ArrayList<String>();
        for(MyOrderFrontAuditDto myOrderFrontAuditDto:myOrderFrontAuditDtoList)
        {
            int status=-1;
            if(StringUtils.isNotEmpty(myOrderFrontAuditDto.getAuditstatus()))
            {
                status=Integer.parseInt(myOrderFrontAuditDto.getAuditstatus());
            }
            if(AuditTaskStatus.UNASSIGNED.getIndex()==status)
            {
                if(batchIdList.add(myOrderFrontAuditDto.getBatchid()));
            }
        }
        Map<String, Boolean> map=null;
        if(batchIdList.size()>0)
        {
            map=changeRequestService.isRequestProcessed(batchIdList);
        }
        for(MyOrderFrontAuditDto myOrderFrontAuditDto:myOrderFrontAuditDtoList)
        {
            int status=-1;
            if(StringUtils.isNotEmpty(myOrderFrontAuditDto.getAuditstatus()))
            {
                status=Integer.parseInt(myOrderFrontAuditDto.getAuditstatus());
            }
            if(AuditTaskStatus.UNASSIGNED.getIndex()==status)
            {
                boolean bCanDelete=true;
                if(map!=null)
                {
                    if(map.containsKey(myOrderFrontAuditDto.getBatchid()))
                    {
                        Boolean b=map.get(myOrderFrontAuditDto.getBatchid());
                        if(b!=null && b.booleanValue()==true)
                            bCanDelete=false;
                    }
                }
                if(bCanDelete)
                    myOrderFrontAuditDto.setRight("DELETE");
            }
            else if(AuditTaskStatus.REJECTED.getIndex()==status)
            {
                myOrderFrontAuditDto.setRight("CONFIRM");
            }
            else
            {
                myOrderFrontAuditDto.setRight("");
            }
        }
    }


    @ExceptionHandler
    @ResponseBody
    public Object handleException(Exception ex, HttpServletRequest request) {
        logger.error("system error:", ex);
        if(ex!=null)
        {
            //System.out.println(ex.getMessage());
        }
        return null;
    }

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    private SysMessageService sysMessageService;

    @RequestMapping(value = "/cancel")
    @ResponseBody
    public Map<String, Object> cancelOrder(HttpServletResponse response, String orderId,String remark)
    {
        Map<String, Object> map=new Hashtable<String,Object>();
        boolean bSucc=false;
        try
        {
            response.reset();
            remark=URLDecoder.decode(remark,"UTF-8");
            if(orderhistService.saveOrderCancelRequest(orderId, remark, this.getCurrentUsrId()))
            {
                map.put("succ","1");
                map.put("audit","1");
                map.put("msg","订单取消已申请成功");
                //HttpUtil.ajaxReturn(response, "11订单取消已申请成功", "text/plain;charset=UTF-8");
            }
            else
            {
                map.put("succ","1");
                map.put("audit","0");
                map.put("msg","订单取消成功");
                //HttpUtil.ajaxReturn(response, "10订单取消成功", "text/plain;charset=UTF-8");
            }
            bSucc=true;
        }catch (Exception exp)
        {
            //获取统一处理
            //HttpUtil.ajaxReturn(response, "0"+ExceptionMsgUtil.getMessage(exp), "text/plain;charset=UTF-8");
            //return "0"+ExceptionMsgUtil.getMessage(exp);
            map.put("succ","0");
            map.put("audit","0");
            map.put("msg",ExceptionMsgUtil.getMessage(exp));
        }

       try
       {
           if(bSucc==true)
           {
                SysMessage sysMessage=new SysMessage();
                sysMessage.setContent(orderId);
                sysMessage.setCreateDate(new Date());
                sysMessage.setCreateUser(this.getCurrentUsrId());

                Order order=orderhistService.getOrderHistByOrderid(orderId);
                sysMessage.setRecivierGroup(userService.getUserGroup(order.getCrusr()));
                sysMessage.setDepartmentId(userService.getDepartment(order.getCrusr()));
                sysMessage.setSourceTypeId(String.valueOf(MessageType.CANCEL_ORDER.getIndex()));
                sysMessageService.addMessage(sysMessage);
           }
       }catch (Exception exp)
       {
           logger.error("create message from cancel order error:", exp);
       }

        return map;
    }

    @RequestMapping(value = "/consult")
    @ResponseBody
    public Map<String, Object> consultOrder(HttpServletResponse response, String orderId)
    {
        Map<String, Object> map=new Hashtable<String,Object>();
        boolean bSucc=false;
        boolean bMessage=false;
        try
        {
            response.reset();
            bMessage = orderhistService.consultOrder(orderId);
            map.put("succ","1");
            map.put("msg","订单转咨询成功");
            bSucc=true;
        }catch (Exception exp)
        {
            map.put("succ","0");
            map.put("msg",ExceptionMsgUtil.getMessage(exp));
        }

        if(bSucc==true && bMessage==true)
        {
            try{
               sysMessageService.cancelOrderMessage(orderId);
            }catch (Exception exp)
            {
                logger.error("cancel message on consult error:",exp);
                logger.error("orderid:"+orderId);
            }
        }
        return map;
    }

    @RequestMapping(value = "/note")
    @ResponseBody
    public Map<String, Object> noteOrder(HttpServletResponse response, OrderNoteDto orderNoteDto)
    {
        Map<String, Object> map=new Hashtable<String,Object>();
        AgentUser agentUser=SecurityHelper.getLoginUser();
        if(agentUser==null)
        {
            map.put("succ","0");
            map.put("msg","未登录，不允许操作");
            return map;
        }

        if(StringUtils.isBlank(orderNoteDto.getNewNote()))
        {
            map.put("succ","0");
            map.put("msg","备注内容不能为空！");
            return map;
        }
        try
        {
            response.reset();
            orderhistService.addOrderNote(orderNoteDto.getOrderId(),orderNoteDto.getNewNote(),new Date(),agentUser.getUserId(),
                    StringUtils.isBlank(agentUser.getDisplayName())?agentUser.getUsername():agentUser.getDisplayName());
            map.put("succ","1");
            map.put("msg","");
        }
        catch (OrderException orderExp)
        {
            map.put("succ","0");
            map.put("msg","当前订单不能执行跟单备注操作");
        }
        catch (Exception exp)
        {
            map.put("succ","0");
            map.put("msg",ExceptionMsgUtil.getMessage(exp));
        }

        return map;
    }

    @RequestMapping(value = "/cancel_audit")
    @ResponseBody
    public void cancelOrderAudit(HttpServletResponse response, String batchId, String orderId, String remark)
    {
        try
        {
            //检查是否可以取消
            if(changeRequestService.isRequestProcessed(batchId)==true)
            {
                HttpUtil.ajaxReturn(response, "0当前审核不可取消", "text/plain;charset=UTF-8");
                return;
            }
            remark=URLDecoder.decode(remark,"UTF-8");
            orderhistService.cancelOrderAudit(batchId, orderId, remark, SecurityHelper.getLoginUser().getUserId());
            //changeRequestService.cancelChangeRequestByBatchID(batchId,remark);
            HttpUtil.ajaxReturn(response, "1取消审核成功", "text/plain;charset=UTF-8");

            try{
                UserBpm ub = changeRequestService.queryApprovingTaskById(batchId);
                MessageType messageType=MessageType.MODIFY_ORDER;
                if(String.valueOf(AuditTaskType.ORDERADD.getIndex()).equals(ub.getBusiType()))
                {
                    messageType=MessageType.ADD_ORDER;
                }
                sysMessageService.handleMessageByUsr(messageType,ub.getOrderID(),ub.getCreateDate());
            }catch (Exception innerExp)
            {
                logger.error("cancel order audit error:",innerExp);
            }
        }catch (Exception exp)
        {
            //获取统一处理
            HttpUtil.ajaxReturn(response, "0"+ExceptionMsgUtil.getMessage(exp), "text/plain;charset=UTF-8");
            //return ExceptionMsgUtil.getMessage(exp);
        }
    }


    @RequestMapping(value = "/query_pay", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryMyOrdersPay(DataGridModel dataGridModel, MyOrderQueryDto myOrderQueryDto)
    {
        List<String> ignoreFieldList=new ArrayList<String>();
        ignoreFieldList.add("STARTPOS");
        ignoreFieldList.add("PAGESIZE");
        ignoreFieldList.add("SORTCRDATE");
        if(!ObjectNullCheckUtils.isFieldsNull(myOrderQueryDto,ignoreFieldList))
        {
            if(StringUtils.isEmpty(myOrderQueryDto.getPayType()))
            {
                myOrderQueryDto.setPaytypeList(notCODPaytypeList);
            }
            //只获取订购状态的订单
            if(StringUtils.isEmpty(myOrderQueryDto.getStatus()))
            {
                myOrderQueryDto.setStatus("1");
            }

            if(StringUtils.isNotEmpty(myOrderQueryDto.getConfirm()))
            {
                if("2".equals(myOrderQueryDto.getConfirm()))
                {
                    myOrderQueryDto.setConfirm("-1");
                }
                else if("1".equals(myOrderQueryDto.getConfirm()))
                {
                    myOrderQueryDto.setConfirm("1");
                }
                else
                {
                    myOrderQueryDto.setConfirm(null);
                }
            }
        }

        return this.queryMyOrders(dataGridModel,myOrderQueryDto);
    }



    @Autowired
    private NamesService namesService;
    
    @Autowired
    private OrderComparasionDetailService orderComparasionDetailService;

    @RequestMapping(value = "/audit_view/{batchId}")
    public ModelAndView auditView(@PathVariable("batchId") String batchId)
            throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("myorder/viewaudit");
    	orderComparasionDetailService.instancializeOrderChange(mav, batchId, false);
        return mav;
    }

    private void setDetail2Dto(OrderdetChange change, OrderdetDto dto) {
        dto.setId(change.getId());
        dto.setProductId(change.getProdid());
        dto.setProductType(change.getProducttype());
        dto.setPrice(change.getSprice() != null ? change.getSprice() : change.getUprice());
        dto.setNum(change.getSpnum() != null ? change.getSpnum() : change.getUpnum());
        dto.setSoldwith(change.getSoldwith());
//		dto.setIsSuite();
    }

    private void setDetail2Dto(OrderDetail detail, OrderdetDto dto) {
        dto.setId(detail.getId());
        dto.setProductId(detail.getProdid());
        dto.setProductType(detail.getProducttype());
        dto.setPrice(detail.getSprice() != null ? detail.getSprice() : detail.getUprice());
        dto.setNum(detail.getSpnum() != null ? detail.getSpnum() : detail.getUpnum());
        dto.setSoldwith(detail.getSoldwith());
//		dto.setIsSuite();
    }


    public String getStartDate()
    {
        Date dt=new Date();
        Date dt1= new Date(dt.getTime()-this.sourceConfigure.getSearchDayDiff()*86400000L);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(dt1);
    }

    public String getCurrentDate()
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }




    @Autowired
    private OrderInfoService orderInfoService;

    private List<ProductType> getProductTypes(List<OrderExtDto> orderExtDtoList)
    {
        List<OrderDetail> orderDetailList=new ArrayList<OrderDetail>();
        for(OrderExtDto orderExtDto:orderExtDtoList)
        {
            for(OrderDetail orderDetail:orderExtDto.getOrder().getOrderdets())
            {
                boolean bFind=false;
                for(OrderDetail orderDetail1:orderDetailList)
                {
                    if(orderDetail.getProdid().equals(orderDetail1.getProdid())
                            &&orderDetail.getProducttype().equals(orderDetail1.getProducttype()))
                    {
                        bFind=true;
                        break;
                    }
                }
                if(bFind==false)
                {
                    orderDetailList.add(orderDetail);
                }
            }
        }
        return orderInfoService.getProductTypes(orderDetailList);
    }

    @Autowired
    private GrpService grpService;

    private List<ContactOrderFrontDto> convertContactFrontFromOrderExtDto(List<OrderExtDto> orderExtDtoList)
    {
        List<ProductType> productTypeList=this.getProductTypes(orderExtDtoList);

        List<ContactOrderFrontDto> contactOrderFrontDtoList=new ArrayList<ContactOrderFrontDto>();
        for(OrderExtDto orderExtDto:orderExtDtoList)
        {
            ContactOrderFrontDto contactOrderFrontDto=new ContactOrderFrontDto();
            BeanUtils.copyProperties(orderExtDto.getOrder(),contactOrderFrontDto);
            for(OrderDetail orderDetail:orderExtDto.getOrder().getOrderdets())
            {
                ContactOrderDetailFrontDto contactOrderDetailFrontDto=new ContactOrderDetailFrontDto();
                //contactOrderDetailFrontDto.setPrice();
                //检查那个数据有效
                if(orderDetail.getSpnum()!=null&&orderDetail.getSpnum().intValue()>0)
                {
                    contactOrderDetailFrontDto.setQuantity(orderDetail.getSpnum());
                    contactOrderDetailFrontDto.setPrice(orderDetail.getSprice());
                }
                else
                {
                    contactOrderDetailFrontDto.setQuantity(orderDetail.getUpnum());
                    contactOrderDetailFrontDto.setPrice(orderDetail.getUprice());
                }
                contactOrderDetailFrontDto.setProdId(orderDetail.getProdid());
                contactOrderDetailFrontDto.setProdName(orderDetail.getProdname());
                contactOrderDetailFrontDto.setSoldwith(orderDetail.getSoldwith());
                //规格名称
                for(ProductType productType:productTypeList)
                {
                    if(orderDetail.getProdid().equals(productType.getProdrecid())&&orderDetail.getProducttype().equals(productType.getRecid()))
                    {
                        contactOrderDetailFrontDto.setTypeName(productType.getDsc());
                        break;
                    }
                }
                if("2".equals(orderDetail.getSoldwith()))
                {
                    contactOrderDetailFrontDto.setSoldwith("搭销");
                }
                else if("3".equals(orderDetail.getSoldwith()))
                {
                    contactOrderDetailFrontDto.setSoldwith("赠品");
                }
                else
                {
                    contactOrderDetailFrontDto.setSoldwith("直接销售");
                }

                //contactOrderDetailFrontDto.setTypeName(orderDetail.getProducttype());
                if(orderExtDto.getShipmentHeader()!=null)
                {
                    contactOrderFrontDto.setShipmentId(orderExtDto.getShipmentHeader().getShipmentId());
                    contactOrderFrontDto.setLogisticsStatusId(orderExtDto.getShipmentHeader().getLogisticsStatusId());
                }
                contactOrderFrontDto.getContactOrderDetailFrontDtos().add(contactOrderDetailFrontDto);
            }
            contactOrderFrontDto.setAddress(null);
            contactOrderFrontDto.setOrderdets(null);
            contactOrderFrontDtoList.add(contactOrderFrontDto);
        }
        setContactNames(contactOrderFrontDtoList);

        getGrpFromOrder(contactOrderFrontDtoList);

        checkOrderRight(contactOrderFrontDtoList,orderExtDtoList,this.getCurrentUsrId());
        return contactOrderFrontDtoList;
    }

    private void getGrpFromOrder(List<ContactOrderFrontDto> contactOrderFrontDtoList)
    {
        List<String> grpIdList=new ArrayList<String>();
        for(ContactOrderFrontDto contactOrderFrontDto:contactOrderFrontDtoList)
        {
            if(!grpIdList.contains(contactOrderFrontDto.getGrpid()))
            {
                grpIdList.add(contactOrderFrontDto.getGrpid());
            }
        }
        List<Grp> grpList=grpService.getGrpList(grpIdList);
        if(grpList!=null)
        {
            for(ContactOrderFrontDto contactOrderFrontDto:contactOrderFrontDtoList)
            {
                for(Grp grp:grpList)
                {
                    if(grp.getGrpid().equals(contactOrderFrontDto.getGrpid()))
                    {
                        contactOrderFrontDto.setGrpName(grp.getGrpname());
                        break;
                    }
                }
            }
        }
    }


    @RequestMapping(value = "/query_contact", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryMyOrdersByContact(DataGridModel dataGridModel, MyOrderQueryDto myOrderQueryDto)
    {
        try
        {
            Map<String, Object> pageMap = new HashMap<String, Object>();
            List<String> ignoreFieldList=new ArrayList<String>();
            ignoreFieldList.add("STARTPOS");
            ignoreFieldList.add("PAGESIZE");
            ignoreFieldList.add("SORTCRDATE");
            myOrderQueryDto.setCheckCrDate(false);

            if(StringUtils.isEmpty(myOrderQueryDto.getContactId()))
            {
                pageMap.put("total",0);
                pageMap.put("rows",new ArrayList<MyOrderFrontDto>());
                return pageMap;
            }

            if(!ObjectNullCheckUtils.isFieldsNull(myOrderQueryDto,ignoreFieldList))
            {
                myOrderQueryDto.setSortCrDate(-1);
            }
            if(myOrderQueryDto.getBeginCrdt()==null)
            {
                if(dtBegin!=null)
                    myOrderQueryDto.setBeginCrdt(dtBegin);
               else
                    myOrderQueryDto.setBeginCrdt(DateUtils.addDays(new Date(),-300));
            }

            List<OrderExtDto> orderExtDtoList=queryOrders(dataGridModel,myOrderQueryDto);
            if(orderExtDtoList==null)
            {
                pageMap.put("total",0);
                pageMap.put("rows",new ArrayList<MyOrderFrontDto>());
                return pageMap;
            }

            List<ContactOrderFrontDto> contactOrderFrontDtoList =this.convertContactFrontFromOrderExtDto(orderExtDtoList);

            //System.out.println("my contact order total:"+myOrderQueryDto.getCountRows());
            if(myOrderQueryDto.getCountRows()!=null&&myOrderQueryDto.getCountRows().intValue()>=0)
            {
                pageMap.put("total",myOrderQueryDto.getCountRows().intValue());
            }
            else
            {
                pageMap.put("total",orderhistService.queryOrderTotalCount(myOrderQueryDto));
            }
            filterContactObsoleteOrder(contactOrderFrontDtoList);
            pageMap.put("rows", contactOrderFrontDtoList);

            return pageMap;
        }catch (Exception exp)
        {
            logger.error("queryMyOrdersContact error:",exp);
            Map<String, Object> pageMap = new HashMap<String, Object>();
            pageMap.put("total", 0);
            pageMap.put("rows", new ArrayList<MyOrderFrontAuditDto>());
            pageMap.put("err", ExceptionMsgUtil.getMessage(exp));
            return pageMap;
        }
    }

    private void filterContactObsoleteOrder(List<ContactOrderFrontDto> contactOrderFrontDtoList)
    {
        if(contactOrderFrontDtoList!=null)
        {
            for(ContactOrderFrontDto contactOrderFrontDto:contactOrderFrontDtoList)
            {
                if(contactOrderFrontDto.getCheckResult()!=null&&contactOrderFrontDto.getCheckResult().intValue()==CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED)
                {
                    contactOrderFrontDto.setStatus(orderStatusObsolete);
                }
            }
        }
    }













    //测试招行索权接口
    @Autowired
    private OnlinePayService onlinePayService;

    @RequestMapping(value = "/test_test",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public String testCreditAdd(@RequestBody String str)
    {
        String str1="";
        if(onlinePayService==null)
        {
            str1="-空的";
        }
        else
        {
            str1="-有值";
            logger.error("creditcardOnlineAuthorizationService is not null");
        }
         return "test:"+str+str1;
    }

    @RequestMapping(value = "/credit_test_add",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public String testCreditAdd(@RequestBody CreditTestDto creditTestDto)
    {
        try
        {
        logger.error("begin credit test-1");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date dt;
        try{
            dt=simpleDateFormat.parse(creditTestDto.getValidateDate());
        }catch (Exception exp)
        {
            return "解析日期错误";
        }
        logger.error("begin credit test");

        OnlinePayment onlinePayment=new OnlinePayment();
        onlinePayment.setAmount(creditTestDto.getAmount());
        onlinePayment.setCardNo(creditTestDto.getCardNo());
        onlinePayment.setExpiryDate(dt);
        onlinePayment.setStageNum(creditTestDto.getStage());
        onlinePayment.setPaytype(sourceConfigure.getTestPayType());

        logger.error("credit test 1");
        //CreditcardOnlineAuthorizationResponse creditcardOnlineAuthorizationResponse= creditcardOnlineAuthorizationService.hirePurchase(creditcardOnlineAuthorization);
        PayResult payResult = onlinePayService.pay(onlinePayment);
        logger.error("credit test 2");
        if(payResult.isSucc())
        {
            logger.error("credit test succ");
            StringBuilder strBld=new StringBuilder();
            strBld.append("授权码："+payResult.getAuthID());
            strBld.append("-订单号："+payResult.getOrderNum());
            strBld.append("-流水号"+payResult.getRefNum());
            SimpleDateFormat sp=new SimpleDateFormat("yyyyMMddhhmmss");
            String str=sp.format(payResult.getTransTime());
            strBld.append("-交易时间"+str);
            return strBld.toString();

        }
        else
        {
            logger.error("credit test error code:"+payResult.getErrorCode());
            logger.error("credit test error:"+payResult.getErrorMsg());
            return payResult.getErrorCode()+"-"+payResult.getErrorMsg();
        }
        }catch (Exception exp)
        {
            logger.error("test unknown error:",exp);
            return "调用错误："+exp.getMessage();
        }
    }

    @RequestMapping(value = "/credit_test_sub",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public String testCreditReturn(@RequestBody CreditReturnTestDto creditTestDto)
    {
        try
        {
            logger.error("begin credit return test-1");
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat tranDateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
            Date dt;
            Date transTime;
            try{
                dt=simpleDateFormat.parse(creditTestDto.getValidateDate());
                transTime=tranDateFormat.parse(creditTestDto.getPreTransTime());
            }catch (Exception exp)
            {
                return "解析日期错误";
            }
            logger.error("begin return credit test");

            /*if(StringUtils.isEmpty(creditTestDto.getOrderNum())||StringUtils.isEmpty(creditTestDto.getPreAuthID())||StringUtils.isEmpty(creditTestDto.getPreBatchNum())
                    ||StringUtils.isEmpty(creditTestDto.getPreRefNum())||StringUtils.isEmpty(creditTestDto.getPreTransTime())||creditTestDto.getPreTraceNum()==null)
            {
                return "输入信息不完整";
            } */
            CreditcardOnlineAuthorizationReturn creditcardOnlineAuthorizationReturn =new CreditcardOnlineAuthorizationReturn();
            creditcardOnlineAuthorizationReturn.setAmount(creditTestDto.getAmount());//
            //creditcardOnlineAuthorizationReturn.setBatchNum();
            creditcardOnlineAuthorizationReturn.setCardNo(creditTestDto.getCardNo());//
            creditcardOnlineAuthorizationReturn.setExpiryDate(dt);//
            creditcardOnlineAuthorizationReturn.setOrderNum(creditTestDto.getOrderNum());//
            creditcardOnlineAuthorizationReturn.setPreAuthID(creditTestDto.getPreAuthID());//
            creditcardOnlineAuthorizationReturn.setPreBatchNum(creditTestDto.getPreBatchNum());//
            creditcardOnlineAuthorizationReturn.setPreRefNum(creditTestDto.getPreRefNum());//
            creditcardOnlineAuthorizationReturn.setPreTransTime(transTime);//
            creditcardOnlineAuthorizationReturn.setPreTraceNum(creditTestDto.getPreTraceNum());//
            creditcardOnlineAuthorizationReturn.setStageNum(creditTestDto.getStage());

            PayResult prePayResult=new PayResult();
            prePayResult.setBatchNum(creditTestDto.getPreBatchNum());
            prePayResult.setOrderNum(creditTestDto.getOrderNum());
            prePayResult.setAuthID(creditTestDto.getPreAuthID());
            prePayResult.setRefNum(creditTestDto.getPreRefNum());
            prePayResult.setTransTime(transTime);
            prePayResult.setTraceNum(creditTestDto.getPreTraceNum());

            OnlinePayment onlinePayment=new OnlinePayment();
            onlinePayment.setPaytype(sourceConfigure.getTestPayType());
            onlinePayment.setAmount(creditTestDto.getAmount());
            onlinePayment.setCardNo(creditTestDto.getCardNo());
            onlinePayment.setExpiryDate(dt);
            onlinePayment.setStageNum(creditTestDto.getStage());


            logger.error("credit return test 1");

            PayResult payResult = onlinePayService.cancelPay(onlinePayment,prePayResult);
            //CreditcardOnlineAuthorizationReturnResponse creditcardOnlineAuthorizationResponse= creditcardOnlineAuthorizationService.hirePurchaseReturn(creditcardOnlineAuthorizationReturn);
            logger.error("credit return test 2");
            if(payResult.isSucc())
            {
                logger.error("credit return test succ");
                StringBuilder strBld=new StringBuilder();
                strBld.append("-流水号"+payResult.getRefNum());
                SimpleDateFormat sp=new SimpleDateFormat("yyyyMMddhhmmss");
                String str=sp.format(payResult.getTransTime());
                strBld.append("-交易时间"+str);
                return strBld.toString();

            }
            else
            {
                logger.error("credit return test error code:"+payResult.getErrorCode());
                logger.error("credit return test error:"+payResult.getErrorMsg());
                return payResult.getErrorCode()+"-"+payResult.getErrorMsg();
            }
        }catch (Exception exp)
        {
            logger.error("test unknown error:",exp);
            return "调用错误："+exp.getMessage();
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/grant/{orderId}")
    public Map<String, String> grant(@PathVariable("orderId") String orderId) {
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
    	if(cardtype != null && StringUtils.equals(cardtype.getBankName(), "招行")){
    		try {
				OnlinePayment request = new OnlinePayment();
				request.setAmount(order.getTotalprice());
				request.setCardNo(card.getCardNumber());
				request.setExpiryDate(DateUtil.string2Date(card.getValidDate(), "yyyy-MM"));
				request.setStageNum(StringUtils.isEmpty(order.getLaststatus()) ? 1
						: Integer.parseInt(order.getLaststatus()));
                request.setPaytype(sourceConfigure.getTestPayType());
                request.setOrderId(orderId);
				
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
		map.put("msg", "非招行卡不能在线索权");
		return map; // error
    }
    
    
    
    /**
     * 
     * <p>分配跟单任务</p>
     * @param orderIds
     * @param trackUser
     * @param ownerUser
     * @return Map<String, Object>
     */
	@RequestMapping(value = "/assignToAgent")
	@ResponseBody
	public Map<String, Object> assignToAgent(String orderIds, String trackUser, String ownerUser) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		Integer totalCount = 0;
		String message = "";

        try {
        	String[] orderIdArr = orderIds.split(",");
			
			totalCount = orderhistTrackTaskService.assignToAgent(orderIdArr, trackUser, ownerUser);
			
			success = true;
		} catch (Exception e) {
			//e.printStackTrace();
			message = e.getMessage();
			logger.error("分配跟单任务失败", e);
		}
		
		rsMap.put("success", success);
		rsMap.put("totalCount", totalCount);
		rsMap.put("message", message);
		
		return rsMap;
	}

    @Autowired
    private UserBpmTaskService userBpmTaskService;

    @ResponseBody
    @RequestMapping(value = "/grantCheck/{orderId}")
    public Map<String,String> grantCheck(@PathVariable("orderId") String orderId)
    {
        Map<String, String> map = new HashMap<String, String>();
        List<UserBpmTask> userBpmTaskList = userBpmTaskService.queryUnterminatedOrderChangeTask(orderId);
        if(userBpmTaskList!=null&&userBpmTaskList.size()>0)
        {
            map.put("result","1");
        }
        else
        {
            map.put("result","0");
        }
        return map;
    }
}
