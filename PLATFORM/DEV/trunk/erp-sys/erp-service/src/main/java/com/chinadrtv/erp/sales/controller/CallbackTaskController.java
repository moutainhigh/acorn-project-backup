/**
 * Copyright (c) 2014, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.model.agent.CallbackTask;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.sales.core.util.DateUtil;
import com.chinadrtv.erp.sales.dto.CallbackTaskHandleDto;
import com.chinadrtv.erp.service.core.dto.CallbackTaskVO;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.ContactService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.service.core.dto.CallbackTaskDistDto;
import com.chinadrtv.erp.service.core.dto.CallbackTaskDto;
import com.chinadrtv.erp.service.core.service.CallbackTaskService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.springframework.web.servlet.ModelAndView;

/**
 * 2014-4-28 上午10:54:38
 * @version 1.0.0
 * @author yangfei
 *
 */
@Controller
@RequestMapping(value = "callbackTask")
public class CallbackTaskController {
	private static final Logger logger = LoggerFactory.getLogger(CallbackTaskController.class);
	
	@Autowired
	private CallbackTaskService callbackTaskService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userSerice;
	
	@RequestMapping(value = "/query")
	@ResponseBody
    public Map<String, Object> query(DataGridModel dataGrid, CallbackTaskDto callbackDto) {
		Map<String, Object> pageMap = null;
		try {
			pageMap = callbackTaskService.query(callbackDto, dataGrid);
		} catch (Exception e) {
			logger.error("error occurs!",e);
		}
		return pageMap;
    }
	
	@RequestMapping(value = "/queryAgentName")
	@ResponseBody
	public AgentUser queryAgentName(@RequestParam(required = true, defaultValue = "") String agentId) {
		AgentUser agentUser = new AgentUser();
		List<AgentUser> users = null;
			try {
				users = userSerice.findUserByUid(agentId);
			} catch (Exception e) {
				logger.error("查询用户出错:"+agentId,e);
			}
			if(users != null && users.size() > 0) {
				for(AgentUser au : users) {
					if(StringUtils.isNotBlank(au.getUsername()) || StringUtils.isNotBlank(au.getDisplayName())) {
						String grpDisplayName = null;
						try {
							grpDisplayName = userSerice.getGroupDisplayName(au.getDefGrp());
						} catch (Exception e) {
							logger.error("获取组显示名失败",e);
						}
						agentUser = au;
						agentUser.setWorkGrp(grpDisplayName);
						break;
					}
				}
			}
		return agentUser;
	}
	
	@RequestMapping(value = "/distCallbackTask", method = RequestMethod.POST)
	@ResponseBody
	public String distCallbackTask(HttpServletResponse response,
			@RequestBody CallbackTaskDistDto callbackTaskDistDto) {
		String message = "分配成功";
		AgentUser user = SecurityHelper.getLoginUser();
		callbackTaskService.distCallbackTask(callbackTaskDistDto.getAllTaskIds(), 
				callbackTaskDistDto.getDbUserIds(), user.getUserId());
		return message;
	}


