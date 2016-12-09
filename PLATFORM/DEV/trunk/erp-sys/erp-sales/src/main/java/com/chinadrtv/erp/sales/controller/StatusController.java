package com.chinadrtv.erp.sales.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.sales.dto.AgentDto;
import com.chinadrtv.erp.sales.service.StatusService;
import com.chinadrtv.erp.user.handle.SalesSessionRegistry;
import com.chinadrtv.erp.user.service.SalesSessionService;

/**
 * 后门程序，不用通过登录即可访问状态页面
 * 
 * @author dengqianyong
 *
 */
@Controller
@RequestMapping(value = "/stat")
public class StatusController extends BaseController {
	
	@Resource
	private SalesSessionRegistry sessionRegistry;
	
	@Resource
	private StatusService statusService;
	
	@Resource
	private SalesSessionService sessionService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String viewStatus() {
		return "status";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/agents", method = RequestMethod.GET)
	public List<AgentDto> ajaxAgents() {
		return statusService.getAgents();
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/cull/{uid}", method = RequestMethod.GET)
	public String ajaxCull(@PathVariable("uid") String uid) {
		Boolean result = statusService.cullAgent(uid);
		return result != null ? result.toString() : Boolean.FALSE.toString();
	}
	
	// 被StatusService轮询调用
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/service/agents", method = RequestMethod.GET)
	public List<AgentDto> getActiveAgents(HttpServletRequest request) {
		List<AgentDto> result = new ArrayList<AgentDto>();
		Map<Object, Date> lastActivityDates = new HashMap<Object, Date>();
		for (Map map : sessionRegistry.getSalesprincipals()) {
			Object principal = map.get("principal");
			String uid = ((UserDetails) principal).getUsername();
			if (StringUtils.isEmpty(uid) || "null".equals(uid)) continue;
			
			AgentDto dto = new AgentDto();

			// a principal may have multiple active sessions
			for (SessionInformation session : sessionRegistry.getAllSessions(principal, false)) {
				dto.setSessionId(session.getSessionId());

				// no last activity stored
				if (lastActivityDates.get(principal) == null) {
					// lastActivityDates.put(principal, session.getLastRequest());
					dto.setLastTime(session.getLastRequest());
				} else {
					// check to see if this session is newer than the last stored
					Date prevLastRequest = lastActivityDates.get(principal);
					if (session.getLastRequest().after(prevLastRequest)) {
						// update if so
						lastActivityDates.put(principal, session.getLastRequest());
						dto.setLastTime(session.getLastRequest());
					}
				}
			}

			dto.setUid(uid);
			dto.setIp(map.get("ip").toString());
			dto.setServerIp(request.getLocalAddr());
			//dto.setOrderCount(statusService.getOrderCount(uid)); // 成单数量
			//dto.setCallCount(statusService.getCallCount(uid)); // 通话数量
			dto.setOrderCount(-1); // 成单数量
			dto.setCallCount(-1); // 通话数量

			result.add(dto);
		}
		return result;
	}
	
	// 被StatusService轮询调用
	@ResponseBody
	@RequestMapping(value = "/service/cull/{uid}", method = RequestMethod.GET)
	public String cullingUser(@PathVariable("uid") String uid) {
		for (Object principal : sessionRegistry.getAllPrincipals()) {
			UserDetails userDetails = (UserDetails) principal;
			if (StringUtils.equals(userDetails.getUsername(), uid)) {
				List<SessionInformation> sessionInfos = sessionRegistry.getAllSessions(userDetails, false);
				if (sessionInfos != null) {
					for (SessionInformation info : sessionInfos) {
						info.expireNow();
						sessionRegistry.removeSessionInformation(info.getSessionId());
						sessionRegistry.removeSalesprincipals(userDetails.getUsername());
						sessionService.removeSalessession(info.getSessionId());
					}
				}
				return Boolean.TRUE.toString();
			}
		}
		return Boolean.FALSE.toString();
	}
	
}
