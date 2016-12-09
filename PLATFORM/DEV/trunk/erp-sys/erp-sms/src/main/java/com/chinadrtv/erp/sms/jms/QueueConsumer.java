package com.chinadrtv.erp.sms.jms;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.chinadrtv.erp.smsapi.service.SmsSendStatusService;
import com.chinadrtv.sms.model.AssignXml;

/**
 * Date: 2008-8-28 Time: 17:10:34
 */
public class QueueConsumer implements MessageListener {

	private static final Logger logger = LoggerFactory
			.getLogger(QueueConsumer.class);

	private JmsTemplate template;
	@Autowired
	private SmsSendStatusService smsSendStatusService;

	/**
	 * 
	 * @Description: 监听客户端消息
	 * @param xml
	 * @return void
	 * @throws
	 */
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			if (message instanceof ObjectMessage) {
				ObjectMessage objMsg = (ObjectMessage) message;
				AssignXml assignXml = (AssignXml) objMsg.getObject();
				logger.info("执行smsSendStatusService" + assignXml.getXml());
				smsSendStatusService.groupSendStatusByZip(assignXml.getXml());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("失败" + e);
		}

	}

	/**
	 * @return the template
	 */
	public JmsTemplate getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

}
