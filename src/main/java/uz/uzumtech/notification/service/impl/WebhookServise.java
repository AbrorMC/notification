package uz.uzumtech.notification.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import uz.uzumtech.notification.dto.NotificationDto;
import uz.uzumtech.notification.entity.NotificationEntity;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebhookServise {

    RestClient restClient;

    @Async("taskExecutor")
    public void sendCallback(NotificationEntity notification) {
        try {
            restClient.post()
                    .uri("https://webhook.site/726d69d9-e960-4523-b317-725b7b0da649")
                    .body(notification)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception ex) {
            log.error("Webhook failed for ID {}", notification.getId());
        }
    }
}
