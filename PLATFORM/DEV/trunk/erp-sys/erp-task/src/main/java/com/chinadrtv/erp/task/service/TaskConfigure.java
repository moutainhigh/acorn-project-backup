package com.chinadrtv.erp.task.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 定时任务中需要用到的参数
 * @author zhangguosheng
 */
@Component
public class TaskConfigure {

	//漏分拣订单邮件提醒接收邮箱列表
	@Value("${LOSE_PICK_REMIND_EMAILS}")
	private String losePickRemindEmails;
	
	//定时任务发生异常通知邮箱列表
	@Value("${EXCEPTION_REMIND_EMAILS}")
	private String exceptionRemindEmails;
	
	//送货公司负责人和处理人的邮箱都为空的替代邮箱列表
	@Value("${ENTITY_EMAIL_VACANCY}")
	private String entityEmailVacancy;

	public String getLosePickRemindEmails() {
		return losePickRemindEmails;
	}

	public void setLosePickRemindEmails(String losePickRemindEmails) {
		this.losePickRemindEmails = losePickRemindEmails;
	}

	public String getExceptionRemindEmails() {
		return exceptionRemindEmails;
	}

	public void setExceptionRemindEmails(String exceptionRemindEmails) {
		this.exceptionRemindEmails = exceptionRemindEmails;
	}

	public String getEntityEmailVacancy() {
		return entityEmailVacancy;
	}

	public void setEntityEmailVacancy(String entityEmailVacancy) {
		this.entityEmailVacancy = entityEmailVacancy;
	}

}
