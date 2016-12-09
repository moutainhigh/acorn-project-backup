package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.dao.SysMessageDao;
import com.chinadrtv.erp.sales.core.model.MessageCheckStatus;
import com.chinadrtv.erp.sales.core.model.MessageQueryDto;
import com.chinadrtv.erp.sales.core.model.UsrMessage;
import com.chinadrtv.erp.sales.core.service.SysMessageAgentService;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class SysMessageServiceImpl implements SysMessageService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SysMessageServiceImpl.class);

    @Autowired
    private SysMessageDao sysMessageDao;

    @Autowired
    private UserService userService;

    @Value("${com.chinadrtv.erp.sales.core.SysMessageService.retryminute}")
    private Long checkRetryMinutes;

    @Transactional(timeout = 30)
    public List<SysMessage> queryMessages(MessageQueryDto messageQueryDto)
    {
        messageQueryDto.setCheckStatusList(new ArrayList<Integer>());
        if(messageQueryDto.getChecked()!=null)
        {
            messageQueryDto.getCheckStatusList().add(messageQueryDto.getChecked());
        }
        else
        {
            //消息状态 - 为2的已经处理过了，只是在数据库中做个记录
            messageQueryDto.getCheckStatusList().add(MessageCheckStatus.NOT_READ.getIndex());
            messageQueryDto.getCheckStatusList().add(MessageCheckStatus.READED.getIndex());
        }
        if(StringUtils.isNotEmpty(messageQueryDto.getRecivierGroup()))
        {
            if(messageQueryDto.getGrpIdList()!=null)
            {
                if(!messageQueryDto.getGrpIdList().contains(messageQueryDto.getRecivierGroup()))
                {
                    messageQueryDto.getGrpIdList().add(messageQueryDto.getRecivierGroup());
                }
            }
            else
            {
                messageQueryDto.setGrpIdList(new ArrayList<String>());
                messageQueryDto.getGrpIdList().add(messageQueryDto.getRecivierGroup());
            }
        }

        if(messageQueryDto.getMonthType()!=null)
        {
            switch (messageQueryDto.getMonthType())
            {
                case THISMONTH :
                    initMonthDateRegion(messageQueryDto,Calendar.getInstance());
                    break;
                case LASTMONTH:
                    Calendar c1 = Calendar.getInstance();
                    c1.add(Calendar.MONTH, -1);
                    initMonthDateRegion(messageQueryDto,c1);
                    break;
                case EARLIERMONTH:
                    Calendar c2 = Calendar.getInstance();
                    c2.add(Calendar.MONTH, -2);
                    initMonthDateRegion(messageQueryDto,c2);
                    break;
                default:break;
            }
        }
        else
        {
            Calendar c2 = Calendar.getInstance();
            c2.add(Calendar.MONTH, -2);
            initMonthDateRegion(messageQueryDto,c2);
            messageQueryDto.setEndDate(null);
        }

        return sysMessageDao.queryMessages(messageQueryDto);
    }


    private void initMonthDateRegion(MessageQueryDto messageQueryDto, Calendar calendar)
    {
        int month=calendar.get(Calendar.MONTH)+1;
        int year=calendar.get(Calendar.YEAR);
        //int day=calendar.get(Calendar.SHORT);
        calendar.set(Calendar.DATE,1);
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DATE,-1);
        int day= calendar.get(Calendar.DATE);
        String strBegin=String.format("%04d-%02d-01 00:00:00",year,month);
        String strEnd=String.format("%04d-%02d-%02d 23:59:59",year,month,day);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try
        {
            if(messageQueryDto.getBeginDate()==null)
                messageQueryDto.setBeginDate(simpleDateFormat.parse(strBegin));
            if(messageQueryDto.getEndDate()==null)
                messageQueryDto.setEndDate(simpleDateFormat.parse(strEnd));
        }catch (Exception exp)
        {

        }
    }

    public void addMessage(SysMessage sysMessage) throws ErpException
    {
        if(sysMessage==null)
        {
            return;
        }
        if(sysMessage.getCreateDate()==null)
        {
            sysMessage.setCreateDate(new Date());
        }
        if(StringUtils.isEmpty(sysMessage.getCreateUser()))
        {
            throw new ErpException(ExceptionConstant.SERVICE_SYSMESSAGE_EXCEPTION, "未设置创建人");
        }
        if(StringUtils.isEmpty(sysMessage.getRecivierGroup()))
        {
            throw new ErpException(ExceptionConstant.SERVICE_SYSMESSAGE_EXCEPTION, "未设置接收组信息");
        }

        MessageType sysMessageType=null;
        if(StringUtils.isEmpty(sysMessage.getSourceTypeId()))
        {
            throw new ErpException(ExceptionConstant.SERVICE_SYSMESSAGE_EXCEPTION, "未设置消息类型");
        }
        else
        {
            for(MessageType messageType:MessageType.values())
            {
                if(String.valueOf(messageType.getIndex()).equals(sysMessage.getSourceTypeId()))
                {
                    sysMessageType=messageType;
                    break;
                }
            }

            if(sysMessageType==null)
            {
                throw new ErpException(ExceptionConstant.SERVICE_SYSMESSAGE_EXCEPTION, "消息类型不正确");
            }
        }

        if(sysMessage.getChecked()==null)
        {
            sysMessage.setChecked(MessageCheckStatus.NOT_READ.getIndex());
        }

        //根据不同消息类型，来判断
        switch (sysMessageType)
        {
            case PROBLEM_ORDER:
            case URGE_ORDER:
            case CANCEL_ORDER_REJECT:
            case ADD_ORDER_REJECT:
            case MODIFY_ORDER_REJECT:
            case ADD_CONTACT_REJECT:
            case MODIFY_CONTACT_REJECT:
                if(StringUtils.isEmpty(sysMessage.getReceiverId()))
                    throw new ErpException(ExceptionConstant.SERVICE_SYSMESSAGE_EXCEPTION, "未设置消息接收人");
                break;
            case CANCEL_ORDER:
                break;
            case MODIFY_ORDER:
            case MODIFY_CONTACT:
            case ADD_ORDER:
            case ADD_CONTACT:
                if(StringUtils.isNotEmpty(sysMessage.getReceiverId()))
                {
                    throw new ErpException(ExceptionConstant.SERVICE_SYSMESSAGE_EXCEPTION, "非法设置消息接收人");
                }
            default:
                break;
        }

        sysMessageDao.save(sysMessage);

        //更新缓存
        this.invalidUsrMessageByAdd(sysMessage);
    }

    private void invalidUsrMessageByAdd(SysMessage sysMessage)
    {
        List<String> usrIdList=new ArrayList<String>();
        if(StringUtils.isEmpty(sysMessage.getReceiverId()))
        {
            //if(StringUtils.isNotEmpty(sysMessage.getRecivierGroup()))
            if(StringUtils.isNotEmpty(sysMessage.getDepartmentId()))
            {
                //获取组管理员
                List<String> list=getManagerList(sysMessage.getRecivierGroup(),sysMessage.getDepartmentId());
                if(list!=null)
                {
                    usrIdList.addAll(list);
                }
            }
        }
        else
        {
            usrIdList.add(sysMessage.getReceiverId());
        }

        if(usrIdList.size()>0)
        {
            sysMessageDao.invalidUsrMessages(usrIdList);
        }
    }


    private List<String> getManagerList(String grpId,String departId)
    {
        List<String> list=null;
        if(StringUtils.isNotEmpty(departId))
        {
            try{
                List<AgentUser> agentUserList= userService.getManageUserList(departId);
                if(agentUserList!=null&&agentUserList.size()>0)
                {
                    list=new ArrayList<String>();
                    for(AgentUser agentUser:agentUserList)
                    {
                        list.add(agentUser.getUserId());
                    }
                }
            }catch (Exception exp){
                logger.error("get managers error:"+exp.getMessage(), exp);
            }
        }
        return list;
    }

    @Transactional(timeout = 30)
    public void checkMessageByExample(SysMessage sysMessage)
    {
        sysMessageDao.checkMessageByExample(sysMessage);

        //更新缓存
        List<String> usrIdList=new ArrayList<String>();
        //if(StringUtils.isNotEmpty(sysMessage.getRecivierGroup()))
        if(StringUtils.isNotEmpty(sysMessage.getReceiverId()))
        {
            usrIdList.add(sysMessage.getReceiverId());
        }
        else if(StringUtils.isNotEmpty(sysMessage.getDepartmentId()))
        {
            //获取组的所有用户
            List<String> list=getManagerList(sysMessage.getRecivierGroup(),sysMessage.getDepartmentId());
            if(list!=null)
            {
                usrIdList.addAll(list);
            }
        }

        if(usrIdList.size()>0)
            sysMessageDao.invalidUsrMessages(usrIdList);
    }

    public void checkMessageByIds(List<Long> msgIdList)
    {
        sysMessageDao.checkMessageByIds(msgIdList);

        //更新缓存--此方法暂不使用
    }

    @Autowired
    private SysMessageAgentService sysMessageAgentService;

    public List<SysMessage> queryUsrMessages(UsrMessage usrMessage)
    {
        List<SysMessage> sysMessageList = this.queryUsrMessagesInner(usrMessage);
        //此时需要处理消息（如果是主管，那么所有同部门的主管的消息都要无效）
        Date dtNow=new Date(new Date().getTime()-checkRetryMinutes*60000);
        if(sysMessageList!=null&&sysMessageList.size()>0)
        {
            List<SysMessage> checkMessageList=new ArrayList<SysMessage>();
            for(SysMessage sysMessage:sysMessageList)
            {
                if(String.valueOf(MessageType.ADD_ORDER_REJECT.getIndex()).equals(sysMessage.getSourceTypeId())
                    ||  String.valueOf(MessageType.ADD_CONTACT_REJECT.getIndex()).equals(sysMessage.getSourceTypeId())
                        ||  String.valueOf(MessageType.CANCEL_ORDER_REJECT.getIndex()).equals(sysMessage.getSourceTypeId()))
                {
                    continue;
                }
                if(sysMessage.getChecked()!=null)
                {
                    if(MessageCheckStatus.READED.getIndex()==sysMessage.getChecked().intValue())
                    {
                        if(dtNow.compareTo(sysMessage.getCheckDate())>0)
                        {
                            checkMessageList.add(sysMessage);
                        }
                    }
                }
            }

            if(checkMessageList.size()>0)
            {
                sysMessageAgentService.updateMessageCheck(checkMessageList, MessageCheckStatus.NOT_READ);
                //最后更新
                String departId="";
                String grpId="";
                for(SysMessage sysMessage:checkMessageList)
                {
                    if(StringUtils.isEmpty(sysMessage.getReceiverId()))
                    {
                        departId=sysMessage.getDepartmentId();
                        grpId=sysMessage.getRecivierGroup();
                    }
                    sysMessage.setChecked(MessageCheckStatus.NOT_READ.getIndex());
                }

                if(StringUtils.isNotEmpty(departId))
                {
                    //
                    List<String> list=this.getManagerList(grpId,departId);
                    if(list!=null&&list.size()>0)
                    {
                        sysMessageDao.invalidUsrMessages(list);
                    }
                }
                else
                {
                    List<String> list=new ArrayList<String>();
                    list.add(checkMessageList.get(0).getReceiverId());
                    sysMessageDao.invalidUsrMessages(list);
                }

            }
        }

        if(sysMessageList!=null&&sysMessageList.size()>0)
        {
            List<SysMessage> notReadSysMessageList=new ArrayList<SysMessage>();
            Integer type=MessageCheckStatus.NOT_READ.getIndex();
            for(SysMessage sysMessage:sysMessageList)
            {
                if(type.equals(sysMessage.getChecked()))
                {
                    notReadSysMessageList.add(sysMessage);
                }
            }
            return notReadSysMessageList;
        }
        else
            return sysMessageList;
    }

    private List<SysMessage> queryUsrMessagesInner(UsrMessage usrMessage)
    {
        if(usrMessage.getBeginDate()==null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);

            String strBegin=String.format("%04d-%02d-01 00:00:00",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try{
                usrMessage.setBeginDate(simpleDateFormat.parse(strBegin));
            }catch (Exception exp){

            }
        }

        return sysMessageDao.queryUsrMessages(usrMessage);
    }

    private List<SysMessage> queryMessageByContent(MessageType messageType,String content)
    {
        MessageQueryDto messageQueryDto=new MessageQueryDto();
        messageQueryDto.setMessageType(messageType);
        messageQueryDto.setContent(content);

        messageQueryDto.setCheckStatusList(new ArrayList<Integer>());
        messageQueryDto.getCheckStatusList().add(MessageCheckStatus.NOT_READ.getIndex());
        messageQueryDto.getCheckStatusList().add(MessageCheckStatus.READED.getIndex());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        String strBegin=String.format("%04d-%02d-01 00:00:00",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try{
            messageQueryDto.setBeginDate(simpleDateFormat.parse(strBegin));
        }catch (Exception exp){
        }

        return sysMessageDao.queryMessages(messageQueryDto);
    }

    @Transactional(timeout = 30)
    public void handleMessageByUsr(MessageType messageType, String content, Date crdt)
    {
        handleMessageInner(messageType,content,crdt,true);
    }

    @Transactional(timeout = 30)
    public void handleMessage(MessageType messageType, String content, Date crdt)
    {
        handleMessageInner(messageType,content,crdt,false);
    }

    private void handleMessageInner(MessageType messageType, String content, Date crdt, boolean isAssign)
    {
        //既然是处理消息了，说明肯定是自己能处理的消息，所以直接从缓存中获取即可
        //除了取消订单流程
        UsrMessage usrMessage=new UsrMessage();
        //赋值
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(isAssign)
        {
            usrMessage.setDepartManager(agentUser.getDepartment());
            usrMessage.setGrpManager(agentUser.getWorkGrp());
        }
        else
        {
            boolean isManager= SecurityHelper.isManager(agentUser);
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
        }
        List<SysMessage> sysMessageList=this.queryUsrMessagesInner(usrMessage);
        //根据类型、内容以及时间来确定
        SysMessage sysMessage=this.findMatchMessage(sysMessageList, messageType, content, crdt);

        if(sysMessage!=null)
        {
            finishMessage(sysMessage);
        }
        else
        {
            //0.问题单 ---可能多个
            //1.催送货 ---可能多个
            //2.取消订单 xxx
            //3.修改订单 xxx
            //4.修改客户 xxx
            //5.取消订单驳回 ---可能多个
            //6.修改订单驳回 *
            //7.修改客户驳回 ---可能多个
            //9.新增客户驳回 *
            //10.新增订单   xxx
            //11.新增订单驳回 *
            logger.error("not find match message:" + String.valueOf(messageType.getIndex()) + "-" + content + "-" + agentUser.getUserId());
            if(messageType.getIndex()==MessageType.MODIFY_ORDER_REJECT.getIndex()||messageType.getIndex()==MessageType.ADD_CONTACT_REJECT.getIndex()
                    ||messageType.getIndex()==MessageType.ADD_ORDER_REJECT.getIndex())
            {
                usrMessage.setDepartManager(null);
                usrMessage.setGrpManager(null);
                usrMessage.setUsrId(null);

                sysMessageList=this.queryMessageByContent(messageType, content);
                if(sysMessageList!=null&&sysMessageList.size()>0)
                {
                    sysMessage=this.findMatchMessage(sysMessageList, messageType, content, crdt);
                }

                if(sysMessage!=null)
                {
                    finishMessage(sysMessage);
                }
                else
                {
                    logger.error("just can not find match message:"+String.valueOf(messageType.getIndex())+"-"+content+"-"+agentUser.getUserId());
                }
            }

        }
    }

    private void finishMessage(SysMessage sysMessage) {
        sysMessage.setChecked(MessageCheckStatus.HANDLED.getIndex());
        sysMessage.setCheckDate(new Date());
        sysMessageAgentService.handleMessage(sysMessage);

        List<String> usrIdList=new ArrayList<String>();
        if(StringUtils.isNotEmpty(sysMessage.getReceiverId()))
        {
            usrIdList.add(sysMessage.getReceiverId());
        }
        else
        {
            List<String> list=this.getManagerList(sysMessage.getRecivierGroup(),sysMessage.getDepartmentId());
            if(list!=null&&list.size()>0)
            {
                usrIdList.addAll(list);
            }
        }

        if(usrIdList.size()>0)
            sysMessageDao.invalidUsrMessages(usrIdList);
    }

    private SysMessage findMatchMessage(List<SysMessage> sysMessageList, MessageType messageType,String content, Date crdt)
    {
        String strType=String.valueOf(messageType.getIndex());
        List<SysMessage> matchMessageList=new ArrayList<SysMessage>();
        for(SysMessage sysMessage:sysMessageList)
        {
            if(strType.equals(sysMessage.getSourceTypeId())&&content.equals(sysMessage.getContent()))
            {
                matchMessageList.add(sysMessage);
            }
        }

        if(matchMessageList.size()>1)
        {
            //取时间最接近的
            if(crdt==null)
            {
                return matchMessageList.get(0);
            }
            else
            {
                Long detalTime=null;//Math.abs(crdt.getTime()-matchMessageList.get(0).getCreateDate().getTime());
                SysMessage matchMessage=null;
                for(SysMessage sysMessage:matchMessageList)
                {
                    Long newDetalTime=Math.abs(crdt.getTime()-sysMessage.getCreateDate().getTime());
                    if(matchMessage==null)
                    {
                        detalTime=newDetalTime;
                        matchMessage=sysMessage;
                    }
                    else
                    {
                        if(newDetalTime<detalTime)
                        {
                            detalTime=newDetalTime;
                            matchMessage=sysMessage;
                        }
                    }
                }
                if(matchMessage!=null)
                    return matchMessage;
                else
                    return matchMessageList.get(0);
            }
        }
        else if(matchMessageList.size()==1)
        {
            return matchMessageList.get(0);
        }
        else
        {
            return null;
        }
    }

    @Transactional(timeout = 30)
    public void cancelOrderMessage(String orderId)
    {
        //首先取到所有未处理的消息
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        calendar.add(Calendar.DATE,-1);
        List<SysMessage> sysMessageList=sysMessageDao.queryOrderMessage(orderId,calendar.getTime());
        if(sysMessageList!=null&&sysMessageList.size()>0)
        {
            for(SysMessage sysMessage:sysMessageList)
            {
                if(StringUtils.isNotEmpty(sysMessage.getSourceTypeId()))
                {
                    int index= Integer.parseInt(sysMessage.getSourceTypeId());
                    MessageType messageType=MessageType.getMessageTypeFromIndex(index);
                    if(messageType!=null)
                    {
                        if(index==MessageType.URGE_ORDER.getIndex()
                                ||index==MessageType.PROBLEM_ORDER.getIndex()
                                ||index==MessageType.ADD_ORDER.getIndex()
                                ||index==MessageType.ADD_ORDER_REJECT.getIndex()
                                ||index==MessageType.CANCEL_ORDER.getIndex()
                                ||index==MessageType.CANCEL_ORDER_REJECT.getIndex()
                                ||index==MessageType.MODIFY_ORDER.getIndex()
                                ||index==MessageType.MODIFY_ORDER_REJECT.getIndex()
                                )
                            this.finishMessage(sysMessage);
                    }
                }
            }
        }
    }
}
