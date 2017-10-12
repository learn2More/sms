package com.ppdai.ac.sms.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Created by wangxiaomei02 on 2017/5/16.
 * 发送企业内部邮件
 */
@Component
public class SendMailService {
    @Autowired
    private JavaMailSender mailSender;


    public SendMailService(){ }
    /**
     * @param addresser 收件人
     * @param recipients 收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return
     * @throws Exception
     */
    public String sendSimpleMail(String addresser,String recipients,String subject,String content) throws Exception {
        String receipt="";
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(addresser);
            message.setTo(recipients);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            receipt="success";
        } catch (MailException e) {
            receipt="failure";
            throw e;
        }
        return  receipt;
    }
}

