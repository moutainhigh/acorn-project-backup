package com.chinadrtv.erp.marketing.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.dao.QuartzDao;
import com.chinadrtv.erp.marketing.dto.QuartzTriggerDto;
import com.chinadrtv.erp.util.StringUtil;

@Repository("quartzDao")
@SuppressWarnings("unchecked")
public class QuartzDaoImpl implements QuartzDao {

	private DataSource dataSource;

	@Autowired
	public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<QuartzTriggerDto> getQrtzTriggers(
			QuartzTriggerDto quartzTriggerDto, DataGridModel dataGridModel) {
		int begin = dataGridModel.getRows() * (dataGridModel.getPage() - 1);
		int end = Math.min(begin + dataGridModel.getRows(),
				dataGridModel.getCount()) + 1;
		List<Map<String, Object>> results = getJdbcTemplate()
				.queryForList(
						"select * from (select TRIGGER_NAME,TRIGGER_GROUP,JOB_NAME,"
								+ " JOB_GROUP,IS_VOLATILE,DESCRIPTION,NEXT_FIRE_TIME,PREV_FIRE_TIME,"
								+ " PRIORITY,TRIGGER_STATE,TRIGGER_TYPE,START_TIME,END_TIME,CALENDAR_NAME,"
								+ " MISFIRE_INSTR ,rownum as nums from QRTZ_TRIGGERS  order by start_time) "
								+ " where 1=1 and nums > ? and nums < ?  ",
						new Object[] { begin, end });
		long val = 0;
		String temp = null;
		List<QuartzTriggerDto> result = new ArrayList<QuartzTriggerDto>();
		QuartzTriggerDto triggerDto = null;
		for (Map<String, Object> map : results) {
			temp = MapUtils.getString(map, "trigger_name");
			triggerDto = new QuartzTriggerDto();
			if (StringUtils.indexOf(temp, "&") != -1) {
				triggerDto.setTriggerName(StringUtils
						.substringBefore(temp, "&"));
				map.put("display_name", StringUtils.substringBefore(temp, "&"));
			} else {
				triggerDto.setTriggerName(temp);
			}
			temp = MapUtils.getString(map, "trigger_group");
			if (!StringUtil.isNullOrBank(temp)) {
				triggerDto.setTriggerGroup(temp);
			}

			val = MapUtils.getLongValue(map, "priority");
			if (val > 0) {
				triggerDto.setPriority(val);
			}
			val = MapUtils.getLongValue(map, "next_fire_time");
			if (val > 0) {
				triggerDto.setNextFireTime(DateFormatUtils.format(val,
						"yyyy-MM-dd HH:mm:ss"));
			}

			val = MapUtils.getLongValue(map, "prev_fire_time");
			if (val > 0) {
				triggerDto.setPrevFireTime(DateFormatUtils.format(val,
						"yyyy-MM-dd HH:mm:ss"));
			}

			val = MapUtils.getLongValue(map, "start_time");
			if (val > 0) {
				triggerDto.setStartTime(DateFormatUtils.format(val,
						"yyyy-MM-dd HH:mm:ss"));
			}

			val = MapUtils.getLongValue(map, "end_time");
			if (val > 0) {
				triggerDto.setEndTime(DateFormatUtils.format(val,
						"yyyy-MM-dd HH:mm:ss"));
			}
			triggerDto.setTriggerState(Constants.status.get(MapUtils.getString(
					map, "trigger_state")));
			result.add(triggerDto);
		}

		return result;
	}

	private JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(this.dataSource);
	}

	public Long getQrtzTriggersCount(QuartzTriggerDto quartzTriggerDto) {
		// TODO Auto-generated method stub

		String sql = " select count(*) from  QRTZ_TRIGGERS ";

		return getJdbcTemplate().queryForLong(sql);
	}
}
