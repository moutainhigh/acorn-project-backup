package com.chinadrtv.erp.distribution.controller;

import com.chinadrtv.erp.distribution.dto.CallDetail;
import com.chinadrtv.erp.distribution.service.CallDetailService;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.uc.dto.AssignAgentDto;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CallDetailController extends BaseController {

	@Autowired
	private CallDetailService callDetailService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (StringUtils.isBlank(text)) {
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
            value = "/call/abandon/count",
            method= RequestMethod.POST)
    public Map<String, Object> findCallAbandonCount(CallbackSpecification specification){
        Map<String, Object> map = new HashMap<String, Object>();
        Long totalNum = callDetailService.findAbandonCount(specification);
        map.put("total", totalNum);
        map.put("rows", new ArrayList<CallDetail>());
        return map;
    }

    @ResponseBody
    @RequestMapping(
            value = "/call/abandon/assign",
            method= RequestMethod.POST)
    public Map<String, Object> assignCallAbandonCount(
            CallbackSpecification specification,
            @RequestParam(required = true) String users,//数据状态
            HttpServletRequest request, HttpServletResponse response){

        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            if(!StringUtils.isBlank(users)){
                users = users.replaceAll("userGroup","workGrp");
            }
            List<AssignAgentDto> assignAgentDtos = mapToAssignAgentDto(jsonBinder.fromJson(users,ArrayList.class));
            List<String> wrongNumbers = callDetailService.assignAbandonCount(specification,assignAgentDtos);
            map.put("result", true);
            map.put("msg", "坐席分配成功");
        }
        catch (Exception ex){
            map.put("result", false);
            if(ex.getCause() != null){
                map.put("msg", "坐席分配失败:"+ex.getCause().getMessage());
            } else{
                map.put("msg", "坐席分配失败:"+ex.getMessage());
            }
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(
             value = "/call/details/count",
             method= RequestMethod.POST)
    public Map<String, Object> findCallDetailCount(CallbackSpecification specification){
        /*
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            specification.setAgentUser(user.getUserId());
            specification.setWorkGroup(user.getWorkGrp());
        }
        */
        Map<String, Object> map = new HashMap<String, Object>();
        Long totalNum = callDetailService.findIvrCount(specification);
        map.put("total", totalNum);
        map.put("rows", new ArrayList<CallDetail>());
        return map;
    }

    @ResponseBody
    @RequestMapping(
            value = "/call/details/assign",
            method= RequestMethod.POST)
    public Map<String, Object> assignCallDetailCount(
              CallbackSpecification specification,
              @RequestParam(required = true) String users,//数据状态
              HttpServletRequest request, HttpServletResponse response){

        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            if(!StringUtils.isBlank(users)){
                users = users.replaceAll("userGroup","workGrp");
            }
            List<AssignAgentDto> assignAgentDtos = mapToAssignAgentDto(jsonBinder.fromJson(users,ArrayList.class));
            List<String> wrongNumbers = callDetailService.assignIvrCount(specification,assignAgentDtos);
            if(wrongNumbers.isEmpty()){
                map.put("result", true);
                map.put("msg", "坐席分配成功！");
            } else {
                map.put("result", true);
                map.put("msg", String.format("坐席分配成功，已排除%d笔非法号码(%s)！", wrongNumbers.size(), StringUtils.join(wrongNumbers, ',')));
            }
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
