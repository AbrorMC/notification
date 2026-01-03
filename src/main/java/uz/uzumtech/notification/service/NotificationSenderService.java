package uz.uzumtech.notification.service;

import uz.uzumtech.notification.constant.enums.NotificationType;
import uz.uzumtech.notification.entity.NotificationEntity;

public interface NotificationSenderService {
    NotificationType getType();
    void send(NotificationEntity notification);
}
