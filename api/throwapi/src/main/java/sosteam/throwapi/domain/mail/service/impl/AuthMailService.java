package sosteam.throwapi.domain.mail.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.mail.exception.MailServiceUnavailableException;
import sosteam.throwapi.domain.mail.service.MailService;
import sosteam.throwapi.global.service.RandomStringService;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthMailService implements MailService {

    private final JavaMailSender javaMailSender;
    private String sendCode;

    @Override
    public MimeMessage createMail(String to) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress("public_chan@naver.com", "SosTeam Throw"));
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject("Throw 이메일 인증");
            String msgg = "";
            msgg += "<div style='margin:100px;'>";
            msgg += "<h1> 안녕하세요 Throw 인증 메일 입니다.</h1>";
            msgg += "<br>";
            msgg += "<p>아래 코드를 메일 인증 창으로 돌아가 입력해주세요<p>";
            msgg += "<br>";
            msgg += "<p>인증 코드 및 인증 유효 시간은 10분 입니다!<p>";
            msgg += "<br>";
            msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
            msgg += "<h3 style='color:blue;'>메일 인증 코드입니다.</h3>";
            msgg += "<div style='font-size:130%'>";
            msgg += "CODE : <strong>";
            msgg += sendCode + "</strong><div><br/> ";
            msgg += "</div>";
            message.setText(msgg, "utf-8", "html");
        } catch (MessagingException e) {
            log.debug("{}", e.getStackTrace());
            throw new MailServiceUnavailableException();
        } catch (UnsupportedEncodingException e) {
            log.debug("{}", e.getStackTrace());
            throw new MailServiceUnavailableException();
        }

        return message;
    }

    @Override
    public String sendMail(String to) {
        //10자리 랜덤 코드를 생성한 뒤 to 이메일로 전송하는 메시지를 만들고 전송한다.
        sendCode = RandomStringService.generateKey(6);
        MimeMessage message = createMail(to);

        javaMailSender.send(message);
        return sendCode;
    }
}
