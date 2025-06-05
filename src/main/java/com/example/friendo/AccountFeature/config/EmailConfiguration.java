package com.example.friendo.AccountFeature.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {
    @Value("${spring.mail.username}")
    private String emailusername;
    @Value("${spring.mail.password}")
    private String emailpass;

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailsender = new JavaMailSenderImpl();
        mailsender.setHost("smtp.gmail.com");
        mailsender.setPort(587);
        mailsender.setUsername(emailusername);
        mailsender.setPassword(emailpass);

        Properties props = mailsender.getJavaMailProperties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailsender;
    }
}
