package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.constant.MonthType;
import com.chinadrtv.erp.sales.core.model.MessageCheckStatus;
import com.chinadrtv.erp.sales.core.model.MessageQueryDto;
import com.chinadrtv.erp.sales.core.model.UsrMessage;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.sales.core.util.ExceptionMsgUtil;
import com.chinadrtv.erp.sales.dto.SysMessageDto;
import com.chinadrtv.erp.sales.util.MessageGroupKey;
import com.chinadrtv.erp.sales.util.MessageTypeEnumUtils;
import com.chinadrtv.erp.sales.util.MonthTypeEnumUtils;
import com.chinadrtv.erp.service.core.util.AgentUserHelpler;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import net.sf.json.JSONSerializer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping("/message")
public class MessageController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MessageController.class);

    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(MonthType.class,new MonthTypeEnumUtils());
        binder.registerCustomEditor(MessageType.class,new MessageTypeEnumUtils());
    }

    @Autowired
    private SysMessageService sysMessageService;

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

    private List<Map<String, String>> getMessageStatusList()
    {
        List<Map<String, String>> mapList=new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "0");
        map.put("dsc", "未读");
        mapList.add(map);

        map = new HashMap<String, String>();
        map.put("id", "1");
        map.put("dsc", "已读");
        mapList.add(map);
        return mapList;
    }

    private List<Map<String, String>> getMessageTypeList()
    {
        List<Map<String, String>> mapList=new ArrayList<Map<String, String>>();
        for(MessageType messageType:MessageType.getShowList())
        {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", String.valueOf(messageType.getIndex()));
            map.put("dsc", messageType.getName());
            mapList.add(map);
        }

        return mapList;
    }

    private List<Map<String, String>> getMessageMonthList()
    {
        List<Map<String, String>> mapList=new ArrayList<Map<String, String>>();
        for(MonthType monthType:MonthType.values())
        {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id",String.valueOf(monthType.getIndex()));
            map.put("dsc",monthType.getName());
            mapList.add(map);
        }

        return mapList;
    }

    @RequestMapping(value = "/index")
    public ModelAndView myMessage()  {
        ModelAndView modelAndView = new ModelAndView("message/message");

        modelAndView.addObject("message_statuses", JSONSerializer.toJSON(getMessageStatusList()));
        modelAndView.addObject("message_monthes",JSONSerializer.toJSON(getMessageMonthList()));
        modelAndView.addObject("message_types",JSONSerializer.toJSON(getMessageTypeList()));
        modelAndView.addObject("currentUsrId",this.getCurrentUsrId());
        AgentUser agentUser = SecurityHelper.getLoginUser();
        boolean isGroupManager=false;
        if(agentUser!=null)
        {
            isGroupManager= AgentUserHelpler.isManager(agentUser);
            /*isGroupManager = agentUser.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER);
            if(isGroupManager==false)
            {
                isGroupManager=agentUser.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);
            }
            if(isGroupManager==false)
            {
                isGroupManager=agentUser.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
            }
            if(isGroupManager==false)
            {
                isGroupManager=agentUser.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER);
            }*/
        }
        modelAndView.addObject("isManager",isGroupManager);
        return modelAndView;
    }

    @RequestMapping(value = "/sleep", method = RequestMethod.POST)
    @ResponseBody
    public  String sleepClient(Long millisecond)
    {
        try{
            Thread.sleep(millisecond);}catch (Exception e)
        {}
        return "succ";
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public  Map<String, Object>  queryMessage(DataGridModel dataGridModel, MessageQueryDto messageQueryDto)
    {
        try
        {
            Map<String, Object> pageMap = new HashMap<String, Object>();

            //messageQueryDto.setPageSize(dataGridModel.getPage());
            //messageQueryDto.setStartPos(dataGridModel.getStartRow());


            //读取数据
            /*SysMessageDto sysMessageDto=new SysMessageDto();
            sysMessageDto.setChecked(true);
            sysMessageDto.setContent("只是测试");
            sysMessageDto.setReceiverId(messageQueryDto.getReceiverId());
            sysMessageDto.setSourceTypeId("0");
            sysMessageDto.setCreateDate(new Date());
            List<SysMessageDto> sysMessageDtoList=new ArrayList<SysMessageDto>();
            sysMessageDtoList.add(sysMessageDto);

            sysMessageDto=new SysMessageDto();
            sysMessageDto.setChecked(false);
            sysMessageDto.setContent("测试2");
            sysMessageDto.setReceiverId(messageQueryDto.getReceiverId());
            sysMessageDto.setSourceTypeId("1");
            sysMessageDto.setCreateDate(new Date());
            sysMessageDtoList.add(sysMessageDto);


            pageMap.put("total", sysMessageDtoList.size());
            pageMap.put("rows", sysMessageDtoList); */



            AgentUser user = SecurityHelper.getLoginUser();
            if(user==null)
                logger.error("login user is null");
            String userId = user.getUserId();
            /*boolean isDepartmentManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER);
            if(isDepartmentManager==false)
            {
                isDepartmentManager=user.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
            }
            boolean isGroupManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER);
            if(isGroupManager==false)
            {
                isGroupManager=user.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);
            }*/
            boolean isDepartmentManager=AgentUserHelpler.isManager(user);
            boolean isGroupManager=isDepartmentManager;
            /*if(isDepartmentManager) {
                messageQueryDto.setDepartment(user.getDepartment());
            } else
            if(isGroupManager) {
                //messageQueryDto.setDepartment(user.getDepartment());
                if(userId.equals(messageQueryDto.getReceiverId()))
                {
                    messageQueryDto.setRecivierGroup(user.getWorkGrp());
                    messageQueryDto.setReceiverId(null);
                }
            }*/

            List<SysMessage> sysMessageList=queryMessageFromParm(messageQueryDto,isGroupManager,isDepartmentManager,user);

            List<SysMessageDto> sysMessageDtoList=summaryMessages(sysMessageList);

            pageMap.put("total", sysMessageDtoList.size());
            pageMap.put("rows", sysMessageDtoList);
            return pageMap;
        }catch (Exception exp)
        {
            //获取统一处理
            logger.error("queryMessage error:", exp);

            Map<String, Object> pageMap = new HashMap<String, Object>();
            pageMap.put("total", 0);
            pageMap.put("rows", new ArrayList<SysMessageDto>());
            pageMap.put("err", ExceptionMsgUtil.getMessage(exp));
            return pageMap;
        }
    }

    private List<SysMessageDto> summaryMessages(List<SysMessage> sysMessageList)
    {
        List<SysMessageDto> sysMessageDtoList=new ArrayList<SysMessageDto>();
        if(sysMessageList==null)
           return sysMessageDtoList;

        //按用户、消息类型分组、消息状态分组
        Map<MessageGroupKey,List<SysMessage>> map=new HashMap<MessageGroupKey, List<SysMessage>>();
        for(SysMessage sysMessage:sysMessageList)
        {
            boolean bFind=false;
            for(Map.Entry<MessageGroupKey,List<SysMessage>> entry:map.entrySet())
            {
                if(entry.getKey().isSameMessageGroup(sysMessage))
                {
                    entry.getValue().add(sysMessage);
                    bFind=true;
                    break;
                }
            }
            if(bFind==false)
            {
                MessageGroupKey messageGroupKey=MessageGroupKey.createMessageGroupKeyFromMessage(sysMessage);
                List<SysMessage> list=new ArrayList<SysMessage>();
                list.add(sysMessage);
                map.put(messageGroupKey,list);
            }
        }

        for(Map.Entry<MessageGroupKey,List<SysMessage>> entry:map.entrySet())
        {
            //
            sysMessageDtoList.add(createSummaryMessage(entry.getValue(),this.getCurrentUsrId()));
        }

        Collections.sort(sysMessageDtoList);
        return sysMessageDtoList;
    }

    private SysMessageDto createSummaryMessage(List<SysMessage> sysMessageList,String currentUsrId)
    {
        if(sysMessageList==null||sysMessageList.size()==0)
            return null;

        SysMessage sysMessage=sysMessageList.get(0);
        SysMessageDto sysMessageDto=new SysMessageDto();
        Integer type=null;
        if(StringUtils.isNotEmpty(sysMessage.getSourceTypeId()))
            type=Integer.parseInt(sysMessage.getSourceTypeId());
        type=getMessageTypeForShow(type);
        if(type!=null)
            sysMessageDto.setSourceTypeId(type.toString());

        sysMessageDto.setOrgSourceType(sysMessage.getSourceTypeId());

        sysMessageDto.setContent(String.valueOf(sysMessageList.size()));
        sysMessageDto.setChecked(sysMessage.getChecked());
        sysMessageDto.setCreateDate(getEarlyDateFromMessageList(sysMessageList));

        if(StringUtils.isNotEmpty(sysMessage.getReceiverId()))
        {
            sysMessageDto.setReceiverId(sysMessage.getReceiverId());
        }
        else
        {
            sysMessageDto.setReceiverId(currentUsrId);
        }

        sysMessageDto.setRecivierGroup(sysMessage.getRecivierGroup());
        sysMessageDto.setDepartmentId(sysMessage.getDepartmentId());

        return sysMessageDto;
    }
    private Date getEarlyDateFromMessageList(List<SysMessage> sysMessageList)
    {
        if(sysMessageList==null||sysMessageList.size()==0)
            return null;
        Date dt=sysMessageList.get(0).getCreateDate();
        for(int i=1;i<sysMessageList.size();i++)
        {
            if(dt.compareTo(sysMessageList.get(i).getCreateDate())>0)
            {
                dt=sysMessageList.get(i).getCreateDate();
            }
        }
        return dt;
    }

    @Autowired
    private UserService userService;

    private List<SysMessage> queryMessageFromParm(MessageQueryDto messageQueryDto, boolean isManager,boolean isDepartManager, AgentUser currentUser)
    {
        if(StringUtils.isEmpty(messageQueryDto.getReceiverId()))
        {
            messageQueryDto.setReceiverId(currentUser.getUserId());
        }
        boolean isQueryUserManager=false;
        if(currentUser.getUserId().equals(messageQueryDto.getReceiverId()))
        {
            if(isDepartManager==true||isManager==true)
                isQueryUserManager=true;
        }
        else
        {
            //TODO:检查是否下属是组主管
            //userService.
        }
        parseQueryParmByUser(messageQueryDto,isQueryUserManager);
       if(isManager|| isDepartManager)
       {
           if(StringUtils.isEmpty(messageQueryDto.getReceiverId()))
           {
               //查询主管下面的所有座席消息
               if(isDepartManager==true)
               {
                   //获取所有的组列表
                   //List<String> grpIdList=new ArrayList<String>();
                   //messageQueryDto.setGrpIdList(grpIdList);
                   messageQueryDto.setDepartmentId(currentUser.getDepartment());
               }
               else
               {
                   messageQueryDto.setRecivierGroup(currentUser.getWorkGrp());
               }
               //messageQueryDto.setRecivierGroup(currentUser.getWorkGrp());
               messageQueryDto.setHaveReceiver(true);
               return sysMessageService.queryMessages(messageQueryDto);
           }
           else
           {
               if(currentUser.getUserId().equals(messageQueryDto.getReceiverId()))
               {
                   //主管自身的消息
                   messageQueryDto.setReceiverId(null);
                   if(isDepartManager==true)
                   {
                      //获取所有的组列表
                       //List<String> grpIdList=new ArrayList<String>();
                       //messageQueryDto.setGrpIdList(grpIdList);
                       messageQueryDto.setDepartmentId(currentUser.getDepartment());
                   }
                   else
                   {
                       messageQueryDto.setRecivierGroup(currentUser.getWorkGrp());
                   }
                   return sysMessageService.queryMessages(messageQueryDto);
               }
               else
               {
                   //主管下面某个座席的消息，同座席查看
                   return sysMessageService.queryMessages(messageQueryDto);
               }
           }
       }
       else
       {
           //座席查看
           return sysMessageService.queryMessages(messageQueryDto);
       }
    }

    private void  parseQueryParmByUser(MessageQueryDto messageQueryDto, boolean isManager)
    {
        if(isManager)
        {
            messageQueryDto.setMessageType(messageQueryDto.getMessageType());
        }
        else
        {
            messageQueryDto.setMessageType(getMessageTypeFromShow(messageQueryDto.getMessageType()));
        }
    }

    private Integer getMessageTypeForShow(Integer messageType)
    {
        if(messageType==null)
            return null;
        if(messageType.intValue()==MessageType.CANCEL_ORDER_REJECT.getIndex())
        {
            return MessageType.CANCEL_ORDER.getIndex();
        }
        else if(messageType.intValue()==MessageType.MODIFY_ORDER_REJECT.getIndex())
        {
            return MessageType.MODIFY_ORDER.getIndex();
        }
        else if(messageType.intValue()==MessageType.MODIFY_CONTACT_REJECT.getIndex())
        {
            return MessageType.MODIFY_CONTACT.getIndex();
        }
        else if(messageType.intValue()==MessageType.ADD_CONTACT_REJECT.getIndex())
        {
            return MessageType.ADD_CONTACT.getIndex();
        }
        else if(messageType.intValue()==MessageType.ADD_ORDER_REJECT.getIndex())
        {
            return MessageType.ADD_ORDER.getIndex();
        }
        else
        {
            return messageType;
        }
    }
    private MessageType getMessageTypeFromShow(MessageType messageType)
    {
        if(messageType==null)
            return null;
        /*if(messageType.getIndex()==2)
        {
            return MessageType.CANCEL_ORDER_REJECT;
        }
        else */if(messageType.getIndex()==MessageType.MODIFY_ORDER.getIndex())
        {
            return MessageType.MODIFY_ORDER_REJECT;
        }
        else if(messageType.getIndex()==MessageType.MODIFY_CONTACT.getIndex())
        {
            return MessageType.MODIFY_CONTACT_REJECT;
        }
        else if(messageType.getIndex()==MessageType.ADD_CONTACT.getIndex())
        {
            return MessageType.ADD_CONTACT_REJECT;
        }
        else if(messageType.getIndex()==MessageType.ADD_ORDER.getIndex())
        {
            return MessageType.ADD_ORDER_REJECT;
        }
        else if(messageType.getIndex()==MessageType.CANCEL_ORDER.getIndex())
        {
            return MessageType.CANCEL_ORDER_REJECT;
        }
        else
        {
            return messageType;
        }
    }


    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public  String updateMessageCheck(SysMessageDto sysMessageDto)
    {
        //sysMessageDto.setCreateDate(null);
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser==null)
            return "";
        if(AgentUserHelpler.isManager(agentUser))
        {
            sysMessageDto.setReceiverId(null);
            sysMessageDto.setRecivierGroup(null);
        }
        else
        {
            sysMessageDto.setDepartmentId(null);
        }
        if(StringUtils.isNotEmpty(sysMessageDto.getOrgSourceType()))
            sysMessageDto.setSourceTypeId(sysMessageDto.getOrgSourceType());
        sysMessageDto.setChecked(MessageCheckStatus.READED.getIndex());
        sysMessageService.checkMessageByExample(sysMessageDto);
        return null;
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

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public  List<String> getSysMessage()
    {
        UsrMessage usrMessage=new UsrMessage();
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser==null)
            return new ArrayList<String>();
        boolean isManager= AgentUserHelpler.isManager(agentUser);
        if(isManager==true)
        {
            usrMessage.setDepartManager(agentUser.getDepartment());
            usrMessage.setGrpManager(agentUser.getWorkGrp());
            usrMessage.setUsrId(agentUser.getUserId());
        }
        else
        {
            usrMessage.setUsrId(agentUser.getUserId());
        }
        List<SysMessage> sysMessageList = sysMessageService.queryUsrMessages(usrMessage);
        //只提示未读的消息
        return convertMessageShow(sysMessageList);
    }

    private List<String> convertMessageShow(List<SysMessage> sysMessageList)
    {
        Map<String,Integer> map=new HashMap<String, Integer>();
        for(SysMessage sysMessage:sysMessageList)
        {
            if(StringUtils.isNotEmpty(sysMessage.getSourceTypeId()))
            {
                Integer count=1;
                if(map.containsKey(sysMessage.getSourceTypeId()))
                {
                    count=map.get(sysMessage.getSourceTypeId());
                    count++;
                }
                map.put(sysMessage.getSourceTypeId(),count);
            }
        }

        List<String> stringList=new ArrayList<String>();
        for(Map.Entry<String,Integer> entry:map.entrySet())
        {
            int index=Integer.parseInt(entry.getKey());
            MessageType messageType= MessageType.getMessageTypeFromIndex(index);
            stringList.add("~有"+ entry.getValue()+"条"+ messageType.getName()+"未读");
        }

        return stringList;
    }
}
