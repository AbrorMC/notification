package uz.uzumtech.notification.component.kafka.consumer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import uz.uzumtech.notification.component.kafka.consumer.handler.NotificationHandler;
import uz.uzumtech.notification.dto.NotificationDto;
import uz.uzumtech.notification.dto.response.SendingResponseDto;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationConsumer {

    Map<String, NotificationHandler> handlers;

    @KafkaListener(
            topics = "notificationTopic",
            groupId = "notification-consumer-group",
            containerFactory = "notificationFactory"
    )
    public void listen(@Payload NotificationDto payload,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String keyHeader,
                       Acknowledgment ack) {
        String key = keyHeader.toLowerCase();

        NotificationHandler handler = handlers.get(key);

        try {
            handler.handle(payload);
            ack.acknowledge();
        } catch (Exception ex) {

        }
    }
}
