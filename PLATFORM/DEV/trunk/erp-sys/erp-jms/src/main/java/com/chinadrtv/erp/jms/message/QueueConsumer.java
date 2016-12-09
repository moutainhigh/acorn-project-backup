package com.chinadrtv.erp.jms.message;

import java.util.List;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;

import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.service.ObContactService;
import com.chinadrtv.erp.message.AssignMessage;
import com.chinadrtv.erp.model.agent.ObContact;

/**
 * Date: 2008-8-28
 * Time: 17:10:34
 */
public class QueueConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueConsumer.class);

	private JmsTemplate template;
	
	@Autowired
	private ObContactService obContactService;
	
	@Value("${assign_limit}")
	private Integer limit;
	
	@Autowired
	private QueueSizeCounter queueCounter;
    /**
	 * @param template the template to set
	 */
	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	/**
	 * 
	* @Description: 同步监听客户端消息
	* @param msg
	* @return void
	* @throws
	 */
	public void receive(Message msg) {
    	
    	if (msg instanceof ObjectMessage) {
    		ObjectMessage objMsg = (ObjectMessage)msg;
    		AssignMessage assignMsg = null;
    		Session session = null;
    		
    		MessageProducer replyProducer = null;
    		ObjectMessage response = null;
			try {
				assignMsg = (AssignMessage)objMsg.getObject();
				
				long start = System.currentTimeMillis();
				long queueSize = queueCounter.getQueueSize(assignMsg.getJobId());
				long end = System.currentTimeMillis();
				
				logger.info("1.start query queue;queueSize="+queueSize);
				
				if(queueSize<=100){
					start = System.currentTimeMillis();
					
					session = template.getConnectionFactory().createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
					logger.info("2.create session 查询queue="+assignMsg.getJobId()+":可取数="+queueSize+";用时="+(end-start)+";queueSize="+queueSize);
					if(assignMsg!=null){
						
						logger.info("3.start query 查询queue="+assignMsg.getJobId()+":可取数="+queueSize+";用时="+(end-start)+";queueSize="+queueSize);
						List<ObContact> resultList = obContactService.query(assignMsg.getJobId(), limit);
						
						logger.info("3-1.start createQueue;resultList="+resultList.size());
						Destination queue = session.createQueue(assignMsg.getJobId());
						logger.info("3-2.start createQueue;resultList="+resultList.size());
						MessageProducer producer = session.createProducer(queue);
						producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
						
						logger.info("4.create queue and producet");
						
						ObjectMessage objectMessage = null;
						AssignMessage message = null;
						//这里添加补充数的逻辑
						if(!resultList.isEmpty()){
							logger.info("5.resultList is not empty");
							for(ObContact contact :resultList){
								
								message = new AssignMessage();
								message.setJobId(contact.getPd_jobid());
								message.setContactId(contact.getContactid());
								message.setStartDate(contact.getStartdate());
								message.setEndDate(contact.getEnddate());
								message.setPdCustomerId(contact.getPd_customerid());
								message.setBatchId(contact.getBatchid());
								message.setQueueId(contact.getQueueid());
								message.setCampaignId(contact.getCampaignId());
								message.setSource(contact.getDatasrcid());
								objectMessage = session.createObjectMessage();
								objectMessage.setObject(message);
								producer.send(objectMessage);
							}
							assignMsg.setStatus(Constants.OBCONTACT_GET_SUCCESS);
						}else{
							logger.info("6.resultList is empty");
							assignMsg.setStatus(Constants.OBCONTACT_GET_NONE);
							assignMsg.setMessge("数据库中已经没有相关数据可取");
						}
						
					}
//					else{
//						assignMsg = new AssignMessage();
//						assignMsg.setStatus(Constants.OBCONTACT_GET_ERROR);
//						assignMsg.setMessge("接收到的消息对象为null");
//					}
					
					 end = System.currentTimeMillis();
					 
					 logger.info("查询queue="+assignMsg.getJobId()+":可取数="+queueSize+";补充数据用时="+(end-start));
				}else{
					logger.info("7.queueSize>100");
					assignMsg.setStatus(Constants.OBCONTACT_GET_SUCCESS);
				}
				
				
				replyProducer = session.createProducer(null);
				replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				response = session.createObjectMessage();
				response.setObject(assignMsg);
				response.setJMSCorrelationID(msg.getJMSCorrelationID());
				replyProducer.send(msg.getJMSReplyTo(), response);
				logger.info("8.reply send to:"+msg.getJMSReplyTo()+";JMSCorrelationID="+msg.getJMSCorrelationID());
			} catch (JMSException e) {
				logger.info("9.error ");
				e.printStackTrace();
			}
			
    		System.out.println("********************33******************* Queue : "+assignMsg.getJobId() );
    		
    	}
		
	}
    
	
	/**
	 * 
	* @Description: 异步监听客户端消息
	* @param msg
	* @return void
	* @throws
	 */
	public void asynchReceive(Message msg) {
    	
    	if (msg instanceof ObjectMessage) {
    		ObjectMessage objMsg = (ObjectMessage)msg;
    		AssignMessage assignMsg = null;
    		Session session = null;
    		
			try {
				assignMsg = (AssignMessage)objMsg.getObject();
				
				long start = System.currentTimeMillis();
				long queueSize = queueCounter.getQueueSize(assignMsg.getJobId());
				long end = System.currentTimeMillis();
				
				logger.info("异步监听===查询queue="+assignMsg.getJobId()+":可取数="+queueSize+";用时="+(end-start));
				
				if(queueSize<=100){
					start = System.currentTimeMillis();
					session = template.getConnectionFactory().createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
					
					if(assignMsg!=null){
						List<ObContact> resultList = obContactService.query(assignMsg.getJobId(), limit);
						
						Destination queue = session.createQueue(assignMsg.getJobId());
						
						MessageProducer producer = session.createProducer(queue);
						producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
						
						ObjectMessage objectMessage = null;
						AssignMessage message = null;
						//这里添加补充数的逻辑
						if(!resultList.isEmpty()){
							for(ObContact contact :resultList){
								
								message = new AssignMessage();
								message.setJobId(contact.getPd_jobid());
								message.setContactId(contact.getContactid());
								message.setStartDate(contact.getStartdate());
								message.setEndDate(contact.getEnddate());
								message.setPdCustomerId(contact.getPd_customerid());
								message.setBatchId(contact.getBatchid());
								message.setQueueId(contact.getQueueid());
								message.setCampaignId(contact.getCampaignId());
								message.setSource(contact.getDatasrcid());
								
								objectMessage = session.createObjectMessage();
								objectMessage.setObject(message);
								producer.send(objectMessage);
							}
							assignMsg.setStatus(Constants.OBCONTACT_GET_SUCCESS);
						}else{
							assignMsg.setStatus(Constants.OBCONTACT_GET_NONE);
							assignMsg.setMessge("异步监听===数据库中已经没有相关数据可取");
						}
						
					}
//					else{
//						assignMsg = new AssignMessage();
//						assignMsg.setStatus(Constants.OBCONTACT_GET_ERROR);
//						assignMsg.setMessge("接收到的消息对象为null");
//					}
					
					 end = System.currentTimeMillis();
					 
					 logger.info("异步监听===查询queue="+assignMsg.getJobId()+":可取数="+queueSize+";补充数据用时="+(end-start));
				}
				
				
			} catch (JMSException e) {
				e.printStackTrace();
			}
    	}
		
	}
	
}
