package com.chinadrtv.erp.distribution.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chinadrtv.erp.constant.LeadInteractionType;
import com.chinadrtv.erp.distribution.parser.userlevel.UserLevelParserFactory;
import com.chinadrtv.erp.distribution.service.WilcomConnectedService;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.marketing.core.service.LeadInterActionService;
import com.chinadrtv.erp.uc.dto.AgentQueryDto4Callback;
import com.chinadrtv.erp.uc.dto.CallbackAssignDto;
import com.chinadrtv.erp.uc.service.CallbackService;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.AgentUserService;
import com.chinadrtv.erp.user.util.SecurityHelper;

@Controller
@RequestMapping(value = "/callbackAssign")
public class CallbackAssignController extends BaseController {
	
	private static transient final Logger logger = LoggerFactory.getLogger(CallbackAssignController.class);

	@Autowired
	private CallbackService callbackService;
	@Autowired
	private LeadInterActionService leadInterActionService;
	@Autowired
	private AgentUserService userService;
	@Autowired
	private WilcomConnectedService wilcomConnectedService;
	
	
	@ResponseBody
	@RequestMapping(value = "/queryDeptAvaliableQty")
	public Map<String, Object> queryDeptAvaliableQty(LeadInteractionSearchDto searchDto){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		
		searchDto.setManagerGrp(SecurityHelper.getLoginUser().getWorkGrp());
		searchDto.getLeadInteractionType().add(LeadInteractionType.INBOUND_IN.getIndex() + "");
		searchDto.getLeadInteractionType().add(LeadInteractionType.OUTBOUND_IN.getIndex() + "");
		searchDto.setDeptIdNotNullNeeded(true);
		searchDto.setDeDuplicatedNeeded(false);
		Integer qty = leadInterActionService.queryAllocatableLeadInteractionCount(searchDto);
		
		rsMap.put("qty", qty);
		
		return rsMap;
	}

