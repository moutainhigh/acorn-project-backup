package com.chinadrtv.erp.task.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.task.common.TriggerType;
import com.chinadrtv.erp.task.core.enums.ViewDataStatus;
import com.chinadrtv.erp.task.core.scheduler.SimpleJob;
import com.chinadrtv.erp.task.core.view.ViewData;
import com.chinadrtv.erp.task.quartz.service.SchedulerService;
import com.chinadrtv.erp.task.vo.PageVo;
import com.chinadrtv.erp.task.vo.SimpleJobClassVo;
import com.chinadrtv.erp.task.vo.TriggerVo;

@Controller
@RequestMapping(value="/task")
public class TaskController {
	
	@Autowired
	private SchedulerService schedulerService;
	
	/**
	 * 暂停一个定时器
	 * @param group
	 * @param name
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pauseTrigger", method=RequestMethod.POST)
	public String pauseTrigger(@RequestParam String group, @RequestParam String name, HttpServletRequest request, HttpServletResponse response, Model model){
		schedulerService.pauseTrigger(name, group);
		return "redirect:/triggerList";
	}
	
	/**
	 * 重启一个定时器
	 * @param group
	 * @param name
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/resumeTrigger", method=RequestMethod.POST)
	public String resumeTrigger(@RequestParam String group, @RequestParam String name, HttpServletRequest request, HttpServletResponse response, Model model){
		schedulerService.resumeTrigger(name, group);
		return "redirect:/triggerList";
	}
	
	@RequestMapping(value="/removeTrigger", method=RequestMethod.POST)
	public String removeTrigger(@RequestParam String group, @RequestParam String name, HttpServletRequest request, HttpServletResponse response, Model model){
		schedulerService.removeTrigger(name, group);
		return "redirect:/triggerList";
	}
	
	@RequestMapping(value="/viewTrigger", method=RequestMethod.POST)
	public String viewTrigger(@RequestParam String group, @RequestParam String name, HttpServletRequest request, HttpServletResponse response, Model model){
		Trigger trigger = schedulerService.getTrigger(name, group);
		if(trigger!=null){
			JobDetail jobDetail = schedulerService.getJobDetail(trigger.getJobKey().getName(), trigger.getJobKey().getGroup());
			if(jobDetail!=null){
				JobDataMap jobDataMap = jobDetail.getJobDataMap();
				String exception = jobDataMap.getString(SimpleJob.JOB_EXCEPTION_KEY);
				int successCount = jobDataMap.getInt(SimpleJob.JOB_SUCCESS_COUNT_KEY);
				int failCount = jobDataMap.getInt(SimpleJob.JOB_FAIL_KEY);
				@SuppressWarnings("unchecked")
				Map<String, String> parms = (Map<String, String>) jobDataMap.get(SimpleJob.JOB_PARMS);
				model.addAttribute("exception", exception);
				model.addAttribute("successCount", successCount);
				model.addAttribute("failCount", failCount);
				model.addAttribute("parms",parms);
				model.addAttribute("jobClass", jobDetail.getJobClass().getName());
			}
			model.addAttribute("trigger", trigger);
			model.addAttribute("description", trigger.getDescription());
			if(trigger instanceof CronTrigger){
				return "cronTrigger";
			}else if(trigger instanceof SimpleTrigger){
				return "simpleTrigger";
			}
		}
		return "redirect:/triggerList";
	}
	
	@RequestMapping(value="/logTrigger")
	public String logTrigger(@RequestParam String group, @RequestParam String name, HttpServletRequest request, HttpServletResponse response, Model model){
		return "logTrigger";
	}
	
	@RequestMapping(value="/execute", method=RequestMethod.POST)
	public String execute(@RequestParam String jobGroup, @RequestParam String jobName, Model model){
		schedulerService.executeJob(jobName,jobGroup);
		return "redirect:/triggerList";
	}
	
	/**
	 * 测试运行
	 * @param jobName
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/testJob/{className}/")
	public String testJob(@PathVariable String className, Model model, HttpServletRequest request, HttpServletResponse response){
		model.addAttribute("className", className);
		return "testJob";
	}
	
	/**
	 * 配置job
	 * @param jobName
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/configTrigger/{className}/{type}", method=RequestMethod.POST)
	public String configTrigger(@PathVariable String className, @PathVariable String type, Model model, HttpServletRequest request, HttpServletResponse response){
		model.addAttribute("className", className);
		if(type!=null){
			if(type.toUpperCase().equals("CRON")){
				return "configCronTrigger";
			}else if(type.toUpperCase().equals("SIMPLE")){
				return "configSimpleTrigger";
			}
		}
		return "redirect:/triggerList";
	}
	
	/**
	 * 创建触发器
	 * @param jobName
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/createTrigger/{className}/{type}", method=RequestMethod.POST)
	public ViewData createTrigger(@PathVariable String className, @PathVariable String type, ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response){
		
		ViewDataStatus status = ViewDataStatus.SUCCESS;
		String msg = null;
		
		if(className!=null && type!=null){
			try {
				TriggerType triggerType = null;
				if(type.toUpperCase().equals(TriggerType.CRON.name())){
					triggerType = TriggerType.CRON;
				}else if(type.toUpperCase().equals(TriggerType.SIMPLE.name())){
					triggerType = TriggerType.SIMPLE;
				}
				String cronExpression = request.getParameter("cronExpression");
				String triggerName = request.getParameter("triggerName");
				String jobName = request.getParameter("jobName");
				String description = request.getParameter("description");
				String keysStr = request.getParameter("keys");
				String vasStr = request.getParameter("vas");


				//延时
				String startDelayStr = request.getParameter("startDelay");
				Integer startDelay = null;
				if(startDelayStr!=null && !"".equals(startDelayStr)){
					startDelay = Integer.parseInt(startDelayStr);
				}
				
				//循环时间
				String loopDelayStr = request.getParameter("loopDelay");
				Integer loopDelay = null;
				if(loopDelayStr!=null && !"".equals(loopDelayStr)){
					loopDelay = Integer.parseInt(loopDelayStr);
				}
				//执行次数
				String repeatCountStr = request.getParameter("repeatCount");
				Integer repeatCount = null;
				if(repeatCountStr!=null && !"".equals(repeatCountStr)){
					repeatCount = Integer.parseInt(repeatCountStr);
				}
				
				//用户参数
				Map<String, String> parms = new HashMap<String, String>();
				String[] keys = null;
				String[] values = null;
				if(keysStr!=null && vasStr!=null){
					keys = keysStr.split(",");
					values = vasStr.split(",");
				}
				if(keys!=null && values!=null && keys.length>0 && values.length>0){
					for(int i=0; (i<keys.length)&&(i<values.length); i++){
						parms.put(keys[i], values[i]);
					}
				}

				Class<SimpleJob> clazz = (Class<SimpleJob>) Class.forName(className);
				
				schedulerService.createTrigger(triggerType, clazz, triggerName, jobName, cronExpression, startDelay, loopDelay, repeatCount, description, parms);
			} catch (Exception e) {
				status = ViewDataStatus.ERROR;
				msg = e.getMessage();
				e.printStackTrace();
			}
		}else{
			status = ViewDataStatus.ERROR;
			msg = "类名和类型不能为空！";
		}
		return new ViewData(status, msg, null);
	}

	@ResponseBody
	@RequestMapping(value="/listTrigger")
	public PageVo<TriggerVo> listTrigger(@RequestParam Integer page, @RequestParam Integer rows, HttpServletRequest request, HttpServletResponse response){
		PageVo<TriggerVo> pageVo = null;
		if(page!=null && rows!=null){
			pageVo = this.schedulerService.getQrtzTriggers(page, rows);
		}
		return pageVo;
	}
	
	@ResponseBody
	@RequestMapping(value="/listJobClass")
	public PageVo<SimpleJobClassVo> listJobClass(@RequestParam Integer page, @RequestParam Integer rows, HttpServletRequest request, HttpServletResponse response){
		PageVo<SimpleJobClassVo> pageVo = null;
		if(page!=null && rows!=null){
			pageVo = this.schedulerService.getRegisteredJob(page, rows);
		}
		return pageVo;
	}
	
	@RequestMapping(value="/configTriggerRemark")
	public String configTriggerRemark(HttpServletRequest request, HttpServletResponse response, Model model){
		return "configTriggerRemark";
	}
	
	@ResponseBody
	@RequestMapping(value="/updateTrigger", method=RequestMethod.POST)
	public ViewData updateTrigger(HttpServletRequest request, HttpServletResponse response){
		
		String oldTriggerGroup = request.getParameter("oldTriggerGroup");
		String oldTriggerName = request.getParameter("oldTriggerName");
		
		ViewDataStatus status = ViewDataStatus.SUCCESS;
		String msg = null;
		
		if(oldTriggerGroup!=null && oldTriggerName!=null){
			try {
				Trigger trigger = schedulerService.getTrigger(oldTriggerName, oldTriggerGroup);
				String triggerGroup = request.getParameter("triggerGroup");
				String triggerName = request.getParameter("triggerName");
				String description = request.getParameter("description");
				
				if(trigger instanceof CronTriggerImpl){
					String cronExpression = request.getParameter("cronExpression");
					CronTriggerImpl impl = (CronTriggerImpl) trigger;
					impl.setName(triggerName);
					impl.setGroup(triggerGroup);
					impl.setDescription(description);
					impl.setCronExpression(cronExpression);
				}else if(trigger instanceof SimpleTriggerImpl){
					//循环时间
					String loopDelayStr = request.getParameter("loopDelay");
					Integer repeatInterval = null;
					if(loopDelayStr!=null && !"".equals(loopDelayStr)){
						repeatInterval = Integer.parseInt(loopDelayStr);
					}
					//执行次数
					String repeatCountStr = request.getParameter("repeatCount");
					Integer repeatCount = null;
					if(repeatCountStr!=null && !"".equals(repeatCountStr)){
						repeatCount = Integer.parseInt(repeatCountStr);
					}
					SimpleTriggerImpl impl = (SimpleTriggerImpl) trigger;
					impl.setName(triggerName);
					impl.setGroup(triggerGroup);
					impl.setDescription(description);
					impl.setRepeatInterval(repeatInterval);
					impl.setRepeatCount(repeatCount);
				}
				
				//用户参数
				String keysStr = request.getParameter("keys");
				String vasStr = request.getParameter("vas");
				Map<String, String> parms = new HashMap<String, String>();
				String[] keys = null;
				String[] values = null;
				if(keysStr!=null && vasStr!=null){
					keys = keysStr.split(",");
					values = vasStr.split(",");
				}
				if(keys!=null && values!=null && keys.length>0 && values.length>0){
					for(int i=0; (i<keys.length)&&(i<values.length); i++){
						parms.put(keys[i], values[i]);
					}
				}
				
				String className = request.getParameter("className");
				String jobName = request.getParameter("jobName");
				
				@SuppressWarnings("unchecked")
				Class<SimpleJob> clazz = (Class<SimpleJob>) Class.forName(className);
				TriggerKey triggerKey = new TriggerKey(oldTriggerName, oldTriggerGroup);
				schedulerService.updateTrigger(triggerKey, trigger.getJobKey(), trigger, clazz, jobName, parms);
			} catch (Exception e) {
				status = ViewDataStatus.ERROR;
				msg = e.getMessage();
				e.printStackTrace();
			}
		}else{
			status = ViewDataStatus.ERROR;
			msg = "类名和类型不能为空！";
		}
		return new ViewData(status, msg, null);
	}
	
	@ResponseBody
	@RequestMapping(value="/getTrigger", method=RequestMethod.POST)
	public ViewData getTrigger(HttpServletRequest request, HttpServletResponse response){
		String triggerGroup = request.getParameter("triggerGroup");
		String triggerName = request.getParameter("triggerName");
		ViewDataStatus status = ViewDataStatus.SUCCESS;
		String msg = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if(triggerGroup!=null && triggerName!=null){
			Trigger trigger = schedulerService.getTrigger(triggerName, triggerGroup);
			map.put("triggerName", trigger.getKey().getName());
			map.put("triggerGroup", trigger.getKey().getGroup());
			map.put("jobName", trigger.getJobKey().getName());
			if(trigger instanceof CronTriggerImpl){
				CronTriggerImpl imp = (CronTriggerImpl) trigger;
				map.put("cronExpression", imp.getCronExpression());
			}else if(trigger instanceof SimpleTriggerImpl){
				SimpleTriggerImpl imp = (SimpleTriggerImpl) trigger;
				map.put("repeatInterval", imp.getRepeatInterval()/1000);
				map.put("repeatCount", imp.getRepeatCount());
			}
			if(trigger!=null){
				JobDetail jobDetail = schedulerService.getJobDetail(trigger.getJobKey().getName(), trigger.getJobKey().getGroup());
				JobDataMap jobDataMap = jobDetail.getJobDataMap();
				String exception = jobDataMap.getString(SimpleJob.JOB_EXCEPTION_KEY);
				@SuppressWarnings("unchecked")
				Map<String, String> parms = (Map<String, String>) jobDataMap.get(SimpleJob.JOB_PARMS);
				map.put("description", trigger.getDescription());
				map.put("exception", exception);
				if(parms!=null){
					JSONArray array = new JSONArray();
					Iterator<Entry<String, String>> it = parms.entrySet().iterator();
					while (it.hasNext()) {
						Entry<String, String> e = it.next();
						JSONObject obj = new JSONObject();
						try {
							obj.put("key", e.getKey());
							obj.put("va", e.getValue());
							array.put(obj);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					}
					String json = "{total:" + parms.size() + ",rows:" + array.toString() + "}";
					map.put("parms",json);
				}
				map.put("jobClass", jobDetail.getJobClass().getName());
			}
		}else{
			status = ViewDataStatus.ERROR;
			msg = "类名和类型不能为空！";
		}
		return new ViewData(status, msg, map);
	}
	
}