    @RequestMapping(value = "/queryServiceTasks", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryServiceTasks(DataGridModel dataGridModel, CallbackTaskDto callbackTaskDto)
    {
        Map<String, Object> pageMap = new HashMap<String, Object>();

        if(StringUtils.isBlank(callbackTaskDto.getCrStartDate()))
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            callbackTaskDto.setCrStartDate(simpleDateFormat.format(DateUtil.addDay(new Date(), -14)));
        }
        if(StringUtils.isBlank(callbackTaskDto.getRdbusrid()))
        {
            callbackTaskDto.setRdbusrid(SecurityHelper.getLoginUser().getUserId());
        }
        List<String> nullTypeList=new ArrayList<String>();
        nullTypeList.add("5");
        callbackTaskDto.setNullUsrTypes(nullTypeList);

        List<String> flagList=new ArrayList<String>();
        flagList.add("1");
        flagList.add("2");
        callbackTaskDto.setProcessStatuses(flagList);

        try
        {
            pageMap.put("total", callbackTaskService.queryCount(callbackTaskDto));
            pageMap.put("rows", callbackTaskService.queryServiceTasks(callbackTaskDto, dataGridModel));
        }catch (Exception exp)
        {
            logger.error("query service tasks error:", exp);
            pageMap.put("total", 0);
            pageMap.put("rows", new ArrayList<CallbackTaskVO>());
            pageMap.put("err", exp.getMessage());
        }

        return pageMap;
    }

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderhistService orderhistService;

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/addressConfirm/open",method = RequestMethod.GET)
    public ModelAndView openWinCouponView (String sourceType,String taskId) {
        ModelAndView modelAndView = new ModelAndView("/task/addressConfirmWindow");
        CallbackTask callbackTask=null;
        if(StringUtils.isNotBlank(taskId))
        {
            callbackTask=callbackTaskService.getCallbackTask(taskId);
        }

        String contactId=null;
        String contactName=null;
        AddressDto address=null;
        if(callbackTask==null)
        {
            callbackTask=new CallbackTask();
        }
        else
        {
            if(StringUtils.isNotBlank(callbackTask.getOrderId()))
            {
                Order order = orderhistService.getOrderHistByOrderid(callbackTask.getOrderId());
                if(order!=null)
                {
                    address = addressService.queryAddress(order.getAddress().getAddressId());
                    contactId=order.getContactid();
                    if(StringUtils.isNotBlank(order.getContactid()))
                    {
                        Contact contact=contactService.findById(contactId);
                        if(contact!=null)
                        {
                            contactName=contact.getName();
                        }
                    }
                }
            }

        }

        if(address==null)
            address=new AddressDto();


        modelAndView.addObject("callbackTask",callbackTask);
        modelAndView.addObject("address",address);
        modelAndView.addObject("contactId",contactId);
        modelAndView.addObject("contactName",contactName);
        modelAndView.addObject("sourceType",sourceType);
        modelAndView.addObject("userId",SecurityHelper.getLoginUser().getUserId());

        return  modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/changeFlag",method = RequestMethod.POST)
    public Map<String,Object> changeFlag (CallbackTask callbackTask) {
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            callbackTaskService.handleCallbackTask(callbackTask.getTaskId(),callbackTask.getNote(),callbackTask.getFlag(),callbackTask.getRdbusrid());
            map.put("succ","succ");
        }catch (Exception exp)
        {
            logger.error("change callback task flag error:",exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/handleTask",method = RequestMethod.POST)
    public Map<String,Object> handleTask (CallbackTaskHandleDto callbackTaskDto) {
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            callbackTaskService.handleOrderAddressTask(callbackTaskDto, callbackTaskDto.getMainAddress(), callbackTaskDto.getAddress());
            map.put("succ", "succ");
        }catch (Exception exp)
        {
            logger.error("handle callback task error:",exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }
        return map;
    }


    @Autowired
    protected SysMessageService sysMessageService;

    @Autowired
    protected UserService userService;

    @ResponseBody
    @RequestMapping(value = "/cancelTaskOrder",method = RequestMethod.POST)
    public Map<String,Object> cancelTaskOrder (CallbackTask callbackTask) {
        Map<String,Object> map=new HashMap<String, Object>();

        boolean bSucc=false;
        try{
            callbackTaskService.cancelOrderAddressByTask(callbackTask);

            bSucc=true;
            map.put("succ", "succ");
        }catch (Exception exp)
        {
            logger.error("handle callback task error:",exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }

        if(bSucc==true)
        {
            try
            {
                //处理消息
                SysMessage sysMessage=new SysMessage();
                sysMessage.setContent(callbackTask.getOrderId());
                sysMessage.setCreateDate(new Date());
                sysMessage.setCreateUser(callbackTask.getRdbusrid());

                sysMessage.setRecivierGroup(userService.getUserGroup(callbackTask.getRdbusrid()));
                sysMessage.setDepartmentId(userService.getDepartment(callbackTask.getRdbusrid()));
                sysMessage.setSourceTypeId(String.valueOf(MessageType.CANCEL_ORDER.getIndex()));
                sysMessageService.addMessage(sysMessage);
            }catch (Exception exp)
            {
                logger.error("create message from cancel task order error:", exp);
            }
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/checkTaskOrder",method = RequestMethod.POST)
    public Map<String,Object> checkTaskOrder (String taskId) {
        Map<String,Object> map=new HashMap<String, Object>();

        try{
            String strMsg=callbackTaskService.canHandleOrderAddressTask(taskId,SecurityHelper.getLoginUser().getUserId());

            if(StringUtils.isNotBlank(strMsg))
            {
                map.put("succ","error");
                map.put("msg",strMsg);
            }
            else
            {
                map.put("succ", "succ");
            }
        }catch (Exception exp)
        {
            logger.error("check callback task error:",exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }

        return map;
    }
}
