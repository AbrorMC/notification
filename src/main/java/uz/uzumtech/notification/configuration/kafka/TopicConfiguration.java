package uz.uzumtech.notification.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

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
}
