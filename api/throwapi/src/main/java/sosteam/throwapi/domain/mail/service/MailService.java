package sosteam.throwapi.domain.mail.service;

import jakarta.mail.internet.MimeMessage;

public interface MailService {

    MimeMessage createMail(String to);

    String sendMail(String to);
}
