package com.base.rest.service;

public interface EmailService {

    boolean sendEmail(String to, String subject, String content);

}
