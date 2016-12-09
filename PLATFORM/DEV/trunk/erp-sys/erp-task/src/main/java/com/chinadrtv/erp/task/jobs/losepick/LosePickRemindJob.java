package com.chinadrtv.erp.task.jobs.losepick;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.chinadrtv.erp.task.core.job.Task;
import com.chinadrtv.erp.task.core.scheduler.SimpleJob;
import com.chinadrtv.erp.task.service.CommonService;
import com.chinadrtv.erp.task.util.MailBean;
import com.ibm.icu.text.SimpleDateFormat;

@Task(name="LosePickRemindJob", description="漏分拣订单邮件提醒")
public class LosePickRemindJob extends SimpleJob{

	private CommonService commonService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
    @Override
	public void init(JobExecutionContext context) {
    	super.init(context);
    	if(applicationContext!=null){
    		commonService = (CommonService) applicationContext.getBean(CommonService.class);
    	}
	}
    
	@Override
	public void doExecute(JobExecutionContext context) throws JobExecutionException {
		List<Object[]> losePickOrders = commonService.queryLosePickOrder(1,1000);
		if(losePickOrders!=null && losePickOrders.size()>0){
			
			//创建邮件内容
			String content = "截止到" + sdf.format(new Date()) + "，漏分拣的订单列表：<br/><table border='1'>";
			content += "<tr> <td>序号</td> <td>订单号</td> <td>订单生成时间</td> <td>订单状态</td> <td>座席工号</td> <td>座席组</td> </tr>";
			for(int i=0; i<losePickOrders.size(); i++){
				Object[] obj = losePickOrders.get(i);
				int no = i+1;
				content += "<tr> <td>" + no + "</td><td>" + obj[0] + "</td> <td>" + obj[1] + "</td> <td>" + obj[2] + "</td> <td>" + obj[3] + "</td> <td>" + obj[4] + "</td> </tr>";
			}
			content += "</table>";
			
			//获取接收邮件列表
			Set<String> emails = new HashSet<String>();
			String emailStr = taskConfigure.getLosePickRemindEmails();
			if(emailStr!=null && !"".equals(emailStr)){
				String[] es = emailStr.split(EMAIL_SUFFIX);
				for(String e : es){
					e = e.trim();
					if(!"".equals(e)){
						emails.add(e);
					}
				}
				//发送邮件
				sendEmail(emails, content);
			}else{
				throw new JobExecutionException("漏分拣订单邮件提醒接收邮箱列表不能为空");
			}
			
		}
		
	}
	
	private void sendEmail(Set<String> emails, String content){
		if(emails.size()>0){
	        //创建邮件  
	        MailBean mailBean = new MailBean();  
	        mailBean.setFrom(mailConfigure.getMailFrom());  
	        mailBean.setSubject("漏分拣订单邮件提醒");  
	        mailBean.setToEmails(emails.toArray(new String[emails.size()]));  
	        mailBean.setTemplate(content);  
	        //发送邮件  
	        try {  
	            mailUtil.send(mailBean);  
	        } catch (MessagingException e) {  
	            e.printStackTrace();  
	        }  
		}	
	}
	
}
