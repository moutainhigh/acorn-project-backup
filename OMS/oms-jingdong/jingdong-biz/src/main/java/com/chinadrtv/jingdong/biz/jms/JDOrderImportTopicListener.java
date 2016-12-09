package com.chinadrtv.jingdong.biz.jms;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.jingdong.model.JingdongOrderConfig;
import com.chinadrtv.jingdong.service.OrderImportJDService;
import com.chinadrtv.runtime.jms.receive.JmsListener;

/**
 * Created with IntelliJ IDEA. 
 * User: liukuan Date: 13-11-7 
 * Time: 下午4:53 To
 * change this template use File | Settings | File Templates. 京东前置订单导入
 */
@SuppressWarnings("rawtypes")
public class JDOrderImportTopicListener extends JmsListener {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JDOrderImportTopicListener.class);

	public JDOrderImportTopicListener() {
		logger.info("JDOrderImportTopicListener is created.");
	}

	private AtomicBoolean isRun = new AtomicBoolean(false);

	@Autowired
	private OrderImportJDService orderImportJDService;

	private List<JingdongOrderConfig> jingdongOrderConfigList;

	private List<JingdongOrderConfig> fbpList;

	@Override
	public void messageHandler(Object msg) throws Exception {
		// 如果有在处理，那么直接忽略此消息
		if (isRun.compareAndSet(false, true)) {
			
			logger.info("begin order import");
			
			Date startDate, endDate;
			endDate = new Date();
			startDate = getAddDay(endDate, Calendar.DATE, -1);
			// 定时消息处理
			try {
				logger.info("process get order and insert pretrade");

				orderImportJDService.importPreOrdersInfo(jingdongOrderConfigList, startDate, endDate);

				// fetch FBP orders
				orderImportJDService.importFBPOrder(fbpList, startDate, endDate);

			} catch (Exception exp) {
				logger.error("import jingdong order error:", exp);
			} finally {
				isRun.set(false);
				logger.info("end import");
			}
		} else {
			logger.error("import is running!!!");
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

	public List<JingdongOrderConfig> getJingdongOrderConfigList() {
		return jingdongOrderConfigList;
	}

	public void setJingdongOrderConfigList(List<JingdongOrderConfig> jingdongOrderConfigList) {
		this.jingdongOrderConfigList = jingdongOrderConfigList;
	}

	public List<JingdongOrderConfig> getFbpList() {
		return fbpList;
	}

	public void setFbpList(List<JingdongOrderConfig> fbpList) {
		this.fbpList = fbpList;
	}
}
