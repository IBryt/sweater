package com.example.sweater.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSendler {
    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;

    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(username);
        mail.setTo(emailTo);
        mail.setSubject(subject);
        mail.setText(message);
        mailSender.send(mail);
    }
}
