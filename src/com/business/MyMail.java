/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.business;

import com.entity.MailMessage;
import com.entity.SMTPServer;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author leanh
 */
public class MyMail {

    public static Session getMailSession(SMTPServer mailServer, String from, String password) {
        Properties props = new Properties();
        props.put("mail.smtp.host", mailServer.getServer());

        if (mailServer.getAuthentication().equalsIgnoreCase("ssl")) {
            props.put("mail.smtp.ssl.enable", "true");
        }

        if (mailServer.getAuthentication().equalsIgnoreCase("starttls")) {
            props.put("mail.smtp.starttls.enable", "true");
        }

        props.put("mail.smtp.socketFactory.port", mailServer.getPort());
        props.put("mail.smtp.auth", "true");
        // Get the Session object
        Session session = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password); 
            }

        });
        return session;
    }

    public static boolean sendEmail(MailMessage mm, Session session) throws AddressException, MessagingException, IOException {
        // Create default message object
        Message message = new MimeMessage(session);
        // set from
        message.setFrom(new InternetAddress(mm.getFrom()));
        // Set to
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mm.getTo()));
        // Set subject
        message.setSubject(mm.getSubject());
        // Set message and attachment file 
        Multipart multipart = new MimeMultipart();
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(mm.getMessage());
        multipart.addBodyPart(messageBodyPart);
        
        if (mm.getAttachment() != null) {
            MimeBodyPart attackFile = new MimeBodyPart();
            attackFile.attachFile(mm.getAttachment());
            multipart.addBodyPart(attackFile);
        }
        message.setContent(multipart);
        // Send Message
        Transport.send(message);
        return true;
    }
}
