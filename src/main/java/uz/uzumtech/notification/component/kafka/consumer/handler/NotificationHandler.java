package uz.uzumtech.notification.component.kafka.consumer.handler;

import uz.uzumtech.notification.dto.NotificationDto;

public interface NotificationHandler {
    String key();
    void handle(NotificationDto payload);
}
