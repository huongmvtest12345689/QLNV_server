package co.jp.api.service;

import co.jp.api.model.EmailDTO;

public interface MailApiService {
    void sendMail(EmailDTO emailDto);
}
