package uz.uzumtech.notification.component.kafka.producer;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import uz.uzumtech.notification.configuration.props.KafkaProps;
import uz.uzumtech.notification.dto.NotificationDto;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationProducer {

    KafkaProps kafkaProps;
    KafkaTemplate<String, NotificationDto> notificationTemplate;

    public NotificationProducer(KafkaProps kafkaProps,
                                @Qualifier("notificationTopic")
                                KafkaTemplate<String, NotificationDto> notificationTemplate) {
        this.kafkaProps = kafkaProps;
        this.notificationTemplate = notificationTemplate;
    }

    public void sendMessage(final NotificationDto payload) {
        final Message<NotificationDto> message = MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, kafkaProps.getTopic().getNotificationTopic())
                .setHeader(KafkaHeaders.KEY, payload.key())
                .setHeader(KafkaHeaders.CORRELATION_ID, payload.correlationId())
                .build();

        notificationTemplate.send(message);
    }
}
