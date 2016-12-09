package com.chinadrtv.service.oms.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.common.context.SystemContext;
import com.chinadrtv.oms.dao.LosePickMailDao;
import com.chinadrtv.service.oms.LosePickMailService;
import com.chinadrtv.util.MailBean;
import com.chinadrtv.util.MailUtil;

@Service
public class LosePickMailServiceImpl implements LosePickMailService{
    
    @Autowired
    private LosePickMailDao losePickMailDao;
    @Autowired
    private SystemContext systemContext;
    @Autowired
    private MailUtil mailUtil;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Override
	public void sendMailNotice() {
		List<Map<String, Object>> losePickOrders = losePickMailDao.queryLosePickOrder();
		
		if(losePickOrders!=null && losePickOrders.size()>0){
			
			//创建邮件内容
			String content = "截止到" + sdf.format(new Date()) + "，漏分拣的订单列表：<br/><table border='1'>";
			content += "<tr> <td>序号</td> <td>订单号</td> <td>订单生成时间</td> <td>订单状态</td> <td>座席工号</td> <td>座席组</td> </tr>";
			for(int i=0; i<losePickOrders.size(); i++){
			    Map<String, Object> orderMap = losePickOrders.get(i);
				int no = i+1;
				content += "<tr> <td>" + no + "</td><td>" + orderMap.get("ORDERID") + "</td> <td>" + orderMap.get("CRDT") 
				        + "</td> <td>" + orderMap.get("DSC") + "</td> <td>" + orderMap.get("CRUSR") + "</td> <td>" 
				        + orderMap.get("GRPNAME") + "</td> </tr>";
			}
			content += "</table>";
			
			//获取接收邮件列表
			Set<String> emails = new HashSet<String>();
			
			@SuppressWarnings("static-access")
            String emailStr = systemContext.get("lose_pick_remind_emails");
			if(emailStr!=null && !"".equals(emailStr)){
				String[] es = emailStr.split(";");
				for(String e : es){
					e = e.trim();
					if(!"".equals(e)){
						emails.add(e);
					}
				}
				//发送邮件
				sendEmail(emails, content);
			}else{
				throw new RuntimeException("漏分拣订单邮件提醒接收邮箱列表不能为空");
			}
			
		}
		
	}
	
	@SuppressWarnings("static-access")
    private void sendEmail(Set<String> emails, String content){
		if(emails.size()>0){
	        //创建邮件  
	        MailBean mailBean = new MailBean();  
	        mailBean.setFrom(systemContext.get("mail.from"));  
	        mailBean.setSubject("漏分拣订单邮件提醒");  
	        mailBean.setToEmails(emails.toArray(new String[emails.size()]));  
	        mailBean.setTemplate(content);
	        mailBean.setData(new HashMap<String, Object>());
	        //发送邮件  
	        try {  
	            mailUtil.send(mailBean);  
	        } catch (MessagingException e) {  
	            e.printStackTrace();  
	        }  
		}	
	}
	
}
