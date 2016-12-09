package com.chinadrtv.erp.task.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * spring发送mail工具
 */
public class MailUtil {

	private final Log logger = LogFactory.getLog(getClass());

	private JavaMailSender javaMailSender;
	private VelocityEngine velocityEngine; // 模板解析

	/**
	 * @param mailBean
	 * @return
	 * @throws MessagingException
	 */
	public boolean send(MailBean mailBean) throws MessagingException {
		MimeMessage msg = createMimeMessage(mailBean);
		if (msg == null) {
			return false;
		}
		this.sendMail(msg, mailBean);
		return true;
	}

	private void sendMail(MimeMessage msg, MailBean mailBean) {
		javaMailSender.send(msg);
		logger.info("$$$ Send mail Subject:" + mailBean.getSubject() + ", TO:" + arrayToStr(mailBean.getToEmails()));
	}

	/*
	 * 记日记用的
	 */
	private String arrayToStr(String[] array) {
		if (array == null || array.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (String str : array) {
			sb.append(str + " , ");
		}
		return sb.toString();
	}

	/*
	 * 根据 mailBean 创建 MimeMessage
	 */
	private MimeMessage createMimeMessage(MailBean mailBean) throws MessagingException {
		if (!checkMailBean(mailBean)) {
			return null;
		}
		String text = getMessage(mailBean);
		if (text == null) {
			logger.warn("@@@ warn mail text is null (Thread name=" + Thread.currentThread().getName() + ") @@@ " + mailBean.getSubject());
			return null;
		}
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
		// messageHelper.setFrom(mailBean.getFrom());
		try {
			messageHelper.setFrom(mailBean.getFrom(), mailBean.getFromName());
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		messageHelper.setSubject(mailBean.getSubject());
		messageHelper.setTo(mailBean.getToEmails());
		messageHelper.setText(text, true); // html: true
		return msg;
	}

	/*
	 * 模板解析
	 * @param mailBean
	 * @return
	 */
	private String getMessage(MailBean mailBean) {
		StringWriter writer = null;
		try {
			writer = new StringWriter();
			VelocityContext context = new VelocityContext(mailBean.getData());
			velocityEngine.evaluate(context, writer, "", mailBean.getTemplate());
			return writer.toString();
		} catch (Exception e) {
			logger.error(" VelocityException : " + mailBean.getSubject() + "\n" + e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("###StringWriter close error ... ");
				}
			}
		}
		return null;
	}

	/*
	 * check 邮件
	 */
	private boolean checkMailBean(MailBean mailBean) {
		if (mailBean == null) {
			logger.warn("@@@ warn mailBean is null (Thread name=" + Thread.currentThread().getName() + ") ");
			return false;
		}
		if (mailBean.getSubject() == null) {
			logger.warn("@@@ warn mailBean.getSubject() is null (Thread name=" + Thread.currentThread().getName() + ") ");
			return false;
		}
		if (mailBean.getToEmails() == null) {
			logger.warn("@@@ warn mailBean.getToEmails() is null (Thread name=" + Thread.currentThread().getName() + ") ");
			return false;
		}
		if (mailBean.getTemplate() == null) {
			logger.warn("@@@ warn mailBean.getTemplate() is null (Thread name=" + Thread.currentThread().getName() + ") ");
			return false;
		}
		return true;
	}

	/* ===================== setter & getter ======================= */
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

}