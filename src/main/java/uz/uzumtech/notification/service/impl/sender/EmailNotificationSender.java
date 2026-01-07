package uz.uzumtech.notification.service.impl.sender;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.uzumtech.notification.constant.enums.NotificationType;
import uz.uzumtech.notification.entity.NotificationEntity;
import uz.uzumtech.notification.service.NotificationSenderService;

@Service
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailNotificationSender implements NotificationSenderService {

    private final JavaMailSender mailSender;

    public EmailNotificationSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }

    @Override
    public void send(NotificationEntity notification) {
        var message = new SimpleMailMessage();
        message.setTo(notification.getReceiverInfo().value());
        message.setText(notification.getText());

        mailSender.send(message);
    }
}
