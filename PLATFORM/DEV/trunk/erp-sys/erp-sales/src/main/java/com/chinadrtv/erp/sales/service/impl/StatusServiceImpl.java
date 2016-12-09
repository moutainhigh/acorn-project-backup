package com.chinadrtv.erp.sales.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.dto.AgentDto;
import com.chinadrtv.erp.sales.service.StatusService;
import com.chinadrtv.erp.tc.core.dto.OrderQueryDto;
import com.chinadrtv.erp.uc.service.LeadInteractionService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StatusServiceImpl implements StatusService {
	
	private List<String> getAgentsUriList;
	private List<String> cullAgentUriList;
	
	private @Value("${GET_ALL_AGENTS_URIS}") String getAgentsUris;
	private @Value("${CULL_AGENT_URIS}") String cullAgentUris;
	
	@Resource
	private OrderhistService orderhistService;
	
	@Resource
	private LeadInteractionService leadInteractionService;
	
	@PostConstruct
	public void init() {
		getAgentsUriList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(getAgentsUris)) {
			for (String uri : StringUtils.split(getAgentsUris, ",")) {
				getAgentsUriList.add(uri);
			}
		}
		
		cullAgentUriList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(cullAgentUris)) {
			for (String uri : StringUtils.split(cullAgentUris, ",")) {
				cullAgentUriList.add(uri);
			}
		}
	}
	
	@Override
	public List<AgentDto> getAgents() {
		List<AgentDto> agents = new ArrayList<AgentDto>();
		for (String uri : getAgentsUriList) {
			agents.addAll(request(uri));
		}
		return agents;
	}
	
	@Override
	public Boolean cullAgent(String uid) {
		for (String uri : cullAgentUriList) {
			httpGet(String.format(uri, uid));
		}
		return true;
	}

	@Override
	public Integer getOrderCount(String uid) {
		OrderQueryDto query = new OrderQueryDto();
		query.setCrUsr(uid);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		query.setBeginCrdt(cal.getTime());
		
		cal.add(Calendar.DAY_OF_MONTH, 1);
		query.setEndCrdt(cal.getTime());
		try {
			return orderhistService.queryOrderTotalCount(query);
		} catch (ErpException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Integer getCallCount(String uid) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date begin = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date end = cal.getTime();
		try {
			return leadInteractionService.countLeadInteractionByAgent(uid, begin, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	private List<AgentDto> request(String uri) {
		String jsonString = httpGet(uri);
		List<AgentDto> result = new ArrayList<AgentDto>();
		if (jsonString != null) {
			ObjectMapper mapper = new ObjectMapper();
			JavaType javaType = mapper.getTypeFactory().constructParametricType(
					ArrayList.class, AgentDto.class);
			try {
				result = (List<AgentDto>) mapper.readValue(jsonString, javaType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private String httpGet(String uri) {
		String jsonString = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		try {
			HttpResponse response = client.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				jsonString = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
