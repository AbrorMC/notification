package uz.uzumtech.notification.service.impl.sender;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    @NonFinal
    @Value("${spring.sms-provider.token}")
    String token;

    @NonFinal
    @Value("${spring.sms-provider.url}")
    String uri;

    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }

    @Override
    public void send(NotificationEntity notification) {

        String webhookUri = notification.getMerchant().getWebhook();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("mobile_phone", notification.getReceiverInfo().value());
        formData.add("from", "s-mara@internet.ru");
        formData.add("message", notification.getText());
        formData.add("callback_url", webhookUri);

        restClient.post()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        (req, res) -> {
                            throw new RuntimeException("Sms provider error");
                        })
                .toBodilessEntity();
    }
}
