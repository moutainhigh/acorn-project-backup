package com.chinadrtv.expeditingmail.service;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-18
 * Time: 上午10:50
 * To change this template use File | Settings | File Templates.
 */
public interface AutoEmailService {
    //邮件发送
    void sendMail(String title, String text, String deliver[], String cDeliver[], String from, String filename, byte[] bufferBytes) throws Exception;
    //邮件发送失败提醒
    void sendRemindMail(String title, String text, String deliver, String from) throws Exception;

}
