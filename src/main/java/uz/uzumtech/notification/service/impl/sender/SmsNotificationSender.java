package uz.uzumtech.notification.service.impl.sender;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import uz.uzumtech.notification.constant.enums.NotificationType;
import uz.uzumtech.notification.entity.NotificationEntity;
import uz.uzumtech.notification.service.NotificationSenderService;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SmsNotificationSender implements NotificationSenderService {

    RestClient restClient;

    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }

    @Override
    public void send(NotificationEntity notification) {
        restClient.post()
                .uri("http://198.163.207.66:8083")
                .body(Map.of(
                        "to", notification.getReceiverInfo().value(),
                        "text", notification.getText()
                ))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        (req, res) -> {
                            throw new RuntimeException("Sms provider error");
                        })
                .toBodilessEntity();
    }
}
