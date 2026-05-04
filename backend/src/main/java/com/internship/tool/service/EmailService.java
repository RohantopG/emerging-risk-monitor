package com.internship.tool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRiskCreatedEmail(String to, String riskTitle) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("New Risk Created");
        message.setText("Risk '" + riskTitle + "' has been created.");

        mailSender.send(message);
    }

    public void sendOverdueEmail(String to, String riskTitle) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Risk Overdue Alert");
        message.setText("Risk '" + riskTitle + "' is overdue!");

        mailSender.send(message);
    }
}