package uz.uzumtech.notification.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import uz.uzumtech.notification.dto.NotificationDto;

@Configuration
public class TopicConfiguration {

    @Bean
    public NewTopic notificationNewTopic() {
        return TopicBuilder.name("notificationTopic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic dlqNewTopic() {
        return TopicBuilder.name("dlqTopic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public RetryTopicConfiguration retryTopic(KafkaTemplate<String, NotificationDto> template) {
        return RetryTopicConfigurationBuilder.newInstance()
                .maxAttempts(3)
                .fixedBackOff(5000)
                .includeTopic("${kafka.topic.notification-topic}")
                .notRetryOn(IllegalArgumentException.class)
                .dltHandlerMethod("notificationConsumer", "handleDtl")
                .create(template);
    }
}
