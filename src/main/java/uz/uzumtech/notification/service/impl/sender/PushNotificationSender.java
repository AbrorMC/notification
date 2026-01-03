package uz.uzumtech.notification.service.impl.sender;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import uz.uzumtech.notification.constant.enums.NotificationType;
import uz.uzumtech.notification.entity.NotificationEntity;
import uz.uzumtech.notification.service.NotificationSenderService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PushNotificationSender implements NotificationSenderService {

    FirebaseMessaging firebaseMessaging;

    @Override
    public NotificationType getType() {
        return NotificationType.PUSH;
    }

    @Override
    public void send(NotificationEntity notification) {
        Message message = Message.builder()
                        .setToken(notification.getReceiverInfo().value())
                        .setNotification(Notification.builder()
                                .setTitle("Notification")
                                .setBody(notification.getText())
                                .build()
                        )
                        .putData("merchantId", notification.getMerchant().getId().toString())
                        .putData("id", notification.getId().toString())
                        .build();

        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("Firebase Error", e);
        }
    }
}
