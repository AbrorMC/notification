package uz.uzumtech.notification.component.kafka.consumer.handler.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uz.uzumtech.notification.component.kafka.consumer.handler.NotificationHandler;
import uz.uzumtech.notification.dto.NotificationDto;
import uz.uzumtech.notification.dto.response.SendingResponseDto;

@Slf4j
@Component("sms")
public class SmsNotificationHandler implements NotificationHandler {

    @Override
    public String key() {
        return "sms";
    }

    @Override
    public void handle(NotificationDto payload) {
        // TODO: реализация отправки SMS
        log.info("Handle SMS notification: {}", payload);    }
}
