package uz.uzumtech.notification.component.kafka.producer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import uz.uzumtech.notification.constant.enums.NotificationType;
import uz.uzumtech.notification.dto.NotificationDto;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationProducer {

    KafkaTemplate<String, NotificationDto> notificationTemplate;

    public void sendMessage(final NotificationDto payload, NotificationType type) {
        String topic = switch (type) {
            case SMS -> "notification.sms";
            case EMAIL -> "notification.email";
            case PUSH -> "notification.push";
        };

        final Message<NotificationDto> message = MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.KEY, payload.key())
                .setHeader(KafkaHeaders.CORRELATION_ID, payload.correlationId())
                .build();

        notificationTemplate.send(message);
    }
}
