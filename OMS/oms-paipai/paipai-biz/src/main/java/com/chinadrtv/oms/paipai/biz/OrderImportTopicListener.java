package com.chinadrtv.oms.paipai.biz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.oms.paipai.dto.OrderConfig;
import com.chinadrtv.oms.paipai.service.OrderImportService;
import com.chinadrtv.runtime.jms.receive.JmsListener;

/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014-11-12 下午1:48:59 
 *
 */
@SuppressWarnings("rawtypes")
public class OrderImportTopicListener extends JmsListener {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderImportTopicListener.class);

	public OrderImportTopicListener() {
		logger.info("OrderImportTopicListener is created.");
	}

	private AtomicBoolean isRun = new AtomicBoolean(false);

	@Autowired
	private OrderImportService orderImportService;

	private List<OrderConfig> configList = new ArrayList<OrderConfig>();

	@Override
	public void messageHandler(Object msg) throws Exception {
		
		if (isRun.compareAndSet(false, true)) {
			logger.info("begin order import");
			
			Date startDate, endDate;
			endDate = new Date();
			startDate = getAddDay(endDate, Calendar.DATE, -1);
			
			try {
				logger.info("process get order and insert pretrade");

				orderImportService.importPreTrade(configList, startDate, endDate);
			} catch (Exception exp) {
				logger.error("import order error:", exp);
			} finally {
				isRun.set(false);
				logger.info("end import");
			}
		} else {
			logger.error("import is running!");
		}
	}

	/**
	 * 转换当前日期的方法
	 * @param d   当前日期
	 * @param field  参数类型
	 * @param amount  参数区间
	 * @return
	 */
	private Date getAddDay(Date d, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(field, amount);
		return cal.getTime();
	}

	public List<OrderConfig> getConfigList() {
		return configList;
	}

	public void setConfigList(List<OrderConfig> configList) {
		this.configList = configList;
	}


}
