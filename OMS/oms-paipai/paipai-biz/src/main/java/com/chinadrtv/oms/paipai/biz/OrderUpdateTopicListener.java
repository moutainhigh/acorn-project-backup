package com.chinadrtv.oms.paipai.biz;

import java.util.ArrayList;
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
public class OrderUpdateTopicListener extends JmsListener {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderUpdateTopicListener.class);

	public OrderUpdateTopicListener() {
		logger.info("OrderUpdateTopicListener is created.");
	}

	private AtomicBoolean isRun = new AtomicBoolean(false);

	@Autowired
	private OrderImportService orderImportService;

	private List<OrderConfig> configList = new ArrayList<OrderConfig>();

	@Override
	public void messageHandler(Object msg) throws Exception {
		
		if (isRun.compareAndSet(false, true)) {
			
			try {
				orderImportService.updateGroupBuyingPreTrade(configList);
			} catch (Exception exp) {
				logger.error("Update PaiPai order error:", exp);
			} finally {
				isRun.set(false);
				logger.info("End update");
			}
		} else {
			logger.error("Update action is running!");
		}
	}


	public List<OrderConfig> getConfigList() {
		return configList;
	}

	public void setConfigList(List<OrderConfig> configList) {
		this.configList = configList;
	}


}
