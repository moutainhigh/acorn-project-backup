package com.chinadrtv.erp.distribution.controller;

import com.chinadrtv.erp.uc.dto.Ivrdist;
import com.chinadrtv.erp.uc.dto.WilcomCallDetail;
import com.chinadrtv.erp.distribution.service.WilcomCallDetailService;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.uc.dto.AssignAgentDto;
import com.chinadrtv.erp.util.StringUtil;
import org.compass.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: gaodj
 * Date: 14-1-6
 * Time: 下午1:14
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class WilcomCallDetailController extends BaseController {

    @Autowired
    private WilcomCallDetailService callDetailService;
    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {
                    try
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        setValue(sdf.parse(text));
                    }
                    catch(Exception ex) { /* do nothing */}
                }
            }
        });
    }

     @ResponseBody
     @RequestMapping(
             value = "/wilcom/ivr/count",
             method= RequestMethod.POST)
     public Map<String, Object> findIvrCount(CallbackSpecification specification){

        Map<String, Object> map = new HashMap<String, Object>();
        Long totalNum = callDetailService.findIvrCount(specification);
        map.put("total", totalNum);
        map.put("rows", new ArrayList<WilcomCallDetail>());
        return map;
    }

    @ResponseBody
    @RequestMapping(
            value = "/wilcom/abandon/count",
            method= RequestMethod.POST)
    public Map<String, Object> findAbandonCount(CallbackSpecification specification){

        Map<String, Object> map = new HashMap<String, Object>();
        Long totalNum = callDetailService.findAbandonCount(specification);
        map.put("total", totalNum);
        map.put("rows", new ArrayList<WilcomCallDetail>());
        return map;
    }

    @ResponseBody
    @RequestMapping(
            value = "/wilcom/ivr/complete",
            method= RequestMethod.POST)
    public Map<String, Object> ivrComplete(
            String caseId,
            String userId,
            HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            if(StringUtil.isNullOrBank(caseId) || StringUtil.isNullOrBank(userId)) {
                map.put("result", false);
                map.put("msg", "参数caseId和userId不能为空");
            }
            else
            {
                callDetailService.ivrComplete(caseId, userId);
                map.put("result", true);
                map.put("msg", "强制任务完成");
            }
        }
        catch (Exception ex){
            map.put("result", false);
            map.put("msg", "强制任务完成失败:"+ex.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(
            value = "/wilcom/ivr/dist",
            method= RequestMethod.POST)
    public Map<String, Object> assignIvrhist(
            Ivrdist ivrdist,
            CallbackSpecification specification,
            HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            callDetailService.assignIvrhist(specification, ivrdist);
            map.put("result", true);
            map.put("msg", "坐席分配成功");
        }
        catch (Exception ex){
            map.put("result", false);
            map.put("msg", "坐席分配失败:"+ex.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(
            value = "/wilcom/ivr/assign",
            method= RequestMethod.POST)
    public Map<String, Object> assignIvrCount(
            CallbackSpecification specification,
            @RequestParam(required = true) String users,//数据状态
            HttpServletRequest request, HttpServletResponse response){

        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            if(!StringUtil.isNullOrBank(users)){
                users = users.replaceAll("userGroup","workGrp");
            }
            List<AssignAgentDto> assignAgentDtos = mapToAssignAgentDto(jsonBinder.fromJson(users,ArrayList.class));
            callDetailService.assignIvrCount(specification,assignAgentDtos);
            map.put("result", true);
            map.put("msg", "坐席分配成功");
        }
        catch (Exception ex){
            map.put("result", false);
            map.put("msg", "坐席分配失败:"+ex.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(
            value = "/wilcom/abandon/assign",
            method= RequestMethod.POST)
    public Map<String, Object> assignAbandonCount(
            CallbackSpecification specification,
            @RequestParam(required = true) String users,//数据状态
            HttpServletRequest request, HttpServletResponse response){

        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            if(!StringUtil.isNullOrBank(users)){
                users = users.replaceAll("userGroup","workGrp");
            }
            List<AssignAgentDto> assignAgentDtos = mapToAssignAgentDto(jsonBinder.fromJson(users,ArrayList.class));
            callDetailService.assignAbandonCount(specification,assignAgentDtos);
            map.put("result", true);
            map.put("msg", "坐席分配成功");
        }
        catch (Exception ex){
            map.put("result", false);
            map.put("msg", "坐席分配失败:"+ex.getMessage());
        }
        return map;
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