	/**
	 * 查询话务分配类型为‘接通’的可分配数量
	 * @param searchDto
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAvaliableQty")
	public Map<String, Object> queryAvaliableQty(LeadInteractionSearchDto searchDto){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		AgentUser user = SecurityHelper.getLoginUser();
		
		searchDto.getLeadInteractionType().add(LeadInteractionType.INBOUND_IN.getIndex() + "");
		searchDto.getLeadInteractionType().add(LeadInteractionType.OUTBOUND_IN.getIndex() + "");
		searchDto.setDeptId(user.getDepartment());
		Integer qty = leadInterActionService.queryAllocatableLeadInteractionCount(searchDto);
		rsMap.put("qty", qty);
		
		return rsMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryAvaliableAgent")
	public Map<String, Object> queryAvaliableAgent(AgentQueryDto4Callback dto){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		List<String> levelList = null;
		if(StringUtils.isNotBlank(dto.getLevel())) {
			levelList = Arrays.asList(dto.getLevel().split(","));
			if(levelList.contains("-1")) {
				dto.setAbNormalShowingRequired(true);
			}
		}
		
		dto.setLevels(levelList);
		dto.setLevel("");
		
		List<AgentUserInfo4TeleDist> agentList = callbackService.queryQualifiedAgentUser(dto);
		
		rsMap.put("rows", agentList);
		return rsMap;
	}
	
	/**
	 * <p>按比例分配到部门</p>
	 * @param liDto
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/assignToDept")
	public Map<String, Object> assignToDept(LeadInteractionSearchDto liDto, String ivrType) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		
		try {
			callbackService.assignToDept(liDto, ivrType);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			logger.error("分配到部门失败", e);
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		
		return rsMap;
	}
	
	/**
	 * 分配类型为‘接通’的话务到坐席组
	 * @param liDto
	 * @param agentUsers
	 * @param averageAssign
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value = "/assignToGroup")
	public Map<String, Object> assignToGroup(LeadInteractionSearchDto liDto, String agentUsersStr){
		
		List<Map<String, Object>> param = new ArrayList<Map<String, Object>>();
		if(null != agentUsersStr && !"".equals(agentUsersStr)){
			JSONObject jsonObj = JSONObject.fromObject(agentUsersStr);
			CallbackAssignDto dto = (CallbackAssignDto) jsonObj.toBean(jsonObj, CallbackAssignDto.class);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("assignCount", dto.getAssignCount());
			paramMap.put("userGroup", dto.getUserGroup());
			param.add(paramMap);
		}
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		try {
			Map<String, Object> tempMap = callbackService.assignToGroup(liDto, param);
			rsMap.putAll(tempMap);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("话务分配到坐席组失败", e);
			message = e.getMessage();
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		
		return rsMap;
	}
	
	
	/**
	 * 分配类型为‘接通’的话务到坐席
	 * @param liDto
	 * @param agentUsers
	 * @param averageAssign
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/assignToAgent")
	public Map<String, Object> assignToAgent(LeadInteractionSearchDto liDto, String agentUsersStr, Boolean averageAssign){
		
		List<Map<String, Object>> param = new ArrayList<Map<String, Object>>();
		if(null != agentUsersStr && !"".equals(agentUsersStr)){
			JSONArray jsonArr = JSONArray.fromObject(agentUsersStr);
			List<CallbackAssignDto> dtoLsit = JSONArray.toList(jsonArr, CallbackAssignDto.class);
			
			for(CallbackAssignDto dto : dtoLsit){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("assignCount", dto.getAssignCount());
				paramMap.put("userGroup", dto.getUserGroup());
				paramMap.put("userId", dto.getUserId());
				param.add(paramMap);
			}
		}
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		try {
			Map<String, Object> tempMap = callbackService.assignToAgent(liDto, param, false);
			rsMap.putAll(tempMap);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("话务分配到坐席失败", e);
			message = e.getMessage();
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		
		return rsMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public Map<String, Object> importUserLevel(@RequestParam("file") MultipartFile file)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (file == null || file.isEmpty()) {
			map.put("code", false);
			map.put("msg", "文件不存在或者文件的内容不存在");
			return map;
		}
		
		Integer count = 0;
		try {
			count = userService.removeAndSaveUserLevels(UserLevelParserFactory.parse(file));
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			map.put("code", false);
			map.put("msg", "插入数据库时发生错误: " + e.getMessage());
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			map.put("code", false);
			map.put("msg", "导入文件的格式或者内容不正确");
			return map;
		}
		
		map.put("code", true);
		map.put("count", count);
		return map;
	}
	

	
	/**
	 * <p>查询井星接通类型的可分配数量</p>
	 * @param searchDto
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/ivr/connected/queryAvaliableQty")
	public Map<String, Object> queryIvrConnectedAvaliableQty(LeadInteractionSearchDto searchDto) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		
		Integer qty = wilcomConnectedService.queryIvrConnectedAvaliableQty(searchDto);
		rsMap.put("qty", qty);
		
		return rsMap;
	}
	
	/**
	 * <p>接通（井星）分配到坐席</p>
	 * @param liDto
	 * @param agentUsersStr
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/ivr/connected/assignToAgent")
	public Map<String, Object> assignIvrConnectedToAgent(LeadInteractionSearchDto liDto, String agentUsersStr) {
		List<Map<String, Object>> param = new ArrayList<Map<String, Object>>();
		if(null != agentUsersStr && !"".equals(agentUsersStr)){
			JSONArray jsonArr = JSONArray.fromObject(agentUsersStr);
			@SuppressWarnings("unchecked")
			List<CallbackAssignDto> dtoLsit = JSONArray.toList(jsonArr, CallbackAssignDto.class);
			
			for(CallbackAssignDto dto : dtoLsit){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("assignCount", dto.getAssignCount());
				paramMap.put("userGroup", dto.getUserGroup());
				paramMap.put("userId", dto.getUserId());
				param.add(paramMap);
			}
		}
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		
		try {
			Map<String, Object> tmpMap = wilcomConnectedService.assignToAgent(liDto, param);
			success = true;
			rsMap.putAll(tmpMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("接通（井星）分配到坐席失败", e);
			message = e.getMessage();
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		
		return rsMap;
	}
}
