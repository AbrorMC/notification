package uz.uzumtech.notification.service.impl.consumer;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import uz.uzumtech.notification.constant.enums.NotificationStatus;
import uz.uzumtech.notification.dto.NotificationDto;
import uz.uzumtech.notification.entity.NotificationEntity;
import uz.uzumtech.notification.repository.NotificationRepository;
import uz.uzumtech.notification.service.NotificationSenderService;
import uz.uzumtech.notification.service.impl.WebhookServise;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationConsumer {

    NotificationRepository notificationRepository;
    List<NotificationSenderService> senderServices;
    WebhookServise webhookServise;

    @KafkaListener(topics = "notification.sms", groupId = "sms-group", containerFactory = "notificationFactory")
    public void consumeSms(NotificationDto dto) {

        Long id = Long.parseLong(dto.message());

        processNotification(id);

    }

    @KafkaListener(topics = "notification.email", groupId = "email-group", containerFactory = "notificationFactory")
    public void consumeEmail(NotificationDto dto) {

        Long id = Long.parseLong(dto.message());

        processNotification(id);

    }

    @KafkaListener(topics = "notification.push", groupId = "push-group", containerFactory = "notificationFactory")
    public void consumePush(NotificationDto dto) {

        Long id = Long.parseLong(dto.message());

        processNotification(id);

    }

    @Transactional
    public void processNotification(Long id) {

        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification with id:" + id + " not found"));

        NotificationSenderService senderService = senderServices.stream()
                .filter(s -> s.getType() == notification.getType())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown type"));

        try {
            senderService.send(notification);
            notification.setStatus(NotificationStatus.SENT);

            webhookServise.sendCallback(notification);
        } catch (Exception ex) {
            throw ex;
        }

    }

    @DltHandler
    public void handleDtl(NotificationDto dto) {

        Long id = Long.parseLong(dto.message());

        processFailedNotification(id);

    }

    @Transactional
    public void processFailedNotification(Long id) {

        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification with id:" + id + " not found"));

        notification.setStatus(NotificationStatus.FAILED);

        notificationRepository.findById(id).ifPresent(webhookServise::sendCallback);

    }
}
