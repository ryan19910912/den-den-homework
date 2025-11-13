package com.base.rest.service.impl;

import com.base.rest.handel.ApiException;
import com.base.rest.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Override
    public boolean sendEmail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(mailProperties.getUsername()); // 發送者
        message.setTo(to); // 收件人
        message.setSubject(subject); // 主題
        message.setText(content); // 內容

        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            throw new ApiException("郵件發送失敗: " + e.getMessage());
        }
    }
}
