package uz.uzumtech.notification.configuration.kafka;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import uz.uzumtech.notification.configuration.props.KafkaProps;
import uz.uzumtech.notification.dto.NotificationDto;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopicConfiguration {

    KafkaProps props;

    @Bean
    public NewTopic smsTopic() {
        KafkaProps.Topics.Topic smsTopic = props.getTopics().getSms();
        return TopicBuilder.name(smsTopic.getName())
                .partitions(smsTopic.getPartitions())
                .replicas(smsTopic.getReplicas())
                .build();
    }

    @Bean
    public NewTopic emailTopic() {
        KafkaProps.Topics.Topic emailTopic = props.getTopics().getEmail();
        return TopicBuilder.name(emailTopic.getName())
                .partitions(emailTopic.getPartitions())
                .replicas(emailTopic.getReplicas())
                .build();
    }

    @Bean
    public NewTopic pushTopic() {
        KafkaProps.Topics.Topic pushTopic = props.getTopics().getPush();
        return TopicBuilder.name(pushTopic.getName())
                .partitions(pushTopic.getPartitions())
                .replicas(pushTopic.getReplicas())
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

        List<String> topics = List.of(
                props.getTopics().getEmail().getName(),
                props.getTopics().getSms().getName(),
                props.getTopics().getPush().getName()
                );

        return RetryTopicConfigurationBuilder.newInstance()
                .maxAttempts(3)
                .fixedBackOff(5000)
                .includeTopics(topics)
                .notRetryOn(IllegalArgumentException.class)
                .dltHandlerMethod("notificationConsumer", "handleDtl")
                .create(template);
    }
}
