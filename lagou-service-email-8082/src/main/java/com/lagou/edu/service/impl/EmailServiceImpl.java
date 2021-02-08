package com.lagou.edu.service.impl;


import com.lagou.edu.service.EmailService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    /**
     * 配置文件中我的qq邮箱
     *
     * */

    @Value("${mail.from}")
    private String from;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.port}")
    private String port;


    @Override
    public boolean sendEmail(String to,String code) {
        try {
            //set mail sender properties
            String host = "smtp.gmail.com";
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "false");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", password);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);
            String[] tos = { to };
            try {
                message.setFrom(new InternetAddress(from));
                InternetAddress[] toAddress = new InternetAddress[tos.length];

                // To get the array of addresses
                for( int i = 0; i < tos.length; i++ ) {
                    toAddress[i] = new InternetAddress(tos[i]);
                }

                for( int i = 0; i < toAddress.length; i++) {
                    message.addRecipient(Message.RecipientType.TO, toAddress[i]);
                }

                message.setSubject("auth code");
                message.setText(code);
                Transport transport = session.getTransport("smtp");
                transport.connect(host, from, password);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                return true;
            }
            catch (AddressException ae) {
                ae.printStackTrace();
            }
            catch (MessagingException me) {
                me.printStackTrace();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
