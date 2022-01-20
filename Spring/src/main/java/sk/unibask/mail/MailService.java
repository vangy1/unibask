package sk.unibask.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendVerificationCode(String mail, String verificationCode) {
        var message = new SimpleMailMessage();
        message.setFrom("UnibASK");
        message.setTo(mail);
        message.setSubject("Overovací kód");
        message.setText("Tvoj overovací kód je: " + verificationCode);
        emailSender.send(message);
    }
}
