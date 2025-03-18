package com.example.addressbook.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    // Inject the sender email from the application properties file
    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setText(body);
            simpleMailMessage.setSubject(subject);

            mailSender.send(simpleMailMessage);
            System.out.println("Mail sent to the user!!");
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
