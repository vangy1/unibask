package sk.unibask.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sk.unibask.data.model.Account;

@Service
public class MailService {
    private final JavaMailSender emailSender;

    @Value("${web.url}")
    private String webUrl;

    public MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendVerificationCode(String mail, String verificationCode) {
        var message = new SimpleMailMessage();
        message.setFrom("UnibASK");
        message.setTo(mail);
        message.setSubject("Overovací kód");
        message.setText("Tvoj overovací kód je: " + verificationCode);
        new Thread(() -> emailSender.send(message)).start();
    }

    public void sendNotification(String title, Long questionId, Account account) {
        var message = new SimpleMailMessage();
        message.setFrom("UnibASK");
        message.setTo(account.getEmail());
        message.setSubject(title);
        message.setText(webUrl + "question?id=" + questionId);
        new Thread(() -> emailSender.send(message)).start();
    }
}
