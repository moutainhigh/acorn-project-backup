package com.chinadrtv.erp.distribution.controller;

import com.chinadrtv.erp.model.agent.CallbackEx;
import com.chinadrtv.erp.distribution.service.AgentCallbackService;
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
 * User: gaudi
 * Date: 14-1-14
 * Time: 下午2:09
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class WilcomCallbackController extends BaseController {

    @Autowired
    private AgentCallbackService agentCallbackService;

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
            value = "/wilcom/callback/count",
            method= RequestMethod.POST)
    public Map<String, Object> findCallbackCount(CallbackSpecification specification){

        Map<String, Object> map = new HashMap<String, Object>();
        Long totalNum = agentCallbackService.findCallbackCount(specification);
        map.put("total", totalNum);
        map.put("rows", new ArrayList<CallbackEx>());
        return map;
    }

    @ResponseBody
    @RequestMapping(
            value = "/wilcom/callback/assign",
            method= RequestMethod.POST)
    public Map<String, Object> assignCallbackCount(
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
            agentCallbackService.assignCallbackCount(specification,assignAgentDtos);
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
