package uz.uzumtech.notification.service.impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import uz.uzumtech.notification.constant.enums.NotificationStatus;
import uz.uzumtech.notification.entity.NotificationEntity;
import uz.uzumtech.notification.mapper.NotificationMapper;
import uz.uzumtech.notification.repository.NotificationRepository;
import uz.uzumtech.notification.service.NotificationSenderService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Async("taskExecutor")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationProcessService {

    NotificationRepository notificationRepository;
    List<NotificationSenderService> senderServices;
    WebhookServise webhookServise;
    NotificationMapper mapper;

    @Transactional
    public void processNotification(Long id) {

        var notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification with id:" + id + " not found"));

        var senderService = senderServices.stream()
                .filter(s -> s.getType() == notification.getType())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown type"));

        try {
            senderService.send(notification);
            notification.setStatus(NotificationStatus.SENT);
            notificationRepository.save(notification);
            webhookServise.sendCallback(notification);
        } catch (Exception ex) {
            throw ex;
        }

    }


    @Transactional
    public void processFailedNotification(Long id) {

        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification with id:" + id + " not found"));

        notification.setStatus(NotificationStatus.FAILED);
//        notificationRepository.findById(id).ifPresent(webhookServise::sendCallback);

    }
}
