package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * This service class is responsible for sending emails
 */
@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    /**
     * Constructor for auto wiring Java Mail Sender
     * @param javaMailSender
     */
    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    /**
     * Sends the MimeMessage by using Java Mail Sender
     * @param email the email that is sent to the user
     */
    @Async
    public void sendEmail(MimeMessage email){
        javaMailSender.send(email);
    }

}
