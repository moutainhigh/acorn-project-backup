package com.chinadrtv.expeditingmail.service.impl;

import com.chinadrtv.expeditingmail.service.AutoEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-18
 * Time: 上午10:52
 * To change this template use File | Settings | File Templates.
 */
@Service("AutoEmailService")
public class AutoEmailServiceImpl implements AutoEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 正常邮件
     * @param title
     * @param text
     * @param deliver
     * @param from
     * @param filename
     * @param bufferBytes
     */
    public void sendMail(String title, String text, String deliver[],String cDeliver[], String from, String filename, byte[] bufferBytes) throws Exception{
        if(javaMailSender != null)
        {
            try
            {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();

                //注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
                //multipart模式 为true时发送附件 可以设置html格式
                MimeMessageHelper msg = new MimeMessageHelper(mimeMessage,true,"utf-8");

                msg.setFrom(from);
                msg.setReplyTo(from);
                msg.setTo(deliver);
                msg.setSubject(title);
                msg.setText(text);
                msg.setCc(cDeliver);
                //FileSystemResource file = new FileSystemResource(new File(fileurl));
                //msg.addAttachment(filename,file);
                msg.addAttachment(MimeUtility.encodeWord(filename), new ByteArrayResource(bufferBytes),"application/vnd.ms-excel");
                javaMailSender.send(mimeMessage);
            }
            catch (Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }

    /**
     * 承运商邮件发送失败提醒
     * @param title
     * @param text
     * @param deliver
     * @param from
     */
    public void sendRemindMail(String title, String text, String deliver, String from) throws Exception{
        if(javaMailSender != null)
        {
            try
            {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();

                //注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
                //multipart模式 为true时发送附件 可以设置html格式
                MimeMessageHelper msg = new MimeMessageHelper(mimeMessage,true,"utf-8");

                msg.setFrom(from);
                msg.setReplyTo(from);
                msg.setTo(deliver);
                msg.setSubject(title);
                msg.setText(text);
                javaMailSender.send(mimeMessage);
            }
            catch (Exception ex)
            {
                throw new Exception(ex);
            }
        }
    }
}
