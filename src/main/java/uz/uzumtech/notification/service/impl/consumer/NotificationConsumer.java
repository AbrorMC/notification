package uz.uzumtech.notification.service.impl.consumer;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import uz.uzumtech.notification.constant.enums.NotificationStatus;
import uz.uzumtech.notification.dto.NotificationDto;
import uz.uzumtech.notification.entity.NotificationEntity;
import uz.uzumtech.notification.repository.NotificationRepository;
import uz.uzumtech.notification.service.NotificationSenderService;
import uz.uzumtech.notification.service.impl.WebhookServise;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationConsumer {

    NotificationRepository notificationRepository;
    List<NotificationSenderService> senders;
    WebhookServise webhookServise;

    @KafkaListener(topics = "$kafka.topic.notificationTopic")
    public void consume(NotificationDto dto) {
        Long id = Long.parseLong(dto.message());
        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification with id:" + id + " not found"));

        NotificationSenderService sender = senders.stream()
                .filter(s -> s.getType() == notification.getType())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown type"));

        try {
            sender.send(notification);
            updateStatus(id, NotificationStatus.SENT);
            notification.setStatus(NotificationStatus.SENT);
            webhookServise.sendCallback(notification);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void updateStatus(Long id, NotificationStatus status) {
        notificationRepository.findById(id).ifPresent(n -> n.setStatus(status));
    }

    @DltHandler
    public void handleDtl(NotificationDto dto) {
        Long id = Long.parseLong(dto.message());
        updateStatus(id, NotificationStatus.FAILED);
        notificationRepository.findById(id).ifPresent(webhookServise::sendCallback);
    }
}
