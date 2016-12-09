package com.chinadrtv.erp.task.quartz.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.task.enums.TriggerState;
import com.chinadrtv.erp.task.vo.PageVo;
import com.chinadrtv.erp.task.vo.TriggerVo;

@Repository("quartzDao")
public class QuartzDao {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private DataSource quartzDS;

	public PageVo<TriggerVo> getQrtzTriggers(int pageNo, int pageSize) {
		
		String sql = "SELECT QT.*, ROWNUM RN FROM ( SELECT * FROM QRTZ_TRIGGERS ORDER BY TRIGGER_NAME ) QT";
		
		//总数
		List<Map<String, Object>> countR = getJdbcTemplate().queryForList("SELECT COUNT(1) AS COUNT FROM ( " + sql + " )");
		BigDecimal count = (BigDecimal) countR.get(0).get("COUNT");
		
		//分页取数据
		List<Map<String, Object>> results = getJdbcTemplate().queryForList("SELECT * FROM ( " + sql + " ) T WHERE T.RN>? AND T.RN<=?", new Object[]{(pageNo-1)*pageSize,pageNo*pageSize});
		List<TriggerVo> list = new ArrayList<TriggerVo>();
		
		for(Map<String, Object> trigger : results){
			TriggerVo triggerVo = new TriggerVo();

			if(trigger.containsKey("end_time") && trigger.get("end_time")!=null){
				BigDecimal endTimeValue = (BigDecimal) trigger.get("end_time");
				Date endTime = new Date(endTimeValue.longValue());
				if(endTime.getTime()>0){
					triggerVo.setEndTime(sdf.format(endTime));	
				}
			}
			
			if(trigger.containsKey("job_group") && trigger.get("job_group")!=null){
				String jobGroup = (String) trigger.get("job_group");
				triggerVo.setJobGroup(jobGroup);
			}
			
			if(trigger.containsKey("job_name") && trigger.get("job_name")!=null){
				String jobName = (String) trigger.get("job_name");
				triggerVo.setJobName(jobName);
			}
			
			if(trigger.containsKey("next_fire_time") && trigger.get("next_fire_time")!=null){
				BigDecimal nextFireTimeValue = (BigDecimal) trigger.get("next_fire_time");
				Date nextFireTime = new Date(nextFireTimeValue.longValue());
				triggerVo.setNextFireTime(sdf.format(nextFireTime));
			}
			
			if(trigger.containsKey("prev_fire_time") && trigger.get("prev_fire_time")!=null){
				BigDecimal prevFireTimeValue = (BigDecimal) trigger.get("prev_fire_time");
				Date prevFireTime = new Date(prevFireTimeValue.longValue());
				triggerVo.setPrevFireTime(sdf.format(prevFireTime));
			}
			
			if(trigger.containsKey("priority") && trigger.get("priority")!=null){
				BigDecimal priority = (BigDecimal) trigger.get("priority");
				triggerVo.setPriority(priority.intValue());
			}
			
			if(trigger.containsKey("start_time") && trigger.get("start_time")!=null){
				BigDecimal startTimeValue = (BigDecimal) trigger.get("start_time");
				Date startTime = new Date(startTimeValue.longValue());
				triggerVo.setStartTime(sdf.format(startTime));
			}
			
			if(trigger.containsKey("trigger_group") && trigger.get("trigger_group")!=null){
				String triggerGroup = (String) trigger.get("trigger_group");
				triggerVo.setTriggerGroup(triggerGroup);
			}
			
			if(trigger.containsKey("trigger_name") && trigger.get("trigger_name")!=null){
				String triggerName = (String) trigger.get("trigger_name");
				triggerVo.setTriggerName(triggerName);
			}
			
			if(trigger.containsKey("trigger_state") && trigger.get("trigger_state")!=null){
				String triggerState = (String) trigger.get("trigger_state");
				
				if(TriggerState.ACQUIRED.name().equals(triggerState)){
					triggerState = TriggerState.ACQUIRED.getDescription();
				}else if(TriggerState.PAUSED.name().equals(triggerState)){
					triggerState = TriggerState.PAUSED.getDescription();
				}else{
					triggerState = TriggerState.WAITING.getDescription();
				}
				triggerVo.setTriggerState(triggerState);
			}
			
			if(trigger.containsKey("trigger_type") && trigger.get("trigger_type")!=null){
				String triggerType = (String) trigger.get("trigger_type");
				triggerVo.setTriggerType(triggerType);
			}
			if(trigger.containsKey("description") && trigger.get("description")!=null){
				String description = (String) trigger.get("description");
				triggerVo.setDescription(description);
			}
			
			list.add(triggerVo);
		}
		PageVo<TriggerVo> page = new PageVo<TriggerVo>(list,count.intValue());
		return page;
	}

	private JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(this.quartzDS);
	}
	
}
