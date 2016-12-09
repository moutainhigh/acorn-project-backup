package com.chinadrtv.erp.task.jobs.margin;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.chinadrtv.erp.task.core.job.Task;
import com.chinadrtv.erp.task.core.scheduler.SimpleJob;
import com.chinadrtv.erp.task.service.CommonService;
import com.chinadrtv.erp.task.util.MailBean;

/**
 * 邮件提醒,保证额度超标
 * @author zhangguosheng
 */
@Task(name="MarginExceededJob", description="保证额度超标邮件提醒")
public class MarginExceededJob extends SimpleJob{
	
	private final static String EMAIL_SUFFIX = ";";

	private CommonService commonService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
    @Override
	public void init(JobExecutionContext context) {
    	super.init(context);
    	if(applicationContext!=null){
    		commonService = (CommonService) applicationContext.getBean(CommonService.class);
    	}
	}

	@Override
	public void doExecute(JobExecutionContext context) throws JobExecutionException {

		List<Object[]> list = commonService.queryMarginExceededCompanyPayment();
		for(Object[] obj : list){
			if(obj!=null){
				String name = (String) obj[0];
				BigDecimal actuclRiskFactor = (BigDecimal) obj[1];
				if(actuclRiskFactor!=null){
					Set<String> emails = new HashSet<String>();
					String riskEmail = (String) obj[2];
					if(riskEmail!=null && !"".equals(riskEmail)){
						String[] es = riskEmail.split(EMAIL_SUFFIX);
						for(String e : es){
							e = e.trim();
							if(!"".equals(e)){
								emails.add(e);
							}
						}
					}
					
					String riskOptsEmail = (String) obj[3];
					if(riskOptsEmail!=null && !"".equals(riskOptsEmail)){
						String[] es = riskOptsEmail.split(EMAIL_SUFFIX);
						for(String e : es){
							e = e.trim();
							if(!"".equals(e)){
								emails.add(e);
							}
						}
					}
					
					//如果该送货公司邮箱列表为空，使用缺省邮箱
					if(emails.size()==0){
						String[] es = taskConfigure.getEntityEmailVacancy().split(EMAIL_SUFFIX);
						for(String e : es){
							e = e.trim();
							if(!"".equals(e)){
								emails.add(e);
							}
						}
					}
					
					if(emails.size()>0){
						String day = sdf.format(new Date());
				        //创建邮件  
				        MailBean mailBean = new MailBean();  
				        mailBean.setFrom(mailConfigure.getMailFrom());  
				        mailBean.setSubject("送货公司应收款风险预警");  
				        mailBean.setToEmails(emails.toArray(new String[emails.size()]));  
				        mailBean.setTemplate("截止${day}，\n ${name}风险系数为${actuclRiskFactor},已超过风险控制上限，请立即采取措施，降低风险系数！");  
				        Map<String, Object> map = new HashMap<String, Object>();
				        map.put("day", day); 
				        map.put("name", name); 
				        map.put("actuclRiskFactor", actuclRiskFactor.intValue());  
				        mailBean.setData(map);  
				          
				        //发送邮件  
				        try {  
				            mailUtil.send(mailBean);  
				        } catch (MessagingException e) {  
				            e.printStackTrace();  
				        }  
					}
					
				}
			}
		}
	}


}
