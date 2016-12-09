package com.chinadrtv.erp.distribution.controller;

import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.uc.constants.CallbackType;
import com.chinadrtv.erp.uc.dto.AssignAgentDto;
import com.chinadrtv.erp.uc.dto.GroupDto;
import com.chinadrtv.erp.uc.service.CallbackService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
import com.chinadrtv.erp.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 话务分配
 *
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-7-30
 * Time: 下午2:31
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value="/traffic")
public class TrafficDistributionController extends BaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TrafficDistributionController.class);
    @Autowired
    private CallbackService callbackService;

   //获取可分配的话务数量　

    //获取可接受话务分配的组

    @RequestMapping(value="/getValidGroup")
    public void getValidGroup(
            @RequestParam(required = false, defaultValue = "") String groupName,
            @RequestParam(required = false, defaultValue = "5") String callType,
            HttpServletRequest request, HttpServletResponse response){
        AgentUser user = SecurityHelper.getLoginUser();
        Map map =  new HashMap();
        List<GroupDto> list =callbackService.findValidGroup(user.getUserId(), user.getWorkGrp(), 0L,  Long.valueOf(callType), groupName);

        logger.info("getVersion_map:"+jsonBinder.toJson(list));
        map.put("total",list.size());
        map.put("rows",list);

        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }




//    //分配话务到组
//    @RequestMapping(value="/allotToGroup")
//    public void allotToGroup(
//            @RequestParam(required = false, defaultValue = "") String groupids,
//            @RequestParam(required = false, defaultValue = "") String groupNames,
//            @RequestParam(required = false, defaultValue = "") String priority, //优先级
//            @RequestParam(required = false, defaultValue = "") String sdt,//开始时间
//            @RequestParam(required = false, defaultValue = "") String edt,//结束时间
//            @RequestParam(required = false, defaultValue = "") String acd,//ACD组
//            @RequestParam(required = false, defaultValue = "") String dnis,//被叫号
//            @RequestParam(required = false, defaultValue = "") String allocatedNumbers,//数据状态
//            HttpServletRequest request, HttpServletResponse response){
//        AgentUser user = SecurityHelper.getLoginUser();
//        Map map =  new HashMap();
//        Boolean result = false;
//        String message="分配到坐席组失败";
//        CallbackSpecification callbackSpecification= new CallbackSpecification();
//
//        callbackSpecification.setAcdGroup(acd);
//        callbackSpecification.setPriority(priority);
//        callbackSpecification.setDnis(dnis);
//
//        if(allocatedNumbers.equals("0")){
//            callbackSpecification.setAssigned(false);
//        }else{
//            callbackSpecification.setAssigned(true);
//        }
//        try {
//            if(! StringUtil.isNullOrBank(sdt)) callbackSpecification.setStartDate(DateUtil.string2Date(sdt, "yyyy-MM-dd HH:mm:ss"));
//            if(! StringUtil.isNullOrBank(edt)) callbackSpecification.setEndDate(DateUtil.string2Date(edt,"yyyy-MM-dd HH:mm:ss"));
//            List<Callback> list=callbackService.findCallbacks(callbackSpecification);
//
//            result= callbackService.assignCallBackToAgentGrp(callbackSpecification,)
//
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            message+=e.getMessage();
//            logger.error(message+e.getMessage());
//        }finally {
//
//
//        map.put("result",result);
//        map.put("msg",message);
//        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
//        }
//    }

    //查询callback
    @RequestMapping(value="/findCallback")
    public void findCallback(  @RequestParam(required = false, defaultValue = "") String priority, //优先级
                               @RequestParam(required = false, defaultValue = "") String sdt,//开始时间
                               @RequestParam(required = false, defaultValue = "") String edt,//结束时间
                               @RequestParam(required = false, defaultValue = "") String acd,//ACD组
                               @RequestParam(required = false, defaultValue = "") String dnis,//被叫号
                               @RequestParam(required = false, defaultValue = "") String allocatedNumbers,//数据状态
                               HttpServletRequest request, HttpServletResponse response){
        Map map = new HashMap();
        Boolean result=false;
        String message="查询CallBack数据失败";
        CallbackSpecification callbackSpecification= new CallbackSpecification();
        callbackSpecification.setAcdGroup(acd);
        callbackSpecification.setPriority(priority);
        callbackSpecification.setDnis(dnis);
        callbackSpecification.setAllocatedNumber(Long.valueOf(allocatedNumbers));
        //取数范围
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            List<GroupDto> list =callbackService.findValidGroup(user.getUserId(), user.getWorkGrp(), 1L, 0L+CallbackType.CALLBACK.getIndex(), null);
            String groups = "";
            for(GroupDto dto : list){
                if(StringUtils.isNotBlank(groups))  groups += ",";
                groups += dto.getGrpid();
            }
            if(StringUtils.isNotBlank(groups)){
                callbackSpecification.setWorkGroup(groups);
            }
        }
        try {
            if(! StringUtil.isNullOrBank(sdt)) callbackSpecification.setStartDate(DateUtil.string2Date(sdt, "yyyy-MM-dd HH:mm:ss"));
            if(! StringUtil.isNullOrBank(edt)) callbackSpecification.setEndDate(DateUtil.string2Date(edt,"yyyy-MM-dd HH:mm:ss"));
            //int count=callbackService.findCallbacks(callbackSpecification).size();
            Long count=callbackService.findCallbackCount(callbackSpecification);
            map.put("count",count);
            result = true;
            message="查询Callback数据成功";
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            message+=e.getMessage();
            logger.error(message+e.getMessage());
        }finally {
            map.put("result",result);
            map.put("msg",message);
            HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
        }
    }
    //分配话务到人
    @ResponseBody
    @RequestMapping(value="/allotCallbackToPerson")
    public void allotCallbackToPerson(
            @RequestParam(required = false, defaultValue = "") String groupids,
            @RequestParam(required = false, defaultValue = "") String groupNames,
            @RequestParam(required = false, defaultValue = "") String priority, //优先级
            @RequestParam(required = false, defaultValue = "") String sdt,//开始时间
            @RequestParam(required = false, defaultValue = "") String edt,//结束时间
            @RequestParam(required = false, defaultValue = "") String acd,//ACD组
            @RequestParam(required = false, defaultValue = "") String dnis,//被叫号
            @RequestParam(required = false, defaultValue = "") String allocatedNumbers,//数据状态
            @RequestParam(required = false, defaultValue = "") String users,//数据状态

            HttpServletRequest request, HttpServletResponse response){
        AgentUser user = SecurityHelper.getLoginUser();
        if(!StringUtil.isNullOrBank(users)){
            users = users.replaceAll("userGroup","workGrp");
        }
        Map map =  new HashMap();
        Boolean result = false;
        String message="分配到坐席失败：";
        CallbackSpecification callbackSpecification= new CallbackSpecification();
        callbackSpecification.setAcdGroup(acd);
        callbackSpecification.setPriority(priority);
        callbackSpecification.setDnis(dnis);
        callbackSpecification.setAllocatedNumber(Long.valueOf(allocatedNumbers));

        //取数范围
        if(user != null){
            List<GroupDto> list =callbackService.findValidGroup(user.getUserId(), user.getWorkGrp(), 1L,  0L+CallbackType.CALLBACK.getIndex(), null);
            String groups = "";
            for(GroupDto dto : list){
                if(StringUtils.isNotBlank(groups))  groups += ",";
                groups += dto.getGrpid();
            }
            if(StringUtils.isNotBlank(groups)){
                callbackSpecification.setWorkGroup(groups);
            }
        }

        try {
            if(! StringUtil.isNullOrBank(sdt)) callbackSpecification.setStartDate(DateUtil.string2Date(sdt, "yyyy-MM-dd HH:mm:ss"));
            if(! StringUtil.isNullOrBank(edt)) callbackSpecification.setEndDate(DateUtil.string2Date(edt,"yyyy-MM-dd HH:mm:ss"));

            List<AssignAgentDto> assignAgentDtos = mapToAssignAgentDto(jsonBinder.fromJson(users,ArrayList.class));


            result = callbackService.assignCallBackToAgent(callbackSpecification,assignAgentDtos);
            message="分配到坐席成功";
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            message+=e.getMessage();
            logger.error(message+e.getMessage());
        }finally {
            map.put("result",result);
            map.put("msg",message);
            logger.info(message);
            HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
        }
    }


    private  List<AssignAgentDto> mapToAssignAgentDto(List<Map> list){
        List<AssignAgentDto> newlist = new ArrayList<AssignAgentDto>();

        for(Map map :list){
            AssignAgentDto dto = new AssignAgentDto();
            dto.setAssignCount(Integer.valueOf(map.get("assignCount").toString()));
            dto.setUserId(map.get("userId").toString());
            dto.setWorkGrp(map.get("workGrp").toString());
            newlist.add(dto);
        }

        return newlist;
    }




}
