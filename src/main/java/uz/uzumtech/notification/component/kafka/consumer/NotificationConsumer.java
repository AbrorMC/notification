package uz.uzumtech.notification.component.kafka.consumer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import uz.uzumtech.notification.dto.NotificationDto;
import uz.uzumtech.notification.service.impl.NotificationProcessService;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationConsumer {

    NotificationProcessService processService;

    @KafkaListener(topics = "notification.sms", groupId = "sms-group", containerFactory = "notificationFactory")
    public void consumeSms(NotificationDto dto) {

        Long id = Long.parseLong(dto.message());
        processService.processNotification(id);

    }

    @KafkaListener(topics = "notification.email", groupId = "email-group", containerFactory = "notificationFactory")
    public void consumeEmail(NotificationDto dto) {

        Long id = Long.parseLong(dto.message());
        processService.processNotification(id);

    }

    @KafkaListener(topics = "notification.push", groupId = "push-group", containerFactory = "notificationFactory")
    public void consumePush(NotificationDto dto) {

        Long id = Long.parseLong(dto.message());
        processService.processNotification(id);

    }

    @DltHandler
    public void handleDtl(NotificationDto dto) {

        Long id = Long.parseLong(dto.message());
        processService.processFailedNotification(id);

    }
}
