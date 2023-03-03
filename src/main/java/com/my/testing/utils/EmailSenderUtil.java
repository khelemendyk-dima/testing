package com.my.testing.utils;

import org.apache.logging.log4j.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailSenderUtil {
    private static final Logger logger = LogManager.getLogger(EmailSenderUtil.class);
    private final String user;
    private final Session session;

    public EmailSenderUtil(Properties properties) {
        user = properties.getProperty("mail.user");
        session = getSession(properties, user);
    }

    public void send(String subject, String body, String sendTo) {
        MimeMessage message = new MimeMessage(session);
        try {
            sendEmail(subject, body, sendTo, message);
            logger.info(String.format("Email was send to %s", sendTo));
        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.error(String.format("Email wasn't send to %s because of %s", sendTo, e.getMessage()));
        }
    }

    private void sendEmail(String subject, String body, String sendTo, MimeMessage message) throws MessagingException, UnsupportedEncodingException {
        message.setFrom(new InternetAddress(user, "TestHub"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(body, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

    private Session getSession(Properties properties, String user) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, properties.getProperty("mail.password"));
            }
        });
    }
}
