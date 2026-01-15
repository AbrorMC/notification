package uz.uzumtech.notification.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import uz.uzumtech.notification.dto.NotificationDto;
import uz.uzumtech.notification.dto.WebhookDto;
import uz.uzumtech.notification.entity.NotificationEntity;
import uz.uzumtech.notification.mapper.NotificationMapper;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebhookServise {

    RestClient restClient;
    NotificationMapper mapper;

    @Async("taskExecutor")
    public void sendCallback(NotificationEntity notification) {

        String webhookUri = notification.getMerchant().getWebhook();
        var webhookDto = mapper.toWebhookDto(notification);

        try {
            restClient.post()
                    .uri(webhookUri)
                    .body(webhookDto)
                    .retrieve()
                    .toBodilessEntity();
            log.info("Webhook {} sent successfully to {}", webhookDto, webhookUri);
        } catch (Exception ex) {
            log.error("Webhook failed for ID {}. Reason: {}", notification.getId(), ex.getMessage());
        }
    }
}
