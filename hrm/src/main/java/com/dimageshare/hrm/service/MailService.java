package com.dimageshare.hrm.service;

public interface MailService {
    void sendMailRemindPassword(String email, String password);
}
